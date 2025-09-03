# Spring Batch

![image.png](Spring%20Batch%2014cd0061bdb680d0947fee23af56ce84/image.png)
<img width="1872" height="886" alt="image" src="https://github.com/user-attachments/assets/62311ec2-593c-46e0-b70b-8671d0c6c691" />

Spring Batch Using own tables for job details.

```java
@Data
@Builder
public class Product {
    private Integer id;
    private String name;
    private String category;
    private BigDecimal price;
    private String stockDate;
    private String endDate;
    private String priceUpdateDate
    private Integer stock;
}
```

![image.png](Spring%20Batch%2014cd0061bdb680d0947fee23af56ce84/image%201.png)

Let’s create a batch job for product price updating. 

There is 800 product in the input file. 

<aside>
💡

> Spring Batch 5.0 required custom coded  data source and database configuration.
> 
</aside>

### Use Case: Increase all product prices by 100 EUR 😊 and update the price update date.

The system will update the price update date field when processing each product.

product.csv input example data.

```
ID,Name,Category,Price,Stock Date,End Date,Price Update Date,Stock
1,Soda,Beverages,**32.39,**2024-06-29,2024-09-11,2024-08-14,200
2,Pork Chop,Meat & Seafood,27.72,2024-09-13,2025-05-10,2025-04-02,477
3,Yogurt,Dairy & Eggs,33.47,2024-04-29,2024-06-19,2024-05-28,947
4,Laundry Detergent,Household Supplies,27.14,2024-11-20,2025-07-06,2025-06-09,28
5,Chicken Breast,Meat & Seafood,35.39,2024-09-23,2025-08-16,2025-03-11,420
```

updatedProducts.csv - output example data

```
1,Soda,Beverages,**132.39**,2024-06-29,2024-09-11,2024-11-28,200
2,Pork Chop,Meat & Seafood,127.71,2024-09-13,2025-05-10,2024-11-28,477
3,Yogurt,Dairy & Eggs,133.46,2024-04-29,2024-06-19,2024-11-28,947
4,Laundry Detergent,Household Supplies,127.14,2024-11-20,2025-07-06,2024-11-28,2
5,Chicken Breast,Meat & Seafood,135.38,2024-09-23,2025-08-16,2024-11-28,420
```

Data Source Configuration Bean

```java
@Bean
public DataSource dataSource(@Value("${db.driverClassName}") String driverClaam,
                             @Value("${db.url}") String url,
                             @Value("${db.username}") String username,
                             @Value("${db.password}") String password) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
}
```

Transaction Manager for Spring Batch

```java
@Bean
public PlatformTransactionManager transactionManager(DataSource dataSource) {
    JdbcTransactionManager transactionManager = new JdbcTransactionManager();
    transactionManager.setDataSource(dataSource);
    return transactionManager;
}
```

Spring Batch Beans

```java
@Bean
public BatchDataSourceScriptDatabaseInitializer batchDataSourceInitializer(DataSource dataSource, BatchProperties properties) {
    return new BatchDataSourceScriptDatabaseInitializer(dataSource, properties.getJdbc());
}

@Bean
public BatchProperties batchProperties(@Value("${batch.db.initialize-schema}") DatabaseInitializationMode initializationMode) {
    BatchProperties properties = new BatchProperties();
    properties.getJdbc().setInitializeSchema(initializationMode);
    return properties;
}
```

### SPRING BATCH JOB- productUpdateJob

```java
@Bean
public Job productUpdateJob(JobRepository jobRepository,@Qualifier("priceUpdateStep") Step priceUpdateStep){
    return new JobBuilder("PRODUCT-UPDATE-JOB", jobRepository)
            .start(priceUpdateStep)
            .build();
}
```

### SPRING BATCH JOB STEP- productUpdateStep

```java
@Bean(name = "priceUpdateStep")
public Step productPriceUpdateStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
   return new StepBuilder("PRODUCT-PRICE-UPDATE-STEP",jobRepository)
            .<Product,Product>chunk(1,transactionManager)
            .reader(new FlatFileItemReaderBuilder<Product>()
                    .name("PRODUCT-PRICE-UPDATE-READER")
                    .resource(productsInputResource)
                    .linesToSkip(1)
                    .lineMapper(productLineMapper)
                            .build()
                    )
           .processor(new ProductPriceUpdateProcessor())
           .writer(new FlatFileItemWriterBuilder<Product>()
                   .name("PRODUCT-PRICE-UPDATE-WRITER")
                   .resource(updatedProductsPricesResource)
                   .lineAggregator(getLineAggregrator())
                   .build())
            .build();
}
```

```java
private DelimitedLineAggregator<Product> getLineAggregrator() {
    DelimitedLineAggregator delimitedLineAggregator = 
																    new DelimitedLineAggregator();
    delimitedLineAggregator.setDelimiter(",");
    delimitedLineAggregator.setFieldExtractor(
    productBeanWrapperFieldExtractor());
    return delimitedLineAggregator;
}

private BeanWrapperFieldExtractor<Product> productBeanWrapperFieldExtractor(){
    List<Field> fields = Arrays
												    .stream(Product.class.getDeclaredFields())
												    .toList();
    List<String> namesL = fields.stream().map(Field::getName).toList();
    String[] beanFields = namesL.toArray(new String[0]);

    BeanWrapperFieldExtractor<Product> extractor = 
														     new BeanWrapperFieldExtractor<>();
    extractor.setNames(beanFields);
    extractor.afterPropertiesSet();
    return extractor;
}
```

```java
@Component
public class ProductLineMapper implements LineMapper<Product> {

    @Override
    public Product mapLine(String line, int lineNumber) throws Exception {
        String[] parsedLine = line.split(",");
        //try objectmapper here
        return Product.builder()
                .id(Integer.valueOf(parsedLine[0]))
                .name(parsedLine[1])
                .category(parsedLine[2])
                .price(new BigDecimal(parsedLine[3]).setScale(2))
                .stockDate(parsedLine[4])
                .endDate(parsedLine[5])
                .priceUpdateDate(parsedLine[6])
                .stock(Integer.valueOf(parsedLine[7]))
                .build();
    }
}
```

### SPRING BATCH PROCESSOR - priceUpdateProcessor

```java
public class ProductPriceUpdateProcessor implements
			 ItemProcessor<Product,Product> {
    private SimpleDateFormat dateFormat =  new SimpleDateFormat ("YYYY-MM-dd");
    @Override
    public Product process(Product item) throws Exception {
        item.setPrice( new BigDecimal(item.getPrice().doubleValue() + 100.0 )
			        .setScale(2,1));
        item.setPriceUpdateDate(dateFormat.format(new Date()));
        System.out.println("ITEM -> " + item.getId() + " -> " + item);
        return item;
    }
}
```



# Spring Batch with JPA: Full Implementation

## 1. Entity for `User`

The `User` entity represents the `User` table in the database.

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String email;
    private String status;
}
```

## 2. Repository Interface for `User`

The repository provides the `findByStatus` method to fetch users by their status.

```java
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByStatus(String status, Pageable pageable);
}
```

## 3. DTO for `UserDTO` (optional)

If you need to transform the `User` entity, you can create a DTO class.

```java
public class UserDTO {
    private Long id;
    private String name;
    private String email;

    public UserDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters and setters
}
```

## 4. Batch Job Configuration

### Reader Bean: `JpaPagingItemReader`

The `JpaPagingItemReader` fetches data from the database using pagination.

```java
@Bean
@StepScope
public JpaPagingItemReader<User> reader(UserRepository userRepository, @Value("#{jobParameters['status']}") String status) {
    JpaPagingItemReader<User> reader = new JpaPagingItemReader<>();
    reader.setRepository(userRepository);  // Set the JPA repository
    reader.setPageSize(10);  // Set page size (chunk size in Spring Batch)
    reader.setQueryString("findByStatus");  // Automatically map to repository method
    return reader;
}
```

### Processor Bean: `ItemProcessor`

The `ItemProcessor` transforms the `User` entity into a `UserDTO`.

```java
@Bean
public ItemProcessor<User, UserDTO> processor() {
    return user -> new UserDTO(user.getId(), user.getName(), user.getEmail());  // Convert to DTO
}
```

### Writer Bean: `JpaItemWriter`

The `JpaItemWriter` writes the `UserDTO` to the database. For simplicity, assume we are writing to a different table or handling it directly.

```java
@Bean
public JpaItemWriter<UserDTO> writer(EntityManagerFactory entityManagerFactory) {
    JpaItemWriter<UserDTO> writer = new JpaItemWriter<>();
    writer.setEntityManagerFactory(entityManagerFactory);
    return writer;
}
```

### Step Configuration

The step wires the reader, processor, and writer components together.

```java
@Bean
public Step step1(JpaPagingItemReader<User> reader, ItemProcessor<User, UserDTO> processor, JpaItemWriter<UserDTO> writer) {
    return stepBuilderFactory.get("step1")
            .<User, UserDTO>chunk(10)  // Define chunk size for batch processing
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
}
```

## 5. Job Configuration

The job is configured to use the step and dynamic job parameters.

```java
@Bean
public Job batchJob(JobBuilderFactory jobBuilderFactory, Step step1) {
    return jobBuilderFactory.get("batchJob")
            .start(step1)
            .build();
}

@Bean
public JobParameters jobParameters() {
    return new JobParametersBuilder()
            .addString("status", "active")  // Pass status dynamically
            .toJobParameters();
}
```

## 6. Main Application to Launch Job

The main application class launches the batch job with dynamic parameters.

```java
@SpringBootApplication
public class BatchApplication implements CommandLineRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job batchJob;

    @Override
    public void run(String... args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("status", "active")  // Set status dynamically when running the job
                .toJobParameters();
        
        jobLauncher.run(batchJob, jobParameters);  // Launch the job with parameters
    }

    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }
}
```

## 7. Final Summary

This implementation creates a **Spring Batch job** that:

1. Reads `User` data based on a dynamic **`status`** parameter.
2. **Transforms** the data (optional) into a `UserDTO` for further processing.
3. **Writes** the processed `UserDTO` data to the database using **`JpaItemWriter`**.
4. Passes the **status** as a **dynamic job parameter** when launching the job.

Everything is properly wired together, including:

- **`JpaPagingItemReader`** for reading paginated data.
- **`ItemProcessor`** for transforming data.
- **`JpaItemWriter`** for saving the processed data.
- **`JobParameters`** for dynamically passing the `status` parameter.

---

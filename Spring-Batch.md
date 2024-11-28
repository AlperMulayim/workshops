# Spring Batch

![image.png](Spring%20Batch%2014cd0061bdb680d0947fee23af56ce84/image.png)

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
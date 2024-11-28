package com.alper.supermarket.batchapi.productsbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Configuration
@PropertySource("classpath:db.properties")
public class ProductsBatchConfiguration  extends DefaultBatchConfiguration {

    @Value("classpath:products/products.csv")
    private Resource productsInputResource;

    @Value("file:products/updatedPricesProducts.csv")
    private WritableResource updatedProductsPricesResource;

    @Autowired
    private ProductLineMapper productLineMapper;


    @Bean
    public DataSource dataSource(@Value("${db.driverClassName}") String driverClassName,
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

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        JdbcTransactionManager transactionManager = new JdbcTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

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

    @Bean
    public Job productUpdateJob(JobRepository jobRepository,@Qualifier("priceUpdateStep") Step priceUpdateStep){
        return new JobBuilder("PRODUCT-UPDATE-JOB", jobRepository)
                .start(priceUpdateStep)
                .build();
    }

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

    private DelimitedLineAggregator<Product> getLineAggregrator() {
        DelimitedLineAggregator delimitedLineAggregator = new DelimitedLineAggregator();
        delimitedLineAggregator.setDelimiter(",");
        delimitedLineAggregator.setFieldExtractor(productBeanWrapperFieldExtractor());
        return delimitedLineAggregator;
    }

    private BeanWrapperFieldExtractor<Product> productBeanWrapperFieldExtractor(){
        List<Field> fields = Arrays.stream(Product.class.getDeclaredFields()).toList();
        List<String> namesL = fields.stream().map(Field::getName).toList();
        String[] beanFields = namesL.toArray(new String[0]);

        BeanWrapperFieldExtractor<Product> extractor =  new BeanWrapperFieldExtractor<>();
        extractor.setNames(beanFields);
        extractor.afterPropertiesSet();
        return extractor;
    }


}


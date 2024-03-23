 package me.bouzri.firstbatch.mongoBatch;

import lombok.RequiredArgsConstructor;
import me.bouzri.firstbatch.data.entities.Person;
import me.bouzri.firstbatch.data.repositories.PersonRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {

    private final JobRepository jobRepository;
    private final PersonRepository personRepository;
    private final PlatformTransactionManager platformTransactionManager;

     @Bean
     public FlatFileItemReader<Person> reader() {
         FlatFileItemReader<Person> itemReader = new FlatFileItemReader<>();
         itemReader.setResource(new FileSystemResource("src/main/resources/person-data.csv"));
         itemReader.setName("csvReader");
         itemReader.setLinesToSkip(1);
         itemReader.setLineMapper(lineMapper());
         return itemReader;
     }

     @Bean
     public RepositoryItemWriter<Person> writer() {
         RepositoryItemWriter<Person> writer = new RepositoryItemWriter<>();
         writer.setRepository(personRepository);
         writer.setMethodName("save");
         return writer;
     }

     @Bean
     public Step step1() {
         return new StepBuilder("csvImport", jobRepository)
                 .<Person, Person>chunk(50, platformTransactionManager)
                 .reader(reader())
                 .processor(processor())
                 .writer(writer())
                 .taskExecutor(taskExecutor())
                 .build();
     }

     @Bean
     public PersonProcessor processor() {
         return new PersonProcessor();
     }

     @Bean
     public Job runJob() {
         return new JobBuilder("importPersons", jobRepository)
                 .start(step1())
                 .build();

     }

     @Bean
     public TaskExecutor taskExecutor() {
         SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
         asyncTaskExecutor.setConcurrencyLimit(10);
         return asyncTaskExecutor;
     }

     private LineMapper<Person> lineMapper() {
         DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();

         DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
         lineTokenizer.setDelimiter(",");
         lineTokenizer.setStrict(false);
         lineTokenizer.setNames("id", "firstName", "lastName", "age");

         BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
         fieldSetMapper.setTargetType(Person.class);

         lineMapper.setLineTokenizer(lineTokenizer);
         lineMapper.setFieldSetMapper(fieldSetMapper);
         return lineMapper;
     }

}

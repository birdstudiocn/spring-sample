package cn.birdstudio.batch;

import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import cn.birdstudio.domain.User;

/**
 * 数据入库，主要包含三个部分:读数据、处理数据、写数据
 * 
 * @author Sam Zhang
 */
@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class BatchConfiguration {
	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	private static final Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

	/**
	 * 1.读数据
	 * 
	 * @return
	 */
	@Bean(name = "reader1")
	@StepScope
	public ItemReader<User> reader() {
		logger.info("read txt");
		ClassPathResource pathResource = new ClassPathResource("user.txt");
		FlatFileItemReader<User> reader = new FlatFileItemReader<>();
		reader.setResource(pathResource);
		reader.setLineMapper(new DefaultLineMapper<User>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer("|") {
					{
						setNames(new String[] { "name", "gender" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {
					{
						setTargetType(User.class);
					}
				});
			}
		});
		reader.open(new ExecutionContext());
		return reader;
	}

	/**
	 * 2.处理数据
	 * 
	 * @return
	 */
	@Bean(name = "processor1")
	@StepScope
	public SampleItemProcessor processor() {
		return new SampleItemProcessor();
	}

	/**
	 * 3.写数据
	 * 
	 * @param entityManagerFactory
	 * @return
	 */
	@Bean(name = "writer1")
	@StepScope
	public ItemWriter<User> writer(EntityManagerFactory entityManagerFactory) {
		logger.info("write data in database");
		JpaItemWriter<User> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
	}

	@Bean
	public Job importJob(@Qualifier("step1") Step s1, JobExecutionListener listener) {
		return jobs.get("importJob").incrementer(new RunIdIncrementer()).listener(listener).flow(s1).end().build();
	}

	@Bean
	public Step step1(@Qualifier("reader1") ItemReader<User> reader, @Qualifier("writer1") ItemWriter<User> writer,
			@Qualifier("processor1") ItemProcessor<User, User> processor, JobExecutionListener listener) {
		return steps.get("step1").<User, User>chunk(10).reader(reader).processor(processor).writer(writer).build();

	}

}

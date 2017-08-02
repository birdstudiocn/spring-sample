package cn.birdstudio.transaction.domain;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackageClasses = Transaction.class, entityManagerFactoryRef = "transactionEntityManagerFactory", transactionManagerRef = "transactionManager")
class TransactionDataSourceConfiguration {
	@Bean
	@ConfigurationProperties("app.datasource.transaction")
	DataSourceProperties transactionDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("app.datasource.transaction")
	DataSource transactionDataSource() {
		return transactionDataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean
	LocalContainerEntityManagerFactoryBean transactionEntityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(false);
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(Transaction.class.getPackage().getName());
		factory.setDataSource(transactionDataSource());
		factory.setPersistenceUnitName("transaction");
		return factory;
	}

	@Bean
	PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(transactionEntityManagerFactory().getObject());
		return txManager;
	}
}

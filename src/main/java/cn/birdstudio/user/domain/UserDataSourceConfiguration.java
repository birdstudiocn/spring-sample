package cn.birdstudio.user.domain;

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
@EnableJpaRepositories(basePackageClasses = User.class, entityManagerFactoryRef = "userEntityManagerFactory", transactionManagerRef = "userTransactionManager")
class UserDataSourceConfiguration {
	@Bean

	@ConfigurationProperties("app.datasource.user")
	DataSourceProperties userDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean

	@ConfigurationProperties("app.datasource.user")
	DataSource userDataSource() {
		return userDataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean
	LocalContainerEntityManagerFactoryBean userEntityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(false);
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(User.class.getPackage().getName());
		factory.setDataSource(userDataSource());
		factory.setPersistenceUnitName("user");
		return factory;
	}

	@Bean
	PlatformTransactionManager userTransactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(userEntityManagerFactory().getObject());
		return txManager;
	}
}

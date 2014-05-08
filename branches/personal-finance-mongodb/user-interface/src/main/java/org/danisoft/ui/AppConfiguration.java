package org.danisoft.ui;

import javax.jcr.SimpleCredentials;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.danisoft.repo.IJcrSessionFactory;
import org.danisoft.repo.IRepositoryFactoryBean;
import org.danisoft.repo.impl.JcrSessionFactoryImpl;
import org.danisoft.repo.impl.RepositoryFactoryBeanImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("org.danisoft.dao")
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
@ComponentScan
public class AppConfiguration {
	
	@Autowired
	Environment env;
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setDriverClassName(env.getProperty("database.driver"));
		dataSource.setUrl(env.getProperty("database.url"));
		dataSource.setUsername(env.getProperty("database.username"));
		dataSource.setPassword(env.getProperty("database.password"));
		
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		
		vendorAdapter.setDatabase(Database.HSQL);
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(false);
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("org.danisoft.model");
		factory.setDataSource(dataSource());

		return factory;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}

	@Bean(initMethod = "openSession", destroyMethod = "closeSession")
	public IJcrSessionFactory jcrSessionFactory() {
		SimpleCredentials credentials = new SimpleCredentials("admin", "admin".toCharArray());
		
		JcrSessionFactoryImpl jcrSessionFactory = new JcrSessionFactoryImpl();
		jcrSessionFactory.setRepositoryFactory(repositoryFactory());
		jcrSessionFactory.setCredentials(credentials);
		
		return jcrSessionFactory;
	}
	
	@Bean
	public IRepositoryFactoryBean repositoryFactory() {
		RepositoryFactoryBeanImpl repositoryFactory = new RepositoryFactoryBeanImpl();
		repositoryFactory.setXml("classpath:repository.xml");
		repositoryFactory.setPath("./jackrabbit");
		return repositoryFactory;
	}
}

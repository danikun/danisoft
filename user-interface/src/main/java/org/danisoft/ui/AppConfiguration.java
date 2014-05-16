package org.danisoft.ui;

import javax.jcr.SimpleCredentials;

import org.danisoft.repo.IJcrSessionFactory;
import org.danisoft.repo.IRepositoryFactoryBean;
import org.danisoft.repo.impl.JcrSessionFactoryImpl;
import org.danisoft.repo.impl.RepositoryFactoryBeanImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;

@Configuration
@EnableMongoRepositories(basePackages="org.danisoft")
public class AppConfiguration extends AbstractMongoConfiguration {
	
	@Autowired
	Environment env;

	@Override
	protected String getDatabaseName() {
		return "personal-finance";
	}

	@SuppressWarnings("deprecation")
	@Override
	public Mongo mongo() throws Exception {
		return new Mongo();
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

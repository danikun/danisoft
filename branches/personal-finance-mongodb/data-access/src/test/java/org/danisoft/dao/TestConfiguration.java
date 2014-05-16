package org.danisoft.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;

@Configuration
@EnableMongoRepositories
class TestConfiguration extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		return "personal-finance-test";
	}

	@SuppressWarnings("deprecation")
	@Override
	public Mongo mongo() throws Exception {
		return new Mongo();
	}

	@Override
	protected String getMappingBasePackage() {
		return "org.danisoft.dao.IContactDao";
	}
}

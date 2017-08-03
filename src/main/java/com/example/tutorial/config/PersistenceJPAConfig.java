/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hibernate, JPA configuration for database connect. Parameters read from
 * .properties file.
 *
 * @author roma
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = {"classpath:configs/db_configuration.properties"})
public class PersistenceJPAConfig {

	@Autowired
	private Environment env;

	/**
	 * Create entity manager.
	 *
	 * @return entity manager
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[]{"com.example.tutorial"});

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());

		return em;
	}

	/**
	 * Set parameters for data source. Parameters read from .properties file.
	 *
	 * @return data source object
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
		return dataSource;
	}

	/**
	 * Set transaction manager for entity manager
	 *
	 * @param emf entity manager
	 * @return transaction manager
	 */
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	/**
	 * Set additional parameters for data source. Parameters read from
	 * .properties file.
	 *
	 * @return parameters for data source
	 */
	Properties additionalProperties() {
		Properties properties = new Properties();
//		properties.setProperty("hibernate.hbm2ddl.auto", env.getRequiredProperty("jdbc.hbm2ddl.auto"));
		properties.setProperty("hibernate.dialect", env.getRequiredProperty("jdbc.dialect"));
		properties.setProperty("hibernate.show_sql", env.getRequiredProperty("jdbc.hibernate.show_sql"));
		return properties;
	}
}

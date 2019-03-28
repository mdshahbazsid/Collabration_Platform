package com.collabration.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages= {"com.collabration.model"})
@EnableTransactionManagement
public class DBConfiguration {

	//DBMS information
	private final static String DATABASE_DRIVER = "oracle.jdbc.OracleDriver" ;
	private final static String DATABASE_URL = "jdbc:oracle:thin:shahbaz@//localhost:1521/xe" ;
	private final static String DATABASE_DIALECT = "org.hibernate.dialect.Oracle10gDialect" ;
	private final static String DATABASE_USERNSME = "shahbaz" ;
	private final static String DATABASE_PASSWORD = "12345" ;
	
	public DBConfiguration() {
		System.out.println("---DBCOnfiguration class instantiated---");
	}

	//Creating the DataSource Bean
	@Bean("dataSource")
	public DataSource getDataSource() {
		
		BasicDataSource dataSource = new BasicDataSource();
		
		// Providing the database connection information
		dataSource.setDriverClassName(DATABASE_DRIVER);
		dataSource.setUrl(DATABASE_URL);
		dataSource.setUsername(DATABASE_USERNSME);
		dataSource.setPassword(DATABASE_PASSWORD);
		System.out.println("---DataSource Object is Created---");
		
		return dataSource;
	}
	
	//Creating the SessionFactory Bean
	@Bean("sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
		
		//Declaring the Hibernate Properties
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect",DATABASE_DIALECT);
		hibernateProperties.put("hibernate.show_sql",true);
		hibernateProperties.put("hibernate.format_sql",true);
		hibernateProperties.put("hibernate.hbm2ddl.auto","update");
		
		LocalSessionFactoryBuilder factoryBuilder = new LocalSessionFactoryBuilder(dataSource);
		factoryBuilder.addProperties(hibernateProperties);
		factoryBuilder.scanPackages("com.collabration.model");
		System.out.println("---SessionFactory object is Created---");
		
		return factoryBuilder.buildSessionFactory();
	}
	
	//Creating the TransactionManager Bean
	@Bean("txManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		
		System.out.println("---Transaction Manager Object is Created---");
		
		return transactionManager;
	}
}

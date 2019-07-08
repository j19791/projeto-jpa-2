package br.com.caelum;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class JpaConfigurator {

	@Bean
	public DataSource getDataSource() throws PropertyVetoException {
	    /*DriverManagerDataSource dataSource = new DriverManagerDataSource();

	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    dataSource.setUrl("jdbc:mysql://localhost/projeto_jpa");
	    dataSource.setUsername("root");
	    dataSource.setPassword("pc1000");

	    */
		//Melhorando escalabilidade utilizando o pool de conexões
		ComboPooledDataSource dataSource = new ComboPooledDataSource();

	    dataSource.setDriverClass("com.mysql.jdbc.Driver");
	    dataSource.setJdbcUrl("jdbc:mysql://localhost/projeto_jpa");
	    dataSource.setUser("root");
	    dataSource.setPassword("pc1000");
	    
	    dataSource.setMinPoolSize(3);//abrir uma certa quantidade de conexões para compartilharmos entre os clientes. número mínimo de conexões que devem ser criadas de antemão e que estarão esperando clientes. 
	    
	    dataSource.setMaxPoolSize(5);//valor máximo de conexões a serem criadas. Lembrando que o pool nunca criará mais conexões do que as que foram estabelecidas. Caso não hajam mais conexões para serem usadas o cliente precisará esperar por uma conexão disponível.
	    
	    
	    dataSource.setInitialPoolSize(3);
	    
	    dataSource.setIdleConnectionTestPeriod(1); //a cada um segundo testamos as conexões ociosas (eliminando o risco de escolher uma conexão quebrada por servidor de bd caido)
	    
	    return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactory.setPackagesToScan("br.com.caelum");
		entityManagerFactory.setDataSource(dataSource);

		entityManagerFactory
				.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties props = new Properties();

		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		
		
		
		
		props.setProperty("hibernate.cache.use_second_level_cache", "true");//habilitando cache de 2o. nivel
        props.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");//provedor de cache: EhCache 

        props.setProperty("hibernate.cache.use_query_cache", "true");//habilita cache de queries: armazenar em cache o resultado de uma query feita para determinados parâmetros
        
		entityManagerFactory.setJpaProperties(props);
		return entityManagerFactory;
	}

	@Bean
	public JpaTransactionManager getTransactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

}

package com.iassets;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:system.properties")
@EnableTransactionManagement
public class JpaConfig {


    @Value("${dbsrvip}")
    private String dbHost;

    @Value("${dbs}")
    private String dbName;

    @Value("${dbu}")
    private String dbUser;

    @Value("${dbup}")
    private String dbPass;

    @Bean
    public DataSource dataSource() {
        String url = String.format("jdbc:mysql://%s:3306/%s", dbHost, dbName);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Use com.mysql.cj.jdbc.Driver if on latest MySQL
        dataSource.setUrl(url);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPass);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.iassets.common.entity");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", "update");
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        props.setProperty("hibernate.show_sql", "true");

        em.setJpaProperties(props);
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}

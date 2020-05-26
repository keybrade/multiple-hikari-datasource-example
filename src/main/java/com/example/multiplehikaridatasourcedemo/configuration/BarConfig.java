package com.example.multiplehikaridatasourcedemo.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "barFactory", transactionManagerRef = "barManager",
        basePackages = "com.example.multiplehikaridatasourcedemo.repository.bar")
public class BarConfig {

    public final static String PERSISTENCE_UNIT_NAME = "bar";

    @Bean
    @ConfigurationProperties("spring.datasource.bar")
    public DataSource barDs() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.bar.jpa")
    public JpaProperties barJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.bar.jpa.hibernate")
    public HibernateProperties barHibernateProperties() {
        return new HibernateProperties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean barFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(barDs())
                .packages("com.example.multiplehikaridatasourcedemo.entity.bar")
                .persistenceUnit(PERSISTENCE_UNIT_NAME)
                .properties(barJpaProperties().getProperties())
                .properties(barHibernateProperties().determineHibernateProperties(barJpaProperties().getProperties(),
                        new HibernateSettings()))
                .build();
    }

    @Bean
    public PlatformTransactionManager barManager(@Qualifier("barFactory") EntityManagerFactory barFactory) {
        return new JpaTransactionManager(barFactory);
    }
}
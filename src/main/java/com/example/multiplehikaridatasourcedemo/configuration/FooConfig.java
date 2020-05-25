package com.example.multiplehikaridatasourcedemo.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author Jay Ehsaniara, Dec
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "fooFactory", transactionManagerRef = "fooManager",
        basePackages = "com.example.multiplehikaridatasourcedemo.repository.foo")
public class FooConfig {

    public final static String PERSISTENCE_UNIT_NAME = "foo";

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.foo")
    public DataSource fooDs() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.foo.jpa")
    public JpaProperties fooJpaProperties() {
        return new JpaProperties();
    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.foo.jpa.hibernate")
    public HibernateProperties fooHibernateProperties() {
        return new HibernateProperties();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean fooFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(fooDs())
                .packages("com.example.multiplehikaridatasourcedemo.entity.foo")
                .persistenceUnit(PERSISTENCE_UNIT_NAME)
                .properties(fooJpaProperties().getProperties())
                .properties(fooHibernateProperties().determineHibernateProperties(fooJpaProperties().getProperties(),
                        new HibernateSettings()))
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager fooManager(EntityManagerFactory fooFactory) {
        return new JpaTransactionManager(fooFactory);
    }
}
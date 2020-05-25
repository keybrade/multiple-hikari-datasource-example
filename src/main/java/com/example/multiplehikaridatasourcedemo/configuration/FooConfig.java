package com.example.multiplehikaridatasourcedemo.configuration;

import lombok.extern.slf4j.Slf4j;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

    @Bean
    @ConfigurationProperties("spring.datasource.foo.jpa")
    public Properties fooHibernate() {
        return new Properties();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean fooFactory(EntityManagerFactoryBuilder builder) {
        Properties properties = fooHibernate();
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            map.put((String) entry.getKey(), entry.getValue());
        }
        log.info("properties:{}", properties);
        log.info("properties:{}", properties);
        return builder.dataSource(fooDs())
                .packages("com.example.multiplehikaridatasourcedemo.entity.foo")
                .persistenceUnit(PERSISTENCE_UNIT_NAME)
                .properties(map)
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager fooManager(EntityManagerFactory fooFactory) {
        return new JpaTransactionManager(fooFactory);
    }
}
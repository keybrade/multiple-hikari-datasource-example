package com.example.multiplehikaridatasourcedemo.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Jay Ehsaniara, Dec
 */
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
    public Properties barHibernate() {
        return new Properties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean barFactory(EntityManagerFactoryBuilder builder) {
        Properties properties = barHibernate();
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            map.put((String) entry.getKey(), entry.getValue());
        }
        log.info("properties:{}", properties);
        return builder.dataSource(barDs())
                .packages("com.example.multiplehikaridatasourcedemo.entity.bar")
                .persistenceUnit(PERSISTENCE_UNIT_NAME)
                .properties(map)
                .build();
    }

    @Bean
    public PlatformTransactionManager barManager(@Qualifier("barFactory") EntityManagerFactory barFactory) {
        return new JpaTransactionManager(barFactory);
    }
}
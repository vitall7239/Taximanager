package org.vpetrovych.taximanager.dao.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("org.vpetrovych.taximanager.dao")
@PropertySource("classpath:/jpa.properties")
public class PersistenceJPAConfig {

    private final Environment env;

    @Autowired
    public PersistenceJPAConfig(Environment env) {
        this.env = env;
    }

    /**
     * Creates Bean of JPA {@link javax.persistence.EntityManagerFactory} for further injection to JPA-based DAOs via
     * dependency injection.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory
                = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPackagesToScan("org.vpetrovych.taximanager.domain.entities");
        entityManagerFactory.setDataSource(dataSource());

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactory.setJpaProperties(additionalProperties());
        return entityManagerFactory;
    }

    /**
     * Creates Bean of {@link javax.sql.DataSource} which provides connections to the physical data source
     * that this DataSource object represents.
     * Configuration settings are read from a {@code jpa.properties} config file.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("hibernate.connection.driver"));
        dataSource.setUrl(env.getProperty("hibernate.connection.url"));
        dataSource.setUsername(env.getProperty("hibernate.connection.username"));
        dataSource.setPassword(env.getProperty("hibernate.connection.password"));
        return dataSource;
    }

    /**
     * Creates a transaction manager bean of {@link org.springframework.orm.jpa.JpaTransactionManager}
     * which is implementation of {@link org.springframework.transaction.PlatformTransactionManager} that integrates
     * the JPA provider with the Spring transaction mechanism.
     *
     * @param entityManagerFactory entity manager factory
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    /**
     * Creates Bean post-processor that automatically applies persistence exception translation to any
     * bean marked with Spring's @{@link org.springframework.stereotype.Repository Repository}
     * annotation
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * Addition properties for DataSource Bean
     *
     * @see #entityManagerFactory
     * @see #dataSource
     * such as
     * hibernate.dialect
     * hibernate.show_sql
     * hibernate.format_sql
     * and so on must be provided here
     */
    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }
}

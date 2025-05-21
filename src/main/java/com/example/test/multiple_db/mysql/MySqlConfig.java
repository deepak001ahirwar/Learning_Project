package com.example.test.multiple_db.mysql;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
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

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.multiple_db.mysql.repo",
entityManagerFactoryRef = "mysqlEntityManager",
transactionManagerRef = "mysqlTransactionManager")
public class MySqlConfig    {


    // set the data sources beans
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSource mysqlDataSource(){
        return DataSourceBuilder.create().build();
    }

    // set the entityManagerFactoryRef
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean  mysqlEntityManager(EntityManagerFactoryBuilder entityManagerFactoryBuilder){

        Map<String,String> props = new HashMap<>();
        props.put("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        props.put("hibernate.show_sql","true");
        props.put("hibernate.hbm2ddl.auto","update");

        return  entityManagerFactoryBuilder.dataSource(mysqlDataSource())
                .packages("com.example.multiple_db.mysql.entity")
                .properties(props)
                .persistenceUnit("mysqlPU").build();
    }


    // set the mysqlTransactionManager

    @Primary
    @Bean
    public PlatformTransactionManager mysqlTransactionManager( @Qualifier("mysqlEntityManager") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }



}

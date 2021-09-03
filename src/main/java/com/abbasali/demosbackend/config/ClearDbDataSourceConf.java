package com.abbasali.demosbackend.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ClearDbDataSourceConf {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.cleardb")
    public DataSourceProperties ginsuMemberDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.cleardb.configuration")
    @Qualifier("clearDbDataSourceConf")
    public JdbcTemplate clearDbDataSource() {
        return new JdbcTemplate(ginsuMemberDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build());
    }
}

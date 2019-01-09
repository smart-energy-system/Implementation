package com.github.smartenergysystem.price.collector.priceCollector;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;


@Configuration
public class DatabaseConfig {

    private Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

	@Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        //Get path to DB
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        File dbFile = new File(currentPath.toFile(),"spring-boot-h2-db");
        String jdbcUrl = "jdbc:h2:file:"+dbFile.getAbsolutePath();
        logger.info("jdbcUrl:"+jdbcUrl);
        dataSourceBuilder.url(jdbcUrl);
        //FIXME
        dataSourceBuilder.password("");
        dataSourceBuilder.username("sa");
        return dataSourceBuilder.build();  
    }

}

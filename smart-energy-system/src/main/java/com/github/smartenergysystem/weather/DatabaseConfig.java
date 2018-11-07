package com.github.smartenergysystem.weather;


import java.nio.file.Path;
import java.nio.file.Paths;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
		
	@Bean  
    public DataSource dataSource() {  
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();  
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        //Get path to DB
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path basePath = currentPath.getParent().getParent();
        //TODO put it in config
        dataSourceBuilder.url("jdbc:sqlite:"+basePath.toString()+"\\WeatherCollector\\weather.db"); 
        return dataSourceBuilder.build();  
    }

}

package com.github.smartenergysystem.weather;


import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DatabaseConfig {
		
	@Bean  
    public DataSource dataSource() throws FileNotFoundException {  
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();  
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        //Get path to DB
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path basePath = currentPath.getParent().getParent();
        //TODO put it in config
        Path subPath = Paths.get("WeatherCollector", "weather.db");
        Path fullPath = basePath.resolve(subPath);
        if(!fullPath.toFile().exists()) {
        	throw new FileNotFoundException("There is no weather.db in this path:"+ fullPath);
        }
        dataSourceBuilder.url("jdbc:sqlite:"+fullPath); 
        return dataSourceBuilder.build();  
    }

}

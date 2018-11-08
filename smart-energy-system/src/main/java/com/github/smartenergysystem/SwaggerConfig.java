package com.github.smartenergysystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final String SUPPLIER_TAG_NAME = "Supplier";
	public static final String CONSUMER_TAG_NAME = "Consumer";

	@Bean
	public Docket produceApi() {
		ApiInfo apiInfo = new ApiInfoBuilder().title("Microgrid Simulation").version("0.1").build();
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).select()
				.apis(RequestHandlerSelectors.basePackage("com.github.smartenergysystem"))
				.paths(PathSelectors.any()).build().tags(new Tag(SUPPLIER_TAG_NAME,"Different Supplier like wind turbines"));
	}
}

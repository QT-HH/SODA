package com.tak.soda.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
	
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				//.consumes(getConsumeContentTypes())
				//.produces(getProduceContentTypes())
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.tak.soda.controller"))
				.paths(PathSelectors.any())
				.build();

	}
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Soda")
				.description("Soda API")
				.version("1.0")
				.build();
	}
	private Set<String> getConsumeContentTypes(){
		Set<String> consumes = new HashSet<>();
		consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
		return consumes;
	}
	private Set<String> getProduceContentTypes(){
		Set<String> produces = new HashSet<String>();
		produces.add("application/json;charset=UTF-8");
		return produces;
	}
}


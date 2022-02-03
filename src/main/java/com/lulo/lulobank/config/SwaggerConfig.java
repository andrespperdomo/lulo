package com.lulo.lulobank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.service.Contact;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	    private static final String TITLLE_BUSINESS = "Lulo bank";
	    private static final String DESCRIPTION_BUSINESS = "Componente que creación de cuenta y valida transacciones";
	    
	    @Bean
	    public Docket documentation() {
	        return  new Docket(DocumentationType.SWAGGER_2).apiInfo(metaData()).pathMapping("/").select()
					.paths(regex("/v1.*")).build();
	    }

	    private ApiInfo metaData() {
	    	 Contact con=new Contact("Andres Perdomo","","andrespperdomo@gmail.com");

	        return new ApiInfoBuilder()
	                .title(TITLLE_BUSINESS)
	                .description(DESCRIPTION_BUSINESS)
	                .contact(con)
	                .version("1.0.1")
	                .build();
	    }

}

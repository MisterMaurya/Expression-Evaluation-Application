package org.evaluation.expression.configuration;

import org.evaluation.expression.constants.APIConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
	private static final String SWAGGER_API_VERSION = "1.0";
	private static final String LICENSE_TEXT = "License";
	private static final String TITLE = "Expression Evaluation RESTFul API";
	private static final String DESCRIPTION = "RESTful APIs for Expression Evaluation Algorithm";
	public static final Contact DEFAULT_CONTACT = new Contact("Pawan Maurya", "http://github.com/mistermaurya",
			"pawank@thelattice.in");

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(TITLE).description(DESCRIPTION).license(LICENSE_TEXT).contact(DEFAULT_CONTACT)
				.version(SWAGGER_API_VERSION).build();
	}

	@Bean
	public Docket bostonApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.tags(new Tag(APIConstants.EXPRESSION_EVALUATION_TAG, APIConstants.EXPRESSION_CONTROLLER_DESCRIPTION))
				.select().apis(RequestHandlerSelectors.basePackage("org.evaluation.expression")).build();
	}

}

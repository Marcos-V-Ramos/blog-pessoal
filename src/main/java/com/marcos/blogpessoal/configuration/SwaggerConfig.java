package com.marcos.blogpessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI springBlogPessoalOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Projeto Blog Pessoal")
						.description("REST API para um blog pessoal.")
						.version("v0.0.1")
						.license(new License()
								.name("Marcos Vinicius Ramos de Caldas")
								.url("https://github.com/Marcos-V-Ramos"))
						.contact(new Contact()
								.name("Marcos Vinicius Ramos de Caldas")
								.url("https://github.com/Marcos-V-Ramos")
								.email("marcosvramosdecaldas@gmail.com")))
				.externalDocs(new ExternalDocumentation()
						.description("LinkedIn")
						.url("https://www.linkedin.com/in/marcos-v-ramos-caldas/"));
	}
	
	@Bean
	OpenApiCustomizer customerGlobalHeaderOpenApiCustomizer() {
		
		return openApi -> {
			
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations()
					.forEach(operation -> {
						
						ApiResponses apiResponses = operation.getResponses();
						
						apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
						apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
						apiResponses.addApiResponse("204", createApiResponse("Objeto Excluido!"));
						apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição!"));
						apiResponses.addApiResponse("401", createApiResponse("Acesso não Autorizado!"));
						apiResponses.addApiResponse("403", createApiResponse("Acesso Proíbido!"));
						apiResponses.addApiResponse("404", createApiResponse("Objeto não Encontrado!"));
						apiResponses.addApiResponse("500", createApiResponse("Erro na Aplicação!"));
					}));
		};
	}

	private ApiResponse createApiResponse(String message) {
		
		return new ApiResponse().description(message);
	}
}
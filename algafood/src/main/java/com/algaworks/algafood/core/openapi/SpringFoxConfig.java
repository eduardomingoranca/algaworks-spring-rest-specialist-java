package com.algaworks.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.OAS_30;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {
        // Docket => classe do springfox que representa
        // a configuracao da api para gerar a definicao
        // usando a especificacao openapi
        // eh um sumario para configurar um conjunto de
        // servicos que devem ser documentados.
        Tag firstTag = new Tag("Cidades", "Gerencia as cidades");

        return new Docket(OAS_30)
                .select()
                .apis(basePackage("com.algaworks.algafood.api"))
                .paths(any())
//                .paths(ant("/restaurantes/*"))
                .build()
                .apiInfo(apiInfo())
                .tags(firstTag);
    }

    public ApiInfo apiInfo() {
        Contact contact = new Contact("Algaworks", "https://www.algaworks.com",
                "contato@algaworks.com");

        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("1")
                .contact(contact)
                .build();
    }

}
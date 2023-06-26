package com.project.jose;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class AppSetupConfig {

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${app.doc.title:Default Title}") String title,
            @Value("${app.doc.version:Default Version}") String version,
            @Value("${app.doc.description:Default Description}") String description,
            @Value("${app.doc.terms-of-service:Default Terms}") String terms,
            @Value("${app.doc.license:Default License}") String license,
            @Value("${app.doc.url:Default Url}") String url
    ) {
        return new OpenAPI().info(new Info()
                .title(title)
                .version(version)
                .description(description)
                .termsOfService(terms)
                .license(new License().name(license)
                        .url(url)));
    }

}
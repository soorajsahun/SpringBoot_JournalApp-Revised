package net.engineeringdigest.journalApp.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	//httpBasic()
//    @Bean
//    public OpenAPI myCustomConfig(){
//        return new OpenAPI()
//                .info(
//                new Info().title("Journal App APIs")
//                        .description("By Suraj Sahu")
//                )
//                .servers(Arrays.asList(new Server().url("http://localhost:8080").description("local")))
//               
//                .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
//                .components(new Components().addSecuritySchemes("basicAuth",
//                        new SecurityScheme()
//                                .type(SecurityScheme.Type.HTTP)
//                                .scheme("basic")
//                ))
//               ;
//    }
	
	//jwt
	@Bean
    public OpenAPI myCustomConfig() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Journal App APIs")
                        .description("By Suraj Sahu")
                )
                .servers(Arrays.asList(
                        new Server().url("http://localhost:8080").description("local")
                ))
                // Tell Swagger to require JWT for secured endpoints
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
}
package br.grupointegrado.movies;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Permite CORS para todas as rotas
                .allowedOrigins("http://localhost:3000")  // Permite apenas o frontend na porta 3000
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Permite esses métodos
                .allowedHeaders("*");  // Permite todos os cabeçalhos
    }
}
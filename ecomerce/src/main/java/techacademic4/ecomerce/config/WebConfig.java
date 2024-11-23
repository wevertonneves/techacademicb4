package techacademic4.ecomerce.config; // Pacote correto para a classe de configuração

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/produtos")  // Ajuste para sua API
                        .allowedOrigins("http://localhost:5174"); // Permite acesso da origem especificada
            }
        };
    }
}

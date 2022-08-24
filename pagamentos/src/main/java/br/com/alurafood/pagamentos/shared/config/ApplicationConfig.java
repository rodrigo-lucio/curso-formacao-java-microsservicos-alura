package br.com.alurafood.pagamentos.shared.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper beanModelMapper(){
        return new ModelMapper();
    }
}

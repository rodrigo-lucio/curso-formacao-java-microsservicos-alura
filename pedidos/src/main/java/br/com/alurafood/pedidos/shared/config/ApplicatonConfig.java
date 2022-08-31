package br.com.alurafood.pedidos.shared.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicatonConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}

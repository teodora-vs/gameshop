package com.softuni.gameshop.config;

import com.softuni.gameshop.model.DTO.AddGameDTO;
import com.softuni.gameshop.model.Game;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }




}

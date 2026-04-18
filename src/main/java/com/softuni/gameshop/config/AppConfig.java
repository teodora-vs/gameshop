package com.softuni.gameshop.config;

import com.softuni.gameshop.model.DTO.ReviewDTO;
import com.softuni.gameshop.model.Review;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Review.class, ReviewDTO.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getAuthor().getFullName(), ReviewDTO::setAuthor);

                    mapper.skip(ReviewDTO::setCreated);
                })
                .setPostConverter(context -> {
                    Review source = context.getSource();
                    ReviewDTO destination = context.getDestination();

                    if (source.getCreated() != null) {
                        String formattedDate = source.getCreated()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        destination.setCreated(formattedDate);
                    }
                    return destination;
                });

        return modelMapper;

    }


}

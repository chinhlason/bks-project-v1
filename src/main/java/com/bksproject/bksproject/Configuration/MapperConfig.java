package com.bksproject.bksproject.Configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        // Tạo object và cấu hình
        ModelMapper modelMapper = new ModelMapper();
        // STRICT
        // mọi từ của thuộc tính đích phải khớp hết
        // mọi từ trong tên thuộc tính nguồn phải tồn tại trong tên thuộc tính đích.
        // mọi từ của thuộc tính đích phải khớp hết
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}

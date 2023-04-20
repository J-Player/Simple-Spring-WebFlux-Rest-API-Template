package com.example.api.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class MapperUtil {

    public static final ModelMapper MAPPER = new ModelMapper();

}

package com.example.demo.annotations;

import com.example.demo.configs.web.WebFluxConfig;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(in = ParameterIn.QUERY
        , name = WebFluxConfig.PAGE_PARAM_NAME
        , description = "One-based page index (1..N)"
        , schema = @Schema(type = "integer", example = WebFluxConfig.PAGE_INITIAL, defaultValue = WebFluxConfig.PAGE_INITIAL))
@Parameter(in = ParameterIn.QUERY
        , name = WebFluxConfig.SIZE_PARAM_NAME
        , description = "The size of the page to be returned"
        , schema = @Schema(type = "integer", example = WebFluxConfig.PAGE_SIZE_DEFAULT, defaultValue = WebFluxConfig.PAGE_SIZE_DEFAULT, maximum = WebFluxConfig.MAX_PAGE_SIZE))
@Parameter(in = ParameterIn.QUERY
        , name = WebFluxConfig.SORT_PARAM_NAME
        , description = """
        Sorting criteria in the format: property,(asc|desc).
        Default sort order is ascending.
        Multiple sort criteria are supported.
        """
        , array = @ArraySchema(schema = @Schema(type = "string")))
public @interface PageableAsQueryParam {
}

package com.aluracursos.literalura_application.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadosDto(
        @JsonAlias("results")List<LibrosDto> results) {
}

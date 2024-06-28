package com.aluracursos.literalura_application.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)  // para que ignore los datos que no necesitamos leer.
public record LibrosDto(
             @JsonAlias("title")String titulo,
             @JsonAlias("authors")List<AutoresDto> autor,
             @JsonAlias("languages") List<String> idiomas,
             @JsonAlias("download_count")Double totalDescargas) {
}

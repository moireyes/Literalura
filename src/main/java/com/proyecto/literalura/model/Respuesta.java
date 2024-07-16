package com.proyecto.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Respuesta(
        @JsonAlias("count")  int count,
        @JsonAlias("next")  String next,
        @JsonAlias("previous")  String previous,
        @JsonAlias("results") List<Libro> results) {
}

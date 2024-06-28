package com.aluracursos.literalura_application.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Libro {
    private Long id;
    private String titulo;
    private Autor autor;
    private String idioma;
    private Double totalDescargas;
    private List<Autor> autores = new ArrayList<>();

    public Libro() {
    }

    public Libro(LibrosDto libroDto, Autor autor) {
        this.titulo = libroDto.titulo();
        this.autor = autor;
        this.idioma = libroDto.idiomas().get(0);
        this.totalDescargas = libroDto.totalDescargas();
    }

    public Libro(LibrosDto librosDto) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() { return autor;}

    public void setAutor(Autor autor) { this.autor = autor;}

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getTotalDescargas() {
        return totalDescargas;
    }

    public void setTotalDescargas(Double totalDescargas) {
        this.totalDescargas = totalDescargas;
    }

    @Override
    public String toString() {
        return """
                ***Libro***
                Titulo: %s
                Autor: %s
                Idioma: %s
                Descargas: %s
                """.formatted(titulo, autor.getNombre(), idioma, totalDescargas);
    }
}

package com.aluracursos.literalura_application.model;

import jakarta.persistence.*;
import org.graalvm.polyglot.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idioma;
    private Double totalDescargas;

    public Libro() {
    }

    public Libro(LibrosDto libroDto, Autor autor) {
        this.titulo = libroDto.titulo();
        this.autor = autor;
        this.idioma = libroDto.idiomas().get(0);
        this.totalDescargas = libroDto.totalDescargas();
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

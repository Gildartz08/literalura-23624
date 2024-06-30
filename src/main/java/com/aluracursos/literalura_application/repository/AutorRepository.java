package com.aluracursos.literalura_application.repository;

import com.aluracursos.literalura_application.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE a.fechaFallecimiento >= :anhoElegido AND a.fechaNacimiento <= :anhoElegido")
    List<Autor> autoresVivosPorAnho(Integer anhoElegido);
}

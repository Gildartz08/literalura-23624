package com.aluracursos.literalura_application;

import com.aluracursos.literalura_application.principal.Principal;
import com.aluracursos.literalura_application.repository.AutorRepository;
import com.aluracursos.literalura_application.repository.LibroRepository;
import com.aluracursos.literalura_application.service.ConsumoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repositorioLibro;
	@Autowired
	private AutorRepository repositorioAutor;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorioLibro, repositorioAutor);
		principal.muestraMenu();
	}
}

package com.aluracursos.literalura_application.principal;

import com.aluracursos.literalura_application.model.*;
import com.aluracursos.literalura_application.service.ConsumoApi;
import com.aluracursos.literalura_application.service.ConvertirDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    public static final String URL = "https://gutendex.com/books/?search=";
    ConsumoApi consumoApi = new ConsumoApi();
    ConvertirDatos convertirDatos = new ConvertirDatos();
    Scanner entrada = new Scanner(System.in);


    public void muestraMenu(){
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1-Buscar libro por título
                    2-Buscar libros registrados
                    3-Buscar autores registrados
                    4-Listar autores vivos en un determinado año
                    5-Listar libros por idioma
                    0-Salir
                    """;
            System.out.println(menu);
            opcion = entrada.nextInt();
            entrada.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
//                case 2:
//                    mostrarLibrosRegistrados();
//                    break;
//                case 3:
//                    mostrarAutoresRegistrados();
//                    break;
//                case 4:
//                    mostrarAutoresVivos();
//                    break;
//                case 5:
//                    mostraLibrosPorIdioma();
//                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción Invalida");
            }

        }
    }


    public void buscarLibro() {
        System.out.println("Escribe el título del libro que deseas buscar:");
        var busqueda = entrada.nextLine();
        String json = consumoApi.solicitarDatos(URL + busqueda.replace(" ", "+"));
        ResultadosDto datos = convertirDatos.obtenerDatos(json, ResultadosDto.class);
        Optional<LibrosDto> libroOptional = datos.results().stream().filter(l -> l.titulo().toLowerCase().contains(busqueda.toLowerCase())).findFirst();
        if (libroOptional.isPresent()) {
            LibrosDto libroDto = datos.results().get(0);
            AutoresDto autorDto = libroDto.autor().get(0);
            var autor = new Autor(autorDto);
            //falta agregar al repository
            var libro = new Libro(libroDto, autor);
            //falta agregar al repository

            System.out.printf("""
                    ******LIBRO ENCONTRADO******
                    NOMBRE: %S
                    AUTOR: %S
                    IDIOMAS: %S
                    DESCARGAS: %S
                    %n""", libroDto.titulo(), autorDto.nombre(), libroDto.idiomas(), libroDto.totalDescargas());
        } else {
            System.out.println("Libro no encontrado");
        }
    }

}

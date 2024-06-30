package com.aluracursos.literalura_application.principal;

import com.aluracursos.literalura_application.model.*;
import com.aluracursos.literalura_application.repository.AutorRepository;
import com.aluracursos.literalura_application.repository.LibroRepository;
import com.aluracursos.literalura_application.service.ConsumoApi;
import com.aluracursos.literalura_application.service.ConvertirDatos;
import org.apache.logging.log4j.util.Strings;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private LibroRepository repositorioLibro;
    private AutorRepository repositorioAutor;
    private List<String> idiomas;
    private Integer anhoElegido;
    public static final String URL = "https://gutendex.com/books/?search=";
    ConsumoApi consumoApi = new ConsumoApi();
    ConvertirDatos convertirDatos = new ConvertirDatos();
    Scanner entrada = new Scanner(System.in);

    public Principal(LibroRepository repositorioLibro, AutorRepository repositorioAutor) {
        this.repositorioLibro = repositorioLibro;
        this.repositorioAutor = repositorioAutor;
    }

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
            try {
                opcion = entrada.nextInt();
                entrada.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Opcion no válida. Por favor, ingrese un número de entre las opciones mostradas");
                entrada.next(); //reinicio el scanner
                continue; //Se salta la iteración actual
            }

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivos();
                    break;
                case 5:
                    mostraLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("No elegió una Opción Valida");
                    break;
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
            repositorioAutor.save(autor);
            var libro = new Libro(libroDto, autor);
            repositorioLibro.save(libro);

            System.out.printf("""
                    ******LIBRO ENCONTRADO******
                    NOMBRE: %S
                    AUTOR: %S
                    IDIOMAS: %S
                    DESCARGAS: %S
                    %n""", libroDto.titulo(), autor.getNombre(), libroDto.idiomas(), libroDto.totalDescargas());
        } else {
            System.out.println("Libro no encontrado, revise el titulo que ingresó e intente nuevamente");
        }
    }

    public void mostrarLibrosRegistrados(){
        List<Libro> librosRegistrados = repositorioLibro.findAll();
        librosRegistrados.stream().forEach(System.out::println);
    }

    public void mostrarAutoresRegistrados(){
        List<Autor> autoresRegistrados = repositorioAutor.findAll();
        autoresRegistrados.stream().forEach(System.out::println);
    }

    public void mostrarAutoresVivos(){
        System.out.println("Escriba el año en que quieres ver que autores estaban vivos: ");
        try {
            anhoElegido = entrada.nextInt();
//            entrada.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("El valor que ingreso no es valido, ingrese un numéro de 4 digitos, Ej. 1950");

        }

        List<Autor> autoresVivos = repositorioAutor.autoresVivosPorAnho(anhoElegido);
        autoresVivos.forEach(System.out::println);
        if (autoresVivos.isEmpty()){
            System.out.println("Ningun autor registrado estaba vivo en el año " + anhoElegido);
        }
    }

    public void mostraLibrosPorIdioma(){
        System.out.println("""
                Ingrese la clave o codigo de 2 carácteres del idioma para buscar los libros:
                es para español
                en para inglés
                fr para frances
                pt para portugués
                """);
        var eleccion = entrada.nextLine();
        List<Libro> librosPorIdioma = repositorioLibro.buscarPorIdioma(eleccion.toLowerCase());
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No hay ningun libro en el registro escrito en ese idioma.");
        } else {
            for (Libro libro : librosPorIdioma){
                System.out.println(libro.toString());
            }
        }
    }


}

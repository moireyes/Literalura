package com.proyecto.literalura;

import com.proyecto.literalura.entity.AutorEntity;
import com.proyecto.literalura.entity.LibroEntity;
import com.proyecto.literalura.mapper.ConvierteDatos;
import com.proyecto.literalura.model.Respuesta;
import com.proyecto.literalura.repository.AutorRepository;
import com.proyecto.literalura.repository.LibroRepository;
import com.proyecto.literalura.service.ConsumoAPI;

import java.util.List;
import java.util.Scanner;

public class Principal {

    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner leer = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;

    public Principal(LibroRepository libroRepositorio, AutorRepository autorRepositorio) {
        this.libroRepositorio = libroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public static void menu() {
        System.out.println("1. Buscar libro por título");
        System.out.println("2. Libros Buscados");
        System.out.println("3. Autores Buscados");
        System.out.println("4. Autores vivos entre ciertos años");
        System.out.println("5. Libros por idioma");
        System.out.println("0. Salir");
        System.out.println("Elija una opción");
    }

    public void ejecutar() {
        boolean salida = false;
        while (!salida) {

            menu();
            int opcion = leer.nextInt();
            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    buscarLibros();
                    break;
                case 3:
                    buscarAutores();
                    break;
                case 4:
                    buscarAutoresVivos();
                    break;
                case 5:
                    buscarPorIdiomas();
                    break;
                case 0:
                    System.out.println("*******FIN DEL PROGRAMA*********");
                    salida = true;
                    break;
                default:
                    System.out.println("Ingrese una opción válida");
            }
        }

    }

    private void buscarLibros() {

        List<LibroEntity> libros = libroRepositorio.findAll();

        if (!libros.isEmpty()) {

            for (LibroEntity libro : libros) {
                System.out.println("********* LIBROS *********");
                System.out.println(" Titulo: " + libro.getTitulo());
                System.out.println(" Autor: " + libro.getAutor().getNombre());
                System.out.println(" Idioma: " + libro.getLenguaje());
                System.out.println(" Descargas: " + libro.getDescargas());
                System.out.println("***************************");
            }

        } else {
            System.out.println("********* 404 NOT FOUND *********");
        }

    }

    private void buscarAutores() {
        List<AutorEntity> autores = autorRepositorio.findAll();

        if (!autores.isEmpty()) {
            for (AutorEntity autor : autores) {
                System.out.println("********* Autores *********");
                System.out.println(" Nombre: " + autor.getNombre());
                System.out.println(" Fecha de Nacimiento: " + autor.getFechaNacimiento());
                System.out.println(" Fecha de Fallecimiento: " + autor.getFechaFallecimiento());
                System.out.println(" Libros: " + autor.getLibros().getTitulo());
                System.out.println("***************************");
            }
        } else {
            System.out.println("********* 404 NOT FOUND *********");

        }

    }

    private void buscarAutoresVivos() {
        System.out.println("Escriba el año de nacimiento: ");
        var anio = leer.nextInt();
        leer.nextLine();

        List<AutorEntity> autores = autorRepositorio.findForYear(anio);

        if (!autores.isEmpty()) {
            for (AutorEntity autor : autores) {
                System.out.println("********* Autores Vivos *********");
                System.out.println(" Nombre: " + autor.getNombre());
                System.out.println(" Fecha de nacimiento: " + autor.getFechaNacimiento());
                System.out.println(" Fecha de fallecimiento: " + autor.getFechaFallecimiento());
                System.out.println(" Libros: " + autor.getLibros().getTitulo());
                System.out.println("************************************");
            }
        } else {
            System.out.println("*********404 NOT FOUND*********");

        }
    }

    private void buscarPorIdiomas() {
        System.out.println("Seleccione Idioma");
        System.out.println("1. Español");
        System.out.println("2. Inglés");
        var idioma = leer.nextInt();
        leer.nextLine();

        String varIdioma = "";

        if(idioma == 1) {
            varIdioma = "es";
        } else 	if(idioma == 2) {
            varIdioma = "en";
        }

        List<LibroEntity> libros = libroRepositorio.findForLanguaje(varIdioma);

        if (!libros.isEmpty()) {

            for (LibroEntity libro : libros) {
                System.out.println("********* LIBROS POR IDIOMA *********");
                System.out.println(" Titulo: " + libro.getTitulo());
                System.out.println(" Autor: " + libro.getAutor().getNombre());
                System.out.println(" Idioma: " + libro.getLenguaje());
                System.out.println(" Descargas: " + libro.getDescargas());
                System.out.println("***************************");
            }

        } else {
            System.out.println("********* 404 NOT FOUND *********");
        }


    }

    private void buscarLibroWeb() {
        Respuesta datos = getDatosLibro();

        if (!datos.results().isEmpty()) {

            LibroEntity libro = new LibroEntity(datos.results().get(0));
            libro = libroRepositorio.save(libro);

        }

        System.out.println("Resultado Encontrado: ");
        System.out.println(datos);
    }

    private Respuesta getDatosLibro() {
        System.out.println("Ingrese el nombre del libro a buscar: ");
        leer.nextLine();
        var otra = leer.nextLine();
        var titulo = otra.replace(" ", "%20");
        System.out.println("Título : " + titulo);
        System.out.println(URL_BASE + titulo);
        var json = consumoApi.obtenerDatos(URL_BASE + titulo);
        System.out.println(json);
        Respuesta datos = conversor.obtenerDatos(json, Respuesta.class);
        return datos;
    }

}

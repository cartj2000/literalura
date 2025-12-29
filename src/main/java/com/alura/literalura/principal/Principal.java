package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;
    private List<DatosLibro> datosLibroList = new ArrayList<>();
    private List<Libro> librosLista;
    private String tituloLibro;
    private Optional<String> libroBuscado;

    public Principal(LibroRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Elija la opción a través de su número:
                    1 - buscar libros por título
                    2 - listar libros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos en un determinado año
                    5 - listar libros por idioma
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibrosPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    menuListarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción Inválida");
            }
        }
    }

    private Datos getDatosLibro() {
        System.out.println("Por favor escribe el nombre del libro que deseas buscar");
        //Busca los datos generales del libro
        tituloLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        System.out.println(json);
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        //System.out.println("Datos de la serie: " + datos);
        return datos;
    }

    private void buscarLibrosPorTitulo() {
        Datos datos = getDatosLibro();
        Optional<DatosLibro> libroBuscado = datos.resultados().stream()
                .filter(l->l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .peek(l-> System.out.println("Filtro Mayúscula (m>M)" + l))
                .findFirst();
        if(libroBuscado.isPresent()) {
            System.out.println(" Libro encontrado\n");
            System.out.println("Los datos son: " + libroBuscado.get());
            if(buscarLibroEnDB(libroBuscado.get().titulo())) {
                System.out.println("Libro ya existente");
            } else {
                Libro libro = new Libro(libroBuscado.get());
                repositorio.save(libro);
                System.out.println(libroBuscado.get());
            }

        } else {
            System.out.println("Libro no encontrado");
        }

    }

    private boolean buscarLibroEnDB(String tituloEnBD){
        libroBuscado = repositorio.findByTitulo(tituloEnBD);
        if(libroBuscado.isPresent()){
            if(libroBuscado.equals(tituloEnBD));
            return true;
        } else{
            return false;
        }
    }

    private void menuListarLibrosPorIdioma() {
        var opcionIdioma = "";
        while (!(opcionIdioma.equals("es")||opcionIdioma.equals("en")||opcionIdioma.equals("fr")||opcionIdioma.equals("pt"))) {
            var menuIdioma = """
                    Ingrese el idioma para buscar los libros:
                    es - español
                    en - inglés
                    fr - francés
                    pt - portugués
                    """;
            System.out.println(menuIdioma);
            opcionIdioma = teclado.nextLine();
            teclado.nextLine();

            switch (opcionIdioma) {
                case "es":
                    buscarLibrosSPANISH();
                    break;
                case "en":
                    buscarLibrosENGLISH();
                    break;
                case "fr":
                    buscarLibrosFRENCH();
                    break;
                case "pt":
                    buscarLibrosPORTUGUESE();
                    break;
                default:
                    System.out.println("Opción Inválida");
            }
        }
    }

    private void listarLibrosRegistrados(){

    };

    private void listarAutoresRegistrados(){

    };

    private void listarAutoresVivos(){

    }

    private void buscarLibrosSPANISH(){

    }

    private void buscarLibrosENGLISH(){

    }

    private void buscarLibrosFRENCH(){

    }

    private void buscarLibrosPORTUGUESE(){

    };

}
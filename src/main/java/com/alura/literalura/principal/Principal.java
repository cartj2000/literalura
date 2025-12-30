package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.LibroService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    //private LibroRepository repositorio;
    private List<DatosLibro> datosLibroList = new ArrayList<>();
    private List<Libro> librosLista;
    private String tituloLibro;
    private Optional<String> libroBuscadoOptional;
    private final LibroService libroService;

    public Principal(LibroService libroService) {
        this.libroService = libroService;
    }
    //public Principal(LibroRepository repository) {
    //    this.repositorio = repository;
    //}

    public void muestraElMenu() {
        String entrada;
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Elija la opción a través de su número:
                    1 - buscar libros por título
                    2 - listar libros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos en un determinado año
                    5 - listar libros por idioma
                    6 - top 10 libros más descargados
                    7 - listar autores por nombre
                    8 - listar autores por nacimiento
                    9 - listar autores por fallecimiento
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            System.out.println("Opcion: ");
            entrada = teclado.nextLine().trim();

            // Validación: solo números
            if (!entrada.matches("\\d+")) {
                System.out.println("Debe ingresar un número válido\n");
                continue;
            }

            opcion = Integer.parseInt(entrada);

            switch (opcion) {
                case 1 -> buscarLibrosPorTitulo();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivos();
                case 5 -> menuListarLibrosPorIdioma();
                case 6 -> top10LibrosMasDescargados();
                case 7 -> listarAutorPorNombre();
                case 8 -> listarAutorPorNacimiento();
                case 9 -> listarAutorPorFallecimiento();
                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción inválida\n");
            }
        }
    }

    private Optional<Datos> getDatosIniciales() {
        System.out.println("Por favor escribe el nombre del libro que deseas buscar");
        //Busca los datos iniciales
        tituloLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(
                URL_BASE + "?search=" +
                        tituloLibro.replace(" ", "+"));
        System.out.println("JSON: " + json);
        Datos datos = conversor.obtenerDatos(json, Datos.class);

        if (datos.resultados() == null || datos.resultados().isEmpty()) {
                return Optional.empty();
        }

        System.out.println("Datos Iniciales: " + datos);
        return Optional.of(datos);
    }

    private void buscarLibrosPorTitulo() {
        Optional<Datos> datos = getDatosIniciales();
        if (datos.isEmpty()){
            System.out.println("Libro no encontrado");
            return;
        } else {
            Optional<DatosLibro> libroBuscadoOptional = datos.get().resultados().stream()
                    .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                    .peek(l -> System.out.println("Filtro Mayúscula (m>M)" + l))
                    .findFirst();
            if(libroBuscadoOptional.isPresent()) {
                System.out.println(" Libro encontrado\n");
                System.out.println("Los datos son: " + libroBuscadoOptional.get());
                String tituloReal = libroBuscadoOptional.get().titulo();
                //if(repositorio.existsByTitulo(libroBuscadoOptional.get().titulo())){
                //if(buscarLibroEnDB(libroBuscadoOptional.get().titulo())) {
                if(libroService.existeLibro(tituloReal)){
                    System.out.println("No se puede registrar el mismo libro más de una vez");
                    return;
                } else {
                    //Libro libro = new Libro(libroBuscadoOptional.get());
                    //repositorio.save(libro);
                    libroService.guardarLibro(libroBuscadoOptional.get());
                    System.out.println("----- LIBRO -----\n");
                    System.out.println("Título: " + libroBuscadoOptional.get().titulo() + "\n");
                    System.out.println("Autor: " + libroBuscadoOptional.get().autoresLista() + "\n");
                    System.out.println("Idioma: " + libroBuscadoOptional.get().idiomas() + "\n");
                    System.out.println("Número de descargas: " + libroBuscadoOptional.get().numeroDeDescargas() + "\n");
                    System.out.println("-----------------\n");
                }
            } else {
                System.out.println("Libro no encontrado");
            }
        }
    }

    //private boolean buscarLibroEnDB(String tituloEnBD){
    //    return repositorio.findByTitulo(tituloEnBD).isPresent();
    //}

    private void menuListarLibrosPorIdioma() {
        String opcionIdioma = "";
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
        }
        //try {
        //    Categoria categoriaIdioma = Categoria.fromString(opcionIdioma);
        //    listarLibrosporIdioma(categoriaIdioma);
        //} catch (IllegalArgumentException e) {
        //    System.out.println("Idioma inválido.");
        //}
        listarLibrosporIdioma(opcionIdioma);
    }

    private void listarLibrosRegistrados(){
        libroService.sacarLibrosRegistrados();
    }

    private void listarAutoresRegistrados(){
        libroService.listarAutoresConLibros();
    }

    private void listarAutoresVivos(){
        System.out.println("Ingrese el año vivo de autor(es) que desea buscar");
        String entrada = teclado.nextLine();
        try{
            Integer yearParaConsulta = Integer.parseInt(entrada);
            if(yearParaConsulta > LocalDate.now().getYear()){
                System.out.println("Ingrese un año válido");
                return;
            }
            libroService.autoresVivosPorYear(yearParaConsulta);
        } catch (NumberFormatException e){
            System.out.println("Entrada inválida. Por favor ingrese un año válido (solo números).");
        }
    }

    private void listarLibrosporIdioma(String idiomaParaConsulta){
        libroService.librosPorIdioma(idiomaParaConsulta);
    }

    private void top10LibrosMasDescargados(){
        libroService.librosMasDescargados();
    }

    private void listarAutorPorNombre(){
        System.out.println("Ingrese el nombre del autor que desea buscar");
        String entrada = teclado.nextLine().trim();
        if(entrada.isEmpty()){
            System.out.println("Ingrese un nombre válido");
            return;
        }
        if (!entrada.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ, ]+")) {
            System.out.println("El nombre solo puede contener letras, espacios y coma");
            return;
        }

        //entrada = entrada.replaceAll("\\s+", " ");

        libroService.autorPorNombre(entrada);
    }

    private void listarAutorPorNacimiento(){
        System.out.println("Ingrese el año de nacimiento que desea buscar");
        String entrada = teclado.nextLine();
        try{
            Integer yearParaConsulta = Integer.parseInt(entrada);
            if(yearParaConsulta > LocalDate.now().getYear()){
                System.out.println("Ingrese un año válido");
                return;
            }
            libroService.autorPorNacimiento(yearParaConsulta);
        } catch (NumberFormatException e){
            System.out.println("Entrada inválida. Por favor ingrese un año válido (solo números).");
        }
    }

    private void listarAutorPorFallecimiento(){
        System.out.println("Ingrese el año de fallecimiento que desea buscar");
        String entrada = teclado.nextLine();
        try{
            Integer yearParaConsulta = Integer.parseInt(entrada);
            libroService.autorPorFallecimiento(yearParaConsulta);
            if(yearParaConsulta > LocalDate.now().getYear()){
                System.out.println("Ingrese un año válido");
                return;
            }
        } catch (NumberFormatException e){
            System.out.println("Entrada inválida. Por favor ingrese un año válido (solo números).");
        }
    }


}
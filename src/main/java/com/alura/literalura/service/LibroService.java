package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;

    public LibroService(LibroRepository libroRepository,
                        AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public Libro guardarLibro(DatosLibro datosLibro) {

        Libro libro = new Libro(datosLibro);


        //List<Autor> autores = new ArrayList<>();
        //for (DatosAutor datosAutor : datosLibro.autoresLista()) {
        //    Autor autor = new Autor(datosAutor);
        //    autores.add(autor);
        //}
        //libro.autoresLista = autores;

        //libro.autoresLista = datosLibro.autoresLista().stream()
        //.map(datosAutor -> new Autor(datosAutor)) // detalle para generar Autor
        ////.map(Autor::new) // versión resumida reemplaza línea anterior
        //.collect(Collectors.toList());
        //libro.autoresLista.forEach(a->a.setLibros(libro.setAutoresLista()));
/*
        List<Autor> autores = datosLibro.autoresLista().stream()
                .map(datosAutor -> {
                    return autorRepository
                            .findByNombre(datosAutor.nombre())
                            .orElseGet(() -> autorRepository.save(new Autor(datosAutor)));
                })
                .toList();
        libro.setAutoresLista(autores);
*/
        List<Autor> autores = datosLibro.autoresLista().stream()
                .map(datosAutor ->
                        autorRepository.findByNombre(datosAutor.nombre())
                                .orElseGet(() ->
                                        autorRepository.save(new Autor(datosAutor))
                                )
                )
                .toList();
        libro.setAutoresLista(autores);

        return libroRepository.save(libro);
    }

    public boolean existeLibro(String titulo) {
        return libroRepository.existsByTituloIgnoreCase(titulo.trim());
        //return libroRepository.existsByTitulo(titulo);
    }

    //public List<Libro> sacarLibrosRegistrados(){
    public void sacarLibrosRegistrados(){
        List<Libro> librosAListar = libroRepository.findAll();
        if (librosAListar.isEmpty()) {
            System.out.println("No hay libros registrados");
            return;
        }

        //librosAListar.stream()
        //        .forEach(libro -> System.out.println(libro));
        //librosAListar.stream().forEach(System.out::println);
        //librosAListar.forEach(System.out::println);

        librosAListar.forEach(libro -> {
            System.out.println("------- LIBRO -------");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autores: " + libro.getAutoresLista());
            System.out.println("Idioma: " + libro.getIdiomas());
            System.out.println("Descargas: " + libro.getNumeroDeDescargas());
            System.out.println("---------------------");
        });

    }

    @Transactional
    //public List<Autor> listarAutoresConLibros(){
    public void listarAutoresConLibros(){
        List<Autor> autoresAListar = autorRepository.findAll();
        if (autoresAListar.isEmpty()) {
            System.out.println("No hay autores registrados");
            return;
        }

        //autoresAListar.stream()
        //        .forEach(autor -> System.out.println(libro));
        //autoresAListar.stream().forEach(System.out::println);
        //autoresAListar.forEach(System.out::println);

        autoresAListar.forEach(autor -> {
            System.out.println("------- AUTOR -------");
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " + autor.getA_Nacimiento());
            System.out.println("Fecha de fallecimiento: " + autor.getA_Fallecimiento());
            System.out.println("Libros: " +
                    autor.getLibros().stream()
                            .map(Libro::getTitulo)
                            .toList());
            System.out.println("---------------------");
        });
    }

    @Transactional
    //public List<Autor> listarAutoresVivos(){
    public void autoresVivosPorYear(Integer year){
        List<Autor> autoresAListar = autorRepository.consultarAutoresVivos(year);
        if (autoresAListar.isEmpty()) {
            System.out.println("No hay Autores vivos en el año: " + year);
            return;
        }

        //autoresAListar.stream()
        //        .forEach(autor -> System.out.println(libro));
        //autoresAListar.stream().forEach(System.out::println);
        //autoresAListar.forEach(System.out::println);

        autoresAListar.forEach(autor -> {
            System.out.println("------- AUTOR -------");
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " + autor.getA_Nacimiento());
            System.out.println("Fecha de fallecimiento: " + autor.getA_Fallecimiento());
            System.out.println("Libros: " +
                    autor.getLibros().stream()
                            .map(Libro::getTitulo)
                            .toList());
            System.out.println("---------------------");
        });
    }

    //public List<Libro> librosPorIdioma(String idioma){
    public void librosPorIdioma(String idioma){
        List<Libro> librosAListar = libroRepository.consultarLibrosPorIdioma(idioma);
        if (librosAListar.isEmpty()) {
            System.out.println("No hay libros registrados");
            return;
        }

        //librosAListar.stream()
        //        .forEach(libro -> System.out.println(libro));
        //librosAListar.stream().forEach(System.out::println);
        //librosAListar.forEach(System.out::println);


        librosAListar.forEach(libro -> {
            System.out.println("------- LIBRO -------");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autores: " + libro.getAutoresLista());
            System.out.println("Idioma: " + libro.getIdiomas());
            System.out.println("Descargas: " + libro.getNumeroDeDescargas());
            System.out.println("---------------------");
        });

        //Trabajando con estadisticas
        IntSummaryStatistics est = librosAListar.stream()
                .collect(Collectors.summarizingInt(libro->libro.getTitulo().length()));
        System.out.println("------------------------------------");
        System.out.println("Cantidad de libros por idioma: " + est.getCount());
        System.out.println("------------------------------------");

    }

    public void librosMasDescargados(){
        List<Libro> librosAListar = libroRepository.findAll();
        if (librosAListar.isEmpty()) {
            System.out.println("No hay libros registrados");
            return;
        }

        //librosAListar.stream()
        //        .forEach(libro -> System.out.println(libro));
        //librosAListar.stream().forEach(System.out::println);
        //librosAListar.forEach(System.out::println);

        System.out.println("------------------------------------");
        System.out.println("Top 10 libros más descargados");
        System.out.println("------------------------------------");
        librosAListar.stream()
                .sorted(Comparator.comparing(Libro::getNumeroDeDescargas).reversed())
                .limit(10)
                .map(l->l.getTitulo().toUpperCase())
                .forEach(System.out::println);

        //Trabajando con estadisticas
        DoubleSummaryStatistics est = librosAListar.stream()
                .sorted(Comparator.comparing(Libro::getNumeroDeDescargas).reversed())
                .limit(10)
                .filter(l -> l.getNumeroDeDescargas() > 0.0)
                .collect(Collectors.summarizingDouble(Libro::getNumeroDeDescargas));
        System.out.println("------------------------------------");
        System.out.println("Media de descargas: " + est.getAverage());
        System.out.println("Cantidad máxima de descargas: " + est.getMax());
        System.out.println("Cantidad mínima de descargas: " + est.getMin());
        System.out.println("Cantidad de registros evaluados para calcular las estadisticas: " + est.getCount());
        System.out.println("------------------------------------");
    }

    @Transactional
    public void autorPorNombre(String nombre){

        if (nombre == null || nombre.isBlank()) {
            System.out.println("Nombre inválido");
            return;
        }

        //List<Autor> autoresAListar = autorRepository.findAutorByNombre(nombre);
        //List<Autor> autoresAListar = autorRepository.findByNombreIgnoreCase(nombre);



        String textoNormalizado = nombre
                .toLowerCase()
                .replace(",", " ")
                .replaceAll("\\s+", " ")
                .trim();

        //String[] partes = textoNormalizado.split(" ");
        String palabraClave = textoNormalizado.split(" ")[0];

        //// Buscar autores que contengan todas las partes
        //List<Autor> autoresAListar = autorRepository.findAll().stream()
        //        .filter(autor -> {
        //            String nombreAutor = autor.getNombre().toLowerCase();
        //            for (String parte : partes) {
        //                if (!nombreAutor.contains(parte)) {
        //                    return false;
        //                }
        //            }
        //            return true;
        //        })
        //        .toList();
        List<Autor> autoresAListar = autorRepository
                .findByNombreContainingIgnoreCase(palabraClave)
                .stream()
                .filter(a -> Arrays.stream(textoNormalizado.split(" "))
                        .allMatch(p -> a.getNombre().toLowerCase().contains(p)))
                .toList();



        if (autoresAListar.isEmpty()) {
            System.out.println("No se encontraron autores");
            return;
        }

        //autoresAListar.stream()
        //        .forEach(autor -> System.out.println(libro));
        //autoresAListar.stream().forEach(System.out::println);
        //autoresAListar.forEach(System.out::println);

        autoresAListar.forEach(autor -> {
            System.out.println("------- AUTOR -------");
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " + autor.getA_Nacimiento());
            System.out.println("Fecha de fallecimiento: " + autor.getA_Fallecimiento());
            System.out.println("Libros: " +
                    autor.getLibros().stream()
                            .map(Libro::getTitulo)
                            .toList());
            System.out.println("---------------------");
        });
    }

    @Transactional
    public void autorPorNacimiento(Integer year){
        List<Autor> autoresAListar = autorRepository.findAutorByANacimiento(year);
        if (autoresAListar.isEmpty()) {
            System.out.println("No hay autores registrados");
            return;
        }

        //autoresAListar.stream()
        //        .forEach(autor -> System.out.println(libro));
        //autoresAListar.stream().forEach(System.out::println);
        //autoresAListar.forEach(System.out::println);

        autoresAListar.forEach(autor -> {
            System.out.println("------- AUTOR -------");
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " + autor.getA_Nacimiento());
            System.out.println("Fecha de fallecimiento: " + autor.getA_Fallecimiento());
            System.out.println("Libros: " +
                    autor.getLibros().stream()
                            .map(Libro::getTitulo)
                            .toList());
            System.out.println("---------------------");
        });
    }

    @Transactional
    public void autorPorFallecimiento(Integer year){
        List<Autor> autoresAListar = autorRepository.findAutorByAFallecimiento(year);
        if (autoresAListar.isEmpty()) {
            System.out.println("No hay autores registrados");
            return;
        }

        //autoresAListar.stream()
        //        .forEach(autor -> System.out.println(libro));
        //autoresAListar.stream().forEach(System.out::println);
        //autoresAListar.forEach(System.out::println);

        autoresAListar.forEach(autor -> {
            System.out.println("------- AUTOR -------");
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " + autor.getA_Nacimiento());
            System.out.println("Fecha de fallecimiento: " + autor.getA_Fallecimiento());
            System.out.println("Libros: " +
                    autor.getLibros().stream()
                            .map(Libro::getTitulo)
                            .toList());
            System.out.println("---------------------");
        });
    }

}

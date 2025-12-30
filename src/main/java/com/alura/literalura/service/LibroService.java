package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Categoria;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public void librosPorIdioma(Categoria idioma){
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

    }

}

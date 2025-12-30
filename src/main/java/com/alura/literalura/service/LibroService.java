package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

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
        return libroRepository.existsByTitulo(titulo);
    }

    public List<Libro> listarLibrosRegistrados(){
        return libroRepository.findAll();
    }

}

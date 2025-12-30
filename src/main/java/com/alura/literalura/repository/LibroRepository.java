package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Categoria;
import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    boolean existsByTituloIgnoreCase(String titulo);
    //boolean existsByTitulo(String titulo);
    Optional<Libro> findByTitulo(String titulo);

    @Query("SELECT l FROM Libro l WHERE l.idiomas = :idioma")
    List<Libro> consultarLibrosPorIdioma(Categoria idioma);

}

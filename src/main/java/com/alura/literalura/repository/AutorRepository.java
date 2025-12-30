package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface AutorRepository  extends JpaRepository<Autor, Long> {
    boolean existsByNombre(String nombre);
    Optional<Autor> findByNombre(String nombre);

    //@Query("SELECT a FROM Autor a WHERE LOWER(a.nombre) = LOWER(:nombre)")
    //@Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros WHERE UPPER(a.nombre) = UPPER(:nombre)")
    //List<Autor> findAutorByNombre(String nombre);
    //List<Autor> findByNombreIgnoreCase(String nombre);
    List<Autor> findByNombreContainingIgnoreCase(String texto);

    @Query("SELECT a FROM Autor a WHERE a.a_Nacimiento <= :year AND (a.a_Fallecimiento IS NULL OR a.a_Fallecimiento >= :year)")
    List<Autor> consultarAutoresVivos(Integer year);

    @Query("SELECT a FROM Autor a WHERE a.a_Nacimiento = :year")
    List<Autor> findAutorByANacimiento(Integer year);

    @Query("SELECT a FROM Autor a WHERE a.a_Fallecimiento = :year")
    List<Autor> findAutorByAFallecimiento(Integer year);

}

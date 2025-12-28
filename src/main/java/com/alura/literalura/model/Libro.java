package com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autoresLista;
    @Enumerated(EnumType.STRING)
    private Categoria idiomas;
    private Double numeroDeDescargas;

    public Libro(){

    }

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.autoresLista = datosLibro.autoresLista();
        this.idiomas = datosLibro.idiomas();
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    @Override
    public String toString() {
        return
                "titulo=" + titulo +
                        ", titulo='" + titulo + '\'' +
                        ", autoresLista=" + autoresLista + '\'' +
                        ", idiomas=" + idiomas +
                        ", numeroDeDescargas='" + numeroDeDescargas;
    }
}


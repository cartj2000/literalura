package com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

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
        this.autoresLista = datosLibro.autoresLista().stream()

        //List<Autor> autores = new ArrayList<>();
        //for (DatosAutor datosAutor : datosLibro.autoresLista()) {
        //    Autor autor = new Autor(datosAutor);
        //    autores.add(autor);
        //}
        //this.autoresLista = autores;

                //.map(datosAutor -> new Autor(datosAutor)) // para generar Autor

                .map(Autor::new)
                .collect(Collectors.toList());
        this.autoresLista.forEach(a->a.setLibro(this));
        this.idiomas = Categoria.fromString(datosLibro.idiomas().get(0));
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutoresLista() {
        return autoresLista;
    }

    public void setAutoresLista(List<Autor> autoresLista) {
        this.autoresLista = autoresLista;
    }

    public Categoria getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Categoria idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
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


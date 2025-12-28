package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombre;
    private Integer a_Nacimiento;
    private Integer a_Fallecimiento;
    @ManyToOne
    private Libro libro;

    public Autor(){

    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.a_Nacimiento = datosAutor.a_Nacimiento();
        this.a_Fallecimiento = datosAutor.a_fallecimiento();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getA_Nacimiento() {
        return a_Nacimiento;
    }

    public void setA_Nacimiento(Integer a_Nacimiento) {
        this.a_Nacimiento = a_Nacimiento;
    }

    public Integer getA_Fallecimiento() {
        return a_Fallecimiento;
    }

    public void setA_Fallecimiento(Integer a_Fallecimiento) {
        this.a_Fallecimiento = a_Fallecimiento;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return
                "nombre=" + nombre + '\'' +
                        ", a_Nacimiento=" + a_Nacimiento +
                        ", a_Fallecimiento=" + a_Fallecimiento;
    }
}


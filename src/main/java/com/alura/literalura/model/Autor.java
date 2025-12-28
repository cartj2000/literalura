package com.alura.literalura.model;

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

    @Override
    public String toString() {
        return
                "nombre=" + nombre + '\'' +
                        ", a_Nacimiento=" + a_Nacimiento +
                        ", a_Fallecimiento=" + a_Fallecimiento;
    }
}


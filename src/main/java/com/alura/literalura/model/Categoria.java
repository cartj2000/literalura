package com.alura.literalura.model;

public enum Categoria {
    SPANISH("es"),
    ENGLISH("en"),
    FRENCH("fr"),
    PORTUGUESE("pt");

    private String idioma;

    Categoria (String idioma) {
        this.idioma = idioma;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.idioma.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

}

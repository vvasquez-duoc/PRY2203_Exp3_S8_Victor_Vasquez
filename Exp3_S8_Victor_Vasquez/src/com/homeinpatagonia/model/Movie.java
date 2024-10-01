package com.homeinpatagonia.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Movie {
    private int id;
    private String titulo;
    private String director;
    private int anno;
    private int duracion;
    private List<String> generos;

    // Constructor por defecto
    public Movie() {
        this.generos = new ArrayList<>();
    }

    // Constructor con parámetros
    public Movie(int id, String titulo, String director, int anno, int duracion) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.anno = anno;
        this.duracion = duracion;
        this.generos = new ArrayList<>();
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public List<String> getGeneros() {
        return new ArrayList<>(generos);
    }

    public void setGeneros(List<String> generos) {
        this.generos = new ArrayList<>(generos);
    }

    public void addGenero(String genero) {
        if (!this.generos.contains(genero)) {
            this.generos.add(genero);
        }
    }

    public void removeGenero(String genero) {
        this.generos.remove(genero);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
               anno == movie.anno &&
               duracion == movie.duracion &&
               Objects.equals(titulo, movie.titulo) &&
               Objects.equals(director, movie.director) &&
               Objects.equals(generos, movie.generos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, director, anno, duracion, generos);
    }

    @Override
    public String toString() {
        return "Movie{" +
               "id=" + id +
               ", titulo='" + titulo + '\'' +
               ", director='" + director + '\'' +
               ", anno=" + anno +
               ", duracion=" + duracion +
               ", generos=" + generos +
               '}';
    }
}
package com.proyecto.literalura.entity;

import com.proyecto.literalura.model.Autor;
import com.proyecto.literalura.model.Libro;
import jakarta.persistence.*;

@Entity
@Table(name = "Libro")
public class LibroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String lenguaje;
    private Integer descargas;
    @OneToOne(mappedBy = "libros", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AutorEntity autor;

    public LibroEntity() {

    }

    public LibroEntity(Libro libro) {
        this.titulo = libro.title();
        this.descargas = libro.download();
        if (!libro.languages().isEmpty())
            this.lenguaje = libro.languages().get(0);
        if (!libro.autores().isEmpty()) {
            for (Autor autor : libro.autores()) {
                this.autor = new AutorEntity(autor);
                break;
            }
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return "LibroEntity [id=" + id + ", titulo=" + titulo + ", lenguaje=" + lenguaje + ", descargas=" + descargas
                + ", autores=" + autor + "]";
    }

    public AutorEntity getAutor() {
        return autor;
    }

    public void setAutor(AutorEntity autor) {
        this.autor = autor;
    }

}

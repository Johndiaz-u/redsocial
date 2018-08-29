package com.final_proy.Clases;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table (name = "ETIQUETA")
public class Etiqueta implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    @Expose
    private int id;
    @Column(name = "TAG")
    @Expose
    private String etiqueta;

    @ManyToMany(mappedBy = "etiquetas", cascade = CascadeType.MERGE)
    private List<Post> posts = new ArrayList<>();

    public Etiqueta() {
    }

    public Etiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}

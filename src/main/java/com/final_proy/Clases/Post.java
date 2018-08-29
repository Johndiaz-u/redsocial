package com.final_proy.Clases;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "POST")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @Expose
    private int id;

    @Column(name = "CUERPO", length = 100000)
    @Expose
    private String cuerpo;

    @Column(name = "IMAGEN", length = 10000000)
    @Expose
    private String imagen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USUARIO_ID")
    @Expose
    private  Usuario usuario;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post", cascade = CascadeType.REMOVE)
    @Expose
    private List<Comentario> comentarios = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "POST_ETIQUETA", joinColumns = {@JoinColumn(name = "POST_ID")}, inverseJoinColumns = {@JoinColumn(name = "ETIQUETA_ID")})
    @Expose
    private List<Etiqueta> etiquetas = new ArrayList<>();

    @Column(name = "FECHA")
    @Expose
    private LocalDate fecha;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "LIKES", joinColumns = {@JoinColumn(name = "POST_ID")}, inverseJoinColumns = {@JoinColumn(name = "USUARIO_ID")})
    @Expose
    private List<Usuario> likes = new ArrayList<>();


    public Post() {
    }

    public Post(String cuerpo, String imagen, Usuario usuario, List<Comentario> comentarios, LocalDate fecha, List<Etiqueta> etiquetas, List<Usuario> likes) {
        this.cuerpo = cuerpo;
        this.imagen = imagen;
        this.usuario = usuario;
        this.comentarios = comentarios;
        this.fecha = fecha;
        this.etiquetas = etiquetas;
        this.likes = likes;
    }

    public Post(String cuerpo, String imagen, Usuario usuario) {
        this.cuerpo = cuerpo;
        this.imagen = imagen;
        this.usuario = usuario;
    }

    public  int getNumLikes(){
        return this.getLikes().size();
    }
    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<Usuario> getLikes() {
        return likes;
    }

    public void setLikes(List<Usuario> likes) {
        this.likes = likes;
    }
}


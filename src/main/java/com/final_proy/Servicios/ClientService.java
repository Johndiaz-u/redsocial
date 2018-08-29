package com.final_proy.Servicios;

import com.final_proy.Clases.Post;
import com.final_proy.Clases.Usuario;
import org.jasypt.util.password.StrongPasswordEncryptor;
import java.time.LocalDate;
import java.util.List;

public class ClientService {
    public List<Post> getAllPost(){
        return MantenimientoPost.getInstancia().findAll();
    }

    public List<Usuario> getAllUsers(){
        return  MantenimientoUsuario.getInstancia().findAll();
    }

    public  List<Post> getPostsByUser(String username){
        Usuario usuario = MantenimientoUsuario.getInstancia().find(username);
        return usuario.getPosts();
    }

    public Post setPost(String username, String imagen, String cuerpo){
        Usuario usuario = MantenimientoUsuario.getInstancia().find(username);
        Post post = new Post();
        post.setUsuario(usuario);
        post.setImagen(imagen);
        post.setCuerpo(cuerpo);
        MantenimientoPost.getInstancia().crear(post);
        return post;
    }


    public boolean crearCliente(Usuario usuario){

        //Creamos al usuario:
        MantenimientoUsuario.getInstancia().crear(usuario);

        return true;
    }

}

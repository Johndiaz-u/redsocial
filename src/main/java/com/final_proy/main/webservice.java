package com.final_proy.main;
import com.final_proy.Clases.Post;
import com.final_proy.Clases.Usuario;
import com.final_proy.Servicios.MantenimientoPost;
import com.final_proy.Servicios.MantenimientoUsuario;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import static com.final_proy.main.JsonUtil.json;
import static spark.Spark.*;


public class webservice{
    public webservice(){

    }


    public void start() {

        File uploadDir = new File("upload");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist
        externalStaticFileLocation("upload");


        get("/webapi/timeline/:nombre", (request, response) -> {

            Usuario usuario = MantenimientoUsuario.getInstancia().find(request.params(":nombre"));
            ArrayList<Post> listaPost = new ArrayList<Post>(usuario.getPosts());

            return listaPost;
        }, json());


        post("/crearpost/:usuario/:imagen/:contenido/", (request, response) -> {

                    Post nu = new Post();
                    Usuario user = MantenimientoUsuario.getInstancia().find(request.params(":usuario"));
                    nu.setUsuario(user);
                    nu.setCuerpo(request.params("contenido"));
                    nu.setFecha(LocalDate.now());
                    nu.setImagen(request.params("imagen"));

                    MantenimientoPost.getInstancia().crear(nu);


            return "File uploaded";
        });
        }

    }

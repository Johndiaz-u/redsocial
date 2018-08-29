package com.final_proy.Servicios;

import com.final_proy.Clases.Post;

public class MantenimientoPost extends GestionDB<Post>{
    private static MantenimientoPost instancia;

    private MantenimientoPost() {
        super(Post.class);
    }

    public static MantenimientoPost getInstancia(){
        if(instancia==null){
            instancia = new MantenimientoPost();
        }
        return instancia;
    }

}

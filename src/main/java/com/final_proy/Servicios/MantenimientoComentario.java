package com.final_proy.Servicios;

import com.final_proy.Clases.Comentario;


public class MantenimientoComentario  extends GestionDB<Comentario> {
    private static MantenimientoComentario instancia;

    private MantenimientoComentario() {
        super(Comentario.class);
    }

    public static MantenimientoComentario getInstancia(){
        if(instancia==null){
            instancia = new MantenimientoComentario();
        }
        return instancia;
    }
}

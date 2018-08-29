package com.final_proy.Servicios;

import com.final_proy.Clases.Etiqueta;


public class MantenimientoEtiqueta extends GestionDB<Etiqueta> {
    private static MantenimientoEtiqueta instancia;

    private MantenimientoEtiqueta() {
        super(Etiqueta.class);
    }

    public static MantenimientoEtiqueta getInstancia(){
        if(instancia==null){
            instancia = new MantenimientoEtiqueta();
        }
        return instancia;
    }
}

package com.final_proy.Servicios;

import com.final_proy.Clases.Usuario;



public class MantenimientoUsuario extends GestionDB<Usuario> {

private static MantenimientoUsuario instancia;

        private MantenimientoUsuario() {
            super(Usuario.class);
        }

        public static MantenimientoUsuario getInstancia(){
            if(instancia==null){
            instancia = new MantenimientoUsuario();
            }
            return instancia;
        }

        public static void setInstancia(MantenimientoUsuario instancia) {
            MantenimientoUsuario.instancia = instancia;
        }

}


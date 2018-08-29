package com.final_proy.main;

import com.final_proy.Clases.Usuario;

import static spark.Spark.after;
import static spark.Spark.before;


//creado sin computador, txt plain, validar 
public class Filtro {
    // metodo que aplica los filtros
    public void aplicarFiltros() {


//valido los lugares donde solo los usuario registrado pueden entrar 
        before("/home", (request, response) -> {
            Usuario usuario = request.session().attribute("usuario");
            if (usuario == null) {

                response.redirect("/");
            }
        });
        before("/crearpost", (request, response) -> {
            Usuario usuario = request.session().attribute("usuario");
            if (usuario == null) {

                response.redirect("/");
            }
        });
        after("/webapi",(req, res) -> {
            res.type("application/json");
        });


    }
}

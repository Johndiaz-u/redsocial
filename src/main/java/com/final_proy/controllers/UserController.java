package com.final_proy.controllers;
import com.final_proy.Servicios.ClientService;

import static spark.Spark.get;

public class UserController {

    public UserController(final ClientService ClientService) {
        // Método para tratar los gets de /users
        get("/users", (request, response) -> ClientService.getAllUsers());
        
        get("/listarPost", (request, response) -> ClientService.getPostsByUser("Antonio"));
    }
}

package com.final_proy.Servicios;

import static com.final_proy.main.JsonUtil.json;
import static spark.Spark.*;

public class ClientController {
    public ClientController(final ClientService clientService) {

        get("/webservices/usuarios", (req, res) -> clientService.getAllUsers(), json());

        get("/webservices/posts", (req, res) -> clientService.getAllPost(), json());

        get("/webservices/posts/:username", (req, res) -> clientService.getPostsByUser(req.params("username")), json());

        get("/webservices/createpost/:username/:cuerpo/:imagen", (req, resp) -> {

            return clientService.setPost(req.params("username"), req.params("imagen"), req.params("cuerpo"));
        }, json());

    }


}


package com.final_proy.main;

import com.final_proy.Clases.*;
import com.final_proy.Servicios.*;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;

import javax.persistence.NoResultException;
import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Main {

    public static void main(String[] args) {

        //Asignando el HerokuPort:
        port(getHerokuAssignedPort());

        staticFileLocation("/Recursos");
        enableDebugScreen();
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/template");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);

        //variables para manejo de imagenes
        File uploadDir = new File("upload");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist
        externalStaticFileLocation("upload");
        new ClientController(new ClientService());


        //iniciamos la base de datos
        try {
            BaseDatos.startDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Filtro ft = new Filtro();
        ft.aplicarFiltros();


        get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            if(request.session().attribute("usuario") != null){
                response.redirect("/home");

            }
            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);


        get("/home", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            Usuario usuario = request.session().attribute("usuario");
            List<Usuario> followings =  usuario.getFollowing();
            List<Usuario> amigoss = usuario.getAmigos();
            List<Post> listaPost = MantenimientoPost.getInstancia().findAll();
            List<Post> listaFollowing = new ArrayList<Post>();
            List<Post> listaAmigos = new ArrayList<Post>();
            for(Post post: listaPost){
                System.out.println("Entre al loop donde estan todos los post");
                if(followings.size()==0){
                    if (post.getUsuario().getUsername().equals(usuario.getUsername())){
                        listaFollowing.add(post);
                    }
                }else{
                    for(Usuario following: followings){
                        System.out.println("Entre al loop donde estan todos los following");
                        System.out.println("Cantidad de following: " +followings.size());
                        System.out.println("Following actual: " + following.getUsername());
                        System.out.println("Usuario del Post: " + post.getUsuario().getUsername());
                        if (post.getUsuario().getUsername().equals(following.getUsername()) || post.getUsuario().getUsername().equals(usuario.getUsername())){
                            listaFollowing.add(post);
                            break;
                        }
                    }
                }
                if(amigoss.size()==0){
                    if (post.getUsuario().getUsername().equals(usuario.getUsername())){
                        listaAmigos.add(post);
                    }
                }else{
                    for(Usuario amigos: amigoss){
                        System.out.println("Entre al loop donde estan todos los Amigos");
                        System.out.println("Cantidad de amigos: " +amigoss.size());
                        System.out.println("amigo actual: " + amigos.getUsername());
                        System.out.println("Usuario del Post: " + post.getUsuario().getUsername());
                        if (post.getUsuario().getUsername().equals(amigos.getUsername()) || post.getUsuario().getUsername().equals(usuario.getUsername())){
                            listaAmigos.add(post);
                            break;
                        }
                    }
                }


            }
            System.out.println("Listados de post en el timeline: "+listaFollowing.size());
            System.out.println("Listados de post en el timeline: "+listaAmigos.size());
            Collections.reverse(listaFollowing);
            Collections.reverse(listaAmigos);
            attributes.put("posts", listaFollowing);
            attributes.put("posts", listaAmigos);
            attributes.put("usuario", usuario);

            return new ModelAndView(attributes, "timelinevs2.ftl");
        }, freeMarkerEngine);

        get("/usuario/:username", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            Usuario usuario = MantenimientoUsuario.getInstancia().find(request.params("username"));
            List<Usuario> amigoss = usuario.getAmigos();
            List<Post> listaPost = MantenimientoPost.getInstancia().findAll();
            List<Post> listaPostAmigo = new ArrayList<Post>();

            for(Post post: listaPost) {

                if (amigoss.size() == 0) {
                    if (post.getUsuario().getUsername().equals(usuario.getUsername())) {
                        listaPostAmigo.add(post);
                    }
                } else {
                    for (Usuario amigos : amigoss) {

                        if (post.getUsuario().getUsername().equals(amigos.getUsername())) {
                            listaPostAmigo.add(post);
                        }

                    }
                }

            }
            System.out.println(usuario.getUsername());
            int followers =  usuario.getFollowers().size();
            int amigos = usuario.getAmigos().size();
            int following = usuario.getFollowing().size();
            Usuario userInSesion = request.session().attribute("usuario");
            List<Post> listaPostUsuario = usuario.getPosts();
            System.out.println(listaPostUsuario.size());
            Collections.reverse(listaPostUsuario);
            attributes.put("posts", listaPostUsuario);
            attributes.put("posts", listaPostAmigo);
            attributes.put("usuario", usuario);
            attributes.put("amigos",amigos);
            attributes.put("followers", followers);
            attributes.put("following", following);
            attributes.put("usuarioEnSesion", userInSesion);


            return new ModelAndView(attributes, "usuario.ftl");
        }, freeMarkerEngine);

        //todo hacerlo tipo modal.
        get("/usuario/:username/:post_id", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            Usuario usuarioSesion = request.session().attribute("usuario");
            Post post = MantenimientoPost.getInstancia().find(Integer.parseInt(request.params("post_id")));
            //Todo Bug muestra 4 veces el mismo comentario
            System.out.println(post.getComentarios().size());
            attributes.put("post", post);
            attributes.put("usuarioEnSesion", usuarioSesion);


            return new ModelAndView(attributes, "vistaprevia.ftl");
        }, freeMarkerEngine);

        


        //Gets Editar Cuenta:
        get("/editarcuenta", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Usuario usuario = request.session().attribute("usuario");
            attributes.put("usuario", usuario);

            attributes.put("upfile", usuario.getImagen());
            attributes.put("descripcion", usuario.getDescripcion());
            attributes.put("apodo", usuario.getApodo());
            attributes.put("nacimiento", usuario.getNacimiento());
            attributes.put("lugarnaci", usuario.getLugarnaci());
            attributes.put("direccion", usuario.getDireccion());
            attributes.put("estudio", usuario.getEstudio());
            attributes.put("trabajo", usuario.getTrabajo());
            attributes.put("email", usuario.getEmail());
            attributes.put("password", usuario.getPassword());
            attributes.put("password2", "");


            return new ModelAndView(attributes, "editarcuenta.ftl");
        }, freeMarkerEngine);


        //Editar Cuenta TimeLine:

        post("/editarcuenta", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            String error = null;
            Usuario usuario = request.session().attribute("usuario");
            Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            try (InputStream is = request.raw().getPart("upfile").getInputStream()) {
                // Use the input stream to create a file

                Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
                File file = tempFile.toFile();
                FileInputStream imageInFile = new FileInputStream(file);
                String mimetype = request.raw().getPart("upfile").getContentType();
                String type = mimetype.split("/")[0];
                if (type.equals("image")) {
                    if (request.queryParams("password").equals(request.queryParams("password2"))) {
                        usuario.setPassword(request.queryParams("password"));

                        if (request.raw().getPart("upfile").getInputStream().read() != -1) {
                            // Reading a Image file from file system
                            byte imageData[] = new byte[(int)file.length()];
                            imageInFile.read(imageData);

                            // Converting Image byte array into Base64 String
                            String imageDataString = Base64.getEncoder().encodeToString(imageData);
                            usuario.setImagen(imageDataString);
                        }
                        usuario.setDescripcion(request.queryParams("descripcion"));
                        usuario.setEmail(request.queryParams("email"));
                        usuario.setApodo(request.queryParams("apodo"));
                        usuario.setNacimiento(request.queryParams("nacimiento"));
                        usuario.setLugarnaci(request.queryParams("lugarnaci"));
                        usuario.setDireccion(request.queryParams("direccion"));
                        usuario.setEstudio(request.queryParams("estudio"));
                        usuario.setTrabajo(request.queryParams("trabajo"));

                    } else {
                        halt("Contraseña no coincide <a href=\"/editarcuenta\">Volver</a>");
                    }
                } else {
                    //halt("El archivo no es una imagen <a href=\"/editarcuenta\">Volver</a>");
                    usuario.setDescripcion(request.queryParams("descripcion"));
                    usuario.setEmail(request.queryParams("email"));
                    usuario.setApodo(request.queryParams("apodo"));
                    usuario.setNacimiento(request.queryParams("nacimiento"));
                    usuario.setLugarnaci(request.queryParams("lugarnaci"));
                    usuario.setDireccion(request.queryParams("direccion"));
                    usuario.setEstudio(request.queryParams("estudio"));
                    usuario.setTrabajo(request.queryParams("trabajo"));
                }

                MantenimientoUsuario.getInstancia().editar(usuario);

                response.redirect("usuario/" + usuario.getUsername());
                attributes.put("usuario", usuario);

            }

            return new ModelAndView(attributes, "editarcuenta.ftl");
        }, freeMarkerEngine);

        get("/usuarios_registrados", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Usuario usuarioSesion = request.session().attribute("usuario");
            List<Usuario> listaUsuarios = MantenimientoUsuario.getInstancia().findAll();
            List<Usuario> followings = usuarioSesion.getFollowing();
            List<Usuario> amigoss = usuarioSesion.getAmigos();
            System.out.println("Cantidad de Seguidores: " + followings.size());
            System.out.println("Cantidad de Amigos: "+ amigoss.size());
            for (Usuario usuario: listaUsuarios){
                System.out.println("Usuario: " + usuario.getUsername());
                for(Usuario following: followings){
                    System.out.println("Siguiendo: " + following.getUsername());
                    if(usuario.getUsername().equals(following.getUsername())){
                        System.out.println("Dejar de Seguir");
                        break;
                    }else{
                        System.out.println("Seguir");
                        break;
                    }

                }
                for(Usuario amigos: amigoss){
                    System.out.println("Amigo: " + amigos.getUsername());
                    if(usuario.getUsername().equals(amigos.getUsername())){
                        System.out.println("Dejar de ser Amigo");
                        break;
                    }else{
                        System.out.println("Agregar Amigo");
                        break;
                    }
                }

            }

            attributes.put("usuarioSesion",usuarioSesion);
            attributes.put("usuarios", listaUsuarios);
            attributes.put("followings", followings);
            attributes.put("amigoss",amigoss);


            return new ModelAndView(attributes, "usuariosRegistrados.ftl");
        }, freeMarkerEngine);


        /**
         * Login
         */
        get("/login", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            if (request.queryParams("r") != null) {
                attributes.put("message", "Estaba registrado y puede iniciar sesión ahora");
            }
            if (request.queryParams("err") != null) {
                attributes.put("error", "Credenciales no validas...");
            }

            return new ModelAndView(attributes, "singin.ftl");
        }, freeMarkerEngine);

        post("/login", (request, response) -> {
            Map<String, Object> map = new HashMap<>();
            Session session = request.session(true);
            Usuario usuario = MantenimientoUsuario.getInstancia().find(request.queryParams("username"));

            if (usuario == null || !request.queryParams("password").equals(usuario.getPassword())) {
                response.redirect("/login?err=1");
                halt();
            } else {
                session.attribute("usuario", usuario);
                response.redirect("/home");
                halt();
            }
            return null;
        });

        get("/cerrarsesion", (request, response) -> {
            request.session().invalidate();
            response.redirect("/");
            return "sesion cerrada";
        });
        /**
         * registro
         */
        get("/register", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "singin.ftl");
        }, freeMarkerEngine);


        //Validar Campos de Registro --> Login:
        post("/register", (request, response) -> {
            //TODO validar todos los campos
            Map<String, Object> attributes = new HashMap<>();
            String error = null;
            Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            try (InputStream is = request.raw().getPart("upfile").getInputStream()) {
                // Use the input stream to create a file
                Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
                File file = tempFile.toFile();
                FileInputStream imageInFile = new FileInputStream(file);
                byte imageData[] = new byte[(int)file.length()];
                imageInFile.read(imageData);
                String imageDataString = Base64.getEncoder().encodeToString(imageData);
                    if (!request.queryParams("username").equals(null)) {
                        String mimetype = request.raw().getPart("upfile").getContentType();
                        String type = mimetype.split("/")[0];
                        if (type.equals("image")) {
                            Usuario usuario = new Usuario();
                            usuario.setImagen(imageDataString);
                            usuario.setPassword(request.queryParams("password"));
                            usuario.setUsername(request.queryParams("username"));
                            usuario.setApodo(request.queryParams("apodo"));
                            usuario.setNacimiento(request.queryParams("nacimiento"));
                            usuario.setLugarnaci(request.queryParams("lugarnaci"));
                            usuario.setDireccion(request.queryParams("direccion"));
                            usuario.setEstudio(request.queryParams("estudio"));
                            usuario.setTrabajo(request.queryParams("trabajo"));
                            usuario.setDescripcion(request.queryParams("descripcion"));
                            usuario.setEmail(request.queryParams("email"));
                            MantenimientoUsuario.getInstancia().crear(usuario);
                            response.redirect("/login?r=1");
                            halt();
                        } else {
                            halt("El archivo no es una imagen");
                        }
                    } else {
                        error = "Error guardando";
                    }

            }

            attributes.put("error", error);
            attributes.put("username", request.queryParams("username"));
            attributes.put("email", request.queryParams("email"));
            return new ModelAndView(attributes, "singin.ftl");
        }, freeMarkerEngine);

        //Crear Usuario Administrador por defecto:
        ClientService clientService = new ClientService();
        if(clientService.getAllUsers().size() < 1){
            Usuario administrador = new Usuario();
            administrador.setUsername("admin");
            administrador.setAdministrador(true);
            administrador.setPassword("admin");
            administrador.setDescripcion("Este es el Usuario Administrador por defecto");
            administrador.setEmail("joelant@admin.com");


            if(clientService.crearCliente(administrador)){
                System.out.println("El Usuario admin se ha creado automaticamente...");
            }

        }

        //////////////////////////////////////////////////////


        get("/crearpost", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            attributes.put("post", new Post("", "", null, null, null, null, null));
            attributes.put("stringEtiquetas", "");

            return new ModelAndView(attributes, "crearPost.ftl");
        }, freeMarkerEngine);

        post("/crearpost", "multipart/form-data", (request, response) -> {


            //CODIGO PARA GUARDAR LA IMAGEN
            Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            try (InputStream is = request.raw().getPart("upfile").getInputStream()) {
                // Use the input stream to create a file
                Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
                File file = tempFile.toFile();
                FileInputStream imageInFile = new FileInputStream(file);
                byte imageData[] = new byte[(int)file.length()];
                imageInFile.read(imageData);
                String imageDataString = Base64.getEncoder().encodeToString(imageData);
                String mimetype = request.raw().getPart("upfile").getContentType();
                String type = mimetype.split("/")[0];
                if (type.equals("image")) {
                    System.out.println("It's an image");
                    String etiquetasStr = request.queryParams("etiquetas");
                    String etiquetas[] = etiquetasStr.split("\\s*,\\s*");
                    Usuario usuario = request.session().attribute("usuario");
                    Post post = new Post();
                    post.setUsuario(usuario);
                    post.setCuerpo(request.queryParams("contenido"));
                    List<Etiqueta> listaEtiquetas = creacionEtiquetas(etiquetas);
                    post.setEtiquetas(listaEtiquetas);
                    post.setFecha(LocalDate.now());
                    post.setImagen(imageDataString);

                    MantenimientoPost.getInstancia().crear(post);
                    response.redirect("/home");
                } else {
                    //halt("El archivo no es una imagen");
                    String etiquetasStr = request.queryParams("etiquetas");
                    String etiquetas[] = etiquetasStr.split("\\s*,\\s*");
                    Usuario usuario = request.session().attribute("usuario");
                    Post post = new Post();
                    post.setUsuario(usuario);
                    post.setCuerpo(request.queryParams("contenido"));
                    List<Etiqueta> listaEtiquetas = creacionEtiquetas(etiquetas);
                    post.setEtiquetas(listaEtiquetas);
                    post.setFecha(LocalDate.now());
                    post.setImagen(imageDataString);

                    MantenimientoPost.getInstancia().crear(post);
                    response.redirect("/home");
                }
            }

            return "File uploaded";
        });

        post("/crearcomentario", (request, response) -> {


            Map<String, Object> attr = new HashMap<String, Object>();
            String comentarioStr = request.queryParams("comentario");

            Usuario usuario = request.session().attribute("usuario");
            System.out.println(usuario.getUsername());
            Post post = MantenimientoPost.getInstancia().find(Integer.parseInt(request.queryParams("id_post")));
            Comentario comentario = new Comentario(comentarioStr, usuario, post);
            MantenimientoComentario.getInstancia().crear(comentario);
            if (comentarioStr.length() == 0) {
                return "El comentario esta vacio";
            }

            response.redirect("/home");

            return "";
        });

        get("/editarpost/:id_post", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Editar articulo");

            int id_post = Integer.parseInt(request.params("id_post"));
            Post post = MantenimientoPost.getInstancia().find(id_post);
            String etiquetasstr = "";
            System.out.println(post.getEtiquetas().size());
            for (Etiqueta s : post.getEtiquetas()) {
                etiquetasstr += s.getEtiqueta() + ", ";
            }
            System.out.println(etiquetasstr);

            attributes.put("post", post);
            attributes.put("stringEtiquetas", etiquetasstr);


            return new ModelAndView(attributes, "editpost.ftl");
        }, freeMarkerEngine);


        post("/editarpost", (request, response) -> {

            String str = request.queryParams("etiquetas");
            String contenido = request.queryParams("contenido");
            String etiquetas[] = str.split("\\s*,\\s*");
            int id = Integer.parseInt(request.queryParams("id"));

            Post post = MantenimientoPost.getInstancia().find(id);
            post.setCuerpo(contenido);
            post.setEtiquetas(creacionEtiquetas(etiquetas));
            MantenimientoPost.getInstancia().editar(post);

            response.redirect("/home");
            return "";

        });
        get("/agregar_usuario", (request, response) -> {


            Map<String, Object> attributes = new HashMap<>();

            String id_usuario = request.queryParams("id");
            System.out.println(id_usuario);
            Usuario usuarioSesion = request.session().attribute("usuario");
            Usuario usuario = MantenimientoUsuario.getInstancia().find(id_usuario);

            if(!usuarioSesion.getFollowing().contains(usuario)){
                List<Usuario> usuarioSesionFollowings = usuarioSesion.getFollowing();
                List<Usuario> usuarioFollowers = usuario.getFollowers();
                usuarioSesionFollowings.add(usuario);
                usuarioFollowers.add(usuarioSesion);
                MantenimientoUsuario.getInstancia().editar(usuarioSesion);
                MantenimientoUsuario.getInstancia().editar(usuario);
            }

            response.redirect("/home");

            return "";
        });


        get("/unfollow_usuario", (request, response) -> {


            Map<String, Object> attributes = new HashMap<>();

            String id_usuario = request.queryParams("id");
            System.out.println(id_usuario);
            Usuario usuarioSesion = request.session().attribute("usuario");
            Usuario usuario = MantenimientoUsuario.getInstancia().find(id_usuario);
            List<Usuario> nuevaLista = new ArrayList<Usuario>();
            for(Usuario following: usuarioSesion.getFollowing()){
                if(following.getUsername().equals(usuario.getUsername())){

                    MantenimientoUsuario.getInstancia().eliminarfollower(usuarioSesion.getUsername(),usuario.getUsername());

                }else{
                    nuevaLista.add(following);
                }
            }
            usuarioSesion.setFollowing(nuevaLista);
            MantenimientoUsuario.getInstancia().editar(usuarioSesion);

            response.redirect("/home");

            return "";
        });

        get("/agregar_amigo", (request, response) -> {


            Map<String, Object> attributes = new HashMap<>();

            String id_usuario = request.queryParams("id");
            System.out.println(id_usuario);
            Usuario usuarioSesion = request.session().attribute("usuario");
            Usuario usuario = MantenimientoUsuario.getInstancia().find(id_usuario);

            if(!usuarioSesion.getAmigos().contains(usuario)){
                List<Usuario> usuarioSesionAmigos = usuarioSesion.getAmigos();
                List<Usuario> usuarioAgcamigo = usuario.getAgcamigo();
                usuarioSesionAmigos.add(usuario);
                usuarioAgcamigo.add(usuarioSesion);
                MantenimientoUsuario.getInstancia().editar(usuarioSesion);
                MantenimientoUsuario.getInstancia().editar(usuario);
            }

            response.redirect("/home");

            return "";
        });

        get("/desligar_amigo", (request, response) -> {


            Map<String, Object> attributes = new HashMap<>();

            String id_usuario = request.queryParams("id");
            System.out.println(id_usuario);
            Usuario usuarioSesion = request.session().attribute("usuario");
            Usuario usuario = MantenimientoUsuario.getInstancia().find(id_usuario);
            List<Usuario> nuevaLista = new ArrayList<Usuario>();
            for(Usuario amigos: usuarioSesion.getAmigos()){
                if(amigos.getUsername().equals(usuario.getUsername())){

                    MantenimientoUsuario.getInstancia().eliminaragcamigo(usuarioSesion.getUsername(),usuario.getUsername());

                }else{
                    nuevaLista.add(amigos);
                }
            }
            usuarioSesion.setAmigos(nuevaLista);
            MantenimientoUsuario.getInstancia().editar(usuarioSesion);

            response.redirect("/home");

            return "";
        });


        get("/eliminarpost/:id_post", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            int id_post = Integer.parseInt(request.params("id_post"));
            Post post = MantenimientoPost.getInstancia().find(id_post);
            MantenimientoPost.getInstancia().eliminar(post);

            response.redirect("/home");

            return "";
        });

        get("/like/:id_post", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            Usuario usuarioSesion = request.session().attribute("usuario");
            int id_post = Integer.parseInt(request.params("id_post"));
            Post post = MantenimientoPost.getInstancia().find(id_post);
            post.getLikes().add(usuarioSesion);
            MantenimientoPost.getInstancia().editar(post);

            response.redirect("/home");

            return "";
        });

        get("/dislike/:id_post", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            Usuario usuarioSesion = request.session().attribute("usuario");
            int id_post = Integer.parseInt(request.params("id_post"));
            Post post = MantenimientoPost.getInstancia().find(id_post);
            List<Usuario> nuevosLikes = new ArrayList<Usuario>();
            for(Usuario like:post.getLikes()){
                if(like.getUsername().equals(usuarioSesion.getUsername())){
                    MantenimientoUsuario.getInstancia().eliminarlikes(usuarioSesion.getUsername(),post.getId());
                }else{
                    nuevosLikes.add(like);
                }
            }
            post.setLikes(nuevosLikes);

            MantenimientoPost.getInstancia().editar(post);

            response.redirect("/home");

            return "";
        });

        get("/eliminarcomentario/:id_comentario", (request, response) -> {

            int id_comentario = Integer.parseInt(request.params("id_comentario"));
            Comentario comentario = MantenimientoComentario.getInstancia().find(id_comentario);
            MantenimientoComentario.getInstancia().eliminar(comentario);
            response.redirect("/home");


            return "";
        });


    }

    //se crean las etiquetas si es necesario y se entran en una lista para luego asignarles un post.
    public static List<Etiqueta> creacionEtiquetas(String[] etiquetas) {
        List<Etiqueta> listaEtiquetas = new ArrayList<Etiqueta>();
        for (String etiqueta : etiquetas) {
            try {
                Etiqueta etiquetaExistente = (Etiqueta) MantenimientoEtiqueta.getInstancia().getEntityManager().createQuery("SELECT E FROM Etiqueta E WHERE E.etiqueta='" + etiqueta + "'").getSingleResult();
                listaEtiquetas.add(etiquetaExistente);
            } catch (NoResultException e) {
                Etiqueta etiquetaNueva = new Etiqueta(etiqueta);
                MantenimientoEtiqueta.getInstancia().crear(etiquetaNueva);
                listaEtiquetas.add(etiquetaNueva);
            }

        }
        return listaEtiquetas;
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }

        return 4567;
    }


}

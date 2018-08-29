<#include "*/head/head.ftl">
<body xmlns="http://www.w3.org/1999/html">
<header class="headerTimeline">
    <div class="name fancy-font">
        <a href="usuario/${usuario.getUsername()}">${usuario.getUsername()}</a>
    </div>

    <a style="float: right; margin-right: 0.5%" href="/cerrarsesion"> | Cerrar sesion</a>
    <a style="float: right ; margin-right: 0.5%" href="/crearpost"> | Crear post</a>
    <a style="float: right; margin-right: 0.5%" href="/usuarios_registrados">Usuarios Registrados</a>
</header>
<section class="instagram-wrap">
    <div class="container">
        <div class="row">
            <div class="col-xs-12">
                <div class="instagram-content">
                    <div class="row photos-wrap">
                        <!-- Instafeed target div -->
                        <div id="instafeed"></div>
                        <!-- The following HTML will be our template inside instafeed -->
                    <#list posts as post>
                    <div class="center-block">
                            <div class="photo-box">
                                <div class="header">
                                    <div class="username">
                                        <div class="avatar">
                                            <img src="data:image/jpeg;base64,${post.getUsuario().getImagen()!}">
                                        </div>
                                        <a href="usuario/${post.getUsuario().getUsername()}">${post.getUsuario().getUsername()}</a>
                                    </div>
                                </div>
                                <div class="image-wrap">
                                    <img src="data:image/jpeg;base64,${post.getImagen()}" class="img-responsive" ><br/>
                                    <div class="likes">${post.getNumLikes()}</div>
                                </div>
                                <div class="description">
                                    <#assign x = 0>
                                    <#list post.getLikes() as like>
                                    <#if like.getUsername() == usuario.getUsername()>
                                        <#assign x = 1>
                                        <form action="/dislike/${post.getId()}">
                                            <input style="float: right" type="submit" value="Dislike">
                                        </form>
                                        <#break >
                                    </#if>
                                    <#else>
                                        <#assign x = 2>
                                        <form action="/like/${post.getId()}">
                                            <input style="float: right" type="submit" value="like">
                                        </form>
                                    </#list>
                                    <#if x==0>
                                        <form action="/like/${post.getId()}">
                                            <input style="float: right" type="submit" value="like">
                                        </form>
                                    </#if>

                                    <#if usuario.getUsername()== post.getUsuario().getUsername() >
                                        <form action="/eliminarpost/${post.getId()}">
                                            <input style="float: right" type="submit" value="Eliminar">
                                        </form>
                                        <form action="/editarpost/${post.getId()}">
                                            <input style="float: right" type="submit" value="Editar">
                                        </form>

                                    </#if>
                                ${post.getCuerpo()}
                                    <#list post.getEtiquetas() as etiqueta>
                                        <span>#${etiqueta.getEtiqueta()} </span>
                                    </#list>
                                    <div class="date">${post.getFecha()}</div>


                                </div>
                                <div class="comentarios">

                                        <h4>Comentarios</h4>

                                    <#list post.getComentarios() as comentario>
                                        <div>
                                            <span><b>${comentario.getUsuario().getUsername()}:</b>${comentario.getComentario()}</span>
                                            <#if comentario.getUsuario().getUsername()== usuario.getUsername() ||  post.getUsuario().getUsername()== usuario.getUsername() >
                                                <a style="color: red; float: right" href="/eliminarcomentario/${comentario.getId()}"> <span>eliminar</span></a>
                                            </#if>
                                        </div>
                                    </#list>
                                    <form method="post" action="/crearcomentario">
                                        <div class="form-group">
                                            <input id="comentario" type="text" name="comentario" placeholder="Escribir comentario..."/>
                                            <input type="hidden" name="id_post" value="${post.getId()}"/>
                                            <button class="btn btn-primary btn-sm">Comentar</button>
                                        </div>

                                    </form>
                                </div>
                            </div>
                        </div>
                    <#else>
                    <h3>No hay publicaciones</h3>
                    </#list>

                    </div>
                </div>
            </div>
        </div>
    </div>

</section>


<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
</body>
</html>
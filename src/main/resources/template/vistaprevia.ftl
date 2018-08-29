<#include "*/head/head.ftl">
<section class="instagram-wrap">
    <div class="container">
        <div class="row">
            <div class="col-xs-12">
                <div class="instagram-content">
                    <div class="row photos-wrap">
                        <!-- Instafeed target div -->
                        <div id="instafeed"></div>
                        <!-- The following HTML will be our template inside instafeed -->
                        <div class="center-block">
                            <div class="photo-box">
                                <div class="header">
                                    <div class="username">
                                        <div class="avatar">
                                            <img src="data:image/jpeg;base64,${post.getUsuario().getImagen()!}">
                                        </div>
                                        <a href="/usuario/${post.getUsuario().getUsername()}">${post.getUsuario().getUsername()}</a>
                                    </div>
                                </div>
                                <div class="image-wrap">
                                    <img src="data:image/jpeg;base64,${post.getImagen()}" class="img-responsive" ><br/>
                                    <div class="likes">${post.getNumLikes()}</div>
                                </div>
                                <div class="description">

                                    <#if usuarioEnSesion.getUsername()== post.getUsuario().getUsername() >
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
                                            <#if comentario.getUsuario().getUsername()== usuarioEnSesion.getUsername() ||  post.getUsuario().getUsername()== usuarioEnSesion.getUsername() >
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


                    </div>
                </div>
            </div>
        </div>
    </div>

</section>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery-3.0.0.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/app.jss"></script>
</body>
</html>
<#include "*/head/head.ftl">
<body>
<header>
    <img src="data:image/jpeg;base64,${usuario.getImagen()!}" alt="Imagen de perfil">
    <div class="name fancy-font">
        <a href="/home">Red-Social</a>
    </div>
<#if usuario.getUsername()== usuarioEnSesion.getUsername() >
    <form action="/editarcuenta">
        <input style="float: right" type="submit" value="Editar">
    </form>

</#if>

<#if usuario.getUsername()== usuarioEnSesion.getUsername()>
<form action="/crearpost">
    <input style="float: right" type="submit" value="Crear Post">
</form>
</#if>

    <div class="titles">
        <h1>Hola! <span>Soy ${usuario.getUsername()}</span></h1>
        <h2>${usuario.getDescripcion()}</h2>
        <br><br><br>
        <h2> Apodo:${usuario.getApodo()} | Lugar de Nacimiento: ${usuario.getLugarnaci()} | Fecha de Nacimiento: ${usuario.getNacimiento()} <br>| Estudia en: ${usuario.getEstudio()} | Trabaja en: ${usuario.getTrabajo()}</h2>
        <br><br>
        <h3>Seguidores:${followers} | Seguidos: ${following} | Amigos: ${amigos}</h3>

    </div>



</header>

<section class="instagram-wrap">
    <div class="container">
        <div class="row">
            <div class="col-xs-12">
                <div class="instagram-content">
                    <h3>Ultimas Fotos</h3>
                    <div class="row photos-wrap">
                        <!-- Instafeed target div -->
                        <div id="instafeed"></div>
                        <!-- The following HTML will be our template inside instafeed -->
                    <#list posts as post>
                        <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                            <div class="photo-box">
                                <div class="image-wrap">
                                    <a href="/usuario/${usuario.getUsername()}/${post.getId()}"><img src="data:image/jpeg;base64,${post.getImagen()}"></a>
                                    <div class="likes">${post.getNumLikes()}</div>
                                </div>
                                <div class="description">
                                    ${post.getCuerpo()}
                                    <#list post.getEtiquetas() as etiqueta>
                                        <span>#${etiqueta.getEtiqueta()} </span>
                                    </#list>
                                    <div class="date">${post.getFecha()}</div>
                                </div>
                            </div>
                        </div>
                    </#list>


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
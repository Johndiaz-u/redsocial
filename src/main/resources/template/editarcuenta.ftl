<#include "*/head/head.ftl">
<body xmlns="http://www.w3.org/1999/html">
<header class="headerTimeline">
    <div class="name fancy-font">
        <a href="usuario/${usuario.getUsername()}">${usuario.getUsername()}</a>
    </div>
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
                        <div class="center-block">

                            <form id="register-form" enctype="multipart/form-data" action="/editarcuenta" method="post" role="form" ">
                            <div class="center-block">
                                <label for="upfile">Imagen de perfil:</label>
                                <div class="avatar">
                                    <img src="data:image/jpeg;base64,${usuario.getImagen()!}">
                                </div>
                                <div id="drop-zone">

                                    <div id="clickHere">
                                        or click here..
                                        <input type="file" name="upfile"  accept="image/*  onchange="readURL(this)"  />

                                    </div>

                                </div>
                            <div class="form-group">


                                </div>
                                <div class="form-group">
                                    <input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="Email Address" value="${email!}">
                                </div>
                                <div class="form-group">
                                    <input type="text" name="descripcion" id="descripcion" tabindex="1" class="form-control" placeholder="Sobre ti" value="${descripcion!}">
                                </div>
                                <div class="form-group">
                                    <input type="text" name="apodo" id="apodo" tabindex="1" class="form-control" placeholder="Tu Apodo" value="${apodo!}">
                                </div>
                                <div class="form-group">
                                    <input type="text" name="lugarnaci" id="lugarnaci" tabindex="1" class="form-control" placeholder="Lugar donde Nacio" value="${lugarnaci!}">
                                </div>
                                <div class="form-group">
                                    <input type="date" name="nacimiento" id="nacimiento" tabindex="1" class="form-control" placeholder="Fecha de Nacimiento" value="${nacimiento!}">
                                </div>
                                <div class="form-group">
                                    <input type="text" name="direccion" id="direccion" tabindex="1" class="form-control" placeholder="Su Direccion" value="${direccion!}">
                                </div>
                                <div class="form-group">
                                    <input type="text" name="estudio" id="estudio" tabindex="1" class="form-control" placeholder="Lugar donde Estudia" value="${estudio!}">
                                </div>
                                <div class="form-group">
                                    <input type="text" name="trabajo" id="trabajo" tabindex="1" class="form-control" placeholder="Lugar donde Trabaja" value="${trabajo!}">
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password" value="${password!}">
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password2" id="confirm-password" tabindex="2" class="form-control" placeholder="Confirm Password" value="${password2!}">
                                </div>
                              <input type="hidden" value="${usuario.getUsername()}" name="usuario"/>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Actualizar">
                                        </div>
                                    </div>
                                </div>

                            </form>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

</section>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/lstyle.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-6">
                            <a href="#" class="active" id="login-form-link">Login</a>
                        </div>
                        <div class="col-xs-6">
                            <a href="#" id="register-form-link">Registrarse</a>
                        </div>
                    </div>
                    <hr>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form id="login-form" action="/login" method="post" role="form" style="display: block;">
                                <div class="form-group">
                                    <input required type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Username" value="${username!}">
                                </div>
                                <div class="form-group">
                                    <input required type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password">
                                </div>
                                <div class="form-group text-center">
                                    <input type="checkbox" tabindex="3" class="" name="remember" id="remember">
                                    <label for="remember"> Guardar Sesión</label>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Log In">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-lg-12">
                                        <#if message??>
                                            <div class="text-center">
                                            ${message}
                                            </div>
                                        </#if>
                                        <#if error??>
                                            <div class="text-center">
                                                <strong>Error:</strong> ${error}
                                            </div>
                                        </#if>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <form id="register-form" enctype="multipart/form-data" action="/register" method="post" role="form" style="display: none;">
                                <div class="form-group">
                                    <label for="upfile">Imagen de perfil:</label>
                                    <input type="file" name="upfile" tabindex="1"  class="form-control" accept="image/*"><br>
                                </div>
                                <div class="form-group ">
                                    <input type="text" required name="username" id="username" tabindex="1" class="form-control" placeholder="Username" value="${username!}" >
                                </div>
                                <div class="form-group ">
                                    <input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="Email Address" value="${email!}" required>
                                </div>
                                <div class="form-group ">
                                    <input type="text" required name="apodo" id="apodo" tabindex="1" class="form-control" placeholder="Tu Apodo" value="${apodo!}" >
                                </div>
                                <div class="form-group ">
                                    <input type="text" required name="lugarnaci" id="lugarnaci" tabindex="1" class="form-control" placeholder="Su lugar de Nacimiento" value="${lugarnaci!}" >
                                </div> 
                                <div class="form-group ">
                                    <input type="date" required name="nacimiento" id="nacimiento" tabindex="1" class="form-control" placeholder="Fecha de Nacimiento (dd-MM-yyyy)" value="${nacimiento!}" >
                                </div>
                                <div class="form-group ">
                                    <input type="text" required name="direccion" id="direccion" tabindex="1" class="form-control" placeholder="Su Direccion" value="${direccion!}" >
                                </div>
                                <div class="form-group ">
                                    <input type="text" required name="estudio" id="estudio" tabindex="1" class="form-control" placeholder="Lugar donde Estudia" value="${estudio!}" >
                                </div>
                                <div class="form-group ">
                                    <input type="text" required name="trabajo" id="trabajo" tabindex="1" class="form-control" placeholder="Lugar donde Trabaja" value="${trabajo!}" >
                                </div>
                                <div class="form-group">
                                    <input type="text" name="descripcion" id="descripcion" tabindex="1" class="form-control" placeholder="Sobre ti" value="${descripcion!}" required>
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password " required>
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password2" id="confirm-password" tabindex="2" class="form-control" placeholder="Confirm Password" required>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Registrarse Ahora">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-lg-12">
                                        <#if error??>
                                            <div class="text-center">
                                                <strong>Error:</strong> ${error}
                                            </div>
                                        </#if>
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
</div>
<script src="/js/Jsignin.js"></script>
</body>
</html>
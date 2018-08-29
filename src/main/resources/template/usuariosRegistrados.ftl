<#include "*/head/head.ftl">
<body xmlns="http://www.w3.org/1999/html">
<header class="headerTimeline">
    <div class="name fancy-font">
        <a href="usuario/${usuarioSesion.getUsername()}">${usuarioSesion.getUsername()}</a>
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

                        <div class="center-block">
                            <h4 style="align-content: center">Usuarios registrados</h4>
                            <#list usuarios as usuario>
                                <div class="header" style="margin: 5%">

                                    <div class="username" style="float: left">
                                        <div class="avatar">
                                            <img src="data:image/jpeg;base64,${usuario.getImagen()!}">
                                        </div>
                                        <div>
                                            <a href="usuario/${usuario.getUsername()} "><h4>${usuario.getUsername()} </h4></a>
                                        </div>


                                        <div>

                                            <#if usuario.getUsername()!= usuarioSesion.getUsername() >
                                                <#assign x = 0>
                                                <#list followings as following>
                                                    <#if usuario.getUsername() == following.getUsername()>
                                                        <form action="/unfollow_usuario">
                                                            <input type="submit" value="Dejar de Seguir" style="float: right">
                                                            <input type="hidden" value="${usuario.getUsername()}" name="id" />
                                                        </form>
                                                        <#assign x = 1>
                                                        <#break >
                                                    </#if>
                                                <#else>
                                                    <#assign x = 2>
                                                        <form action="/agregar_usuario">
                                                            <input type="submit" value="Seguir" style="float: right">
                                                            <input type="hidden" value="${usuario.getUsername()}" name="id" />
                                                        </form>

                                                </#list>
                                                <#if x==0>
                                                    <form action="/agregar_usuario">
                                                        <input type="submit" value="Seguir" style="float: right">
                                                        <input type="hidden" value="${usuario.getUsername()}" name="id" />
                                                    </form>
                                                </#if>
												<#assign x = 3>
                                                <#list amigoss as amigos>
                                                    <#if usuario.getUsername() == amigos.getUsername()>
                                                        <form action="/desligar_amigo">
                                                            <input type="submit" value="Dejar de ser Amigo" style="float: right">
                                                            <input type="hidden" value="${usuario.getUsername()}" name="id" />
                                                        </form>
                                                        <form action="/crearpost">
                                                            <input style="float: right" type="submit" value="Crear Post">
                                                        </form>

                                                        <#assign x = 4>
                                                        <#break >
                                                    </#if>
                                                <#else>
                                                    <#assign x = 5>
                                                        <form action="/agregar_amigo">
                                                            <input type="submit" value="Agregar Amigo" style="float: right">
                                                            <input type="hidden" value="${usuario.getUsername()}" name="id" />
                                                        </form>


                                                </#list>
                                                <#if x==3>
                                                    <form action="/agregar_amigo">
                                                        <input type="submit" value="Agregar Amigo" style="float: right">
                                                        <input type="hidden" value="${usuario.getUsername()}" name="id" />
                                                    </form>

                                                </#if>


                                            </#if>

                                        </div>

                                    </div>


                                </div>

                            </#list>


                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

</section>
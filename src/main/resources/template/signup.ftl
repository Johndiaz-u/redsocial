<#include "*/head/head.ftl">
<h2>Sign Up</h2>
    <#if error??>
    <div class="error">
        <strong>Error:</strong> ${error}
    </div>
    </#if>
<form action="/register" method="post" enctype="multipart/form-data">
    <dl>
        <dt>Imagen de Perfil:</dt>
        <dd><input type="file" name="upfile" accept="image/*"><br></dd>
        <dt>Username:
        <dd><input type="text" name="username" maxlength="50" size="30" value="${username!}">
        <dt>E-Mail:
        <dd><input type="text" name="email" maxlength="50" size="30" value="${email!}">
        <dt>Sobre ti:
        <dd><input type="text" name="descripcion" maxlength="144" size="50" value="${descripcion!}">
        <dt>Password:
        <dd><input type="password" name="password" maxlength="20" size="30">
        <dt>Password <small>(repeat)</small>:
        <dd><input type="password" name="password2" maxlength="20" size="30">
    </dl>
    <div class="actions"><input type="submit" value="Sign Up"></div>
</form>

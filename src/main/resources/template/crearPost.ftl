<#include "*/head/head.ftl">

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h3>Crea un nuevo post:</h3>
            <form method="post" action="/crearpost" enctype="multipart/form-data">
                <div class="center-block">
                    <label>Imagen:</label>
                    <div id="drop-zone">

                        <div id="clickHere">
                            or click here..
                            <input type="file" name="upfile"  accept="image/*  onchange="readURL(this)"  />

                        </div>

                    </div>
                </div>

                <div class="form-group">
                    <label>Contenido</label>
                    <textarea type="text" name="contenido" class="form-control">${post.getCuerpo()}</textarea>
                </div>

                <div class="form-group">
                    <label>Etiquetas</label>
                    <input  type="text" name="etiquetas" class="form-control" value="${stringEtiquetas}" />
                </div>

                <!-- <input type="hidden" value="${post.getId()}" name="id" /> -->
                <div class="input-group">
                    <button class="btn btn-success">Procesar</button>
                </div>
            </form>
        </div>
    </div>
</div>
$(function () {
    var boton = $('#btn-enviar');
    var fecha = $('#fecha');
    var origen = $('#origen');
    var destino = $('#destino');
    boton.on('click', f)

    function f() {
        var url = "/vuelos/" + fecha.val() + "/" + origen.val() + "-" + destino.val();
        if (origen.val() === "" || destino.val() === "")
            url = "/vuelos/" + fecha.val() + "/";


        $.getJSON(url, function (respuesta) {
            console.log(url);
            var lista = $("#lista");
            lista.empty();
            $.each(respuesta, function (i, item) {
                lista.append($('<p>').html(item.codigo + " Origen: " + item.origen.nombre + " Destino: " + item.destino.nombre  + " Fecha: " + new Date(item.salida).toUTCString()));
            })
        });
    }
    /*
    $.ajax({
        url : "/vuelos/" + fecha.val() + "/" + origen.val() + "-" + destino.val(),
        type: "GET",
        success    : function() {
            console.log("Exito");
        }
    });

     */

})
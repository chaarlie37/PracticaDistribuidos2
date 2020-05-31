

$(function () {
    var boton = $('#btn-enviar');
    var fecha_ida = $('#fecha-ida');
    var fecha_vuelta = $('#fecha-vuelta')
    var radio_idavuelta = $('#ida-vuelta');
    var radio_soloida = $('#solo-ida');
    var origen = $('#origen');
    var destino = $('#destino');
    var vuelos_ida = new Array();
    var vuelos_vuelta = new Array();
    var parejas_vuelos = [];
    var lista = $("#lista");
    var dialogo = $("#dialog");
    var aerolinea = $("#aerolinea");
    var puntuacion = $("#rateYo")
    var barra_fecha = $(".barra-fecha");

   puntuacion.rateYo({
        starWidth: "20px",
        numStars: 5,
        rating: 4.5,
        halfStar: true,
        readOnly:true,
      });



    boton.on('click', f)
    radio_soloida.on('click', function () {
        fecha_vuelta.hide();
        $('#destino.barra-busqueda').css({
            "border-bottom-right-radius": "0.50rem"
        });
    })
    radio_idavuelta.on('click', function () {
        fecha_vuelta.show();
        $('#destino.barra-busqueda').css({
            "border-bottom-right-radius": "0"
        });
    })

    barra_fecha.on("click", function () {
        $(this).prop('type', 'date');
    });


    /*
    barra_fecha.on("mouseleave", function () {
        if ($(this).val() == "") {
            $(this).prop('type', 'text');
        }
    });
*/

    $("#dialog").dialog({
        autoOpen: false,
        modal: true,
        buttons: {
            "Cerrar": function () {
                $(this).dialog("close");
            }
        }
    });

    lista.on('click', '.aerolinea', function () {
        var dialogo = $('#aerolinea-' + $(this).attr('id'))
        dialogo.dialog({
            autoOpen: false,
            modal: true,
            buttons: {
                "Cerrar": function () {
                    $(this).dialog("close");
                }
            }
        });
        dialogo.dialog("open");
    })






    function ParejaVuelos(ida, vuelta, precio) {
        this.ida = ida;
        this.vuelta = vuelta;
        this.precio = precio;
    }

    var aeropuertos = new Array();
    $.getJSON("/aeropuertos/", function (respuesta) {
        var lista = [];
        $.each(respuesta, function (i, item) {
            lista.push(item.nombre);
        })
        guardar_aeropuertos(lista);
    });


    function f() {
        var url = "/vuelos/" + fecha_ida.val() + "/" + encodeURI(origen.val()) + "/" + encodeURI(destino.val());
        console.log(url);
        if (origen.val() === "" || destino.val() === "")
            url = "/vuelos/" + fecha_ida.val() + "/";
        if (radio_idavuelta.is(':checked')){
            console.log("AAAAAAAAAAA");
            var url2 = "/vuelos/" + fecha_vuelta.val() + "/" + encodeURI(destino.val()) + "/" + encodeURI(origen.val());
            console.log(url2);
            $.getJSON(url, function (respuesta) {
                var lista = [];
                $.each(respuesta, function (i, item) {
                    lista.push(item);
                    console.log(item);
                })
                guardar_ida(lista);
            });
            $.getJSON(url2, function (respuesta) {
                var lista = [];
                $.each(respuesta, function (i, item) {
                    lista.push(item);
                    console.log("v" + item);
                })
                guardar_vuelta(lista);
            });
        }else{
            $.getJSON(url, function (respuesta) {
                lista.empty();
                $.each(respuesta, function (i, item) {
                    lista.append($('<p>').html(item.codigo + " Origen: " + item.origen.nombre + " Destino: " + item.destino.nombre  + " Fecha: " + new Date(item.salida).toUTCString()));
                    lista.append($('<p class="aerolinea" id="' + item.aerolinea.codigo + '">>').html(item.aerolinea.nombre));
                    dialogo.append($('<div id="aerolinea-' + item.aerolinea.codigo + '">').html(item.aerolinea.nombre + " " + item.aerolinea.codigo + " " + item.aerolinea.telefono + " " + item.aerolinea.web));
                })
            });
        }
    }

    /*function dialogo(){
                $("#dialog").dialog({
                autoOpen: false,
                modal: true,
                buttons: {
                    "Cerrar": function () {
                        $(this).dialog("close");
                    }
                }
                });
                $("#abrir").button().click(function () {
                    $("#dialog").dialog("option", "width", 600);
                    $("#dialog").dialog("option", "height", 300);
                    $("#dialog").dialog("open");
                });
    });
    */



    function guardar_ida(a) {
        console.log("guardando");
        vuelos_ida = a;
        console.log("ida" + vuelos_ida);
        if (vuelos_vuelta.length > 0){
            console.log(vuelos_ida);
            mostrar_parejas_vuelos();
        }
    }

    function guardar_vuelta(a) {
        vuelos_vuelta = a;
        console.log("vuelta " + vuelos_vuelta);
        if (vuelos_ida.length > 0){

            mostrar_parejas_vuelos();
        }
    }

    function mostrar_parejas_vuelos() {
        lista.empty();
        for (var i = 0; i<vuelos_ida.length; i++){
            for (var j = 0; j<vuelos_vuelta.length; j++){
                var precio = vuelos_ida[i].precio + vuelos_vuelta[j].precio;
                if(vuelos_ida[i].aerolinea.codigo === vuelos_vuelta[j].aerolinea.codigo){
                    precio = precio * 0.80;
                }
                var pareja = new ParejaVuelos(vuelos_ida[i], vuelos_vuelta[j], precio);
                parejas_vuelos.push(pareja);
                var codigo_ida = vuelos_ida[i].codigo;
                var codigo_vuelta = vuelos_vuelta[j].codigo;
                var aeropuerto_origen = vuelos_ida[i].origen.nombre;
                var aeropuerto_vuelta = vuelos_ida[i].destino.nombre;
                var duracion_ida = formatear_duracion(vuelos_ida[i].duracion);
                var duracion_vuelta = formatear_duracion(vuelos_vuelta[j].duracion);
                var hora_salida_ida = formatear_hora(vuelos_ida[i].salida);
                var hora_llegada_ida = formatear_hora(vuelos_ida[i].llegada);
                var hora_salida_vuelta = formatear_hora(vuelos_vuelta[j].salida);
                var hora_llegada_vuelta = formatear_hora(vuelos_vuelta[j].llegada);
                lista.append("<div class=\"zona-vuelo\">\n" +
                    "            <div class=\"pareja\">\n" +
                    "                <div class=\"vuelo\">\n" +
                    "                    <div class=\"aerolinea-codigo-vuelo\">\n" +
                    "                        <img src=\"/images/" + pareja.ida.aerolinea.codigo + ".png\" height=\"30px\">\n" +
                    "                        <div class=\"texto-codigo-vuelo\">" + codigo_ida + "</div>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"duracion-icono\">\n" +
                    "                        <div class=\"texto-duracion\">" + duracion_ida + "</div>\n" +
                    "                        <img src=\"/images/union.svg\" height=\"30px\">\n" +
                    "                    </div>\n" +
                    "                    <div class=\"detalles-vuelo\">\n" +
                    "                        <div class=\"grupo-horas-ubicaciones\">\n" +
                    "                            <div class=\"horas\">\n" +
                    "                                <div class=\"texto-hora\">" + hora_salida_ida + "</div>\n" +
                    "                                <div class=\"texto-hora\">" + hora_llegada_ida + "</div>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"ubicaciones\">\n" +
                    "                                <div>\n" + aeropuerto_origen +
                    "                                </div>\n" +
                    "                                <div>\n" + aeropuerto_vuelta +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "                <div class=\"vuelo\">\n" +
                    "                    <div class=\"aerolinea-codigo-vuelo\">\n" +
                    "                        <img src=\"/images/" + pareja.vuelta.aerolinea.codigo + ".png\" height=\"30px\">\n" +
                    "                        <div class=\"texto-codigo-vuelo\">" + codigo_vuelta + "</div>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"duracion-icono\">\n" +
                    "                        <div class=\"texto-duracion\">" + duracion_vuelta + "</div>\n" +
                    "                        <img src=\"/images/union.svg\" height=\"30px\">\n" +
                    "                    </div>\n" +
                    "                    <div class=\"detalles-vuelo\">\n" +
                    "                        <div class=\"grupo-horas-ubicaciones\">\n" +
                    "                            <div class=\"horas\">\n" +
                    "                                <div class=\"texto-hora\">" + hora_salida_vuelta + "</div>\n" +
                    "                                <div class=\"texto-hora\">" + hora_llegada_vuelta + "</div>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"ubicaciones\">\n" +
                    "                                <div>\n" + aeropuerto_vuelta +
                    "                                </div>\n" +
                    "                                <div>\n" + aeropuerto_origen +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <div class=\"precio\">\n" +
                    "                <h3 class=\"h3\">" + precio + "€</h3>\n" +
                    "            </div>" +
                    "        </div>");
                dialogo.append($('<div id="aerolinea-' + pareja.ida.aerolinea.codigo + '">').html(pareja.ida.aerolinea.nombre + " " + pareja.ida.aerolinea.codigo + " " + pareja.ida.aerolinea.telefono + " " + pareja.ida.aerolinea.web));
                dialogo.append($('<div id="aerolinea-' + pareja.vuelta.aerolinea.codigo + '">').html(pareja.vuelta.aerolinea.nombre + " " + pareja.vuelta.aerolinea.codigo + " " + pareja.ida.aerolinea.telefono + " " + pareja.ida.aerolinea.web));

            }
        }
    }

    function guardar_aeropuertos(l) {
        aeropuertos = l;
        origen.autocomplete({
            source: aeropuertos,
            autoFocus: true,
            delay:0,
            change: function (event, ui) {
                if(!ui.item && origen.val() != ""){
                    origen.val($('ul#ui-id-1 li:first div').text());
                }
            }
        });
        destino.autocomplete({
            source: aeropuertos,
            autoFocus: true,
            delay: 0,
            change: function (event, ui) {
                if(!ui.item && destino.val() != ""){
                    destino.val($('ul#ui-id-2 li:first div').text());
                }
            }
        });
    }

    function formatear_hora(d) {
        var fecha = new Date(d);
        return new Intl.DateTimeFormat('es', { hour: '2-digit', minute: '2-digit' }).format(fecha);
    }

    function formatear_duracion(min) {
        if (min >= 60 && min % 60 != 0) {
            return Math.trunc(min / 60) + "h " + (min % 60) + "min";
        }else if(min >= 60 && min % 60 == 0){
            return Math.trunc(min / 60) + "h";
        }else{
            return min + "min";
        }
    }

})
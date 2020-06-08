$(function () {
    var boton = $('#btn-enviar');
    var fecha_ida = $('#fecha-ida');
    var fecha_vuelta = $('#fecha-vuelta')
    var radio_idavuelta = $('#ida-vuelta');
    var radio_soloida = $('#solo-ida');
    var origen = $('#origen');
    var destino = $('#destino');
    var lista = $("#lista");
    var dialogo = $("#dialog");
    var cabecera_vuelos = $('#cabecera-vuelos');
    var contenido = $('.contenido');
    var boton_volver = $('#btn-volver');
    var zona_boton_vuelos = $('.zona-boton-vuelos');
    var aerolineas = []
    var vuelos_ida = [];
    var vuelos_vuelta = [];
    var parejas_vuelos = [];
    var aeropuertos = [];

    /*
    Al hacer click en el botón de buscar, en función de si está seleccionado el radio button de ida o ida y vuelta,
    se hacen las peticiones con getJSON al servicio web correspondientes. Una vez recibida la información se va haciendo
    append al div "lista" del HTML para que aparezcan los vuelos.
    Además, se hace control de errores para que el usuario no pueda buscar en caso de no completar todos los campos
    requeridos, o si la fecha de vuelta es anterior a la fecha de ida, o si el aeropuerto de origen es el mismo que el
    aeropuerto destino.
     */
    boton.on('click', function() {
        // Controlar que la fecha de ida sea anterior a la fecha de vuelta
        var f1 = fecha_ida.val().split("-");
        var f2 = fecha_vuelta.val().split("-");
        if(new Date(f1[2], f1[1] - 1, f1[0]) > new Date(f2[2], f2[1] - 1, f2[0])){
            // Animaciones fade del contenido que se muestra. Siempre se hace un fadeout de todo el contenido por si hay
            // algo antes. Nos aseguramos de que termina el fade out para hacer el append y mostrar la información con fade in.
            // También se hace empty() a los divs a los cuales se va a hacer append para que el contenido anterior ya no aparezca.
            // Este esquema se sigue cada vez que se muestra información.
            contenido.fadeOut().promise().done(function () {
                $('html, body').animate({
                    scrollTop: cabecera_vuelos.offset().top
                }, 1000);
                lista.empty();
                lista.append('<div class="cuadro-advertencia">\n' +
                    '           <span class="material-icons texto-advertencia">warning</span>\n' +
                    '           <div class="texto-advertencia">La fecha de ida es posterior a la fecha de vuelta. Inténtalo de nuevo.</div>\n' +
                    '         </div>');
                contenido.fadeIn();
            });
        }
        // Controlar que el aeropuerto origen es distinto al aeropuerto destino
        else if(origen.val() === destino.val() && origen.val() !== ""){
            contenido.fadeOut().promise().done(function () {
                $('html, body').animate({
                    scrollTop: cabecera_vuelos.offset().top
                }, 1000);
                lista.empty();
                lista.append('<div class="cuadro-advertencia">\n' +
                    '        <span class="material-icons texto-advertencia">warning</span>\n' +
                    '        <div class="texto-advertencia">El destino no puede ser el mismo que el origen. Inténtalo de nuevo.</div>\n' +
                    '    </div>');
                contenido.fadeIn();
            });
        }else{
            // Se generan las url dependiendo si el usuario ha seleccionado ida y vuelta o sólo ida.
            var url_ida = "/vuelos/" + fecha_ida.val() + "/" + encodeURI(origen.val()) + "/" + encodeURI(destino.val());
            if (origen.val() === "" || destino.val() === "")
                url_ida = "/vuelos/" + fecha_ida.val() + "/";
            // El usuario ha seleccionado ida y vuelta
            if (radio_idavuelta.is(':checked')){
                // Comprobar que el usuario ha completado todos los campos requeridos.
                // Se hace dos veces (aquí y otra más adelante) ya que si selecciona sólo ida, en este caso daría error
                // al no introducir la fecha de vuelta.
                if(origen.val() === "" || destino.val() === "" || fecha_ida.val() === "" || fecha_vuelta.val() === ""){
                    contenido.fadeOut().promise().done(function () {
                        // Cada vez que se hace una búsqueda, se hace una animación de autoscroll hacia la lista de vuelos,
                        // ocultando la barra de búsqueda, optimizando así el espacio para visualizar los vuelos.
                        $('html, body').animate({
                            scrollTop: cabecera_vuelos.offset().top
                        }, 1000);
                        lista.empty();
                        lista.append('<div class="cuadro-advertencia">\n' +
                            '        <span class="material-icons texto-advertencia">warning</span>\n' +
                            '        <div class="texto-advertencia">¡Cuidado! Te has dejado algún campo sin completar. Vuelve a intentarlo.</div>\n' +
                            '    </div>');
                        contenido.fadeIn();
                    });
                }else{
                    // Se genera la url del vuelo de vuelta
                    var url_vuelta = "/vuelos/" + fecha_vuelta.val() + "/" + encodeURI(destino.val()) + "/" + encodeURI(origen.val());
                    // Consultas al servicio web con getJSON
                    // Al ser asíncronas y no ser seguro recibir la ida antes que la vuelta, debemos guardar los elementos
                    // a través de una función auxiliar.
                    // Una vez se hayan guardado en arrays los vuelos correspondientes, se invocará a otra función para que los muestre.
                    $.getJSON(url_ida, function (respuesta) {
                        var lista = [];
                        $.each(respuesta, function (i, item) {
                            lista.push(item);
                        })
                        guardar_ida(lista);
                    });
                    $.getJSON(url_vuelta, function (respuesta) {
                        var lista = [];
                        $.each(respuesta, function (i, item) {
                            lista.push(item);
                        })
                        guardar_vuelta(lista);
                    });
                }
            // El usuario ha seleccionado sólo ida
            }else{
                // Comprobar que el usuario ha completado todos los campos
                if(origen.val() === "" || fecha_ida.val() === ""){
                    lista.empty();
                    lista.append('<div class="cuadro-advertencia">\n' +
                        '        <span class="material-icons texto-advertencia">warning</span>\n' +
                        '        <div class="texto-advertencia">¡Cuidado! Te has dejado algún campo sin completar. Vuelve a intentarlo.</div>\n' +
                        '    </div>');
                }else {
                    // Consultas al servidor web de los vuelos de ida. En este caso sí podemos mostrar los vuelos en cuanto
                    // se recibe la información ya que no esperamos más datos.
                    $.getJSON(url_ida, function (respuesta) {
                        contenido.fadeOut().promise().done(function () {
                            $('html, body').animate({
                                scrollTop: cabecera_vuelos.offset().top
                            }, 1000);
                            lista.empty();
                            cabecera_vuelos.empty();
                            // Pequeño mensaje y botón para volver a buscar. Por defecto está escondido en el HTML pero
                            // aquí hacemos que se muestre.
                            zona_boton_vuelos.css("display", "flex");
                            zona_boton_vuelos.fadeIn();
                            // Si la respuesta es vacía, es decir, si no hay vuelos en función de los datos introducidos,
                            // informar al usuario de que no hay vuelos registrados.
                            if (respuesta == "") {
                                lista.append('<div class="cuadro-advertencia">\n' +
                                    '        <span class="material-icons texto-advertencia">warning</span>\n' +
                                    '        <div class="texto-advertencia">Lo sentimos, no hay vuelos disponibles desde el origen hasta el destino seleccionados para esa fecha.</div>\n' +
                                    '    </div>');
                                contenido.fadeIn();
                            // En el caso que la respuesta sí contenga vuelos, se muestran.
                            }else{
                                // Se añade espacio al final del HTML porque al hacer una búsqueda se produce una animación
                                // de scroll. Si la página fuera corta, la animación se quedaría a medias.
                                $('.contenido').css("padding-bottom", "28%");
                                // Cabecera a modo de resumen con el origen, destino y fecha buscados.
                                cabecera_vuelos.append('<div class="cabecera-vuelos">\n' +
                                    '    <div class="origen-destino">\n' +
                                    '        <div class="origen-fecha-cabecera">\n' +
                                    '            <h5 class="texto-cabecera">' + origen.val() + '</h5>\n' +
                                    '            <h5 class="texto-cabecera">' + fecha_ida.val() + '</h5>\n' +
                                    '        </div>\n' +
                                    '        <div class="iconos-cabecera">\n' +
                                    '            <span class="texto-cabecera icono-info material-icons">arrow_right_alt</span>\n' +
                                    '            <span class="texto-cabecera icono-info material-icons">today</span>\n' +
                                    '        </div>' +
                                    '        <div class="destino-fecha-cabecera">\n' +
                                    '            <h5 class="texto-cabecera">' + destino.val() + '</h5>\n' +
                                    '            <h5 class="texto-cabecera">' + fecha_ida.val() + '</h5>\n' +
                                    '        </div>\n' +
                                    '    </div>\n' +
                                    '</div>');
                                // Por cada vuelo encontrado, mostrarlo en la lista. Se guardan en variables los elementos a mostrar y
                                // se muestran siempre con la misma "plantilla". Cada vuelo es una "tarjeta" donde aparece la información
                                // del vuelo de forma clara para el usuario.
                                // También, aparece la aerolínea (logo en vez de texto, por estética), el cual es clickable para ver los
                                // detalles de la aerolínea en un dialog.
                                $.each(respuesta, function (i, item) {
                                    var codigo_aerolinea = item.aerolinea;
                                    var codigo_vuelo = item.codigo;
                                    var aeropuerto_origen = item.origen;
                                    var aeropuerto_destino = item.destino;
                                    var duracion = formatear_duracion(item.duracion);
                                    var hora_salida = formatear_hora(item.salida);
                                    var hora_llegada = formatear_hora(item.llegada);
                                    var precio = item.precio;
                                    // Aclaración: al ser realmente un string, el entorno de programación lo formatea de esta forma
                                    // para que sea más fácil visualizarlo en desarrollo.
                                    lista.append("<div class=\"zona-vuelo\">\n" +
                                        "            <div class=\"pareja\">\n" +
                                        "                <div class=\"vuelo\">\n" +
                                        "                    <div class=\"aerolinea-codigo-vuelo\">\n" +
                                        "                        <img class=\"aerolinea\" id=\"" + codigo_aerolinea + "\" src=\"/images/" + codigo_aerolinea + ".png\" title=\"Ver detalles de aerolinea\" height=\"30px\">\n" +
                                        "                        <div class=\"texto-codigo-vuelo\">" + codigo_vuelo + "</div>\n" +
                                        "                    </div>\n" +
                                        "                    <div class=\"duracion-icono\">\n" +
                                        "                        <div class=\"texto-duracion\">" + duracion + "</div>\n" +
                                        "                        <img src=\"/images/icono-viaje.png\" height=\"45px\">\n" +
                                        "                    </div>\n" +
                                        "                    <div class=\"detalles-vuelo\">\n" +
                                        "                        <div class=\"grupo-horas-ubicaciones\">\n" +
                                        "                            <div class=\"horas\">\n" +
                                        "                                <div class=\"texto-hora\">" + hora_salida + "</div>\n" +
                                        "                                <div class=\"texto-hora\">" + hora_llegada + "</div>\n" +
                                        "                            </div>\n" +
                                        "                            <div class=\"ubicaciones\">\n" +
                                        "                                <div>\n" + aeropuerto_origen +
                                        "                                </div>\n" +
                                        "                                <div>\n" + aeropuerto_destino +
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
                                });
                            }
                            // El contenido se muestra una vez cargado
                            contenido.fadeIn();
                        });
                    });
                }
            }
        }
    });

    // Al dar click al botón de "volver a buscar" se hace autoscroll hacia la barra de búsqueda y ésta se limpia para
    // introducir datos nuevos.
    boton_volver.on('click', function () {
        $('html, body').animate({
            scrollTop: $('html, body').offset().top
        },1000);
        console.log("volver");
        origen.val("");
        destino.val("");
        fecha_ida.val("");
        fecha_vuelta.val("");
    });

    // Al hacer click en el radiobutton de sólo ida, oculta el campo de fecha de vuelta ya que es innecesario.
    radio_soloida.on('click', function () {
        fecha_vuelta.hide();
        $('#destino.barra-busqueda').css({
            "border-bottom-right-radius": "0.50rem"
        });
    });
    // Deshace el cambio anterior en caso de volver a hacer click en el radiobutton de ida y vuelta.
    radio_idavuelta.on('click', function () {
        fecha_vuelta.show();
        $('#destino.barra-busqueda').css({
            "border-bottom-right-radius": "0"
        });
    })

    // Datepickers de JQuery. Hemos considerado que son más estéticos y más cómodos que los que vienen en cada navegador.
    // Están adaptados al español y a nuestro formato de fecha.
    fecha_ida.datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd-mm-yy",
        firstDay: 1,
        closeText: 'Cerrar',
        prevText: '<Ant',
        nextText: 'Sig>',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mié', 'Juv', 'Vie', 'Sáb'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sá']
    });
    fecha_vuelta.datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd-mm-yy",
        firstDay: 1,
        closeText: 'Cerrar',
        prevText: '<Ant',
        nextText: 'Sig>',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mié', 'Juv', 'Vie', 'Sáb'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sá']
    });

    // Diseño de JQueryUI de los radiobuttons.
    $('.radio').checkboxradio();

    // Al hacer click en el logotipo de la aerolínea en cada vuelo abre el dialog correspondiente.
    lista.on('click', '.aerolinea', function () {
        var dialogo_aerolinea = $('#aerolinea-' + $(this).attr('id'));
        dialogo_aerolinea.dialog("open");
    })

    // Constructor del objeto ParejaVuelos, necesario para mostrar las parejas de vuelos en ida y vuelta.
    function ParejaVuelos(ida, vuelta, precio) {
        this.ida = ida;
        this.vuelta = vuelta;
        this.precio = precio;
    }

    // Consulta al servicio web de los aeropuertos
    $.getJSON('/aeropuertos/', function (respuesta) {
        var lista = [];
        $.each(respuesta, function (i, item) {
            lista.push(item.nombre);
        })
        guardar_aeropuertos(lista);
    });

    // Consulta al servicio web de las aerolíneas
    $.getJSON('/aerolineas/', function (respuesta) {
        var lista = [];
        $.each(respuesta, function (i, item) {
            lista.push(item);
        })
        guardar_aerolineas(lista);
    });

    // Variables para saber cuándo se han cargado todos los vuelos. Para mostrarlos no es necesario, pero sí que se necesita
    // saber para mostrar el error de que no hay vuelos disponibles.
    var vuelosIdaCargados = false;
    var vuelosVueltaCargados = false;

    // Función auxiliar para guardar los vuelos de la ida
    function guardar_ida(a) {
        vuelos_ida = a;
        vuelosIdaCargados = true;
        if (vuelos_vuelta.length > 0){
            mostrar_parejas_vuelos(vuelos_ida, vuelos_vuelta);
            vuelos_ida = []
            vuelos_vuelta = []
        }
        else if(vuelosIdaCargados && vuelosVueltaCargados && vuelos_ida.length === 0 && vuelos_vuelta.length === 0){
            contenido.fadeOut().promise().done(function () {
                lista.empty();
                lista.append('<div class="cuadro-advertencia">\n' +
                    '        <span class="material-icons texto-advertencia">warning</span>\n' +
                    '        <div class="texto-advertencia">Lo sentimos, no hay vuelos disponibles desde el origen hasta el destino seleccionados para esa fecha.</div>\n' +
                    '    </div>');
                contenido.fadeIn();
            });
            vuelosIdaCargados = false;
            vuelosVueltaCargados = false;
        }
    }

    // Función auxiliar para guardar los vuelos de la vuelta. Ambas funciones invocan a mostrar_parejas_vuelos()
    // pero sólo se ejecuta una vez ya que siempre una lista se va a completar antes que la otra.
    function guardar_vuelta(a) {
        vuelos_vuelta = a;
        vuelosVueltaCargados = true;
        if (vuelos_ida.length > 0){
            mostrar_parejas_vuelos(vuelos_ida, vuelos_vuelta);
            vuelos_ida = []
            vuelos_vuelta = []
        }
        else if(vuelosIdaCargados && vuelosVueltaCargados && vuelos_ida.length === 0 && vuelos_vuelta.length === 0){
            contenido.fadeOut().promise().done(function () {
                lista.empty();
                lista.append('<div class="cuadro-advertencia">\n' +
                    '        <span class="material-icons texto-advertencia">warning</span>\n' +
                    '        <div class="texto-advertencia">Lo sentimos, no hay vuelos disponibles desde el origen hasta el destino seleccionados para esa fecha.</div>\n' +
                    '    </div>');
                contenido.fadeIn();
            });
            vuelosIdaCargados = false;
            vuelosVueltaCargados = false;
        }
    }

    // Una vez guardados los vuelos de ida tanto como de vuelta, se pueden mostrar. Esta función se encarga de ello.
    // Es análoga al caso de sólo ida, con la diferencia que muestra parejas de vuelos en vez de vuelos individuales.
    // Se comentan los aspectos diferentes.
    function mostrar_parejas_vuelos(vuelos_ida, vuelos_vuelta) {
        contenido.fadeOut().promise().done(function () {
            $('html, body').animate({
                scrollTop: cabecera_vuelos.offset().top
            },1000);
            lista.empty();
            cabecera_vuelos.empty();
            if (vuelos_ida.length === 0 || vuelos_vuelta.length === 0){
                lista.append('<div class="cuadro-advertencia">\n' +
                    '        <span class="material-icons texto-advertencia">warning</span>\n' +
                    '        <div class="texto-advertencia">Lo sentimos, no hay vuelos disponibles desde el origen hasta el destino seleccionados para esa fecha.</div>\n' +
                    '    </div>');
            }else{
                cabecera_vuelos.append('<div class="cabecera-vuelos">\n' +
                    '    <div class="origen-destino">\n' +
                    '        <div class="origen-fecha-cabecera">\n' +
                    '            <h5 class="texto-cabecera">' + vuelos_ida[0].origen + '</h5>\n' +
                    '            <h5 class="texto-cabecera">' + formatear_fecha(vuelos_ida[0].salida) + '</h5>\n' +
                    '        </div>\n' +
                    '        <div class="iconos-cabecera">\n' +
                    '            <span class="texto-cabecera icono-info material-icons">sync_alt</span>\n' +
                    '            <span class="texto-cabecera icono-info material-icons">today</span>\n' +
                    '        </div>\n' +
                    '        <div class="destino-fecha-cabecera">\n' +
                    '            <h5 class="texto-cabecera">' + vuelos_vuelta[0].origen + '</h5>\n' +
                    '            <h5 class="texto-cabecera">' + formatear_fecha(vuelos_vuelta[0].salida) + '</h5>\n' +
                    '        </div>\n' +
                    '    </div>\n' +
                    '</div>');
                zona_boton_vuelos.css("display", "flex");
                zona_boton_vuelos.fadeIn();
                // Se iteran todos los vuelos de ida y todos los vuelos de vuelta recibidos. Se crea una pareja de vuelos
                // por combinación que exista.
                for (var i = 0; i<vuelos_ida.length; i++){
                    for (var j = 0; j<vuelos_vuelta.length; j++) {
                        // Por cada combinación se crea el objeto ParejaVuelos.
                        // Antes es necesario calcular el precio, por si coinciden las aerolíneas,
                        // hacer el descuento del 20%
                        var precio = vuelos_ida[i].precio + vuelos_vuelta[j].precio;
                        var precio_sin_descuento = "";
                        if (vuelos_ida[i].aerolinea === vuelos_vuelta[j].aerolinea) {
                            precio_sin_descuento = precio + "€";
                            precio = precio * 0.80;
                        }
                        var pareja = new ParejaVuelos(vuelos_ida[i], vuelos_vuelta[j], precio);
                        parejas_vuelos.push(pareja);
                        var codigo_aerolinea_ida = pareja.ida.aerolinea;
                        var codigo_aerolinea_vuelta = pareja.vuelta.aerolinea;
                        var codigo_ida = pareja.ida.codigo;
                        var codigo_vuelta = pareja.vuelta.codigo;
                        var aeropuerto_origen = pareja.ida.origen;
                        var aeropuerto_vuelta = pareja.vuelta.origen;
                        var duracion_ida = formatear_duracion(pareja.ida.duracion);
                        var duracion_vuelta = formatear_duracion(pareja.vuelta.duracion);
                        var hora_salida_ida = formatear_hora(pareja.ida.salida);
                        var hora_llegada_ida = formatear_hora(pareja.ida.llegada);
                        var hora_salida_vuelta = formatear_hora(pareja.vuelta.salida);
                        var hora_llegada_vuelta = formatear_hora(pareja.vuelta.llegada);
                        // Este if es para asegurarnos de que el vuelo de vuelta es posterior al de ida.
                        // Aunque se haya comprobado eso al introducir la fecha, podría ocurrir que se muestre
                        // una combinación con un vuelo de vuelta anterior al de ida si la fecha de ida y de vuelta coincide
                        if (hora_llegada_ida < hora_salida_vuelta) {
                            // Se muestran las parejas de vuelos. Es análogo a "sólo ida". La diferencia es que en cada tarjeta
                            // aparecen dos vuelos.
                            lista.append("<div class=\"zona-vuelo\">\n" +
                                "            <div class=\"pareja\">\n" +
                                "                <div class=\"vuelo\">\n" +
                                "                    <div class=\"aerolinea-codigo-vuelo\">\n" +
                                "                        <img class=\"aerolinea\" id=\"" + codigo_aerolinea_ida + "\" src=\"/images/" + codigo_aerolinea_ida + ".png\" title=\"Ver detalles de aerolinea\" height=\"30px\">\n" +
                                "                        <div class=\"texto-codigo-vuelo\">" + codigo_ida + "</div>\n" +
                                "                    </div>\n" +
                                "                    <div class=\"duracion-icono\">\n" +
                                "                        <div class=\"texto-duracion\">" + duracion_ida + "</div>\n" +
                                "                        <img src=\"/images/icono-viaje.png\" height=\"45px\">\n" +
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
                                "                        <img class=\"aerolinea\" id=\"" + codigo_aerolinea_vuelta + "\" src=\"/images/" + codigo_aerolinea_vuelta + ".png\" title=\"Ver detalles de aerolinea\" height=\"30px\">\n" +
                                "                        <div class=\"texto-codigo-vuelo\">" + codigo_vuelta + "</div>\n" +
                                "                    </div>\n" +
                                "                    <div class=\"duracion-icono\">\n" +
                                "                        <div class=\"texto-duracion\">" + duracion_vuelta + "</div>\n" +
                                "                        <img src=\"/images/icono-viaje.png\" height=\"45px\">\n" +
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
                                "                <h3 class=\"precio-sin-descuento\">" + precio_sin_descuento + "</h3>\n" +
                                "                <h4 class=\"h3\">" + precio + "€</h4>\n" +
                                "            </div>" +
                                "        </div>");
                        }
                    }
                }
                // En el caso en el que haya pocos vuelos, crear espacio en blanco para que la animación de autoscroll
                // se muestre correctamente.
                if(parejas_vuelos.length < 3){
                    $('.contenido').css("padding-bottom", "28%");
                }
            }
            contenido.fadeIn();
            // Vaciar los arrays de esta búsqueda
        });
    }

    // Función auxiliar para guardar los aeropuertos
    // recibidos por el getJSON y que se muestren en el autocomplete.
    function guardar_aeropuertos(l) {
        aeropuertos = l;
        origen.autocomplete({
            source: aeropuertos,
            autoFocus: true,
            delay: 0,
            change: function (event, ui) {
                if(!ui.item && origen.val() !== ""){
                    origen.val($('ul#ui-id-1 li:first div').text());
                }
            }
        });
        destino.autocomplete({
            source: aeropuertos,
            autoFocus: true,
            delay: 0,
            change: function (event, ui) {
                if(!ui.item && destino.val() !== ""){
                    destino.val($('ul#ui-id-2 li:first div').text());
                }
            }
        });
    }

    // Función auxiliar para guardar las aerolíneas y tener cargados y preparados los datos para mostrarlos en el
    // JDialog. Una vez se guardan las aerolíneas, se preparan con el formato que aparecerían en el dialog en divs
    // dentro del div dialog. Se identifican con el id "aerolínea-CODIGO".
    function guardar_aerolineas(l) {
        aerolineas = l;
        for (var i = 0; i<aerolineas.length; i++){
            dialogo.append('<div id="aerolinea-' + aerolineas[i].codigo + '">\n' +
                '        <div class="dialogo-aerolinea">\n' +
                '            <h3 class="h2">' + aerolineas[i].nombre + ' (' + aerolineas[i].codigo + ')</h3>\n' +
                '            <div class="info-aerolinea">\n' +
                '                <div>\n' +
                '                    <div class="atributo-aerolinea">\n' +
                '                        <span class="icono-info material-icons">language</span>\n' +
                '                        <div class="titulo-info">\n' +
                '                            <span class="titulo-atributo-aerolinea">Página web</span>\n' +
                '                            <a href="' + aerolineas[i].web + '">' + aerolineas[i].web + '</a>\n' +
                '                        </div>\n' +
                '                    </div>\n' +
                '                    <div class="atributo-aerolinea">\n' +
                '                        <span class="icono-info material-icons">phone</span>\n' +
                '                        <div class="titulo-info">\n' +
                '                            <span class="titulo-atributo-aerolinea">Atención al cliente</span>\n' +
                '                            <span>' + aerolineas[i].telefono + '</span>\n' +
                '                        </div>\n' +
                '                    </div>\n' +
                '                    <div class="atributo-aerolinea">\n' +
                '                        <span class="icono-info material-icons">grade</span>\n' +
                '                        <div class="titulo-info">\n' +
                '                            <span class="titulo-atributo-aerolinea">Valoración</span>\n' +
                '                            <div class="valoracion">\n' +
                '                                <div id="rateYo-' + aerolineas[i].codigo + '">\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                        </div>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '                <div class="logo-aerolinea">\n' +
                '                    <img src="/images/' + aerolineas[i].codigo + '.png" height="100rem">\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </div>\n' +
                '    </div>');
            // Valoración con el plugin RateYo, para que aparezca por estrellas.
            $('#rateYo-' + aerolineas[i].codigo).rateYo({
                starWidth: "20px",
                numStars: 5,
                rating: aerolineas[i].valoracion,
                halfStar: true,
                readOnly:true,
            });
            // Se le indica a JQuery que el div generado va a ser un dialog.
            var dialogo_aerolinea = $('#aerolinea-' + aerolineas[i].codigo);
            dialogo_aerolinea.dialog({
                autoOpen: false,
                modal: true,
                minHeight:200,
                minWidth:700,
                draggable:false,
                show:'fade',
                hide:'fade',
                buttons: {
                    "Cerrar": function () {
                        $(this).dialog("close");
                    }
                }
            });
        }
    }

    // Función auxiliar para formatear la hora a partir de la fecha de un vuelo a un formato legible.
    function formatear_hora(d) {
        var fecha = new Date(d);
        return new Intl.DateTimeFormat('es', { hour: '2-digit', minute: '2-digit' }).format(fecha);
    }

    // Función auxiliar para formatear la fecha a partir de la fecha de un vuelo a un formato legible.
    function formatear_fecha(d) {
        var fecha = new Date(d);
        return new Intl.DateTimeFormat('es').format(fecha);
    }

    // Función auxiliar para convertir la duración en minutos de cada vuelo al formato Xh Ymin, que es más legible
    function formatear_duracion(min) {
        if (min >= 60 && min % 60 !== 0) {
            return Math.trunc(min / 60) + "h " + (min % 60) + "min";
        }else if(min >= 60 && min % 60 === 0){
            return Math.trunc(min / 60) + "h";
        }else{
            return min + "min";
        }
    }

})
document.getElementById('obtenerDireccionBtn').addEventListener('click', function() {
    if ("geolocation" in navigator) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var latitud = position.coords.latitude;
            var longitud = position.coords.longitude;

            var apiKey = 'f1280a7cd360446d9667e3c61c13fef1'; // Reemplaza con tu propia clave de API de OpenCage
            var apiUrl = `https://api.opencagedata.com/geocode/v1/json?key=${apiKey}&q=${latitud}+${longitud}&language=es`;

            fetch(apiUrl)
                .then(response => response.json())
                .then(data => {
                    if (data.results.length > 0) {
                        var direccion = data.results[0].formatted;
                        document.getElementById('direccionInput').value = direccion;
                        console.log("Dirección actual:", direccion);
                    } else {
                        console.error("No se encontró la dirección para las coordenadas proporcionadas.");
                    }
                })
                .catch(error => {
                    console.error("Error al obtener la dirección:", error);
                });
        }, function(error) {
            console.error("Error al obtener la ubicación:", error.message);
        });
    } else {
        console.error("Geolocalización no compatible en este navegador.");
    }
});
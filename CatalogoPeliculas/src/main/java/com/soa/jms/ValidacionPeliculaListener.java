/**
 * 
 */
package com.soa.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.soa.business.PeliculasBusiness;
import com.soa.dto.Peliculas;
import com.soa.dto.RespuestaPeliculas;
import com.soa.dto.Tarjeta;

/**
 * Class for receiving message in an artemis queue.
 */

@Service
public class ValidacionPeliculaListener {
    @Autowired
    private PeliculasBusiness business;

    @Autowired
    private JmsSender sender;

    /**
     * Obtiene el JSON de entrada.
     * 
     * @param jsonEnviar JSON de entrada . { "nombre": "", "duracion":,
     *                   "titular":"", "noTC": "" }
     */
    @JmsListener(destination = "${queue.name.in}")
    public void receiveValidacion(String jsonEnviar) {
        System.out.println(String.format("Received message: %s",
                jsonEnviar));

        // deserializa el mensaje con un Json
        Gson gson = new Gson();

        try {
            // Contiene los datos de la tarjeta.
            Tarjeta tarjeta = gson.fromJson(jsonEnviar, Tarjeta.class);
            // Contiene los datos de la pelicula.
            Peliculas pelicula = gson.fromJson(jsonEnviar, Peliculas.class);

            /**
             * Obtiene los datos: {"nombre","duracion","costo","titular","noTC"}
             */
            RespuestaPeliculas respuesta = business.qryPelicula(pelicula, tarjeta);
            // Enviar a la cola para el servicio del Banco.
            sender.sendMessage(respuesta.toString(), "q.validacion.out");

        } catch (Exception e) {
            // imprime el mensaje de error
            System.out.println(e.getMessage());
            
            // Crea un objeto de tipo RespuestaPeliculas en el caso de que exista un error
            RespuestaPeliculas errorResponse = new RespuestaPeliculas();
            errorResponse.setMessage(e.getMessage());
            // Se envia a la cola de streaming.out para que el usuario (la cola) sepa que esta mal
            sender.sendMessage(errorResponse.toString(), "streaming.out");
        }

    }
}

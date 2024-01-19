/**
 * 
 */
package com.soa.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.soa.dto.Streams;
import com.google.gson.Gson;
import com.soa.business.StreamingBusiness;
import com.soa.dto.RespuestaStreams;

/**
 * Clase para Agregar una renta..
 */
@Component
public class StreamingAgregarListener {
    @Autowired
    private StreamingBusiness business;

    @Autowired
    private JmsSender sender;

    /**
     * Agrega una renta.
     * 
     * @param respBanco Recibe los datos de pelicula,tarjeta,tiempo contratado.
     */
    @JmsListener(destination = "banco.validacion.out")
    public void receiveOrq3(String respBanco) {
        System.out.println(String.format("Received message: %s",
                respBanco));

        // deserializa el mensaje
        Gson gson = new Gson();

        try {
            // Recibe los datos necesarios para registrar la renta.
            Streams datosRenta = gson.fromJson(respBanco, Streams.class);

            // Agrega una renta de pelicula y le asigna un id
            RespuestaStreams respuesta = business.addRenta(datosRenta);
            // Manda id a cola para mostrar la renta.
            sender.sendMessage(respuesta.toString(), "stream.agregado.out");

        } catch (Exception e) {
            // imprime el mensaje de error
            System.out.println(e.getMessage());

            // Crea un objeto de tipo RespuestaStreams en el caso de que exista un error
            RespuestaStreams errorResponse = new RespuestaStreams();
            errorResponse.setMessage(e.getMessage());
            // Se envia a la cola de streaming.out para que el usuario (la cola) sepa que
            // esta mal
            sender.sendMessage(errorResponse.toString(), "streaming.out");
        }
    }
}

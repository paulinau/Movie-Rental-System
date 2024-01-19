/**
 * 
 */
package com.soa.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.soa.business.StreamingBusiness;
import com.soa.dto.RespuestaStreams;

/**
 * Class for receiving messages in an artemis queue.
 */
@Component
public class StreamingVisualizarListener {
    @Autowired
    private StreamingBusiness business;

    @Autowired
    private JmsSender sender;

    @JmsListener(destination = "stream.agregado.out")
    public void receiveOrq4(String codigoVisualizar) {
        System.out.println(String.format("Received message: %s",
                codigoVisualizar));

        // deserializa el mensaje
        Gson gson = new Gson();

        try {
            // Recibe los datos necesarios para consultar la renta.
            RespuestaStreams idRenta = gson.fromJson(codigoVisualizar, RespuestaStreams.class);
            // Consulta que el servicio este activo o agotado
            RespuestaStreams respuesta = business.qryConsultar(idRenta);

            // Envia el status de la visualizacion a la cola de salida
            sender.sendMessage(respuesta.toString(), "streaming.out");
            System.out.println(String.format("Status del servicio: %s",
                    respuesta.toString()));
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

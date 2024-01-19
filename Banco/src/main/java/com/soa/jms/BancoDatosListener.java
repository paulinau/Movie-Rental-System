/**
 * 
 */
package com.soa.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.soa.dto.Tarjetas;
import com.google.gson.Gson;
import com.soa.business.BancoBusiness;
import com.soa.dto.RespuestaTarjetas;

/**
 * Clase para comprobar datos de la tarjeta y saldo disponible suficiente.
 */
@Component
public class BancoDatosListener {
    @Autowired
    private BancoBusiness business;

    @Autowired
    private JmsSender sender;

    /**
     * Comprobar tarjeta existente y saldo suficiente.
     * 
     * @param respPelicula Recibe datos de la tarjeta y pelicula.
     */
    @JmsListener(destination = "q.validacion.out")
    public void receiveOrq2(String respPelicula) {
        System.out.println(String.format("Received message: %s",
                respPelicula));

        // deserializa el mensaje
        Gson gson = new Gson();

        try {
            // Contiene la estructura para la cola de salida
            RespuestaTarjetas datosCompletos = gson.fromJson(respPelicula, RespuestaTarjetas.class);
            // Contiene la estructura para consultar datos de Tarjeta.
            Tarjetas tarjeta = gson.fromJson(respPelicula, Tarjetas.class);

            RespuestaTarjetas respuesta = business.qrySaldoTC(tarjeta, datosCompletos);
            // Cola de salida que lleva al Cobro.
            sender.sendMessage(respuesta.toString(), "banco.validacion");

        } catch (Exception e) {
            // imprime el mensaje de error
            System.out.println(e.getMessage());

            // Crea un objeto de tipo RespuestaTarjetas en el caso de que exista un error
            RespuestaTarjetas errorResponse = new RespuestaTarjetas();
            errorResponse.setMessage(e.getMessage());
            // Se envia a la cola de streaming.out para que el usuario (la cola) sepa que
            // esta mal
            sender.sendMessage(errorResponse.toString(), "streaming.out");
        }
    }
}

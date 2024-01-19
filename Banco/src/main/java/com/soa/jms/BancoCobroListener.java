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
 * Clase para realizar Cobro.
 */
@Component
public class BancoCobroListener {
    @Autowired
    private BancoBusiness business;

    @Autowired
    private JmsSender sender;

    /**
     * Actualiza el saldo de la tarjeta.
     * 
     * @param respPelicula Recibe los datos validados de la tarjeta.
     */
    @JmsListener(destination = "banco.validacion")
    public void receiveOrq2(String respPelicula) {
        System.out.println(String.format("Received message: %s",
                respPelicula));

        // deserializa el mensaje
        Gson gson = new Gson();

        try {
            // Contiene los datos de pelicula y tarjeta para la cola de salida.
            RespuestaTarjetas datosCompletos = gson.fromJson(respPelicula, RespuestaTarjetas.class);
            // Estructura de los datos de la tarjeta.
            Tarjetas tarjeta = gson.fromJson(respPelicula, Tarjetas.class);

            RespuestaTarjetas respuesta = business.updSaldo(tarjeta, datosCompletos);
            // Envia datos de tarjeta y pelicula para Straming.
            sender.sendMessage(respuesta.toString(), "banco.validacion.out");

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

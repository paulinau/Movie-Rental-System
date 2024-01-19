/**
 * 
 */
package com.soa.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soa.dao.BancoDao;
import com.soa.dto.RespuestaTarjetas;
import com.soa.dto.Tarjetas;

/**
 * Clase que contiene las operaciones del Banco.
 */
@Component
public class BancoBusiness {
    /** Objeto de acceso a datos. */
    @Autowired
    private BancoDao bancoDao;

    /**
     * Consulta de tarjetas basado en el número de la misma.
     * 
     * @param tarjeta          Datos de la tarjeta
     * @param respuestaTarjeta El JSON que recibimos de la cola anterior.
     * @return
     * @throws Exception Envia el mensaje de error en caso de que los datos 
     *                   de la tarjeta sean erroneos o que no tenga saldo suficiente
     * 
     */
    public RespuestaTarjetas qrySaldoTC(Tarjetas tarjeta, RespuestaTarjetas respuestaTarjeta) throws Exception {
        RespuestaTarjetas respuesta = new RespuestaTarjetas();

        List<Tarjetas> list = bancoDao.qry(tarjeta);

        // checar que la tarjeta exista
        VerificarTarjeta(list);

        // verificar que tenga saldo suficiente
        ConsultarSaldo(list, respuestaTarjeta);

        respuesta = respuestaTarjeta;

        return respuesta;
    }

    /**
     * Verifica que la tarjeta exista y los datos de la tarjeta coincidan
     * 
     * @param lista Lista que contiene la información de la tarjeta
     * @throws Exception Mensaje de error
     */
    public void VerificarTarjeta(List<Tarjetas> lista) throws Exception {
        if (lista.isEmpty()) {
            throw new Exception("Verifique los datos de la tarjeta e intente nuevamente.");
        }
    }

    /**
     * Verifica que el tiempo rentado sea menor o igual a la duracion de la pelicula
     * 
     * @param lista    Lista que contiene la info de la pelicula
     * @param pelicula Datos de entrada del JSON
     * @throws Exception Mensaje de error
     */
    public void ConsultarSaldo(List<Tarjetas> lista, RespuestaTarjetas tarjeta) throws Exception {
        for (Tarjetas tarj : lista) {

            // Obtenemos el saldo disponible de la tarjeta
            Double saldo_tarjeta = tarj.getSaldo();

            if (saldo_tarjeta < tarjeta.getCosto()) {
                throw new Exception(String.format("La tarjeta no tiene suficiente saldo, %.2f solo tiene %.2f",
                        tarjeta.getCosto(), saldo_tarjeta));
            }
        }
    }

    /**
     * Hacer cargo a la tarjeta
     * @param tarjeta Datos de la tarjeta
     * @param respuestaTarjeta El JSON que recibimos de la cola anterior.
     * @return
     * @throws Exception Mensaje en caso de que ocurra algun error
     */
    public RespuestaTarjetas updSaldo(Tarjetas tarjeta, RespuestaTarjetas respuestaTarjeta) throws Exception {
        RespuestaTarjetas respuesta = new RespuestaTarjetas();

        List<Tarjetas> list = bancoDao.qry(tarjeta);
        for (Tarjetas tarj : list) {
            // Calculamos el saldo
            double saldoNuevo = tarj.getSaldo() - respuestaTarjeta.getCosto();
            tarj.setSaldo(saldoNuevo);
            bancoDao.actualizarSaldo(tarj);

        }
        respuesta = respuestaTarjeta;

        return respuesta;
    }
}

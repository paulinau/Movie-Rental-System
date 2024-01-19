/**
 * 
 */
package com.soa.business;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soa.dao.StreamingDao;
import com.soa.dto.RespuestaStreams;
import com.soa.dto.Streams;

/**
 * Clase para operaciones de renta.
 */
@Component
public class StreamingBusiness {
    /** Objeto de acceso a datos. */
    @Autowired
    private StreamingDao streamDao;

    /**
     * Agregar una renta de pelicula.
     * 
     * @param datosRenta Contine los datos "nombre" de la pelicula, "duracion" de la
     *                   renta, "noTC" que realizo el pago, "horaInicio" de la
     *                   renta.
     * @return el id de renta generado al agregar la renta.
     * @throws Exception Mensaje de error.
     */
    public RespuestaStreams addRenta(Streams datosRenta) throws Exception {
        RespuestaStreams respuesta = new RespuestaStreams();

        System.out.println(datosRenta);

        // Obtener la hora actual
        LocalTime horaActual = LocalTime.now();
        // Crear un objeto DateTimeFormatter con el formato deseado
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
        // Formatear la hora actual en un String con el formato especificado
        String horaInicioRenta = horaActual.format(formato);
        // Agregamos la hora actual en horaInicio de datosRenta
        datosRenta.setHoraInicio(horaInicioRenta);

        // Agregar Renta.
        streamDao.addRenta(datosRenta);

        // Consultar id Renta.
        List<RespuestaStreams> list = streamDao.qryId(datosRenta);
        for (RespuestaStreams rent : list) {
            respuesta.setId(rent.getId());
        }

        return respuesta;
    }


    /**
     * Consultar si una renta está activa o caducada
     * @param datosRenta Id de la renta.
     * @return mensaje de status
     * @throws Exception Mensaje de error.
     */
    public RespuestaStreams qryConsultar(RespuestaStreams datosRenta) throws Exception {
        RespuestaStreams respuesta = new RespuestaStreams();

        List<RespuestaStreams> list = streamDao.qry(datosRenta);

        for (RespuestaStreams rent : list) {
            String horaRenta = rent.getHoraInicio();
            int duracion = rent.getDuracion();

            // Parsea horaRenta a un objeto LocalTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime horaDeseada = LocalTime.parse(horaRenta, formatter);

            // Suma segundos
            LocalTime horaFin = horaDeseada.plusSeconds(duracion);

            // Obtiene la hora actual
            LocalTime horaActual = LocalTime.now();

            // Compara si la hora esta dentro del rango de renta.
            if (horaFin.isAfter(horaActual) || horaFin.equals(horaActual)) {
                respuesta.setMessage("Aun esta disponible el contenido.");
            } else {
                respuesta.setMessage("Tu tiempo se ha agotado.");
            }
        }

        return respuesta;
    }

}

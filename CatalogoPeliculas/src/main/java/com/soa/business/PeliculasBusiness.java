/**
 * 
 */
package com.soa.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soa.dao.PeliculasDao;
import com.soa.dto.RespuestaPeliculas;
import com.soa.dto.Tarjeta;
import com.soa.dto.Peliculas;

/**
 * Clase validar la pelicula.
 */
@Component
public class PeliculasBusiness {
    /** Objeto de acceso a datos. */
    @Autowired
    private PeliculasDao peliculasDao;

    /**
     * Consulta peliculas basados en el nombre.
     * 
     * @param pelicula Datos de la pelicula
     * @param tarjeta  Datos de la tarjeta
     * @return El JSON original validado con el costo
     * @throws Exception Envia el mensaje de error en caso de que no existe la
     *                   pelicula o no dure lo suficiente
     */
    public RespuestaPeliculas qryPelicula(Peliculas pelicula, Tarjeta tarjeta) throws Exception {
        RespuestaPeliculas respuesta = new RespuestaPeliculas();

        List<Peliculas> list = peliculasDao.qry(pelicula);

        // checamos disponibilidad
        VerificarCatalogo(list);

        // checar duracion
        ChecaDuracion(list, pelicula);

        // calcular costo
        Double costo = CalcularCosto(list, pelicula);

        // Asignar el costo
        respuesta.setCosto(costo);
        // Asignar los datos para la respuesta de tarjeta y pelicula.
        respuesta.setTitular((String) tarjeta.getTitular());
        respuesta.setNoTC((String) tarjeta.getNoTC());
        respuesta.setNombre((String) pelicula.getNombre());
        respuesta.setDuracion((Integer) pelicula.getDuracion());

        return respuesta;
    }

    /**
     * Verifica que exista la pelicula en la base de datos y hace el procesamiento
     * 
     * @param lista Lista que contiene la información de la pelicula
     * @throws Exception Mensaje en caso de que este vacia
     */
    public void VerificarCatalogo(List<Peliculas> lista) throws Exception {
        if (lista.isEmpty()) {
            throw new Exception("Pelicula no encontrada, escoja otra.");
        }
    }

    /**
     * Verifica que el tiempo rentado sea menor o igual a la duracion de la pelicula
     * 
     * @param lista    Lista que contiene la info de la pelicula
     * @param pelicula Datos de entrada del JSON
     * @throws Exception Mensaje de error
     */
    public void ChecaDuracion(List<Peliculas> lista, Peliculas pelicula) throws Exception {
        for (Peliculas peli : lista) {
            // Obtenemos la duracion de la pelicula
            int duracion_pelicula = peli.getDuracion();

            // Obtenemos el tiempo que el cliente quiere rentar la pelicula
            int tiempo_rentado = pelicula.getDuracion();

            if (tiempo_rentado > duracion_pelicula) {
                throw new Exception(String.format("La peli no dura tanto tiempo,"
                        + " solo dura %d", duracion_pelicula));
            }
        }
    }

    /**
     * Calcula el costo total por segundo
     * 
     * @param lista    Lista que contiene la info de la pelicula
     * @param pelicula Datos de entrada del JSON
     * @return precio
     */
    public Double CalcularCosto(List<Peliculas> lista, Peliculas pelicula) {
        // int duracion, int tiempo, double costo
        double precio = 0;
        for (Peliculas str : lista) {
            precio = (str.getCosto() / str.getDuracion()) * pelicula.getDuracion();
        }
        return precio;
    }
}

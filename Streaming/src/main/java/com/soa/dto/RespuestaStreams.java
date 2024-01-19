/**
 * 
 */
package com.soa.dto;

import com.google.gson.Gson;

/**
 * Clase que modela la informacion resumida de si una pelicula está disponible.
 */
public class RespuestaStreams {

    /** Respuesta tipo string. */
    private String message;
    
    /** Id de renta. */
    private Integer id;
    
    /** Hora de inicio del servicio*/
    private String horaInicio;
    
    /** Duracion de la contratacion del servicio.  */
    private Integer duracion;
    
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }


    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }


    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * @return the horaInicio
     */
    public String getHoraInicio() {
        return horaInicio;
    }


    /**
     * @param horaInicio the horaInicio to set
     */
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }


    /**
     * @return the duracion
     */
    public Integer getDuracion() {
        return duracion;
    }


    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }


    @Override
    public String toString() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}

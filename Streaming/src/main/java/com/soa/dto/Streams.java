/**
 * 
 */
package com.soa.dto;

import com.google.gson.Gson;

/**
 * Clase que modela la informacion de una persona.
 */
public class Streams {

    /** Respuesta tipo string. */
    private String message;
    
    /** Id de renta. */
    private Integer id;
    
    /** Nombre de la pelicula. */
    private String nombre;
    
    /** NoTC que realizo el pago */
    private String noTC;
    
    /** Hora de inicio del servicio*/
    private String horaInicio;
    
    /** Duracion de la contratacion del servicio.  */
    private Integer duracion;
    
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the noTC
     */
    public String getNoTC() {
        return noTC;
    }

    /**
     * @param noTC the noTC to set
     */
    public void setNoTC(String noTC) {
        this.noTC = noTC;
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

    public String toString() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}

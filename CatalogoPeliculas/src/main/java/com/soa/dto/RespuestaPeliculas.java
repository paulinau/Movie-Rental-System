/**
 * 
 */
package com.soa.dto;

import com.google.gson.Gson;

/**
 * Clase que modela la informacion resumida de si una pelicula está disponible.
 */
public class RespuestaPeliculas {

    /** Respuesta para el próximo servicio. */
    private String message;
    
    /** Nombre de la pelicula. */
    private String nombre;
    
    /** Duracion de la pelicula */
    private Integer duracion;
    
    /** Costo del servicio. */
    private Double costo;
    
    /** Titular */
    private String titular;
    
    /** NoTC */
    private String noTC;

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
     * @return the costo
     */
    public Double getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(Double costo) {
        this.costo = costo;
    }

    /**
     * @return the titular
     */
    public String getTitular() {
        return titular;
    }

    /**
     * @param titular the titular to set
     */
    public void setTitular(String titular) {
        this.titular = titular;
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

    @Override
    public String toString() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
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
}

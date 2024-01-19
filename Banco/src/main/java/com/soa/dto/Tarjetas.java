/**
 * 
 */
package com.soa.dto;

import com.google.gson.Gson;

/**
 * Clase que modela la informacion de una persona.
 */
public class Tarjetas {

    /** Nombre de la pelicula. */
    private String noTC;
    
    /** saldo de la tarjeta. */
    private Double saldo;
    
    /** nombre del titular de la tarjeta. */
    private String titular;

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
     * @return the saldo
     */
    public Double getSaldo() {
        return saldo;
    }


    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(Double saldo) {
        this.saldo = saldo;
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


    public String toString() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}

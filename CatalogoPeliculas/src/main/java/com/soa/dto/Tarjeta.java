/**
 * 
 */
package com.soa.dto;

import com.google.gson.Gson;

/**
 * Clase que modela la informacion de una persona.
 */
public class Tarjeta {

    /** Titular */
    private String titular;
    
    /** NoTC */
    private String noTC;
    
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

    public String toString() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}

/**
 * 
 */
package com.soa.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.soa.dto.Tarjetas;

/**
 * Clase para las operaciones de banco en BD.
 */
@Repository
public class BancoDao {

    /**
     * Objeto especializado en acceso a la DB.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Consulta el saldo de la tarjeta basados en el numero de la tarjeta.
     * @param tarjeta Objeto que tiene los datos de la tarjeta
     */
    public List<Tarjetas> qry(Tarjetas tarjeta) {
        List<Tarjetas> resp = new ArrayList<>();
        List<Map<String,Object>> list = 
                jdbcTemplate.queryForList(
                        "select numerotarjeta, saldo, titular "
                + "from tarjetas where numerotarjeta = '"
                + tarjeta.getNoTC() + "' AND titular = '"
                + tarjeta.getTitular()+ "'");
        for (Map<String, Object> registro : list) {
            Tarjetas card = new Tarjetas();
            card.setNoTC((String) registro.get("numerotarjeta"));
            card.setSaldo((Double) registro.get("saldo"));
            card.setTitular((String) registro.get("titular"));
            resp.add(card);
        }
        return resp;
    }
    
    /**
     * Modificar el saldo de la tarjeta en la BD.
     * @param tarjeta Tarjeta a modificar.
     */
    public void actualizarSaldo(Tarjetas tarjeta) {
        jdbcTemplate.execute("update tarjetas set saldo = "
                + tarjeta.getSaldo() +" where numerotarjeta = '"
                        + tarjeta.getNoTC() + "' AND titular = '"
                        + tarjeta.getTitular()+ "'");
    }
}

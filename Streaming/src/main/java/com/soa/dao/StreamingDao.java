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

import com.soa.dto.RespuestaStreams;
import com.soa.dto.Streams;

/**
 * Clase para operaciones en BD de rentas.
 */
@Repository
public class StreamingDao {

    /**
     * Objeto especializado en acceso a la DB.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Agregar una renta a la BD.
     * @param datosRenta contiene los datos para agregar una renta.
     */
    public void addRenta(Streams datosRenta) {
        jdbcTemplate.execute("INSERT INTO rentas "
                + "(numerotarjeta, nombrePelicula, horaInicio, tiempoRenta)"
                + "VALUES ('"+datosRenta.getNoTC()+"','"+datosRenta.getNombre()
                +"', '"+datosRenta.getHoraInicio()+"','"+datosRenta.getDuracion()+"');");
    }
    
    /**
     * Consultar una renta por NoTC, pelicula, horaInicio, duracion.
     * @param datosRenta 
     * @return
     */
    public List<RespuestaStreams> qryId(Streams datosRenta) {
        List<RespuestaStreams> resp = new ArrayList<>();
        List<Map<String,Object>> list = 
                jdbcTemplate.queryForList(
                        "select id "
                + "from rentas where numerotarjeta = '"
                + datosRenta.getNoTC() + "' AND nombrePelicula = '" 
                +datosRenta.getNombre()+ "' AND horaInicio = '"
                +datosRenta.getHoraInicio()+ "' AND tiempoRenta = '"
                +datosRenta.getDuracion()+"'");
        for (Map<String, Object> registro : list) {
            RespuestaStreams rent = new RespuestaStreams();
            rent.setId((Integer) registro.get("id"));
            resp.add(rent);
        }
        return resp;
    }
    
    /**
     * Consultar una renta por id.
     * @param datosRenta 
     * @return
     */
    public List<RespuestaStreams> qry(RespuestaStreams datosRenta) {
        List<RespuestaStreams> resp = new ArrayList<>();
        List<Map<String,Object>> list = 
                jdbcTemplate.queryForList(
                        "select horaInicio, tiempoRenta "
                + "from rentas where id = '"
                + datosRenta.getId() + "'");
        for (Map<String, Object> registro : list) {
            RespuestaStreams rent = new RespuestaStreams();
            rent.setHoraInicio((String) registro.get("horaInicio"));
            rent.setDuracion((Integer) registro.get("tiempoRenta"));
            resp.add(rent);
        }
        System.out.println(resp);
        return resp;
    }
    
}

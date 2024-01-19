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

import com.soa.dto.Peliculas;

/**
 * Clase para buscar las películas en BD.
 */
@Repository
public class PeliculasDao {

    /**
     * Objeto especializado en acceso a la DB.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Consulta de peliculas basados en el nombre en BD.
     * @param pelicula Objeto que contiene la pelicula a consultar.
     */
    public List<Peliculas> qry(Peliculas pelicula) {
        List<Peliculas> resp = new ArrayList<>();
        List<Map<String,Object>> list = 
                jdbcTemplate.queryForList(
                        "select nombre, costo, duracion "
                + "from peliculas where nombre = '"
                + pelicula.getNombre() + "'");
        for (Map<String, Object> registro : list) {
            Peliculas movie = new Peliculas();
            movie.setNombre((String) registro.get("nombre"));
            movie.setCosto((Double) registro.get("costo"));
            movie.setDuracion((Integer) registro.get("duracion"));
            resp.add(movie);
        }
        return resp;
    }
}

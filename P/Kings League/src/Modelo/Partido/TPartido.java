package Modelo.Partido;

import Modelo.BaseDeDatos.BaseDeDatos;

import Modelo.BaseDeDatos.BaseDeDatos;
import Modelo.Equipo.TEquipo;

/**
 * Generar la clase tPartido.
 * Esta clase contiene los métodos necesarios para hacer operaciones con los partidos (actualizar partidos, insertarlos, etc.).
 */
public class TPartido {

    public static boolean actualizarPartido(Partido partido) {
        return BaseDeDatos.executeUpdate("update partidos set GOLES_EQUIPO1 = ?, GOLES_EQUIPO2 = ? where ID_EQUIPO1 = ? and  ID_EQUIPO2 = ?",
                new Object[]{partido.getGolesEquipo1(), partido.getGolesEquipo2(), partido.getEquipo1().getId(), partido.getEquipo2().getId()});
    }

    public static boolean insertarPartido(Partido partido) {
        return BaseDeDatos.executeUpdate("insert into partidos (id, id_jornada, fecha, lugar, id_equipo1, id_equipo2) values ((select max(id) from partidos) + 1, (select max(id) from jornadas),?,?,?,?) ",
                new Object[]{partido.getFecha(), partido.getLugar(), partido.getEquipo1().getId(), partido.getEquipo2().getId()});
    }
}

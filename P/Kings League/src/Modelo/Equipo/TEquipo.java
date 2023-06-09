package Modelo.Equipo;

import Modelo.BaseDeDatos.BaseDeDatos;
import Modelo.Enumeraciones.TipoPersonal;
import Modelo.Jugador.Jugador;
import Modelo.Personal.Personal;
import Modelo.Personal.TPersonal;
import Modelo.Usuario.Usuario;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Generar la clase TEquipo.
 * Esta clase contiene los métodos necesarios para hacer operaciones con los equipos (mostrar equipos, insertarlos, etc.).
 */
public class TEquipo {

    public static ArrayList<Jugador> getInfomeEquipos(Equipo equipo, Personal[] personales) {
        try {
            BaseDeDatos.abrirConexion();
            CallableStatement statement = BaseDeDatos.getCon().prepareCall("{call INFORME_EQUIPO(?,?)}");
            statement.registerOutParameter(1, OracleTypes.CURSOR);
            statement.setString(2, equipo.getNombre());
            statement.execute();
            ResultSet resultSet = (ResultSet) statement.getObject(1);
            ArrayList<Jugador> jugadores = new ArrayList<>();
            if (resultSet.next()) {
                equipo.resultSetObjeto(resultSet);
                personales[0] = new Personal(resultSet.getInt("id_presidente"), TipoPersonal.PRESIDENTE);
                personales[1] = new Personal(resultSet.getInt("id_entrenador"), TipoPersonal.ENTRENADOR);

                TPersonal.ConsultarPersonal(personales[0]);
                TPersonal.ConsultarPersonal(personales[1]);
                do {
                    jugadores.add(new Jugador());
                    jugadores.get(jugadores.size() - 1).resultSetObjeto(resultSet);
                } while (resultSet.next());
            }
            BaseDeDatos.cerrarConexion();
            return jugadores;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static Equipo getEquipoPorNombre(String nombre) {
        Equipo equipo = new Equipo();
        System.out.println(nombre);
        BaseDeDatos.consultaObjeto(equipo, "select * from equipos where upper(nombre) = upper(?)", new Object[]{nombre});
        return equipo;
    }


    public static ArrayList<Equipo> selectAllEquipos(ArrayList equipos) throws SQLException {
        BaseDeDatos.abrirConexion();
        PreparedStatement ps = BaseDeDatos.getCon().prepareStatement("select nombre, logo_img, color from equipos order by id");
        ResultSet resul = ps.executeQuery();
        while (resul.next()) {
            Equipo equipo = new Equipo();
            equipo.setNombre(resul.getString("nombre"));
            equipo.setLogoImg(resul.getString("logo_img"));
            equipo.setColor(resul.getString("color"));
            equipos.add(equipo);
        }
        BaseDeDatos.cerrarConexion();
        return equipos;
    }
    public static ArrayList<String> selectNombre(ArrayList<String> nombres) {
        try {
            BaseDeDatos.abrirConexion();
            PreparedStatement ps= BaseDeDatos.getCon().prepareStatement("select distinct nombre from equipos");
            ResultSet resulatdo=ps.executeQuery();
            nombres.add("Seleccione un nombre");
            while (resulatdo.next()){
                nombres.add(resulatdo.getString("nombre"));
            }
            return nombres;
        }catch (Exception e){
            System.out.println(e.getClass()+ e.getMessage());
            return null;
        }
    }

    public static boolean insertar(Equipo equipo) {
        boolean insertar=false;
        try {
            BaseDeDatos.abrirConexion();
            PreparedStatement ps= BaseDeDatos.getCon().prepareStatement("INSERT INTO EQUIPOS (NOMBRE, PRESUPUESTO_ANUAL, LOGO_IMG, COLOR) VALUES (initcap(?),?,?,?)");
            ps.setString(1, equipo.getNombre());
            ps.setDouble(2, equipo.getPresupuestoAnual());
            ps.setString(3, equipo.getLogoImg());
            ps.setString(4, equipo.getColor());
            ResultSet resultado= ps.executeQuery();
            if (resultado.next()){
                insertar=true;
            }
            BaseDeDatos.cerrarConexion();
            return insertar;
        }catch (Exception e){
            System.out.println(e.getClass()+ e.getMessage());
            return false;
        }
    }
    public static boolean selectEquipoInsertar(Equipo equipo){
        boolean existe = false;
        try {
            BaseDeDatos.abrirConexion();
            PreparedStatement ps= BaseDeDatos.getCon().prepareStatement("select * from equipos where upper(nombre) = upper(?)");
            ps.setString(1, equipo.getNombre());
            ResultSet resultado= ps.executeQuery();
            if (resultado.next()) {
                equipo.setId(resultado.getInt("id"));
                existe = true;
            }
            BaseDeDatos.cerrarConexion();
            return existe;
        }catch (Exception e){
            return false;
        }
    }
    public static boolean update(Equipo equipo){
        boolean update=false;
        try {
            BaseDeDatos.abrirConexion();
            PreparedStatement ps= BaseDeDatos.getCon().prepareStatement("UPDATE EQUIPOS SET NOMBRE=?, PRESUPUESTO_ANUAL=?, LOGO_IMG=?, COLOR=? WHERE id=?");
            ps.setString(1, equipo.getNombre());
            ps.setDouble(2, equipo.getPresupuestoAnual());
            ps.setString(3, equipo.getLogoImg());
            ps.setString(4, equipo.getColor());
            ps.setInt(5, equipo.getId());
            int resultado= ps.executeUpdate();
            System.out.println(resultado);
            if (resultado==1){
                update=true;
            }
            BaseDeDatos.cerrarConexion();
            return update;
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getClass()+ e.getMessage());
            return false;
        }
    }
    public static Equipo getEquipoPorNombreDavid(Equipo equipo){
        try {
            BaseDeDatos.abrirConexion();
            PreparedStatement ps= BaseDeDatos.getCon().prepareStatement("select * from equipos where nombre=?");
            ps.setString(1, equipo.getNombre());
            ResultSet resulatdo=ps.executeQuery();
            if (resulatdo.next()){
                equipo.setId(resulatdo.getInt("id"));
                equipo.setNombre(resulatdo.getString("nombre"));
                equipo.setPresupuestoAnual(resulatdo.getDouble("presupuesto_anual"));
                equipo.setLogoImg(resulatdo.getString("logo_img"));
                equipo.setColor(resulatdo.getString("color"));
            }
            return equipo;
        }catch (Exception e){
            System.out.println(e.getClass()+ e.getMessage());
            return null;
        }
    }
    public static Equipo getEquipoPorIDDavid(Equipo equipo){
        try {
            BaseDeDatos.abrirConexion();
            PreparedStatement ps= BaseDeDatos.getCon().prepareStatement("select * from equipos where id=?");
            ps.setInt(1, equipo.getId());
            ResultSet resulatdo=ps.executeQuery();
            if (resulatdo.next()){
                equipo.setId(resulatdo.getInt("id"));
                equipo.setNombre(resulatdo.getString("nombre"));
                equipo.setPresupuestoAnual(resulatdo.getDouble("presupuesto_anual"));
                equipo.setLogoImg(resulatdo.getString("logo_img"));
                equipo.setColor(resulatdo.getString("color"));
            }
            return equipo;
        }catch (Exception e){
            System.out.println(e.getClass()+ e.getMessage());
            return null;
        }
    }
    public static boolean delete(Equipo equipo) {
        boolean borrar = false;
        try {
            BaseDeDatos.abrirConexion();
            PreparedStatement ps= BaseDeDatos.getCon().prepareStatement("delete equipos where id=?");
            ps.setInt(1, equipo.getId());
            int resul= ps.executeUpdate();
            System.out.println(resul +" Fila eliminada en Equipos");
            if (resul==1){
                borrar=true;
            }
            System.out.println();
            return borrar;
        }catch (Exception e){
            System.out.println(e.getClass()+ e.getMessage());
            return false;
        }

    }
}

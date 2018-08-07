package accesobd;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Clase Conector
 *
 * @version 1.0
 * @author Laura Monge Izaguirre Clase que inicializa la conexi�n con los
 * valores correctos y permite manejar una unica conexion para todo el proyecto
 * y
 *
 */
public class Conector {
    //atributo de la clase	

    private static AccesoBD conectorBD = null;

    /**
     * Metodo estatico que devuelve el objeto AccesoBD para que sea utilizado
     * por las clases
     *
     * @return objeto del tipo AccesoBD del paquete CapaAccesoDatos
     */
//	public static AccesoBD getConector() throws 
//	java.sql.SQLException,Exception{
//		if (conectorBD == null){			
////			conectorBD = new AccesoBD("sun.jdbc.odbc.JdbcOdbcDriver","jdbc:odbc:BDCxC","sa","jass2002");
//			conectorBD = new AccesoBD("sun.jdbc.odbc.JdbcOdbcDriver","jdbc:odbc:BDCxCAccess","","");
//		}
//		return conectorBD;
//	}
//	
    public static AccesoBD getConector() throws java.sql.SQLException, Exception {

        if (conectorBD == null) {
            FileReader reader = new FileReader("Accesobd.txt");
            BufferedReader buffer = new BufferedReader(reader);
            String datos;
            while ((datos = buffer.readLine()) != null) {
                String data[];
                data = datos.split(",");
                conectorBD = new AccesoBD(data[0], data[1]);
            }
            reader.close();

        }
        return conectorBD;
    }
}

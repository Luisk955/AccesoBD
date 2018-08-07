package accesobd;

/**
 * Clase AccesoBD
 *
 * @version 2.0
 * @author Laura Monge Izaguirre Clase que maneja el acceso a la base de datos.
 * Se debe hacer una instancia de la clase para poder utilizar sus servicios
 *
 *
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class AccesoBD {
    //atributos del objeto

    private Connection conn = null;
    private Statement st;
    private CallableStatement proc;

    /**
     * Método constructor que recibe todos los parametros necesarios para abrir
     * una conexion valida
     *
     * @param driver especificacion del tipo de driver que se utiliza, el cual
     * responde al repositorio utilizado
     * @param conexion cadena de conexion con la base de datos
     * @param usuario nombre del usuario de la base de datos, si no se utiliza,
     * se debe enviar un string vacio
     * @param clave palabra clave del usuario para realizar su autenticacion en
     * la base de datos
     * @throws java.sql.SQLException
     */
//	public AccesoBD(String driver, String conexion,	String usuario, String clave) throws SQLException,Exception{
//		Class.forName(driver);
//		conn = DriverManager.getConnection(conexion, usuario, clave);
//		st = conn.createStatement();
//	}
    public AccesoBD(String driver, String conexion) throws SQLException, Exception {
        Class.forName(driver);
        conn = DriverManager.getConnection(conexion);
        st = conn.createStatement();
    }

    /**
     * Método que ejecuta una sentencia en la base de datos, la cual no tiene
     * retorno, es decir un insert, delete o update
     *
     * @param data arreglo con los datos para insertar/buscar
     * @param query cadena sql que ser� ejecutada en la base de datos
     * @throws java.sql.SQLException
     *
     */
    public void ejecutarSQL(ArrayList<Object> data, String query) throws SQLException {
        proc = conn.prepareCall(query);
        try {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i) instanceof String) {
                    proc.setString(i + 1, (String) data.get(i));
                }
                if (data.get(i) instanceof Integer) {
                    proc.setInt(i + 1, (int) data.get(i));
                }
                if (data.get(i) instanceof Boolean) {
                    proc.setBoolean(i + 1, (Boolean) data.get(i));
                }
                if (data.get(i) instanceof Date) {
                    proc.setDate(i + 1, (Date) data.get(i));
                }
            }
            proc.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void ejecutarSQL(int num, String query) throws SQLException, Exception {
        proc = conn.prepareCall(query);
        proc.setInt(1, num);
        proc.executeUpdate();
    }
    
    public ResultSet ejecutarSQL(int num, String query, boolean result) throws SQLException, Exception {
        ResultSet rs;
        proc = conn.prepareCall(query);
        proc.setInt(1, num);
        proc.executeUpdate();
        rs = proc.getResultSet();
        return rs;
    }

    public void ejecutarSQL(String sentencia) throws SQLException, Exception {
        st.execute(sentencia);
    }

    /**
     * Metodo que ejecuta una sentencia en la base de datos y devuelve un
     * ResultSet con los resultados
     *
     * @param sentencia cadena sql que ser� ejecutada en la base de datos
     * @param retorno booleana que indica que se desea un resultado de la
     * consulta
     * @return
     * @throws java.sql.SQLException
     */
//    public ResultSet ejecutarSQL(String sentencia, boolean retorno) throws SQLException, Exception {
//        ResultSet rs;
//        rs = st.executeQuery(sentencia);
//        return rs;
//    }
    public ResultSet ejecutarSQL(String query, boolean retorno) throws SQLException, Exception {
        ResultSet rs;
        proc = conn.prepareCall(query);
        proc.execute();
        rs = proc.getResultSet();
        return rs;
    }

    /**
     * Permite controlar el inicio una transaccion desde afuera. A partir de
     * este momento todas las sentencias esperaran la orden para ser aceptadas
     * en la base de datos
     *
     * @throws java.sql.SQLException
     */
    public void iniciarTransaccion() throws java.sql.SQLException {
        conn.setAutoCommit(false);
    }

    /**
     * Permite controlar el termino una transaccion desde afuera. A partir de
     * este momento todas las sentencias se ejecturan de forma individual en la
     * base de datos
     *
     * @throws java.sql.SQLException
     */
    public void terminarTransaccion() throws java.sql.SQLException {
        conn.setAutoCommit(true);
    }

    /**
     * Indica que la transaccion ha sido aceptada
     *
     * @throws java.sql.SQLException
     */
    public void aceptarTransaccion() throws java.sql.SQLException {
        conn.commit();
    }

    /**
     * Indica que la transaccion debe ser deshecha porque no se realizo de forma
     * exitosa
     *
     * @throws java.sql.SQLException
     */
    public void deshacerTransaccion() throws java.sql.SQLException {
        conn.rollback();
    }

    /**
     * Metodo sobreescrito de la clase Object que es invocado por el Garbage
     * Collector cuando es invocado libera la conexion abierta durante la
     * creacion del objeto
     *
     */
    protected void finalize() {
        try {
            conn.close();
        } catch (SQLException e) {
            /*este metodo es llamado por el
			 *garbage collector, por lo tanto
			 *se atrapa la excepcion pero no se
			 *reporta*/
        }
    }
}

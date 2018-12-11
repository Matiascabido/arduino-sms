package Clases;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Conexion {

    private String user = "";
    private String pass = "";
    private String driver = "";
    private String url = "";
    private Connection conn;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");

    private void cerrarconexion() throws SQLException {
        conn.close();
    }

    private void abrirconexion() throws SQLException {
        conn = DriverManager.getConnection(url, user, pass);
    }

    public void Cerrar1(Connection conn, CallableStatement stmt) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String limpiarAcentos(String cadena) {
        String limpio = null;
        if (cadena != null) {
            String valor = cadena;
            valor = valor.toUpperCase();
            // Normalizar texto para eliminar acentos, dieresis, cedillas y tildes
            limpio = Normalizer.normalize(valor, Normalizer.Form.NFD);
            // Quitar caracteres no ASCII excepto la enie, interrogacion que abre, exclamacion que abre, grados, U con dieresis.
            limpio = limpio.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
            // Regresar a la forma compuesta, para poder comparar la enie con la tabla de valores
            limpio = Normalizer.normalize(limpio, Normalizer.Form.NFC);
        }
        return limpio;
    }

    public static String cleanString(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }

    public ArrayList<contact> cargarContacto() throws ParseException {
        ArrayList<contact> resultado = new ArrayList<>();

        try {
            abrirconexion();
            Statement stmt = conn.createStatement();
            ResultSet query = stmt.executeQuery("select id, Numero, Mensaje,estado from Mensajes where estado = 0");

            while (query.next()) {

                String numeroTel = query.getString("numero");
                String mensaje = query.getString("Mensaje");
                int id = query.getInt("id");
                boolean estado = query.getBoolean("estado");
                Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                contact p = new contact(numeroTel, mensaje,estado,id);
                    resultado.add(p);
                }

            query.close();
            stmt.close();
            cerrarconexion();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return resultado;
    }
 
   public int InsertRegistros(int cantidad) {
        int filasAfectadas = 0;
        try {
            abrirconexion();
            PreparedStatement stmt = 
            conn.prepareStatement("insert into auditoria(fecha,campo) values(getdate(),'"+cantidad+"')");
            filasAfectadas = stmt.executeUpdate();

            stmt.close();
            cerrarconexion();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return filasAfectadas;
    }

       public int UpdatenNumero(int id) {
        int filasAfectadas = 0;
        try {
            abrirconexion();
            PreparedStatement stmt = 
            conn.prepareStatement("update Mensajes set estado = 1 where id = " + id);
            filasAfectadas = stmt.executeUpdate();

            stmt.close();
            cerrarconexion();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return filasAfectadas;
    }
  
  public int UpdateLog(int id, String error) {
        int filasAfectadas = 0;
        try {
            abrirconexion();
            PreparedStatement stmt = 
            conn.prepareStatement("update Mensajes set error = '"+ error + "' where id = " + id);
            filasAfectadas = stmt.executeUpdate();

            stmt.close();
            cerrarconexion();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return filasAfectadas;
    }
    
}

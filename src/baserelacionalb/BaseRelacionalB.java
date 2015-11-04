/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baserelacionalb;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oracle
 */
public class BaseRelacionalB {

    Connection conn;
    ResultSet rs;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BaseRelacionalB brB = new BaseRelacionalB();
        brB.conexion();
        brB.listar();
        //  brB.actualizar();
        //  brB.insertar("p4", "martelo", 20);
        brB.borrar(3);
        brB.listar();
    }

    public void conexion() {

        try {
            String driver = "jdbc:oracle:thin:";
            String host = "localhost.localdomain"; // tambien puede ser una ip como "192.168.1.14"
            String porto = "1521";
            String sid = "orcl";
            String usuario = "hr";
            String password = "hr";
            String url = driver + usuario + "/" + password + "@" + host + ":" + porto + ":" + sid;

            conn = DriverManager.getConnection(url);
            System.out.println("Conexion establecida.");

        } catch (SQLException ex) {
            Logger.getLogger(BaseRelacionalB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        try {
            rs = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("select * from produtos");

            while (rs.next() != false) {
                System.out.print(rs.getString(1) + " - ");
                System.out.print(rs.getString(2) + " - ");
                System.out.println(rs.getString(3));
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(BaseRelacionalB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizar() {
        try {
            rs = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("select produtos.* from produtos");
            while (rs.next()) {
                if ("p2".equals(rs.getString(1))) {
                    rs.updateInt("prezo", 8);
                    rs.updateRow();
                }
            }
            System.out.println("Actualizacion realizada");

        } catch (SQLException ex) {
            Logger.getLogger(BaseRelacionalB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertar(String cod, String desc, int pre) {
        try {
            rs = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("select produtos.* from produtos");
            rs.moveToInsertRow();
            rs.updateString("codigo", cod);
            rs.updateString("descricion", desc);
            rs.updateInt("prezo", pre);
            rs.insertRow();
            System.out.println("Inserccion realizada");
        } catch (SQLException ex) {
            Logger.getLogger(BaseRelacionalB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void borrar(int numC) {
        try {
            rs = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("select produtos.* from produtos");
            rs.absolute(numC);
            rs.deleteRow();
            System.out.println("Borrado realizado");
        } catch (SQLException ex) {
            Logger.getLogger(BaseRelacionalB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

package com.final_proy.Servicios;



import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class BaseDatos {



    private static BaseDatos instancia;
    private String URL = "jdbc:h2:tcp://localhost/~/pruebaImg";

    /**
     *Implementando el patron Singlenton
     */
    public BaseDatos(){
        registrarDriver();
    }

    /**
     * Retornando la instancia.
     * @return
     */
    public static BaseDatos getInstancia(){
        if(instancia==null){
            instancia = new BaseDatos();
        }
        return instancia;
    }

    /**
     * Metodo para el registro de driver de conexión.
     */
    private void registrarDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.print("Error al tratar de registrar base de Datos");
            System.out.println(ex.getMessage());
        }
    }

    public Connection getConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, "sa", "");
        } catch (SQLException ex) {
           // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
            System.out.print("Error en la Conexion");
            System.out.println(ex.getMessage());
        }
        return con;
    }

    public void testConexion() {
        try {
            getConexion().close();
            System.out.println("Conexión realizado con exito...");
        } catch (SQLException ex) {
            //Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
            System.out.print("Fail X(");
            System.out.println(ex.getMessage());
        }
    }
        /* *
         *
         * @throws SQLException
         */
        public static void startDb() throws SQLException {
            Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
        }

        /* *
         *
         * @throws SQLException
         */
        public static void stopDb() throws SQLException {
            Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
        }



}


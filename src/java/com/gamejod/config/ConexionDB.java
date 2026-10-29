/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamejod.config;

/**
 *
 * @author maico
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
/**
 * Conexión a MySQL.
 * CAMBIO: el puerto era 8080 (que esno en el de Tomcat) y ahora es 3306 (el de MySQL).
 */
public class ConexionDB {
 
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String BD = "gamebase";
    private static final String USER = "root";
    private static final String PASS = "";
 
    private ConexionDB() {
    }
 
    public static Connection getConnection() {
 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró ningún conector de MySQL (falta el .jar en Libraries): " + e.getMessage());
        }
 
        try {
            return DriverManager.getConnection(URL + BD, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Error en las credenciales de acceso al SGBD");
            System.out.println("Detalle real: " + e.getMessage() + "  (URL: " + URL + BD + ", usuario: " + USER + ")");
        }
 
        return null;
    }
 
    public static void main(String[] args) {
 
        Connection prueba = ConexionDB.getConnection();
        String SQL = "SELECT NOW() AS Fecha";
 
        try {
 
            PreparedStatement puente = prueba.prepareStatement(SQL);
 
            ResultSet resultado = puente.executeQuery();
 
            while (resultado.next()) {
 
                System.out.println("Por nombre: " + resultado.getString("Fecha"));
                System.out.println("Por posición: " + resultado.getString(1));
 
            }
 
        } catch (SQLException ex) {
 
            System.out.println("Verificar la sentencia SQL");
 
        }
 
    }
}
 
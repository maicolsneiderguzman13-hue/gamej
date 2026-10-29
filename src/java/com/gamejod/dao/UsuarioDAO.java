/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamejod.dao;

import com.gamejod.config.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author maico
 */

public class UsuarioDAO {
 
    /** Datos que necesita el login de un usuario. */
    public static class Credencial {
        public int id;
        public String password;
        public boolean bloqueado;
    }
 
    private Connection conectar() throws SQLException {
        Connection c = ConexionDB.getConnection();
        if (c == null) {
            throw new SQLException("Sin conexión con la base de datos");
        }
        return c;
    }
 
    /** Busca al usuario; devuelve null si no existe. */
    public Credencial buscar(String usuario) throws SQLException {
        String sql = "SELECT id, password, "
                + "(bloqueado_hasta IS NOT NULL AND bloqueado_hasta > NOW()) AS bloqueado "
                + "FROM usuario WHERE usuario = ?";
        try (Connection c = conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Credencial cr = new Credencial();
                cr.id = rs.getInt("id");
                cr.password = rs.getString("password");
                cr.bloqueado = rs.getBoolean("bloqueado");
                return cr;
            }
        }
    }
 
    public boolean existe(String usuario) throws SQLException {
        String sql = "SELECT 1 FROM usuario WHERE usuario = ?";
        try (Connection c = conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
 
    /** Crea el usuario con la contraseña YA cifrada. */
    public void crear(String nombre, String usuario, String passwordCifrada) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, usuario, password, fecha_registro) VALUES (?, ?, ?, NOW())";
        try (Connection c = conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, usuario);
            ps.setString(3, passwordCifrada);
            ps.executeUpdate();
        }
    }
 
    /** True si ya hay una cuenta con ese correo (columna correo, ver etapa1_correo.sql). */
    public boolean existeCorreo(String correo) throws SQLException {
        String sql = "SELECT 1 FROM usuario WHERE correo = ?";
        try (Connection c = conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
 
    /** Crea el usuario guardando tambien su correo. Contraseña YA cifrada. */
    public void crearConCorreo(String nombre, String usuario, String correo, String passwordCifrada) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, usuario, correo, password, fecha_registro) VALUES (?, ?, ?, ?, NOW())";
        try (Connection c = conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, usuario);
            ps.setString(3, correo);
            ps.setString(4, passwordCifrada);
            ps.executeUpdate();
        }
    }
 
    /**
     * Suma un intento fallido. Si llega al máximo, bloquea la cuenta por 'minutos' minutos.
     * @return true si la cuenta quedó bloqueada
     */
    public boolean registrarFallo(int id, int maxIntentos, int minutos) throws SQLException {
        try (Connection c = conectar()) {
            try (PreparedStatement ps = c.prepareStatement(
                    "UPDATE usuario SET intentos_fallidos = intentos_fallidos + 1 WHERE id = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
            int intentos = 0;
            try (PreparedStatement ps = c.prepareStatement(
                    "SELECT intentos_fallidos FROM usuario WHERE id = ?")) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        intentos = rs.getInt(1);
                    }
                }
            }
            if (intentos >= maxIntentos) {
                try (PreparedStatement ps = c.prepareStatement(
                        "UPDATE usuario SET bloqueado_hasta = DATE_ADD(NOW(), INTERVAL ? MINUTE), "
                        + "intentos_fallidos = 0 WHERE id = ?")) {
                    ps.setInt(1, minutos);
                    ps.setInt(2, id);
                    ps.executeUpdate();
                }
                return true;
            }
            return false;
        }
    }
 
    /** Login correcto: limpia los intentos y, si se pasa, guarda la contraseña ya cifrada. */
    public void registrarExito(int id, String nuevoHash) throws SQLException {
        String sql = (nuevoHash == null)
                ? "UPDATE usuario SET intentos_fallidos = 0, bloqueado_hasta = NULL WHERE id = ?"
                : "UPDATE usuario SET intentos_fallidos = 0, bloqueado_hasta = NULL, password = ? WHERE id = ?";
        try (Connection c = conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            if (nuevoHash == null) {
                ps.setInt(1, id);
            } else {
                ps.setString(1, nuevoHash);
                ps.setInt(2, id);
            }
            ps.executeUpdate();
        }
    }
}
 
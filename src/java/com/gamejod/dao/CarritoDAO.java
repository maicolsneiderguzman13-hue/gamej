/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamejod.dao;

import com.gamejod.config.ConexionDB;
import com.gamejod.modelo.ItemCarrito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author maico
 */
public class CarritoDAO {
 
    private Connection conectar() throws SQLException {
        Connection c = ConexionDB.getConnection();
        if (c == null) {
            throw new SQLException("Sin conexión con la base de datos");
        }
        return c;
    }
 
    /** Busca el id del usuario a partir del nombre que quedó en la sesión. */
    public Integer idUsuario(String usuario) throws SQLException {
        try (Connection c = conectar();
             PreparedStatement ps = c.prepareStatement("SELECT id FROM usuario WHERE usuario = ?")) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : null;
            }
        }
    }
 
    public List<ItemCarrito> items(int usuarioId) throws SQLException {
        String sql = "SELECT j.id, j.titulo, j.imagen, j.precio, j.stock, ci.cantidad "
                + "FROM carrito_item ci JOIN juego_catalogo j ON j.id = ci.juego_id "
                + "WHERE ci.usuario_id = ? ORDER BY ci.id";
        List<ItemCarrito> lista = new ArrayList<>();
        try (Connection c = conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ItemCarrito it = new ItemCarrito();
                    it.setJuegoId(rs.getInt("id"));
                    it.setTitulo(rs.getString("titulo"));
                    it.setImagen(rs.getString("imagen"));
                    it.setPrecioUnitario(rs.getInt("precio"));
                    it.setStock(rs.getInt("stock"));
                    it.setCantidad(rs.getInt("cantidad"));
                    lista.add(it);
                }
            }
        }
        return lista;
    }
 
    /** Suma el total de todas las líneas. */
    public static long total(List<ItemCarrito> items) {
        long t = 0;
        for (ItemCarrito it : items) {
            t += it.getTotal();
        }
        return t;
    }
 
    /** Agrega una unidad del juego al carrito. */
    public String agregar(int usuarioId, int juegoId) throws SQLException {
        try (Connection c = conectar()) {
            String titulo;
            int stock;
            boolean proximamente;
            try (PreparedStatement ps = c.prepareStatement(
                    "SELECT titulo, stock, proximamente FROM juego_catalogo WHERE id = ? AND activo = 1")) {
                ps.setInt(1, juegoId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return "Ese juego no existe.";
                    }
                    titulo = rs.getString(1);
                    stock = rs.getInt(2);
                    proximamente = rs.getBoolean(3);
                }
            }
            if (proximamente) {
                return titulo + " todavía no está disponible para comprar (próximamente).";
            }
            int actual = cantidadActual(c, usuarioId, juegoId);
            if (actual + 1 > stock) {
                return stock <= 0 ? titulo + " está agotado."
                        : "Solo hay " + stock + " unidades disponibles de " + titulo + ".";
            }
            try (PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO carrito_item (usuario_id, juego_id, cantidad) VALUES (?, ?, 1) "
                    + "ON DUPLICATE KEY UPDATE cantidad = cantidad + 1")) {
                ps.setInt(1, usuarioId);
                ps.setInt(2, juegoId);
                ps.executeUpdate();
            }
            return null;
        }
    }
 
    /** Cambia la cantidad (delta = +1 o -1). Si llega a 0 se quita el juego. */
    public String cambiar(int usuarioId, int juegoId, int delta) throws SQLException {
        try (Connection c = conectar()) {
            int actual = cantidadActual(c, usuarioId, juegoId);
            if (actual == 0) {
                return "Ese juego no está en tu carrito.";
            }
            int nueva = actual + delta;
            if (nueva <= 0) {
                quitarCon(c, usuarioId, juegoId);
                return null;
            }
            int stock = 0;
            try (PreparedStatement ps = c.prepareStatement("SELECT stock FROM juego_catalogo WHERE id = ?")) {
                ps.setInt(1, juegoId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        stock = rs.getInt(1);
                    }
                }
            }
            if (nueva > stock) {
                return "Solo hay " + stock + " unidades disponibles.";
            }
            try (PreparedStatement ps = c.prepareStatement(
                    "UPDATE carrito_item SET cantidad = ? WHERE usuario_id = ? AND juego_id = ?")) {
                ps.setInt(1, nueva);
                ps.setInt(2, usuarioId);
                ps.setInt(3, juegoId);
                ps.executeUpdate();
            }
            return null;
        }
    }
 
    public void quitar(int usuarioId, int juegoId) throws SQLException {
        try (Connection c = conectar()) {
            quitarCon(c, usuarioId, juegoId);
        }
    }
 
    private int cantidadActual(Connection c, int usuarioId, int juegoId) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(
                "SELECT cantidad FROM carrito_item WHERE usuario_id = ? AND juego_id = ?")) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, juegoId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }
 
    private void quitarCon(Connection c, int usuarioId, int juegoId) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(
                "DELETE FROM carrito_item WHERE usuario_id = ? AND juego_id = ?")) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, juegoId);
            ps.executeUpdate();
        }
    }
}
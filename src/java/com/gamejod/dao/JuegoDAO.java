/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamejod.dao;

import com.gamejod.config.ConexionDB;
import com.gamejod.modelo.Juegocatalogo;
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
public class JuegoDAO {
 
    private Connection conectar() throws SQLException {
        Connection c = ConexionDB.getConnection();
        if (c == null) {
            throw new SQLException("Sin conexión con la base de datos");
        }
        return c;
    }
 
    private Juegocatalogo mapear(ResultSet rs) throws SQLException {
        Juegocatalogo j = new Juegocatalogo();
        j.setId(rs.getInt("id"));
        j.setTitulo(rs.getString("titulo"));
        j.setCategoria(rs.getString("categoria"));
        j.setPlataforma(rs.getString("plataforma"));
        j.setPrecio(rs.getInt("precio"));
        j.setImagen(rs.getString("imagen"));
        j.setStock(rs.getInt("stock"));
        j.setProximamente(rs.getBoolean("proximamente"));
        return j;
    }
 
    /** Lista los juegos. Cualquier filtro vacío o null se ignora. */
    public List<Juegocatalogo> listar(String texto, String categoria, String plataforma) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT id, titulo, categoria, plataforma, precio, imagen, stock, proximamente "
                + "FROM juego_catalogo WHERE activo = 1");
        List<String> params = new ArrayList<>();
        if (texto != null && !texto.trim().isEmpty()) {
            sql.append(" AND titulo LIKE ?");
            params.add("%" + texto.trim() + "%");
        }
        if (categoria != null && !categoria.trim().isEmpty()) {
            sql.append(" AND categoria = ?");
            params.add(categoria.trim());
        }
        if (plataforma != null && !plataforma.trim().isEmpty()) {
            sql.append(" AND plataforma = ?");
            params.add(plataforma.trim());
        }
        sql.append(" ORDER BY titulo");
 
        List<Juegocatalogo> lista = new ArrayList<>();
        try (Connection c = conectar(); PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }
 
    /** Un solo juego con todos sus datos (para la pagina de detalle). null si no existe. */
    public Juegocatalogo obtener(int id) throws SQLException {
        String sql = "SELECT id, titulo, categoria, plataforma, precio, imagen, stock, proximamente, "
                + "banner, jugadores, generos, clasificacion, calificacion, num_calificaciones "
                + "FROM juego_catalogo WHERE id = ? AND activo = 1";
        try (Connection c = conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Juegocatalogo j = mapear(rs);
                j.setBanner(rs.getString("banner"));
                j.setJugadores(rs.getString("jugadores"));
                j.setGeneros(rs.getString("generos"));
                j.setClasificacion(rs.getString("clasificacion"));
                double cal = rs.getDouble("calificacion");
                j.setCalificacion(rs.wasNull() ? null : cal);
                j.setNumCalificaciones(rs.getInt("num_calificaciones"));
                return j;
            }
        }
    }
 
    /** Valores distintos de una columna (categoria o plataforma) para llenar los filtros. */
    public List<String> valoresDistintos(String columna) throws SQLException {
        if (!"categoria".equals(columna) && !"plataforma".equals(columna)) {
            throw new IllegalArgumentException("Columna no permitida");
        }
        List<String> valores = new ArrayList<>();
        String sql = "SELECT DISTINCT " + columna + " FROM juego_catalogo WHERE activo = 1 ORDER BY " + columna;
        try (Connection c = conectar(); PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                valores.add(rs.getString(1));
            }
        }
        return valores;
    }
}
 
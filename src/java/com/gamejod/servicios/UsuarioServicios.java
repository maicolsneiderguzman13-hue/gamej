/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamejod.servicios;

import com.gamejod.dao.UsuarioDAO;
import com.gamejod.dao.UsuarioDAO.Credencial;
import com.gamejod.modelo.UsuarioVO;
import com.gamejod.util.PasswUtil;
import java.sql.SQLException;

/**
 * Clase de servicio que contiene la logica de negocio
 * relacionada con el registro y la autenticacion de usuarios.
 */
public class UsuarioServicios {
 
    private static final int MAX_INTENTOS = 3;
    private static final int MINUTOS_BLOQUEO = 5;
 
    // Objeto DAO que permite hablar con la base de datos
    private final UsuarioDAO gestion_usuario = new UsuarioDAO();
 
    /**
     * Registra un nuevo usuario, validando primero que los datos
     * minimos sean correctos antes de guardarlo en la base de datos.
     */
    public boolean crear_usuario(UsuarioVO tmp) {
        if (tmp == null || tmp.getNombre() == null || tmp.getUsuario() == null || tmp.getPassword() == null) {
            return false;
        }
        // invalido si esta vacio O tiene menos de 3 caracteres
        if (tmp.getNombre().trim().length() < 3) {
            return false;
        }
        if (tmp.getUsuario().trim().length() < 3) {
            return false;
        }
        if (tmp.getPassword().length() < 6) {
            return false;
        }
        try {
            if (gestion_usuario.existe(tmp.getUsuario().trim())) {
                return false; // usuario repetido
            }
            gestion_usuario.crear(tmp.getNombre().trim(), tmp.getUsuario().trim(),
                    PasswUtil.hash(tmp.getPassword()));
            return true;
        } catch (SQLException ex) {
            System.out.println("Verificar la sentencia de crear usuario: " + ex.getMessage());
        }
        return false;
    }
 
    /**
     * recibe un usuario y una contrasena,
     * y devuelve un mensaje segun si la autenticacion fue exitosa o no.
     */
    public String autenticar(String usuario, String password) {
        if (usuario == null || password == null || usuario.trim().isEmpty() || password.isEmpty()) {
            return "Error en la autenticacion";
        }
        try {
            Credencial cr = gestion_usuario.buscar(usuario.trim());
            if (cr == null) {
                return "Error en la autenticacion";
            }
            if (cr.bloqueado) {
                return "Cuenta bloqueada temporalmente";
            }
            if (PasswUtil.verificar(password, cr.password)) {
                String nuevoHash = PasswUtil.estaCifrada(cr.password) ? null : PasswUtil.hash(password);
                gestion_usuario.registrarExito(cr.id, nuevoHash);
                return "Autenticacion satisfactoria";
            }
            boolean bloqueada = gestion_usuario.registrarFallo(cr.id, MAX_INTENTOS, MINUTOS_BLOQUEO);
            return bloqueada ? "Cuenta bloqueada temporalmente" : "Error en la autenticacion";
        } catch (SQLException ex) {
            System.out.println("Error de base de datos al autenticar: " + ex.getMessage());
            return "Error en la autenticacion";
        }
    }
}
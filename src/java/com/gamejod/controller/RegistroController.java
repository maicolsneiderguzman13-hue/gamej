/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.gamejod.controller;

import com.gamejod.dao.UsuarioDAO;
import com.gamejod.util.PasswUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 *
 * @author maico
 */
@WebServlet(name = "RegistroController", urlPatterns = {"/RegistroController"})
public class RegistroController extends HttpServlet {
 
    // Solo letras, números y . _ @ + - (sin espacios ni símbolos de código)
    private static final Pattern USUARIO = Pattern.compile("^[A-Za-z0-9._@+\\-]{3,50}$");
 
    private final UsuarioDAO dao = new UsuarioDAO();
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("registro.jsp");
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        // El diseño del registro tiene 3 campos: usuario, correo y contraseña.
        String usuario = limpiar(request.getParameter("usuario"));
        String correo = limpiar(request.getParameter("correo")).toLowerCase();
        String password = request.getParameter("password");
 
        String error = validar(usuario, correo, password);
 
        if (error == null) {
            try {
                if (dao.existe(usuario)) {
                    error = "Ese usuario ya está registrado.";
                } else if (dao.existeCorreo(correo)) {
                    error = "Ese correo ya está registrado.";
                } else {
                    // nombre = usuario (la tabla pide un nombre y el diseño no tiene ese campo)
                    dao.crearConCorreo(usuario, usuario, correo, PasswUtil.hash(password));
                    response.sendRedirect("login.jsp?registro=ok");
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                error = "No se pudo completar el registro. Intenta más tarde.";
            }
        }
 
        // Vuelve al formulario con el mensaje y lo que ya había escrito (sin la contraseña)
        request.setAttribute("error", error);
        request.setAttribute("usuario", usuario);
        request.setAttribute("correo", correo);
        request.getRequestDispatcher("/registro.jsp").forward(request, response);
    }
 
    private static String limpiar(String s) {
        return (s == null) ? "" : s.trim();
    }
 
    private static String validar(String usuario, String correo, String password) {
        if (usuario.isEmpty() || correo.isEmpty() || password == null || password.isEmpty()) {
            return "Todos los campos son obligatorios.";
        }
        if (!USUARIO.matcher(usuario).matches() || usuario.contains("@")) {
            return "El usuario debe tener entre 3 y 50 caracteres, sin espacios "
                    + "(solo letras, números y . _ + -).";
        }
        if (correo.length() > 100 || !correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return "El correo no tiene un formato válido.";
        }
        if (password.length() < 6) {
            return "La contraseña debe tener al menos 6 caracteres.";
        }
        return null;
    }
}
 
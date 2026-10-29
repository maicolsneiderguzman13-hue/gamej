/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.gamejod.controller;

import com.gamejod.dao.CarritoDAO;
import com.gamejod.modelo.ItemCarrito;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author maico
 */
@WebServlet(name = "CarritoController", urlPatterns = {"/CarritoController"})
public class CarritoController extends HttpServlet {
 
    private final CarritoDAO dao = new CarritoDAO();
 
    private String usuarioEnSesion(HttpServletRequest request) {
        HttpSession s = request.getSession(false);
        Object u = (s == null) ? null : s.getAttribute("usuario");
        return u == null ? null : u.toString();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = usuarioEnSesion(request);
        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        try {
            Integer id = dao.idUsuario(usuario);
            List<ItemCarrito> items = (id == null) ? new java.util.ArrayList<ItemCarrito>() : dao.items(id);
            request.setAttribute("items", items);
            request.setAttribute("total", CarritoDAO.total(items));
        } catch (SQLException e) {
            request.setAttribute("errorBD", "No se pudo cargar tu carrito. Intenta de nuevo en un momento.");
        }
        request.setAttribute("aviso", request.getParameter("aviso"));
        request.getRequestDispatcher("carrito.jsp").forward(request, response);
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String usuario = usuarioEnSesion(request);
        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String accion = request.getParameter("accion");
        int juegoId;
        try {
            juegoId = Integer.parseInt(request.getParameter("juegoId"));
        } catch (NumberFormatException e) {
            response.sendRedirect("CarritoController");
            return;
        }
        String aviso = null;
        try {
            Integer id = dao.idUsuario(usuario);
            if (id == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            if ("agregar".equals(accion)) {
                aviso = dao.agregar(id, juegoId);
            } else if ("mas".equals(accion)) {
                aviso = dao.cambiar(id, juegoId, 1);
            } else if ("menos".equals(accion)) {
                aviso = dao.cambiar(id, juegoId, -1);
            } else if ("quitar".equals(accion)) {
                dao.quitar(id, juegoId);
            }
        } catch (SQLException e) {
            aviso = "No se pudo actualizar el carrito. Intenta de nuevo.";
        }
        String destino = "CarritoController";
        if (aviso != null) {
            destino += "?aviso=" + java.net.URLEncoder.encode(aviso, "UTF-8");
        }
        response.sendRedirect(destino);
    }
}
 

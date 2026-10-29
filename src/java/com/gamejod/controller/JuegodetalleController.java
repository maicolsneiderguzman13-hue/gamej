/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.gamejod.controller;

import com.gamejod.dao.JuegoDAO;
import com.gamejod.modelo.Juegocatalogo;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 *
 * @author maico
 */
@WebServlet(name = "JuegodetalleController", urlPatterns = {"/JuegoDetalleController"})
public class JuegodetalleController extends HttpServlet {
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendRedirect("index.jsp");
            return;
        }
        try {
            Juegocatalogo juego = new JuegoDAO().obtener(id);
            if (juego == null) {
                response.sendRedirect("index.jsp");
                return;
            }
            request.setAttribute("juego", juego);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorBD", "No se pudo cargar el juego. Intenta de nuevo en un momento.");
        }
       
        String pagina = (id == 2) ? "JuegoRe4.jsp" : "JuegoRequiem.jsp";
        request.getRequestDispatcher(pagina).forward(request, response);
    }
}
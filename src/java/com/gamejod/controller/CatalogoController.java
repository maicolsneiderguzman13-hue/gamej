/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.gamejod.controller;

import com.gamejod.dao.JuegoDAO;
import com.gamejod.modelo.Juegocatalogo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author maico
 */
@WebServlet(name = "CatalogoController", urlPatterns = {"/CatalogoController"})
public class CatalogoController extends HttpServlet {
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String q = request.getParameter("q");
        String categoria = request.getParameter("categoria");
        String plataforma = request.getParameter("plataforma");
        JuegoDAO dao = new JuegoDAO();
        try {
            List<Juegocatalogo> juegos = dao.listar(q, categoria, plataforma);
            request.setAttribute("juegos", juegos);
            request.setAttribute("categorias", dao.valoresDistintos("categoria"));
            request.setAttribute("plataformas", dao.valoresDistintos("plataforma"));
        } catch (SQLException e) {
            request.setAttribute("errorBD", "No se pudo cargar el catálogo. Intenta de nuevo en un momento.");
        }
        request.setAttribute("q", q == null ? "" : q);
        request.setAttribute("categoriaSel", categoria == null ? "" : categoria);
        request.setAttribute("plataformaSel", plataforma == null ? "" : plataforma);
        request.getRequestDispatcher("catalogo.jsp").forward(request, response);
    }
}
 
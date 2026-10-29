package com.gamejod.controller;

import com.gamejod.dao.UsuarioDAO;
import com.gamejod.dao.UsuarioDAO.Credencial;
import com.gamejod.util.PasswUtil;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * @author maico
 */
@WebServlet(name = "AuthController", urlPatterns = {"/AuthController"})
public class AuthController extends HttpServlet {
 
    private static final int MAX_INTENTOS = 3;
    private static final int MINUTOS_BLOQUEO = 5;
 
    private final UsuarioDAO dao = new UsuarioDAO();
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");
        usuario = (usuario == null) ? "" : usuario.trim();
 
        if (usuario.isEmpty() || password == null || password.isEmpty()) {
            response.sendRedirect("login.jsp?error=1");
            return;
        }
 
        Credencial cr = null;
        try {
            cr = dao.buscar(usuario);
        } catch (SQLException ex) {
            System.getLogger(AuthController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        // Usuario que no existe: mismo mensaje que contraseña mala (no revela qué usuarios hay)
        if (cr == null) {
            response.sendRedirect("login.jsp?error=1");
            return;
        }
        // Cuenta bloqueada: se rechaza aunque la contraseña sea correcta
        if (cr.bloqueado) {
            response.sendRedirect("login.jsp?error=2");
            return;
        }
        if (PasswUtil.verificar(password, cr.password)) {
            // Si la contraseña guardada estaba en texto plano, se cifra ahora
            String nuevoHash = PasswUtil.estaCifrada(cr.password) ? null : PasswUtil.hash(password);
            try {
                dao.registrarExito(cr.id, nuevoHash);
            } catch (SQLException ex) {
                System.getLogger(AuthController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            
            HttpSession vieja = request.getSession(false);
            if (vieja != null) {
                vieja.invalidate();   // sesión nueva al entrar (evita fijación de sesión)
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("usuario", usuario);
            session.setAttribute("usuarioId", cr.id);
            session.setMaxInactiveInterval(30 * 60);
            response.sendRedirect("index.jsp");
        } else {
            boolean bloqueada = false;
            try {
                bloqueada = dao.registrarFallo(cr.id, MAX_INTENTOS, MINUTOS_BLOQUEO);
            } catch (SQLException ex) {
                System.getLogger(AuthController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            response.sendRedirect(bloqueada ? "login.jsp?error=2" : "login.jsp?error=1");
        }
    }
 
    @Override
    public String getServletInfo() {
        return "Inicio de sesión con bloqueo por intentos fallidos";
    }
}
<%-- 
    Document   : JuegoRe4
    Created on : 27/10/2026, 10:17:15 p. m.
    Author     : maico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.gamejod.modelo.Juegocatalogo, com.gamejod.util.Html, com.gamejod.util.Moneda"%>
<%
    Juegocatalogo j = (Juegocatalogo) request.getAttribute("juego");
    if (j == null) {
        // Si se abre la pagina directo (sin pasar por el servlet), carga el juego 2 (RE4)
        try {
            j = new com.gamejod.dao.JuegoDAO().obtener(2);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorBD", "No se pudo cargar el juego. Intenta de nuevo en un momento.");
        }
    }
    if (j == null && request.getAttribute("errorBD") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    // Datos de esta pagina (segun el diseno de Resident Evil 4: Remake)
    String portada = "img/Re4fondo.jpg";
    boolean tieneBanner = false;
    String generos = "Accion y aventura";
    String miles = "5 mil calificaciones";
    String nota = "5.0";
    int llenas = 5;
    String jugadores = "Una persona";
    String clasificacion = "Mayores de +18";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>GameJod - <%= j == null ? "Juego" : Html.esc(j.getTitulo()) %></title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="JuegoRe4.css">
</head>
<body>
<% if (request.getAttribute("errorBD") != null) { %>
    <div class="det-pagina"><p class="det-aviso"><%= Html.esc((String) request.getAttribute("errorBD")) %></p></div>
<% } else { %>
<div class="det-pagina">
    <div class="det-hero pano<%= tieneBanner ? " ancho" : "" %>" style="--foto:url('<%= Html.esc(portada) %>')">
        <div class="det-foto">
            <img src="<%= Html.esc(portada) %>" alt="<%= Html.esc(j.getTitulo()) %>">
        </div>
    </div>

    <section class="det-card">
        <h1><%= Html.esc(j.getTitulo()) %></h1>

        <div class="det-rating">
            <% if (true) { %>
                <span class="det-nota"><%= nota %></span>
                <span class="det-estrellas" aria-label="<%= nota %> de 5">
                    <% for (int i = 1; i <= 5; i++) { %><span class="<%= i <= llenas ? "" : "vacia" %>">&#9733;</span><% } %>
                </span>
                <span><%= miles %></span>
            <% } else { %>
                <span>Sin calificaciones todavía</span>
            <% } %>
        </div>

        <div class="det-compra">
            <form method="post" action="CarritoController">
                <input type="hidden" name="accion" value="agregar">
                <input type="hidden" name="juegoId" value="<%= j.getId() %>">
                <% if (j.isProximamente()) { %>
                    <button class="det-boton" type="button" disabled>Próximamente</button>
                <% } else if (j.getStock() <= 0) { %>
                    <button class="det-boton" type="button" disabled>Agotado</button>
                <% } else { %>
                    <button class="det-boton" id="btn-agregar" type="submit">Agregar a carrito</button>
                <% } %>
            </form>
            <span class="det-precio"><%= Moneda.cop(j.getPrecio()) %></span>
        </div>
    </section>

    <div class="det-info">
        <% if (true) { %>
        <div class="det-dato">
            <svg width="30" height="30" viewBox="0 0 24 24" aria-hidden="true"><circle cx="12" cy="12" r="11" fill="none" stroke="#000" stroke-width="2"/><circle cx="12" cy="9.5" r="3.6" fill="#000"/><path d="M5.5 19c1.3-3.2 3.6-4.6 6.5-4.6s5.2 1.4 6.5 4.6c-1.8 1.8-4 2.8-6.5 2.8s-4.7-1-6.5-2.8z" fill="#000"/></svg>
            <%= Html.esc(jugadores) %>
        </div>
        <% } %>
        <div class="det-dato">
            <svg width="40" height="34" viewBox="0 0 30 24" aria-hidden="true" fill="none" stroke="#000" stroke-width="1.4" stroke-linecap="round" stroke-linejoin="round"><path d="M1 8.5H8M0.5 12H6.5M2.5 15.5H8"/><g transform="translate(5.5 0)" stroke-width="1.8"><circle cx="15" cy="4.3" r="2" fill="#000" stroke="none"/><path d="M14.4 8L12.4 14M14.2 9.2L18 11.6M13.6 9.8L9.6 11.4L8.2 14M12.4 14L16 17.4L14.4 21M12.4 14L8.8 16.8L5.4 17.2"/></g></svg>
            <%= Html.esc(generos) %>
        </div>
        <% if (true) { %>
        <div class="det-dato">
            <svg width="30" height="30" viewBox="0 0 24 24" aria-hidden="true"><circle cx="12" cy="12" r="10.5" fill="#fff" stroke="#d32f2f" stroke-width="2"/><text x="12" y="16.5" text-anchor="middle" font-size="12" font-weight="700" font-family="Arial" fill="#000">18</text><line x1="4.5" y1="19.5" x2="19.5" y2="4.5" stroke="#d32f2f" stroke-width="2"/></svg>
            <%= Html.esc(clasificacion) %>
        </div>
        <% } %>
    </div>

    <div class="det-pie"></div>
</div>
<% } %>
</body>
</html>

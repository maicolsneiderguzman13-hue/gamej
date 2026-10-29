<%-- 
    Document   : JuegoRequiem
    Created on : 27/10/2026, 7:14:30 p. m.
    Author     : maico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.gamejod.modelo.Juegocatalogo, com.gamejod.util.Html, com.gamejod.util.Moneda"%>
<%
    Juegocatalogo j = (Juegocatalogo) request.getAttribute("juego");
    if (j == null && request.getAttribute("errorBD") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    String portada = "";
    boolean tieneBanner = false;
    String generos = "";
    String miles = "";
    int llenas = 0;
    if (j != null) {
        tieneBanner = (j.getBanner() != null && !j.getBanner().isEmpty());
        portada = tieneBanner ? j.getBanner() : j.getImagen();
        generos = (j.getGeneros() != null && !j.getGeneros().isEmpty()) ? j.getGeneros() : j.getCategoria();
        int n = j.getNumCalificaciones();
        miles = (n >= 1000) ? String.format(java.util.Locale.US, "%.1f mil calificaciones", n / 1000.0)
                            : n + " calificaciones";
        if (j.getCalificacion() != null) {
            llenas = (int) Math.round(j.getCalificacion());
        }
    }
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
    <link rel="stylesheet" href="JuegoRequiem.css">
</head>
<body>
<% if (request.getAttribute("errorBD") != null) { %>
    <div class="det-pagina"><p class="det-aviso"><%= Html.esc((String) request.getAttribute("errorBD")) %></p></div>
<% } else { %>
<div class="det-pagina">
    <div class="det-hero<%= tieneBanner ? " ancho" : "" %>" style="--foto:url('<%= Html.esc(portada) %>')">
        <div class="det-foto">
            <img src="<%= Html.esc(portada) %>" alt="<%= Html.esc(j.getTitulo()) %>">
        </div>
    </div>

    <section class="det-card">
        <h1><%= Html.esc(j.getTitulo()) %></h1>

        <div class="det-rating">
            <% if (j.getCalificacion() != null) { %>
                <span class="det-nota"><%= j.getCalificacion() %></span>
                <span class="det-estrellas" aria-label="<%= j.getCalificacion() %> de 5">
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
        <% if (j.getJugadores() != null && !j.getJugadores().isEmpty()) { %>
        <div class="det-dato">
            <svg width="30" height="30" viewBox="0 0 24 24" aria-hidden="true"><circle cx="12" cy="12" r="11" fill="none" stroke="#000" stroke-width="2"/><circle cx="12" cy="9.5" r="3.6" fill="#000"/><path d="M5.5 19c1.3-3.2 3.6-4.6 6.5-4.6s5.2 1.4 6.5 4.6c-1.8 1.8-4 2.8-6.5 2.8s-4.7-1-6.5-2.8z" fill="#000"/></svg>
            <%= Html.esc(j.getJugadores()) %>
        </div>
        <% } %>
        <div class="det-dato">
            <svg width="30" height="30" viewBox="0 0 24 24" aria-hidden="true"><path d="M12 2C7 2 3.5 5.4 3.5 10c0 2.6 1.2 4.4 3 5.6V19c0 .6.4 1 1 1h2v-2h1v2h2v-2h1v2h2c.6 0 1-.4 1-1v-3.4c1.8-1.2 3-3 3-5.6C20.5 5.4 17 2 12 2zM8.5 12.5a2 2 0 110-4 2 2 0 010 4zm7 0a2 2 0 110-4 2 2 0 010 4z" fill="#000"/></svg>
            <%= Html.esc(generos) %>
        </div>
        <% if (j.getClasificacion() != null && !j.getClasificacion().isEmpty()) { %>
        <div class="det-dato">
            <svg width="30" height="30" viewBox="0 0 24 24" aria-hidden="true"><circle cx="12" cy="12" r="10.5" fill="#fff" stroke="#d32f2f" stroke-width="2"/><text x="12" y="16.5" text-anchor="middle" font-size="12" font-weight="700" font-family="Arial" fill="#000">18</text><line x1="4.5" y1="19.5" x2="19.5" y2="4.5" stroke="#d32f2f" stroke-width="2"/></svg>
            <%= Html.esc(j.getClasificacion()) %>
        </div>
        <% } %>
    </div>

    <div class="det-pie"></div>
</div>
<% } %>
</body>
</html>

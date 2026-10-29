<%-- 
    Document   : Carrito
    Created on : 27/10/2026, 5:47:48 p. m.
    Author     : maico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, com.gamejod.modelo.ItemCarrito, com.gamejod.util.Html, com.gamejod.util.Moneda"%>
<%
    Object usuario = session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<ItemCarrito> items = (List<ItemCarrito>) request.getAttribute("items");
    if (items == null && request.getAttribute("errorBD") == null) {
        response.sendRedirect("CarritoController");
        return;
    }
    Long total = (Long) request.getAttribute("total");
    String aviso = (String) request.getAttribute("aviso");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>GameJod - Carrito</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Chewy&family=Open+Sans:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="carrito.css">
</head>
<body>
<main class="car-panel">
    <a class="car-volver" href="index.jsp">&larr; Seguir comprando</a>
    <h1 class="car-titulo">Game carrito</h1>

    <% if (aviso != null && !aviso.isEmpty()) { %>
        <p class="car-aviso" id="aviso"><%= Html.esc(aviso) %></p>
    <% } %>
    <% if (request.getAttribute("errorBD") != null) { %>
        <p class="car-aviso car-error"><%= Html.esc((String) request.getAttribute("errorBD")) %></p>
    <% } %>

    <% if (items != null && items.isEmpty()) { %>
        <div class="car-vacio" id="carrito-vacio">
            <p>Tu carrito está vacío</p>
            <a class="car-boton" href="index.jsp">Ver juegos</a>
        </div>
    <% } else if (items != null) { %>
        <div class="car-cabecera">
            <span>Producto</span>
            <span>Cantidad</span>
            <span>Total</span>
        </div>

        <% for (ItemCarrito it : items) { %>
        <div class="car-fila">
            <div class="car-producto">
                <form method="post" action="CarritoController">
                    <input type="hidden" name="accion" value="quitar">
                    <input type="hidden" name="juegoId" value="<%= it.getJuegoId() %>">
                    <button class="car-quitar" type="submit" aria-label="Quitar del carrito">&times;</button>
                </form>
                <img class="car-img" src="<%= Html.esc(it.getImagen()) %>" alt="<%= Html.esc(it.getTitulo()) %>">
                <span class="car-nombre"><%= Html.esc(it.getTitulo()) %></span>
            </div>
            <div class="car-cantidad">
                <form method="post" action="CarritoController">
                    <input type="hidden" name="accion" value="menos">
                    <input type="hidden" name="juegoId" value="<%= it.getJuegoId() %>">
                    <button type="submit" aria-label="Quitar una unidad">&minus;</button>
                </form>
                <span class="car-num"><%= it.getCantidad() %></span>
                <form method="post" action="CarritoController">
                    <input type="hidden" name="accion" value="mas">
                    <input type="hidden" name="juegoId" value="<%= it.getJuegoId() %>">
                    <button type="submit" aria-label="Agregar una unidad">+</button>
                </form>
            </div>
            <div class="car-precio"><%= Moneda.cop(it.getTotal()) %></div>
        </div>
        <% } %>

        <div class="car-pie">
            <span class="car-total">Total: <span id="total"><%= Moneda.cop(total == null ? 0 : total) %></span></span>
        </div>
    <% } %>
</main>
</body>
</html>



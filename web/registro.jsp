<%-- 
    Document   : registro
    Created on : 27/10/2026, 11:13:42 a. m.
    Author     : maico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.gamejod.util.Html"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>GameJod - Registro</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Chewy&family=Open+Sans:wght@400;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="registro.css">
    </head>
    <body>
        <header class="reg-header">
            <a href="index.jsp"><img src="img/Gamjod.jpeg" alt="GameJod"></a>
        </header>

        <main class="reg-contenedor">
            <div class="reg-caja">
                <h1>Registro de usuario</h1>

                <% if (request.getAttribute("error") != null) { %>
                <p class="reg-error"><%= Html.esc((String) request.getAttribute("error")) %></p>
                <% } %>

                <form action="RegistroController" method="post">
                    <label for="usuario">ingresa usuario:</label>
                    <input type="text" id="usuario" name="usuario" required maxlength="50"
                           value="<%= Html.esc((String) request.getAttribute("usuario")) %>">

                    <label for="correo">Ingrese correo electronico:</label>
                    <input type="email" id="correo" name="correo" required maxlength="100"
                           value="<%= Html.esc((String) request.getAttribute("correo")) %>">

                    <label for="password">Ingrese contraseña:</label>
                    <input type="password" id="password" name="password" required minlength="6">

                    <button type="submit" class="reg-boton">Registrar</button>
                </form>
                <p class="reg-enlace">¿Ya tienes cuenta? <a href="login.jsp">Inicia sesión</a></p>
            </div>
        </main>
    </body>
</html>

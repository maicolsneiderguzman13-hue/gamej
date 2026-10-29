<%-- 
    Document   : login
    Created on : 21/08/2026, 9:59:03 p. m.
    Author     : maico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GameJod- Inicio de sesion </title>
        <link rel="stylesheet" href="login.css">
    </head>
    <body>
        <header class="header">
            <div class="header-top">
                <div class="logo">GameJod</div>
            </div>
        </header>

        <main class="login-contenedor">
            <div class="login-caja">
                <h1>Inicio de sesion</h1>
                <% if ("1".equals(request.getParameter("error"))) { %>
                <p class="login-error">Usuario o contraseña incorrectos</p>
                <% } %>

                <form action="AuthController" method="post">
                    <label for="usuario">usuario o correo:</label>
                    <input type="text" id="usuario" name="usuario" required>

                    <label for="password">contraseña:</label>
                    <input type="password" id="password" name="password" required>

                    <button type="submit" class="btn-start">Entrar</button>
                </form>

                <p class="login-link">¿No tienes cuenta? <a href="registro.jsp">Registrate</a></p>
            </div>
        </main>
    </body>
</html>
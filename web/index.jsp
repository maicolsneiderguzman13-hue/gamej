<%-- 
    Document   : index
    Created on : 21/08/2026, 9:06:31 p. m.
    Author     : maico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Gamejod</title>
<link rel="stylesheet" href="index.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap" rel="stylesheet">
</head>
<body>
    <nav class="navbar-top">
        <div style="display: flex; align-items: center; flex-wrap: nowrap; gap: 20px; width: 100%;">
            <img src="img/Gamjod.jpeg" style="height: 200px; width: auto; flex-shrink: 0;">

        <div class="search-bar d-flex" style="flex: 1; min-width: 220px; margin-left: 20px;">
            <input type="text" placeholder="buscar...."/>
            <button><i class="bi bi-search"></i></button>
        </div>

         <div class="nav-icons">
            <% if (session.getAttribute("usuario") != null) { %>
                <span class="btn-start" style="cursor:default;">Hola, <%= session.getAttribute("usuario") %></span>
            <% } else { %>
                <button class="btn-start" onclick="window.location.href='login.jsp'">START</button>
            <% } %>
            <div class="nav-icons-bottom">
             <div class="text-center">
                <i class="bi bi-person nav-icon" style="display:block;"></i>
                   <span class="nav-icon-label">perfil</span>
                </div>
                <div class="text-center">
                      <i class="bi bi-star nav-icon" style="display:block;"></i>
                      <span class="nav-icon-label">favoritos</span>
                </div>
                <<a href="CarritoController" title="Carrito" style="text-decoration:none;"><i class="bi bi-cart3 nav-icon"></i></a>
                <i class="bi bi-gear nav-icon"></i>
            </div>
        </div>
    </nav>

    <nav class="nav-menu">
        <div class="container-fluid d-flex aling-items-center py-0" style="width: 100%;">
            <div class="dropdown flex-fill text-center">
                 <a class="nav-link dropdown-toggle w-100 py-2" href="#" data-bs-toggle="dropdown">categorias</a>
                 <ul class="dropdown-menu">
                    <li> <a class="dropdown-item" href="#">accion</a></li>
                    <li> <a class="dropdown-item" href="#">aventura</a></li>
                    <li> <a class="dropdown-item" href="#">RPG</a></li>
                    <li> <a class="dropdown-item" href="#">terror</a></li>
                    <li> <a class="dropdown-item" href="#">deportes</a></li>
                    <li> <a class="dropdown-item" href="#">indie</a></li>
                 </ul>
            </div>

            <a class="nav-link flex-fill text-center py-2" href="#">ofertas</a>
            <a class="nav-link flex-fill text-center py-2" href="#">vender</a>

            <div class="dropdown flex-fill text-center">
                <a class="nav-link dropdown-toggle w-100 py-2" href="#" data-bs-toggle="dropdown">comunidad</a>
                <ul class="dropdown-menu">
                    <li> <a class="dropdown-item" href="#">foros</a></li>
                    <li> <a class="dropdown-item" href="#">reseñas</a></li>
                    <li> <a class="dropdown-item" href="#">eventos</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <main class="conteiner-fluid px4 mt-3">
        <h2 class="selection-tittle">vistos recientemente</h2>
        <div class="game-row">
            <div class="row g-3 justify-content-center">
                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="game-card">
                        <img src="img/rerq.jpg" alt="Resident Evil: Requiem">
                        <p class="game-tittle">Resident Evil: requiem</p>
                    </div>
                </div>
                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="game-card">
                        <img src="img/re4.jpg" alt="Resident Evil 4: remake">
                        <p class="game-tittle">Resident Evil 4: remake</p>
                    </div>
                </div>
                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="game-card">
                        <img src="img/lego.jpg" alt="Lego: Batman Legacy of the Dark Night">
                        <p class="game-tittle">Lego: Batman Legacy of the Dark Night</p>
                    </div>
                </div>
                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="game-card">
                        <img src="img/sub.jpg" alt="Subnautica 2">
                        <p class="game-tittle">Subnautica 2</p>
                    </div>
                </div>
            </div>
        </div>
        <h2 class="selection-tittle">Basados en tus intereses</h2>
        <div class="game-row">
            <div class="row g-3 justify-content-center">
            <div class="col-6 col-sm-3 col-md-3 col-lg-2">
                     <div class="game-card">
                        <img src="img/re2.jpg" alt="Resident Evil 2: Remake ">
                        <p class="game-tittle">Resident Evil 2: Remake</p>
                    </div>
                </div>
                 <div class="col-6 col-sm-3 col-md-3 col-lg-2">
                     <div class="game-card">
                        <img src="img/f.jpg" alt="Silent Hill F">
                        <p class="game-tittle">Silent Hill F</p>
                    </div>
                </div>
                 <div class="col-6 col-sm-3 col-md-3 col-lg-2">
                     <div class="game-card">
                        <img src="img/gta.jpg" alt="Grand Theft Auto VI ">
                        <p class="game-tittle">Grand Theft Auto VI<span class="badge-soon">Proximamente</span></p>
                    </div>
                </div>
                   <div class="col-6 col-sm-3 col-md-3 col-lg-2">
                     <div class="game-card">
                        <img src="img/ps.jpg" alt="Marvel Spiderman ">
                        <p class="game-tittle">Marvel Spiderman</p>
                    </div>
                </div>
                </div>
            </div>
        </div>

        <h2 class="selection-tittle">Pura Accion</h2>
        <div class="game-row">
            <div class="row g-3 justify-content-center">
                <div class="col-6 col-sm-3 col-md-3 col-lg-2">
                     <div class="game-card">
                        <img src="img/gears.jpg" alt="Gears Of War 3">
                        <p class="game-tittle">Gears Of War 3</p>
                    </div>
                </div>
                <div class="col-6 col-sm-3 col-md-3 col-lg-2">
                     <div class="game-card">
                        <img src="img/doom.jpg" alt="Doom: The Dark Age">
                        <p class="game-tittle">Doom: The Dark Age</p>
                    </div>
                </div>
                 <div class="col-6 col-sm-3 col-md-3 col-lg-2">
                     <div class="game-card">
                        <img src="img/cuphead.jpg" alt="Cuphead">
                        <p class="game-tittle">Cuphead</p>
                    </div>
                </div>
                 <div class="col-6 col-sm-3 col-md-3 col-lg-2">
                     <div class="game-card">
                        <img src="img/gow.jpg" alt="God Of War ">
                        <p class="game-tittle">God Of War</p>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <footer>
        <div class="conteiner-fluid">
            <div class="row aling-items-start text-start gx-4">
                <div class="col-12 col-md-12">
                    <p class="mb-1 fw-bold" style="font-size: .9rem;">Contactanos:</p>
                    <p class="mb-0" style="font-size:.85rem;">(1011) 1098 7656 0814</p>
                    <p class="mb-0" style="font-size:.85rem;">GamejodCommunity@gmail.com</p>
                </div>
                <div class="col-12 col-md-4 text-center">
                    <p class="mas-info-toggle" data-bs-toggle="collapse" data-bs-target="#masInfo">Mas informacion <i class="bi bi-chevron-up"></i></p>
                </div>
                <div class="collapse-show" id="masInfo">
                    <a href="#" class="footer-link d-block mb-1">Terminos y condiciones</a>
                </div>
                <div class="footer-brand mb-3">
                    <p>Gamejod</p>
                    <p style="font-size: .7rem;">Juego seguro, compra facil</p>
                </div>
            </div>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
  var ids = {"rerq.jpg":1,"re4.jpg":2,"lego.jpg":3,"sub.jpg":4,"re2.jpg":5,"f.jpg":6,
             "gta.jpg":7,"ps.jpg":8,"gears.jpg":9,"doom.jpg":10,"cuphead.jpg":11,"gow.jpg":12};
  document.querySelectorAll('.game-card').forEach(function (c) {
    var f = c.querySelector('img').getAttribute('src').split('/').pop();
    if (ids[f]) {
      c.style.cursor = 'pointer';
      c.addEventListener('click', function () { location.href = 'JuegoDetalleController?id=' + ids[f]; });
    }
  });
</script>
</body>
</html>
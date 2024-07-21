<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page
	import="it.unisa.model.Utente, it.unisa.model.UtenteCompratore, it.unisa.model.UtenteVenditore,it.unisa.model.Carrello,it.unisa.helper.PasswordHasher"%>



<%

	
boolean login = false;

Utente utente = (Utente) session.getAttribute("utente");
Carrello carrello = (Carrello) session.getAttribute("carrello");

response.setHeader("Cache-Control", "no-cache, no-store"); // HTTP 1.1.

response.setDateHeader("Expires", 0);



if (session.getAttribute("utente")!=null) {
    login = true;
    

}else{
	login=false;
}


int totaleArticoli = 0;
if (carrello != null) {
    for (Integer quantita : carrello.getProdotti().values()) {
        totaleArticoli += quantita;
    }
}

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Everythings</title>
<link rel="stylesheet" href="css/header.css">
<script src="js/header.js"></script>
</head>

<body>

	<header class="header">
		<div class="navbar-container">
			<div class="logo">
				<a href="homepage.jsp"><img src="images/logo_header.png"
					alt="Logo"></a>
			</div>
			<div class="search-bar">
			<form id="formSearch" action="product" method="get">
			<input type="hidden" name="action" value="cercaProdotti">
				<input type="text" name="searchString" placeholder="Cerca..."> 
				 <input type="submit" value="Cerca">
			</form>
				<% if (login && session.getAttribute("utente")!=null) { %>
				<span id="benvenutoMessaggio">Ciao <%= utente.getNome() %></span> <a
					href="utente?action=logout" class="button" id="logoutButton"
					onclick="logout()">Logout</a>
				<% } %>
			</div>

			<div class="account-btn">
				<a
					href="<%= login ? (utente instanceof UtenteVenditore ? "AreaVenditore.jsp" : "AreaCompratore.jsp") : "Login.jsp" %>">
					<img src="images/account.png" alt="Immagine di un account"
					<%= !login ? "title='Accedi o registrati'" : "title='Utente: " + utente.getNome() + " " + utente.getCognome() + "'" %>>
				</a>
			</div>

			<div class="orders-btn">
				<a href="Transazioni.jsp"><img src="images/ordini.png" alt="Ordini"></a>
			</div>

			<div class="cart-btn">
                <a href="Carrello.jsp">
                    <img src="images/carrello.png" alt="Carrello">
                    <span id="cartCount"><%= totaleArticoli %></span>
                </a>
            </div>

		</div>
	</header>




</body>
</html>










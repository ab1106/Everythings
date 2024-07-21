<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="it.unisa.model.*" %>
<%@ include file="Header.jsp" %>

<%

	String disponibilita=(String) session.getAttribute("dispo");

	Carrello kart = null;

    Prodotto prodotto = (Prodotto) session.getAttribute("prodottoFromControl");
    
    if((Carrello) session.getAttribute("carrello")!=null){
    	kart = (Carrello) session.getAttribute("carrello");
    }
    
    if(kart!=null){
	    if(!(kart.getProdotti().containsKey(prodotto))){
	    	disponibilita=null; 
	    	session.setAttribute("dispo", null);
	    }
    }
    
    String base64img = null;

    if (prodotto != null && prodotto.getImmagine() != null) {
        byte[] imageB = prodotto.getImmagine();
        base64img = Base64.getEncoder().encodeToString(imageB);
    }

    UtenteModel model = new IDM_Utente();
    UtenteVenditore venditore = null;
    if (prodotto != null) {
        venditore = (UtenteVenditore) model.doLoad(prodotto.getEmail());
    }
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/Prodotto.css">
    <script src="js/Prodotto.js"></script>
</head>

<body>
<div class="page-wrapper">
    <div class="content-wrapper">
        <!-- Left column content -->
        <div class="left-column">
            <img src="data:image/jpg;base64, <%= base64img %>" width="300" height="300" />
        </div>
        <!-- Right column content -->
        <div class="right-column">
            <div class="right-column-content">
                <!-- Product information -->
                <div class="container-mt-5">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title"><%= prodotto != null ? prodotto.getNome() : "" %></h5>
                            <h6 class="card-subtitle mb-2 text-muted">
                                Venditore: <%= venditore != null ? venditore.getRagionesociale() : "" %>
                            </h6>
                            <p class="card-text"><%= prodotto != null ? prodotto.getDescrizione() : "" %></p>
                            <p class="card-text">
                                Prezzo: <%= prodotto != null ? prodotto.getPrezzo() : "" %>€
                            </p>
                            <p class="card-text">
                                Disponibilità: <%= prodotto != null ? prodotto.getDisponibilita() : "" %>
                            </p>
                            <% if (utente != null && !(utente instanceof UtenteVenditore)) { %>
                                <!-- Add to cart form -->
                                <form id="formCart" class="hidden" action="carrello" method="post">
                                    <input type="hidden" name="action" value="addCart">
                                    <input type="hidden" name="product" value="<%= prodotto != null ? prodotto.getId() : "" %>">
                                    <input type="submit" class="bottoni" value="Aggiungi al carrello">
                                </form>
                                <%if(disponibilita!=null) {%>
                                	Impossibile aggiungere altri prodotti di questo tipo al carrello, disponibilià inferiore!
                               	<%} %>
                            <% } %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <% if (utente != null && !(utente instanceof UtenteVenditore)) {
            int somma = 0;
            %>
            <!-- Cart section -->
            <div class="carrello">
                <h5 class="card-title">CARRELLO:</h5>
                <% if (carrello != null) {
                    for (Map.Entry<Prodotto, Integer> entry : carrello.getProdotti().entrySet()) {
                        somma += entry.getKey().getPrezzo() * entry.getValue();
                %>
                        <div class="prodotto">
                            <p class="nome"><%= entry.getKey().getNome() %></p>
                            <p class="quantita"><%= entry.getValue() %></p>
                            <p class="prezzo"><%= entry.getKey().getPrezzo() %>€</p>
                        </div>
                <% }
                } %>
                <div class="prodotto">
                    <h5 class="totale">TOTALE:</h5>
                    <h5 class="prezzoTOTALE"><%= somma %>€</h5>
                </div>
                <form id="formCheck" class="hidden" action="carrello" method="post">
                    <input type="hidden" name="action" value="toCheck">
                    <input type="hidden" name="total" value=<%= somma %>>
                    <input type="submit" id="go_checkout" class="bottoni" value="Vai al check-out">
                </form>
            </div>
        <% } %>
    </div>
</div>	

<%@ include file="Footer.jsp"%>
</body>
</html>


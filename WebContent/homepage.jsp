<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Homepage</title>
<link rel="stylesheet" type="text/css" href="css/homepage.css">
<script type="text/javascript" src="js/homepage.js"></script>
<%@ page
	import="java.util.*,it.unisa.model.Utente,it.unisa.model.IDM_Prodotto,it.unisa.model.Prodotto,java.util.Iterator,it.unisa.model.Carrello,java.util.*,java.net.URLEncoder"%>
</head>

<body>

<%@ include file="Header.jsp"%>


<% 
IDM_Prodotto idm_prodotto = new IDM_Prodotto();
Collection<Prodotto> products = idm_prodotto.doRetrieveAll();
request.setAttribute("products", products);

Iterator<?> it = products.iterator();
Iterator<?> it2 = products.iterator();
int count = 0;
%>

<div class="content">
    <div class="container">
        <div class="slider-container">
            <div id="slider" class="slider">
                <div class="slide-text right-text">PRODUCTS</div>
                <div class="slide">
                    <img src="images/airpodsMax.jpg" alt="Slide 1" width="300" height="300" />
                </div>
                <div class="slide-text left-text">BEST</div>
                <div class="slide">
                    <img src="images/dunkNere.jpg" alt="Slide 2" width="300" height="300" />
                </div>
                <div class="slide">
                    <img src="images/iphone14.jpg" alt="Slide 3" width="300" height="300" />
                </div>
            </div>
            <div class="slider-buttons">
                <button class="prev-button" onclick="previousSlide()">&lt;</button>
                <button class="next-button" onclick="nextSlide()">&gt;</button>
            </div>
        </div>
    </div>

    <div id="listaprod" class="container">
        <%
        int columnCount = 0; // Contatore per le colonne
        int nProducts=0;
        while (it.hasNext()) {
            nProducts++;
            if (columnCount % 3 == 0) {
                // Inizia una nuova colonna
        %>
        <div class="column">
            <table>
                <%
                } 
                %>
                <tr>
                    <%
                    for (int i = 0; i < 3 && it.hasNext(); i++) { // Itera su ogni riga della colonna fino a 3 prodotti o finché ci sono ancora prodotti 
                    Prodotto bean = (Prodotto) it.next();
                    String base64img = null;
                    if (bean.getImmagine() != null) {
                        byte[] imageB = bean.getImmagine();
                        base64img = Base64.getEncoder().encodeToString(imageB);
                    }
                    %>
                    <td>
                        <a href="product?action=toProductID&id_product=<%= bean.getId() %>">
                            <img src="data:image/jpg;base64,<%= base64img %>" width="300" height="300" />
                        </a>
                    </td>
                    <td>
                        <div class="container-mt-5">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title"><%= bean.getNome() %></h5>
                                    <p class="card-text"><%= bean.getDescrizione() %></p>
                                    <p class="card-text">Prezzo: <%= bean.getPrezzo() %> €</p>
                                    <p class="card-text">Disponibilità: <%= bean.getDisponibilita() %></p>
                                    <br>
                                </div>
                            </div>
                        </div>
                    </td>
                    <%
                    }
                    %>
                </tr>
                <%
                if (++columnCount % 3 == 0 || !it.hasNext()) { // Chiudi la colonna se ci sono 3 prodotti o non ci sono più prodotti
                %>
            </table>
        </div>
        <%
        }
        %>
        <%
        }
        %>
    </div>
</div>

<%@ include file="Footer.jsp"%>

</body>
</html>

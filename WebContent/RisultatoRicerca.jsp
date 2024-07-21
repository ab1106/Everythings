<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*,it.unisa.model.Utente,it.unisa.model.IDM_Prodotto,it.unisa.model.Prodotto,it.unisa.model.ProdottoModel"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/risultatoRicerca.css">

<%

Collection<Prodotto> products = new ArrayList<>();;
Object obj = session.getAttribute("productsSearched");
if (obj instanceof Collection<?>) {
    products = (Collection<Prodotto>) obj;
    
    // Ora puoi usare 'products' in modo sicuro
} else {
    // Gestisci il caso in cui l'oggetto non sia una Collection<Prodotto>
}
Iterator<?> it = products.iterator();

int count = 0;

%>
</head>
<%@include file="Header.jsp"%>
<body>
	<div class="page-wrapper">
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
								<% } %>
								<tr>
									<% for (int i = 0; i < 3 && it.hasNext(); i++) { // Itera su ogni riga della colonna fino a 5 prodotti o finché ci sono ancora prodotti %>
									<%
				                    Prodotto bean = (Prodotto) it.next();
				                    String base64img = null;
				                    if (bean.getImmagine() != null) {
				                        byte[] imageB = bean.getImmagine();
				                        base64img = Base64.getEncoder().encodeToString(imageB);
				                    }
				                %>
			
									<td><a
										href="product?action=toProductID&id_product=<%= bean.getId() %>">
											<img src="data:image/jpg;base64, <%= base64img %>" width="300"
											height="300" />
									</a></td>
									<td>
										<div class="container-mt-5">
											<div class="card">
												<div class="card-body">
													<h5 class="card-title"><%= bean.getNome() %></h5>
													<p class="card-text"><%= bean.getDescrizione() %></p>
													<p class="card-text">
														Prezzo:
														<%= bean.getPrezzo() %>
														€
													</p>
													<p class="card-text">
														Disponibilità:
														<%= bean.getDisponibilita() %></p>
													<br>
			
			
												</div>
			
											</div>
										</div>
									</td>
									<% } %>
								</tr>
								<% if (++columnCount % 3 == 0 || !it.hasNext()) { // Chiudi la colonna se ci sono 5 prodotti o non ci sono più prodotti %>
							</table>
						</div>
						<% } %>
						<% } %>
					</div>
	</div>
</body>
	<%@ include file="Footer.jsp"%>
</html>

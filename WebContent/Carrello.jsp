<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*,it.unisa.model.Utente,it.unisa.model.IDM_Prodotto,it.unisa.model.Prodotto,java.util.Iterator"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Carrello</title>
<link rel="stylesheet" type="text/css" href="css/carrello.css">
<script type="text/javascript" src="js/carrello.js">
</script>
</head>


<%@include file="Header.jsp"%>
<body>

<% 
String disponibilita=(String) session.getAttribute("dispo");
if(carrello!=null){%>
	<div class="page-wrapper">
		<div class="carrello">
			<h5 class="card-title">CARRELLO:</h5>
			<%
			    int somma=0;
			    for (Map.Entry<Prodotto, Integer> entry : carrello.getProdotti().entrySet()) {
			    	somma += entry.getKey().getPrezzo()*entry.getValue();
			    	String base64img = null;

			    	if (entry.getKey().getImmagine() != null) {
			    			byte[] imageB = entry.getKey().getImmagine();
			    			base64img = Base64.getEncoder().encodeToString(imageB);
			    	}
			    %>
			<div class="prodotto">
				<img src="data:image/jpg;base64, <%= base64img %>" width="50"
					height="50" />
				<p class="nome"><%= entry.getKey().getNome() %></p>
				<p class="quantita"><%= entry.getValue() %></p>
				<form id="formCheck" class="hidden" action="carrello" method="post">
					<input type="hidden" name="action" value="delete1"> <input
						type="hidden" name="idprod" value=<%= entry.getKey().getId() %>>
					<input type="submit" id="delete1" class="bottoni2" value="-">
				</form>
				<form id="formCheck" class="hidden" action="carrello" method="post">
					<input type="hidden" name="action" value="addCart1"> <input
						type="hidden" name="product" value=<%= entry.getKey().getId() %>>
					<input type="submit" id="add1" class="bottoni" value="+">
				</form>
				<p class="prezzo"><%= entry.getKey().getPrezzo() %>€
				</p>
				<form id="formCheck" class="hidden" action="carrello" method="post">
					<input type="hidden" name="action" value="delete"> <input
						type="hidden" name="idprod" value=<%= entry.getKey().getId() %>>
					<input type="submit" id="delete" class="bottoni2" value="X">
						
				</form>
				
				


			</div>
				
			<%
			    }
			    %>
			    
			    <%if(disponibilita!=null) {%>
                                	Impossibile aggiungere altri prodotti di questo tipo al carrello, disponibilià inferiore!
                   	<%} %>
			<div class="prodotto">
				<h5 class="totale">SOMMA:</h5>
				<h5 class="prezzoTOTALE"><%= somma %>€
				</h5>
			</div>
			<form id="acquisto" class="hidden" action="ordine" method="post">
				<input type="hidden" name="action" value="acquisto"> 
				<input type="hidden" name=totale value=<%= somma %>> 
				<input type="hidden" name=compratore value=<%= utente.getEmail() %>>
		<%  
				
		    if(carrello.getNumProdotti()<=0){
		    	%> 
			    	<input type="submit" class="bottoni" value="Compra" disabled> 
				<%}else{ %>
				  
				<input type="submit" id="compra" class="bottoni" value="Compra">
				<%} %>
			</form>
<%} %>

		</div>
	</div>


	<%@ include file="Footer.jsp"%>
</body>


</html>

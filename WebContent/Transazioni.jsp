<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*,it.unisa.model.Utente,it.unisa.model.IDM_Prodotto,it.unisa.model.Prodotto,java.util.Iterator,it.unisa.model.IDM_Ordine,it.unisa.model.Ordine,it.unisa.model.Utente,it.unisa.model.UtenteCompratore"%>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Transazioni</title>
<link rel="stylesheet" type="text/css" href="css/transazioni.css">
<script type="text/javascript" src="js/transazioni.js">
</script>

</head>


<%@include file="Header.jsp"%>

	<%
	Collection<Ordine> ordini = new ArrayList<>();
	Map<Prodotto, Integer> prodottiVenduti = new HashMap<>();
	Iterator<?> it=null;
	int count = 0;
	IDM_Ordine idm_ordine = new IDM_Ordine();
	UtenteCompratore utenteCompratore = new UtenteCompratore();
	UtenteVenditore utenteVenditore = new UtenteVenditore();
	if(utente instanceof UtenteCompratore){
		utenteCompratore = (UtenteCompratore) utente;
		session.setAttribute("utente", utenteCompratore);
		ordini = idm_ordine.doRetrieveByKey(utenteCompratore.getEmail());
		request.setAttribute("ordini", ordini);
		it = ordini.iterator();
	}else if(utente instanceof UtenteVenditore){
		utenteVenditore = (UtenteVenditore) utente;
		session.setAttribute("utente", utenteVenditore);
		prodottiVenduti = idm_ordine.getProdottiVenduti(utenteVenditore.getEmail());
		request.setAttribute("prodottiVenduti", prodottiVenduti);
		
	}





%>
<body>
<div class="content">
    <% if (utente instanceof UtenteCompratore) { %>
        <table>
            <thead>
                <tr>
                    <th>Data Ordine</th>
                    <th>Totale</th>
                    <th>Prodotti</th>
                </tr>
            </thead>
            <tbody>
                <% while (it.hasNext()) {
                    Ordine bean = (Ordine) it.next(); %>
                    <tr>
                        <td><%= bean.getDataOrdine() %></td>
                        <td><%= bean.getTotaleOrdine() %>€</td>
                        <td>
                            <%
                            // Ottieni il carrello dall'ordine
                            byte[] carrellox = bean.getCarrello();
                            // Deserializza il carrello
                            Carrello cart = bean.deserializeCarrello(carrellox);
                            // Ottieni i prodotti dal carrello
                            Map<Prodotto, Integer> prodotti = cart.getProdotti();
                            // Itera attraverso i prodotti e visualizzali
                            for (Map.Entry<Prodotto, Integer> entry : prodotti.entrySet()) {
                                Prodotto prodotto = entry.getKey();
                                int quantita = entry.getValue();
                                String base64img = null;

                            	if (prodotto.getImmagine() != null) {
                            			byte[] imageB = prodotto.getImmagine();
                            			base64img = Base64.getEncoder().encodeToString(imageB);
                            	}
                                %>
                                <img src="data:image/jpg;base64, <%= base64img %>" width="100"
					height="100" />
                                
                            <% } %>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% }else if(utente instanceof UtenteVenditore){ %>
    
    <%
			    
			    for (Map.Entry<Prodotto, Integer> entry : prodottiVenduti.entrySet()) {
			    	
			    	String base64img = null;

			    	if (entry.getKey().getImmagine() != null) {
			    			byte[] imageB = entry.getKey().getImmagine();
			    			base64img = Base64.getEncoder().encodeToString(imageB);
			    	}
			    %>
			<div class="prodotto">
				<img src="data:image/jpg;base64, <%= base64img %>" width="300"
					height="300" />
				<p class="nome"><%= entry.getKey().getNome() %></p>
				<p class="quantita"><%= entry.getValue() %></p>
				
				<p class="prezzo"><%= entry.getKey().getPrezzo() %>€
				</p>
				


			</div>
			<%
			    }
			    %>
    

    
    <%} %>
</div>
</body>


	<%@ include file="Footer.jsp"%>
	





</html>

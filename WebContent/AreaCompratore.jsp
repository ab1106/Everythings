<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*,it.unisa.model.Utente,it.unisa.model.IDM_Prodotto,it.unisa.model.Prodotto,java.util.Iterator,it.unisa.model.IDM_Ordine,it.unisa.model.OrdineModel,it.unisa.model.Ordine"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Area Compratore</title>
<link rel="stylesheet" type="text/css" href="css/areaCompratore.css">
<script type="text/javascript" src="js/areaCompratore.js">
</script>
</head>


<%@include file="Header.jsp"%>



<%
	
	boolean flag_email=false;
	
	UtenteCompratore utenteCompratore = (UtenteCompratore) utente;

	Object message = request.getAttribute("message");
	if (message != null && message.equals("email_presente")) {
		System.out.println(message);
		 flag_email=true;
	}
	
	session.setAttribute("utente", utenteCompratore);
	

	IDM_Ordine idm_ordine = new IDM_Ordine();
	Collection<Ordine> ordini = idm_ordine.doRetrieveByKey(utenteCompratore.getEmail());
	request.setAttribute("ordini", ordini);
	
	Iterator<?> it = ordini.iterator();
	Iterator<?> it2 = ordini.iterator();
	int count = 0;
	int count2=0;
%>

<body>
	<div class="page-wrapper">
		<div class="content-wrapper">
			<div class="left-column">
				<!-- Elenco cliccabile -->
				<ul>
					<li onclick="loadContent('Profilo')">Profilo</li>
					<li onclick="loadContent('Visualizza_ordini')">Visualizza
						Fatture</li>
				</ul>
			</div>
			<div class="right-column">
				<!-- Contenuto della parte destra -->
				<div class="right-column-content">
					<form id="form1" class="hidden" action="utente" method="post">
						<input type="hidden" name="action" value="change"> <input
							type="hidden" name="firstEmail"
							value="<%= utenteCompratore.getEmail()%>"> <label
							for="nome">NOME:</label> <input type="text" class="testo"
							maxlength="49" id="nome" name="nome"
							value="<%= utenteCompratore.getNome()%>"
							placeholder="<%= utenteCompratore.getNome()%>" autofocus required>

						<label for="cognome">COGNOME:</label> <input type="text"
							class="testo" maxlength="49" id="cognome" name="cognome"
							value="<%= utenteCompratore.getCognome()%>"
							placeholder="<%= utenteCompratore.getCognome()%>" required>

						<label for="email">EMAIL:</label> <input type="email"
							class="testo" maxlength="99" id="email" name="email"
							value="<%= utenteCompratore.getEmail()%>"
							placeholder="<%= utenteCompratore.getEmail()%>" required>

						<label for="piva">CAP:</label> <input type="text" class="testo"
							maxlength="49" id="cap" name="cap"
							value="<%= utenteCompratore.getCap()%>"
							placeholder="<%= utenteCompratore.getCap()%>" required
							pattern="\d{5}"
							title="Inserisci un CAP italiano valido (5 cifre)"> <label
							for="telefono">INDIRIZZO:</label> <input type="text"
							class="testo" maxlength="49" id="indirizzo" name="indirizzo"
							value="<%= utenteCompratore.getIndirizzo()%>"
							placeholder="<%= utenteCompratore.getIndirizzo()%>"
							pattern="^(Via|Viale|Piazza)\s[A-Za-z\s]+$"
							title="Inserisci un indirizzo italiano valido" required>

						<label for="ragionesoc">CIVICO:</label> <input type="text"
							class="testo" maxlength="49" id="civico" name="civico"
							value="<%= utenteCompratore.getCivico()%>"
							placeholder="<%= utenteCompratore.getCivico()%>"
							pattern="^\d+[A-Za-z]?$"
							title="Inserisci un numero civico italiano valido" required>

						<label for="ragionesoc">CELLULARE:</label> <input type="text"
							class="testo" maxlength="49" id="cellulare" name="cellulare"
							value="<%= utenteCompratore.getCellulare()%>"
							placeholder="<%= utenteCompratore.getCellulare()%>"
							pattern="\d{10}"
							title="Inserisci un numero di telefono italiano valido" required>

						<br> <br>

						<% 	if(message=="email_presente"){ %>

						<div id="error_login">Email già presente, imposta una nuova
							email</div>

						<% } %>

						<br> <br> <input type="button" id="modifica_but"
							class="bottoni" onclick="enableEditMode('form1','salva_but')"
							value="Modifica"> <input type="button" id="salva_but"
							class="bottoni" onclick="submitForm('form1')" value="Salva"
							disabled>
					</form>
					<div id="listaordini" class="hidden">
						<table>
							<tr>
								<%
									while (it.hasNext()) {
										if (count % 4 == 0) {
								%>
							</tr>
							<tr>
								<%
									}
									Ordine bean = (Ordine) it.next();
							
								%>
							
							<tr>


								<td>DATA: <%= bean.getDataOrdine() %>

								</td>

								<td>TOTALE: <%= bean.getTotaleOrdine() %>€

								</td>
								<td><a
									href="ordine?action=visualizza&id_ordine=<%= bean.getIdOrdine() %>"
									target="_blank">Visualizza fattura</a></td>
								<td><a
									href="ordine?action=scarica&id_ordine=<%= bean.getIdOrdine() %>">Scarica
										fattura</a></td>
							</tr>
							<%
									}
									count++;
								

								// Chiudi l'ultima riga della tabella se ci sono ancora prodotti
								if (count % 4 != 0) {
								%>

							<%
								}
								%>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="Footer.jsp"%>
</body>

<%

	if(flag_email){
		
		%><script> loadContent('Profilo');</script>
<% 
	}

%>
</html>

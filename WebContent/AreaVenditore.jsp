<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*,it.unisa.model.Utente,it.unisa.model.IDM_Prodotto,it.unisa.model.Prodotto,java.util.Iterator"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/areaVenditore.css">
<script type="text/javascript" src="js/areaVenditore.js"></script>
</head>

<%@ include file="Header.jsp"%>

<%
	boolean flag_email=false;
	
	UtenteVenditore utenteVenditore = (UtenteVenditore) utente;

	Object message = request.getAttribute("message");
	if (message != null && message.equals("email_presente")) {
		System.out.println(message);
		 flag_email=true;
	}

	IDM_Prodotto idm_prodotto = new IDM_Prodotto();
	Collection<Prodotto> products = idm_prodotto.doRetrieveByKey(utenteVenditore.getEmail());
	request.setAttribute("products", products);
	
	Iterator<?> it = products.iterator();
	Iterator<?> it2 = products.iterator();
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
					<li onclick="loadContent('Aggiungi prodotto')">Aggiungi
						prodotto</li>
					<li onclick="loadContent('Modifica prodotto')">Modifica
						prodotto</li>
					<li onclick="loadContent('Elimina prodotto')">Elimina prodotto</li>
				</ul>
			</div>
			<div class="right-column">
				<!-- Contenuto della parte destra -->
				<div class="right-column-content">
					<form id="form1" class="hidden" action="utente" method="post">
						<input type="hidden" name="action" value="change"> <input
							type="hidden" name="firstEmail"
							value="<%= utenteVenditore.getEmail()%>"> <label
							for="nome">NOME:</label> <input type="text" class="testo"
							maxlength="49" id="nome" name="nome"
							value="<%= utenteVenditore.getNome()%>"
							placeholder="<%= utente.getNome()%>" autofocus required>

						<label for="cognome">COGNOME:</label> <input type="text"
							class="testo" maxlength="49" id="cognome" name="cognome"
							value="<%= utenteVenditore.getCognome()%>"
							placeholder="<%= utente.getCognome()%>" required> <label
							for="email">EMAIL:</label> <input type="email" class="testo"
							maxlength="99" id="email" name="email"
							value="<%= utenteVenditore.getEmail()%>"
							placeholder="<%= utente.getEmail()%>" required> <label
							for="piva">PARTITA IVA:</label> <input type="text" class="testo"
							maxlength="49" id="piva" name="piva"
							value="<%= utenteVenditore.getPartitaiva()%>"
							placeholder="<%= utenteVenditore.getPartitaiva()%>"
							pattern="^\d{11}$"
							title="Inserisci una partita IVA italiana valida (11 cifre)"
							required> <label for="telefono">TELEFONO:</label> <input
							type="text" class="testo" maxlength="49" id="telefono"
							name="telefono" value="<%= utenteVenditore.getTelefono()%>"
							placeholder="<%= utenteVenditore.getTelefono()%>"
							pattern="\d{10}"
							title="Inserisci un numero di telefono italiano valido" required>

						<label for="ragionesoc">RAGIONE SOCIALE:</label> <input
							type="text" class="testo" maxlength="49" id="ragionesoc"
							name="ragione" value="<%= utenteVenditore.getRagionesociale()%>"
							placeholder="<%= utenteVenditore.getRagionesociale()%>" required>

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

					<form id="form2" onsubmit="return validateForm()" class="hidden" action="product" method="post"
						enctype="multipart/form-data">
						<input type="hidden" name="action" value="addProduct"> <input
							type="hidden" name="email1"
							value="<%= utenteVenditore.getEmail()%>"> <label
							for="nome">NOME:</label> <input type="text" class="testo"
							maxlength="49" id="nomeprodotto" name="nomeprodotto" required>

						<label for="descrizione">DESCRIZIONE:</label> <input type="text"
							class="testo" maxlength="99" id="descrizione" name="descrizione"
							required> <label for="prezzo">PREZZO:</label> <input
							type="number" class="testo" maxlength="5" id="prezzo"
							name="prezzo"  required> 
							<label for="disponibilita">DISPONIBILITA:</label>
						<input type="number" class="testo" maxlength="49"
							id="disponibilita" name="disponibilita" min="1"
							pattern="[1-9]\d*" title="Il valore deve essere maggiore di zero"
							required> <label for="immagine">IMMAGINE:</label> <input
							type="file" id="immagine" name="immagine" accept="image/*"
							required> <input id="add_prod" class="bottoni"
							type="submit" value="Invia">
					</form>

					<div id="listaprod" class="hidden">
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
									Prodotto bean = (Prodotto) it.next();
									String base64img = null;
									if (bean.getImmagine() != null) {
										byte[] imageB = bean.getImmagine();
										base64img = Base64.getEncoder().encodeToString(imageB);
								%>
							
							<tr>
								<td><a
									href="product?action=toProductID&id_product=<%= bean.getId() %>">
										<img src="data:image/jpg;base64, <%= base64img %>" width="300"
										height="300" />
								</a></td>


								<td>
									<form id="form3_<%= bean.getId() %>" action="product"
										method="post" enctype="multipart/form-data" onsubmit="return validateForm2('mod_prezzo_<%= bean.getId() %>')">
										<input type="hidden" name="action" value="change">
										
										<td><label for="id">ID:</label><input type="text"
											class="testo" maxlength="49" id="idProd_<%= bean.getId() %>"
											name="idProd" value="<%= bean.getId()%>" readonly></td>
										<td><label for="nome">NOME:</label><input type="text"
											pattern="^[a-zA-Z ]{1,100}$"
											title="Permette solo caratteri alfabetici e spazi, con una lunghezza massima di 100 caratteri."
											class="testo" maxlength="49" value="<%= bean.getNome()%>"
											id="mod_nomeprodotto_<%= bean.getId() %>"
											name="mod_nomeprodotto" readonly></td>
										<td><label for="descrizione">DESCRIZIONE:</label><input
											type="text" class="testo" maxlength="99"
											id="mod_descrizione_<%= bean.getId() %>"
											name="mod_descrizione" value="<%= bean.getDescrizione()%>"
											readonly></td>
										<td><label for="prezzo">PREZZO:</label><input
											type="number" class="testo" 
											maxlength="5" id="mod_prezzo_<%= bean.getId() %>"
											name="mod_prezzo" value="<%= bean.getPrezzo()%>" readonly>
										</td>
										<td><label for="disponibilita">DISPONIBILITA:</label><input
											type="number" class="testo" maxlength="49"
											id="mod_disponibilita_<%= bean.getId() %>" min="1"
											pattern="[1-9]\d*"
											title="Il valore deve essere maggiore di zero"
											name="mod_disponibilita"
											value="<%= bean.getDisponibilita()%>" readonly></td>
										<td><label for="immagine">IMMAGINE:</label> <input
											type="file" id="mod_immagine_<%= bean.getId() %>"
											name="mod_immagine" accept="image/*" readonly></td>
										<td><input type="button"
											id="modifica_but_prod_<%= bean.getId() %>" class="bottoni"
											onclick="enableEditMode('<%= bean.getId() %>','salva_but_prod_<%= bean.getId() %>')"
											value="Modifica"></td>
										<td><input type="submit"
											id="salva_but_prod_<%= bean.getId() %>" class="bottoni"
											value="Salva" disabled></td>

									</form>

								</td>
							</tr>
							<%
									}
									count++;
								}

								// Chiudi l'ultima riga della tabella se ci sono ancora prodotti
								if (count % 4 != 0) {
								%>

							<%
								}
								%>
						</table>
					</div>

				</div>


				<div id="listaprod_delete" class=hidden>

					<form id="form5" action="product" method="post">
						<input type="hidden" name="action" value="deleteMORE"> <input
							type="hidden" name="idArray" id="idArray" value="" /> <label
							for="deleteGroup">SELEZIONA ALMENO 2 PER ELIMINARLI
							CONTEMPORANEAMENTE:</label><input type="button" id="deleteALL"
							name="deleteALL" class="bottoni" onclick="inviaCheckbox()"
							value="Elimina selezionati" disabled>
					</form>
					<table>
						<tr>
							<%
									while (it2.hasNext()) {
										if (count2 % 4 == 0) {
								%>
						</tr>
						<tr>
							<%
									}
									Prodotto bean = (Prodotto) it2.next();
									String base64img = null;
									if (bean.getImmagine() != null) {
										byte[] imageB = bean.getImmagine();
										base64img = Base64.getEncoder().encodeToString(imageB);
								%>
						
						<tr>




							<td><img src="data:image/jpg;base64, <%= base64img %>"
								width="300" height="300" /></td>
							<td><label for="id">ID:</label><input type="text"
								class="testo" maxlength="49" id="idProd_<%= bean.getId() %>"
								name="idProd" value="<%= bean.getId()%>" readonly></td>
							<td><label for="nome">NOME:</label><input type="text"
								pattern="^[a-zA-Z ]{1,100}$"
								title="Permette solo caratteri alfabetici e spazi, con una lunghezza massima di 100 caratteri."
								class="testo" maxlength="49" value="<%= bean.getNome()%>"
								id="mod_nomeprodotto_<%= bean.getId() %>"
								name="mod_nomeprodotto" readonly></td>
							<td><label for="descrizione">DESCRIZIONE:</label><input
								type="text" class="testo" maxlength="99"
								id="mod_descrizione_<%= bean.getId() %>" name="mod_descrizione"
								value="<%= bean.getDescrizione()%>" readonly></td>
							<td><label for="prezzo">PREZZO:</label><input type="number"
								class="testo" pattern="^\d{1,8}(\.\d{1,2})?$"
								title="Consente un massimo di 8 cifre totali, di cui fino a 2 decimali. Ad esempio, 12345.67 è valido."
								maxlength="49" id="mod_prezzo_<%= bean.getId() %>"
								name="mod_prezzo" value="<%= bean.getPrezzo()%>" readonly>
							</td>
							<td><label for="disponibilita">DISPONIBILITA:</label><input
								type="number" class="testo" maxlength="49"
								id="mod_disponibilita_<%= bean.getId() %>" min="1"
								pattern="[1-9]\d*"
								title="Il valore deve essere maggiore di zero"
								name="mod_disponibilita" value="<%= bean.getDisponibilita()%>"
								readonly></td>


							<form id="form4_<%= bean.getId() %>" action="product"
								method="post">
								<input type="hidden" name="action" value="delete1">

								<td><input type="checkbox"
									id="checkbox_<%= bean.getId() %>" name="checkbox"
									value="<%= bean.getId() %>"
									onchange="controllaCheckbox(<%= bean.getId() %>)"></td>
								<td><input type="submit" class="bottoni"
									id="delete_<%= bean.getId() %>" name="<%= bean.getId() %>"
									value="Elimina" disabled></td>


							</form>



						</tr>
						<%
									}
									count2++;
								}

								// Chiudi l'ultima riga della tabella se ci sono ancora prodotti
								if (count2 % 4 != 0) {
								%>

						<%
								}
								%>
					</table>


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

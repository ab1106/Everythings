<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*,it.unisa.model.Utente"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Registrazione</title>
<link rel="stylesheet" href="css/registrazione.css">

<script src="js/registrazione.js"></script>


</head>

<body onload="resetForm()">


	<div class="container">

		<div class="logo">
			<a href="homepage.jsp"><img src="images/logo_vuoto.png"
				alt="Logo"></a>
		</div>




		<div class="forms-container">


			<div class="f0rm">
				<form id="form1" action="utente" method="post">



					<br> <br> <input type="hidden" name="action"
						value="register"> <input type="text" class="testo"
						maxlength="49" id="nome" name="nome" placeholder="Nome:" autofocus
						required> <br> <br> <input type="text"
						class="testo" maxlength="49" id="cognome" name="cognome"
						placeholder="Cognome:" required> <br> <br> <input
						type="email" class="testo" maxlength="99" id="email" name="email"
						placeholder="Email:" required> <br> <br> <input
						type="password" class="testo" id="password" name="password"
						placeholder="Password:"
						pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,12}"
						title="Deve contenere almeno 1 numero, 1 lettera maiuscola,
	                1 minuscola e DEVE essere dai 8 ai 12 caratteri"
						required> <input type="button" id="pw_but" value="Mostra"
						onclick="pw_button()"> <br> <br> <select
						name="tipo_utente" id="tipo_utente" size="1"
						onchange="sceltaUtente()" required>
						<option value="" selected disabled hidden=>Seleziona il
							tipo di utente</option>
						<option value="compratore">compratore</option>
						<option value="venditore">venditore</option>
					</select> <br> <br>

					<div id="error-message"></div>

					<br> <br>
				</form>
				
				<input
					type="button" class="log_but" value="Login"
					onclick="toPage('login')">
					
					
				<input type="button" id="submit_button" class="reg_but"
					value="Iscriviti" onclick="submitForms()"> 
					

			</div>


			<form id="form2" class="hidden" action="utente" method="post">
				<!--             	<input type="hidden" name="action" value="register">  -->
				<input type="text" class="testo" maxlength="49" id="cap" name="cap"
					placeholder="CAP Spedizione:" required pattern="\d{5}"
					title="Inserisci un CAP italiano valido (5 cifre)"> <input
					type="text" class="testo" maxlength="49" id="indirizzo"
					name="indirizzo" placeholder="Indirizzo:"
					pattern="^(Via|Viale|Piazza)\s[A-Za-z\s]+$"
					title="Inserisci un indirizzo italiano valido" required> <input
					type="text" class="testo" maxlength="49" id="civico" name="civico"
					placeholder="Civico:" pattern="^\d+[A-Za-z]?$"
					title="Inserisci un numero civico italiano valido" required>
				<input type="text" class="testo" maxlength="49" id="cellulare"
					name="cellulare" placeholder="Numero di cellulare:"
					pattern="\d{10}"
					title="Inserisci un numero di telefono italiano valido" required>
			</form>

			<form id="form3" class="hidden" action="utente" method="post">
				<!--             	<input type="hidden" name="action" value="register">  -->
				<input type="text" class="testo" maxlength="49" id="piva"
					name="piva" placeholder="Partita Iva:" pattern="^\d{11}$"
					title="Inserisci una partita IVA italiana valida (11 cifre)"
					required> <input type="text" class="testo" maxlength="49"
					id="telefono" name="telefono" placeholder="Numero di telefono:"
					pattern="\d{10}"
					title="Inserisci un numero di telefono italiano valido" required>
				<input type="text" class="testo" maxlength="49" id="ragionesoc"
					name="ragione" placeholder="Ragione Sociale:" required>
			</form>


		</div>
	</div>



</body>
</html>


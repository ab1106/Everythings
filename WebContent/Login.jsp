<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Object message ;
	message=request.getAttribute("message");

%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*,it.unisa.model.Utente"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Login</title>
<link rel="stylesheet" href="css/login.css">
<script src="js/login.js"></script>

</head>

<body>
	<div class="container">

		<div class="logo">
			<a href="homepage.jsp"><img src="images/logo_vuoto.png"
				alt="Logo"></a>
		</div>

		<div class="forms-container">
			<div class="f0rm">
				<form action="utente" method="post">
					<input type="hidden" name="action" value="do_login"> <input
						type="email" maxlength="99" id="email" name="email"
						placeholder="Email:" required> <br> <br> <input
						type="password" id="password" name="password"
						placeholder="Password:"
						pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,12}"
						title="Deve contenere almeno 1 numero, 1 lettera maiuscola,
							 1 minuscola e DEVE essere dai 8 ai 12 caratteri"
						required> <br> <input type="button" id="pw_but"
						value="Mostra" onclick="pw_button()"> <br> <br>
					
					
					<input
						type="button" class="reg_but" value="Registrati"
						onclick="toPage('registrazione')"> 
						
						
					<input type="submit" class="log_but" value="Login"> <br> <br>
					

					<% 	if(message=="email_presente"){ %>

					<div id="error_login">Email già presente, effettua il login
						o riprova la registrazione</div>

					<% }else if(message=="login_fail"){ %>
					<div id="error_login">Email o password errate</div>
					<%  } %>




				</form>
			</div>
		</div>
	</div>





</body>







</html>
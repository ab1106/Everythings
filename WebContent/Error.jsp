<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*,it.unisa.model.Utente"%>

<head>
<title>Errore</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/Error.css">
</head>

<body>
<div class="forms-container">
	<div class="f0rm">
	    <div class="error-message">
	        <h1>Ops, si è verificato un errore</h1>
	        <form action="homepage.jsp" method="post">
	            <input type="submit" value="Homepage">
	        </form>
	    </div>
    </div>
</div>
</body>
</html>

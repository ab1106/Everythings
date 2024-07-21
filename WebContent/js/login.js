function pw_button() {
	var pw = document.getElementById("password");
	var but = document.getElementById("pw_but");
	if (pw.type === "password") {
		pw.type = "text";
		but.value = "Nascondi";
	} else {
		pw.type = "password";
		but.value = "Mostra";
	}
}

function toPage(x) {
	if (x === 'login') {
		window.location.href = "Login.jsp";
	} else if (x === 'registrazione') {
		window.location.href = "Registrazione.jsp";
	}

}
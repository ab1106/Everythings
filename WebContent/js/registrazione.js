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

function sceltaUtente() {
	var selectElement = document.getElementById("tipo_utente");
	var form2 = document.getElementById("form2");
	var form3 = document.getElementById("form3");

	var selectedOption = selectElement.options[selectElement.selectedIndex];
	var selectedValue = selectedOption.value;

	if (selectedValue === "compratore") {
		if (form3.classList.contains("f0rm")) {
			form3.reset();
			form3.classList.remove("f0rm");
			form3.classList.add("hidden");
		}
		if (form2.classList.contains("hidden")) {
			form2.classList.remove("hidden");
			form2.classList.add("f0rm");
		}
	} else if (selectedValue === "venditore") {
		if (form2.classList.contains("f0rm")) {
			form2.reset();
			form2.classList.remove("f0rm");
			form2.classList.add("hidden");
		}
		if (form3.classList.contains("hidden")) {
			form3.classList.remove("hidden");
			form3.classList.add("f0rm");
		}
	}
}

function setErrorMessage(errorMessage) {
	var message = document.getElementById("error-message");
	message.innerHTML = errorMessage;
}


function submitForms() {
	var emailInput = document.getElementById("email");
	var form1 = document.getElementById("form1");
	var selectElement = document.getElementById("tipo_utente");
	var selectedOption = selectElement.options[selectElement.selectedIndex];
	var selectedValue = selectedOption.value;

	if (selectedValue === "compratore") {
		var form2 = document.getElementById("form2");
	} else if (selectedValue === "venditore") {
		var form2 = document.getElementById("form3");
	}

	var form1Valid = form1.reportValidity();
	var form2Valid = form2.reportValidity();

	if (form1Valid && form2Valid) {
		unisciForm(form1, form2);

	} else {
		setErrorMessage("Compila tutti i campi!");
	}
}

function unisciForm(form1, form2) {
	var newForm = document.createElement("form");
	newForm.method = "POST";
	newForm.action = "utente";

	for (var i = 0; i < form1.elements.length; i++) {
		var element = form1.elements[i];
		if (element.name) {
			var newElement = document.createElement("input");
			newElement.type = element.type;
			newElement.name = element.name;
			newElement.value = element.value;
			newForm.appendChild(newElement);
		}
	}

	// Aggiungi i campi del secondo form al nuovo form
	for (var i = 0; i < form2.elements.length; i++) {
		var element = form2.elements[i];
		if (element.name) {
			var newElement = document.createElement("input");
			newElement.type = element.type;
			newElement.name = element.name;
			newElement.value = element.value;
			newForm.appendChild(newElement);

		}
	}

	document.body.appendChild(newForm);
	newForm.submit();
}

function resetForm() {
	var form1 = document.getElementById("form1");
	var form2 = document.getElementById("form2");
	var form3 = document.getElementById("form3");

	form1.reset();
	form2.reset();
	form3.reset();
}

function toPage(x) {
	if (x === 'login') {
		window.location.href = "Login.jsp";
	} else if (x === 'registrazione') {
		window.location.href = "Registrazione.jsp";
	}

}

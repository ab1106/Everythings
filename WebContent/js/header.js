

function deleteCookies() {
    var cookies = document.cookie.split(";");

    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        var eqPos = cookie.indexOf("=");
        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/";
    }
}

function logout() {

        var benvenutoMessaggio = document.getElementById('benvenutoMessaggio');
    	console.log('logout');
        benvenutoMessaggio.textContent = '';
        deleteCookies();
    	// Reindirizza l'utente a una nuova pagina
    	window.location.href = 'homepage.jsp';
        
}
// script.js

function loadContent(contentType) {
    var rightColumnContent = document.querySelector('.right-column-content');

    // Contenuto per il profilo
    if (contentType === 'Profilo') {
    	nascondiAddProdotto();
    	nascondiModificaProdotto()
        addProfilo();
        nascondiEliminaProdotto();
    }else if(contentType ==='Aggiungi prodotto'){
    	nascondiProfilo();
    	nascondiModificaProdotto();
    	addProdotto();
    	nascondiEliminaProdotto();
    }else if(contentType==='Modifica prodotto'){
    	nascondiProfilo();
    	nascondiAddProdotto();
    	modificaProdotto();
    	nascondiEliminaProdotto();
    }else if(contentType==='Elimina prodotto'){
    	nascondiProfilo();
    	nascondiAddProdotto();
    	nascondiModificaProdotto();
    	eliminaProdotto();
    }
    

}

function nascondiEliminaProdotto(){
	var listaprod_delete=document.getElementById('listaprod_delete');
	var form4=document.getElementById('form4');
	
	
	
	
	listaprod_delete.classList.add("hidden");

}



function eliminaProdotto(){
	
	var listaprod_delete=document.getElementById('listaprod_delete');
	
	listaprod_delete.classList.remove("hidden");
}



function nascondiModificaProdotto(){
	var listaProdotti=document.getElementById('listaprod');
	var form3=document.getElementById('form3');
	var salva_but_mod=document.getElementById('salva_but_mod');
	
	
	
	
	listaProdotti.classList.add("hidden");
}

function modificaProdotto(){
	
	
	var listaProdotti=document.getElementById('listaprod');
	
	
	
	listaProdotti.classList.remove("hidden");
	
	
	
}




function addProdotto(){
	
	
	
	var form2=document.getElementById('form2');
	var but=document.getElementById('add_prod');
	
	
	form2.classList.remove("hidden");
	but.classList.add("add_prod");


}

function addProfilo(){
	var form=document.getElementById('form1');
	var salva_but=document.getElementById('salva_but');

	form.classList.remove("hidden");
	
	
    impostaReadOnly('form1', true);
}


function enableEditMode(object,button) {
    
    document.getElementById(button).disabled = false;
    
    if(object==='form1'){
    	impostaReadOnly('form1', false);
    }else{
	    var nome=document.getElementById('mod_nomeprodotto_'+object);
	    var descrizione=document.getElementById('mod_descrizione_'+object);
	    var prezzo=document.getElementById('mod_prezzo_'+object);
	    var disponibilita=document.getElementById('mod_disponibilita_'+object);
	    var immagine=document.getElementById('mod_immagine_'+object);
	    
	    nome.removeAttribute("readonly");
	    descrizione.removeAttribute("readonly");
	    prezzo.removeAttribute("readonly");
	    disponibilita.removeAttribute("readonly");
	    immagine.removeAttribute("readonly");
    
    }
    
}



function submitForm(id) {
    var form = document.getElementById(id);

    

    // Controlla la validità del form
    console.log('Form valido:', form.checkValidity());

    if (form.checkValidity()) {
        // Se il form è valido, invia il form
        
        
        form.submit();
        
    	
    } else {
        // Se il form non è valido, mostra eventuali messaggi di errore
        form.reportValidity();
    }
}


function impostaReadOnly(formId,stato) {
    var form = document.getElementById(formId);
    var campiInput = form.getElementsByTagName("input");
    for (var i = 0; i < campiInput.length; i++) {
        campiInput[i].readOnly = stato;
    }
}


function nascondiProfilo(){
	
	var form=document.getElementById('form1');
	var salva_but=document.getElementById('salva_but');
	
	form.classList.add("hidden");
	salva_but.disabled=true;
	
	
	if(form){
		form.reset();
	}	
		
	impostaReadOnly('form1', true);
	
	

}

function nascondiAddProdotto(){

	var form2=document.getElementById('form2');
	
	if(form2){
		form2.reset();
	}
	form2.classList.add("hidden");
	

}

function inviaCheckbox() {
    // Recupera i valori dei checkbox selezionati
    var checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    var valoriCheckbox = Array.from(checkboxes).map(checkbox => checkbox.value).join(',');

    // Assegna i valori al campo nascosto del form1
    document.getElementById('idArray').value = valoriCheckbox;

    // Invia il form1
    document.getElementById('form5').submit();
}

function controllaCheckbox(id) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    var submitButton = document.getElementById('deleteALL');
    
    var checkbox = document.getElementById('checkbox_'+id);
    var deleteprod = document.getElementById('delete_'+id);
    
    if(checkbox.checked){
    	deleteprod.disabled=false;
    }else{
    	deleteprod.disabled=true;
    }
    
    
    

    if (checkboxes.length >= 2) {
        submitButton.disabled = false;
    } else {
        submitButton.disabled = true;
    }
}




    function validateForm() {
        // Ottieni il valore dell'input
        var prezzo = parseInt(document.getElementById('prezzo').value);

        // Verifica se il prezzo è negativo o 0
        if (prezzo <= 0 || isNaN(prezzo)) {
            // Mostra un messaggio di errore o esegui altre azioni di validazione necessarie
            alert("Il prezzo deve essere maggiore di 0");
            // Impedisci il submit del form restituendo false
            return false;
        }
        // Se il prezzo è valido, il submit del form verrà eseguito
        return true;
    }
    
    function validateForm2(id) {
        // Ottieni il valore dell'input
        var prezzo = parseInt(document.getElementById(id).value);

        // Verifica se il prezzo è negativo o 0
        if (prezzo <= 0 || isNaN(prezzo)) {
            // Mostra un messaggio di errore o esegui altre azioni di validazione necessarie
            alert("Il prezzo deve essere maggiore di 0");
            // Impedisci il submit del form restituendo false
            return false;
        }
        // Se il prezzo è valido, il submit del form verrà eseguito
        return true;
    }


function loadContent(contentType) {
    var rightColumnContent = document.querySelector('.right-column-content');

    // Contenuto per il profilo
    if (contentType === 'Profilo') {
        addProfilo();
        nascondivisualizzaOrdini();
    }else if(contentType ==='Visualizza_ordini'){
    	nascondiProfilo();
    	visualizzaOrdini();
    }
    

}


function visualizzaOrdini(){
	
	
	
	var form2=document.getElementById('listaordini');

	
	
	form2.classList.remove("hidden");



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

function nascondivisualizzaOrdini(){

	var form2=document.getElementById('listaordini');
	
	
	form2.classList.add("hidden");
	

}

function impostaReadOnly(formId,stato) {
    var form = document.getElementById(formId);
    var campiInput = form.getElementsByTagName("input");
    for (var i = 0; i < campiInput.length; i++) {
        campiInput[i].readOnly = stato;
    }
}


function enableEditMode(object,button) {
    
    document.getElementById(button).disabled = false;
    
    if(object==='form1'){
    	impostaReadOnly('form1', false);
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


function addProfilo(){
	var form=document.getElementById('form1');
	var salva_but=document.getElementById('salva_but');

	form.classList.remove("hidden");
	
	
    impostaReadOnly('form1', true);
}

package it.unisa.model;

import java.sql.SQLException;

public interface CarrelloModel {

	int getNumProdotti();
	
	void aggiungiProdotto(Prodotto prodotto);
	
	void rimuoviProdotto(int idProdotto);
	
	void rimuovi1Prodotto(int idProdotto);
	
	public boolean checkUpdateProduct(Prodotto prodotto,int quantita) throws SQLException ;
}



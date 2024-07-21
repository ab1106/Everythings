package it.unisa.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

public interface OrdineModel {

	public void importa(Ordine ordine) throws SQLException;
	
	public Collection<Ordine> doRetrieveAll() throws SQLException;
	
	public Ordine doRetrieveById(int id, String compratore) throws SQLException;
	
	public Collection<Ordine> doRetrieveByKey(String email) throws SQLException ;
	
	public boolean updateProducts(Carrello cart) throws SQLException ;
	
	public Map<Prodotto, Integer> getProdottiVenduti(String venditore) throws SQLException, ClassNotFoundException, IOException; 
}


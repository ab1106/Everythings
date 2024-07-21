
package it.unisa.model;
import java.sql.SQLException;
import java.util.Collection;

public interface ProdottoModel {

    void addProdotto(Prodotto prodotto) throws SQLException;
    
    Collection<Prodotto> doRetrieveAll() throws SQLException;
    
    Collection<Prodotto> doRetrieveByKey(String email) throws SQLException;

	void changeProdotto(Prodotto prodotto, int id);

	void deleteProdotto(int id);
	
	void deleteProdotti(int[] ids);
	
	Prodotto doRetrieveById(int id) throws SQLException;
	
	Prodotto doRetrieveByName(String name) throws SQLException;

	Collection<Prodotto> searchProducts(String search)throws SQLException;
}



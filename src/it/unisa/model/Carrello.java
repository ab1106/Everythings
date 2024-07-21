package it.unisa.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import it.unisa.model.Prodotto;
import it.unisa.helper.PasswordHasher;

public class Carrello implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idCarrello;
	private String idUtente;
	private Map<Prodotto, Integer> prodotti = new HashMap<>();

	public Carrello() {
		this.idCarrello = null;
		this.idUtente = null;
		this.prodotti = new HashMap<>();
	}

	public Carrello(String idUtente) {
		this.idCarrello = PasswordHasher.randGenerator();
		this.idUtente = idUtente;
		this.prodotti = new HashMap<>();

	}

	public String getIdCarrello() {
		return idCarrello;
	}

	public void setIdCarrello(String idCarrello) {
		this.idCarrello = idCarrello;
	}

	public String getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}

	public Map<Prodotto, Integer> getProdotti() {
		return prodotti;
	}

	public void setProdotti(Map<Prodotto, Integer> prodotti) {
		this.prodotti = prodotti;
	}

	public int getNumProdotti() {
		return prodotti.size();
	}

	public void aggiungiProdotto(Prodotto prodotto) {

		if (prodotti.containsKey(prodotto)) {

			int valoreAttuale = prodotti.get(prodotto);
			prodotti.put(prodotto, valoreAttuale + 1);
		} else {

			prodotti.put(prodotto, 1);
		}
	}

	public void rimuoviProdotto(int idProdotto) {

		Iterator<Map.Entry<Prodotto, Integer>> iterator = prodotti.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<Prodotto, Integer> entry = iterator.next();
			Prodotto prodotto = entry.getKey();

			if (prodotto.getId() == idProdotto) {
				iterator.remove();

				return;
			}
		}
	}

	public void resetCart() {
	    Iterator<Map.Entry<Prodotto, Integer>> iterator = prodotti.entrySet().iterator();

	    while (iterator.hasNext()) {
	        iterator.next(); 
	        iterator.remove(); 
	    }
	}

	public void rimuovi1Prodotto(int idProdotto) {

		Iterator<Map.Entry<Prodotto, Integer>> iterator = prodotti.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<Prodotto, Integer> entry = iterator.next();
			Prodotto prodotto = entry.getKey();
			Integer quantita = entry.getValue();

			if (prodotto.getId() == idProdotto) {

				quantita--;

				if (quantita <= 0) {
					iterator.remove();
				} else {
					prodotti.put(prodotto, quantita);
				}

				return;
			}
		}
	}
	
	public synchronized boolean checkUpdateProducts(Carrello cart) throws SQLException {

		boolean flag=false;
		
		Iterator<Map.Entry<Prodotto, Integer>> iterator = cart.getProdotti().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Prodotto, Integer> entry = iterator.next();
			Prodotto prodotto = entry.getKey();
			
			if(entry.getValue()<=prodotto.getDisponibilita()) {
				System.out.println("DENTROIF "+entry.getValue());
				System.out.println("DENTROIF "+prodotto.getDisponibilita());
				flag=true;
			}

		}
		return flag;
	}
	
	public synchronized boolean checkUpdateProduct(Prodotto prodotto,int quantita) throws SQLException {

		boolean flag=false;
		if(quantita<=prodotto.getDisponibilita()) {
			
			flag=true;
		}

		return flag;
	}

	@Override
	public String toString() {
		return "Carrello [idCarrello=" + idCarrello + ", idUtente=" + idUtente + ", prodotti=" + prodotti + "]";
	}

}
 

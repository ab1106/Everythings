package it.unisa.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class IDM_Ordine implements OrdineModel {

	private static final String TABLE_NAME1 = "ordini";

	public synchronized void importa(Ordine ordine) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO " + IDM_Ordine.TABLE_NAME1
					+ " (COMPRATORE, DATA_ORDINE, TOTALE_ORDINE, CARRELLO) VALUES (?, ?, ?, ?)");

			preparedStatement.setString(1, ordine.getCompratore());
			preparedStatement.setString(2, ordine.getDataOrdine());
			preparedStatement.setInt(3, ordine.getTotaleOrdine());
			preparedStatement.setBytes(4, ordine.getCarrello());
			preparedStatement.executeUpdate();

			connection.commit();

		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento dell'utente: " + e.getMessage());
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
	}

	public synchronized Collection<Ordine> doRetrieveAll() throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Ordine> ordini = new LinkedList<Ordine>();

		String selectSQL = "SELECT * FROM " + IDM_Ordine.TABLE_NAME1;

		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Ordine bean = new Ordine();

				bean.fillOrdine(bean, rs.getInt("id_ordine"), rs.getString("compratore"), rs.getString("data_ordine"),
						rs.getInt("totale_ordine"), rs.getBlob("carrello"));

				ordini.add(bean);
			}
			connection.commit();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return ordini;

	}

	public synchronized Ordine doRetrieveById(int id, String compratore) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Ordine bean = new Ordine();

		String selectSQL = "SELECT * FROM " + IDM_Ordine.TABLE_NAME1 + " WHERE id_ordine = ? AND compratore = ?";

		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, compratore);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				bean.fillOrdine(bean, rs.getInt("id_ordine"), rs.getString("compratore"), rs.getString("data_ordine"),
						rs.getInt("totale_ordine"), rs.getBlob("carrello"));
			}
			connection.commit();

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}

		return bean;
	}

	public synchronized Collection<Ordine> doRetrieveByKey(String email) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Ordine> ordini = new LinkedList<Ordine>();

		String selectSQL = "SELECT * FROM " + IDM_Ordine.TABLE_NAME1 + " WHERE COMPRATORE = ?";

		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Ordine bean = new Ordine();

				bean.fillOrdine(bean, rs.getInt("id_ordine"), rs.getString("compratore"), rs.getString("data_ordine"),
						rs.getInt("totale_ordine"), rs.getBlob("carrello"));

				ordini.add(bean);
			}
			connection.commit();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return ordini;

	}

	public synchronized boolean updateProducts(Carrello cart) throws SQLException {

		boolean flag=false;
		
		IDM_Prodotto op = new IDM_Prodotto();
		Iterator<Map.Entry<Prodotto, Integer>> iterator = cart.getProdotti().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Prodotto, Integer> entry = iterator.next();
			Prodotto prodotto = entry.getKey();
			if(entry.getValue()<=prodotto.getDisponibilita()) {
				prodotto.setDisponibilita(prodotto.getDisponibilita() - entry.getValue());
				flag=true;
				op.changeProdotto(prodotto, prodotto.getId());
			}
			

		}
		return flag;
	}
	
	
	
	public synchronized Map<Prodotto, Integer> getProdottiVenduti(String venditore) throws SQLException, ClassNotFoundException, IOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Map<Prodotto, Integer> products = new HashMap<>();
		
		Carrello cart = new Carrello();
		
		Ordine ordine = new Ordine();

		String selectSQL = "SELECT CARRELLO FROM "+ IDM_Ordine.TABLE_NAME1;
		
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				byte[] carrelloByte = rs.getBlob("carrello").getBytes(1, (int) rs.getBlob("carrello").length());
				cart =  ordine.deserializeCarrello( carrelloByte);

				 for (Map.Entry<Prodotto, Integer> entry : cart.getProdotti().entrySet()) {
			            Prodotto prodotto = entry.getKey();
			            Integer quantita = entry.getValue();

			            if(prodotto.getEmail().equalsIgnoreCase(venditore)) {
			            	products.put(prodotto, quantita);
			            }
			            
			        }
				
				
			}
			connection.commit();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}

		return products;
	}

}

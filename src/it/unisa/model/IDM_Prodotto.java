package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class IDM_Prodotto implements ProdottoModel {

	private static final String TABLE_NAME1 = "prodotti";

	@Override
	public synchronized void addProdotto(Prodotto prodotto) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		connection = ConnectionPool.getConnection();
		preparedStatement = connection.prepareStatement("INSERT INTO " + IDM_Prodotto.TABLE_NAME1
				+ " (EMAIL, NOME, DESCRIZIONE, PREZZO, DISPONIBILITA, IMMAGINE) VALUES (?, ?, ?, ?, ?, ?)");

		preparedStatement.setString(1, prodotto.getEmail());
		preparedStatement.setString(2, prodotto.getNome());
		preparedStatement.setString(3, prodotto.getDescrizione());
		preparedStatement.setDouble(4, prodotto.getPrezzo());
		preparedStatement.setInt(5, prodotto.getDisponibilita());
		preparedStatement.setBytes(6, prodotto.getImmagine());
		preparedStatement.executeUpdate();

		connection.commit();
	}

	public synchronized Collection<Prodotto> doRetrieveAll() throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Prodotto> products = new LinkedList<Prodotto>();

		String selectSQL = "SELECT * FROM " + IDM_Prodotto.TABLE_NAME1;

		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Prodotto bean = new Prodotto();

				bean.fillProduct(bean, rs.getInt("id_prodotto"), rs.getString("email"), rs.getString("nome"),
						rs.getString("descrizione"), rs.getDouble("prezzo"), rs.getInt("disponibilita"),
						rs.getBlob("immagine"));

				products.add(bean);
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

	public synchronized Collection<Prodotto> doRetrieveByKey(String email) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Prodotto> products = new LinkedList<Prodotto>();

		String selectSQL = "SELECT * FROM " + IDM_Prodotto.TABLE_NAME1 + " WHERE EMAIL = ?";

		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Prodotto bean = new Prodotto();

				bean.fillProduct(bean, rs.getInt("id_prodotto"), rs.getString("email"), rs.getString("nome"),
						rs.getString("descrizione"), rs.getDouble("prezzo"), rs.getInt("disponibilita"),
						rs.getBlob("immagine"));

				products.add(bean);
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

	@Override
	public synchronized void changeProdotto(Prodotto prodotto, int id) {
		Connection connection = null;

		try {
			connection = ConnectionPool.getConnection();

			updateNome(prodotto.getNome(), id, connection);
			updateDescrizione(prodotto.getDescrizione(), id, connection);
			updatePrezzo(prodotto.getPrezzo(), id, connection);
			updateDisponibilita(prodotto.getDisponibilita(), id, connection);
			updateImmagine(prodotto.getImmagine(), id, connection);

			connection.commit();

		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiornamento del prodotto: " + e.getMessage());
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
	}

	private synchronized void updateNome(String nome, int id, Connection connection) throws SQLException {
		if (nome != null) {
			try (PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE " + IDM_Prodotto.TABLE_NAME1 + " SET NOME=? WHERE ID_PRODOTTO = ?")) {
				preparedStatement.setString(1, nome);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
			}
		}
	}

	private synchronized void updateDescrizione(String descrizione, int id, Connection connection) throws SQLException {
		if (descrizione != null) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE " + IDM_Prodotto.TABLE_NAME1 + " SET DESCRIZIONE=? WHERE ID_PRODOTTO = ?")) {
				preparedStatement.setString(1, descrizione);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();

			}
		}
	}

	private synchronized void updatePrezzo(Double prezzo, int id, Connection connection) throws SQLException {
		if (prezzo != null) {
			try (PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE " + IDM_Prodotto.TABLE_NAME1 + " SET PREZZO=? WHERE ID_PRODOTTO = ?")) {
				preparedStatement.setDouble(1, prezzo);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
			}
		}
	}

	private synchronized void updateDisponibilita(int disponibilita, int id, Connection connection) throws SQLException {
		if (disponibilita > 0) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE " + IDM_Prodotto.TABLE_NAME1 + " SET DISPONIBILITA=? WHERE ID_PRODOTTO = ?")) {
				preparedStatement.setInt(1, disponibilita);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
			}
		} else {
			this.deleteProdotto(id);
		}
	}

	private synchronized void updateImmagine(byte[] immagine, int id, Connection connection) throws SQLException {
		if (immagine.length > 0) {

			try (PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE " + IDM_Prodotto.TABLE_NAME1 + " SET IMMAGINE=? WHERE ID_PRODOTTO = ?")) {
				preparedStatement.setBytes(1, immagine);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
			}
		}
	}

	@Override
	public synchronized void deleteProdotto(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String query = "DELETE FROM " + IDM_Prodotto.TABLE_NAME1 + " WHERE id_prodotto = ?";

		try {
			connection = ConnectionPool.getConnection();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

			connection.commit();

		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiornamento del prodotto: " + e.getMessage());
		} finally {
			ConnectionPool.releaseConnection(connection);
		}

	}

	public synchronized void deleteProdotti(int[] ids) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String query = "DELETE FROM " + IDM_Prodotto.TABLE_NAME1 + " WHERE id_prodotto = ?";

		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(query);

			for (int i = 0; i < ids.length; i++) {
				preparedStatement.setInt(1, ids[i]);
				preparedStatement.executeUpdate();
			}

			connection.commit();

		} catch (SQLException e) {
			System.out.println("Errore durante la cancellazione dei prodotti: " + e.getMessage());
		} finally {

			ConnectionPool.releaseConnection(connection);
		}
	}

	public synchronized Prodotto doRetrieveById(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Prodotto bean = new Prodotto();

		String selectSQL = "SELECT * FROM " + IDM_Prodotto.TABLE_NAME1 + " WHERE id_prodotto = ?";

		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				bean.fillProduct(bean, rs.getInt("id_prodotto"), rs.getString("email"), rs.getString("nome"),
						rs.getString("descrizione"), rs.getDouble("prezzo"), rs.getInt("disponibilita"),
						rs.getBlob("immagine"));
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

	public synchronized Prodotto doRetrieveByName(String name) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Prodotto bean = new Prodotto();

		String selectSQL = "SELECT * FROM " + IDM_Prodotto.TABLE_NAME1 + " WHERE nome = ?";

		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, name);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				bean.fillProduct(bean, rs.getInt("id_prodotto"), rs.getString("email"), rs.getString("nome"),
						rs.getString("descrizione"), rs.getDouble("prezzo"), rs.getInt("disponibilita"),
						rs.getBlob("immagine"));
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

	

	public synchronized Collection<Prodotto> searchProducts(String searchTerm) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Prodotto> products = new LinkedList<Prodotto>();

		String selectSQL = "SELECT * FROM " + IDM_Prodotto.TABLE_NAME1 + " WHERE NOME LIKE ? OR DESCRIZIONE LIKE ?";
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, "%" + searchTerm + "%");
	        preparedStatement.setString(2, "%" + searchTerm + "%");

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Prodotto bean = new Prodotto();
				

				bean.fillProduct(bean, rs.getInt("id_prodotto"), rs.getString("email"), rs.getString("nome"),
						rs.getString("descrizione"), rs.getDouble("prezzo"), rs.getInt("disponibilita"),
						rs.getBlob("immagine"));

				products.add(bean);
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




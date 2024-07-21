package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unisa.helper.PasswordHasher;

public class IDM_Utente implements UtenteModel {

	private static final String TABLE_NAME1 = "utenti";
	private static final String TABLE_NAME2 = "compratori";
	private static final String TABLE_NAME3 = "venditori";

	public synchronized void doSave(Utente utente, String password_bean, String tipo_utente) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;

		String rand = PasswordHasher.randGenerator();

		String password = PasswordHasher.hashPassword(password_bean + rand);

		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO " + IDM_Utente.TABLE_NAME1
					+ " (EMAIL, NOME, COGNOME, PASSWORD, TIPO_UTENTE, SALT) VALUES (?, ?, ?, ?, ?, ?)");

			preparedStatement.setString(1, utente.getEmail());
			preparedStatement.setString(2, utente.getNome());
			preparedStatement.setString(3, utente.getCognome());
			preparedStatement.setString(4, password);
			preparedStatement.setString(5, tipo_utente);
			preparedStatement.setString(6, rand);
			preparedStatement.executeUpdate();

			if ("compratore".equals(tipo_utente)) {
				UtenteCompratore compratore = (UtenteCompratore) utente;

				preparedStatement2 = connection.prepareStatement("INSERT INTO " + IDM_Utente.TABLE_NAME2
						+ " (EMAIL, CAP, INDIRIZZO, CIVICO, CELLULARE) VALUES (?, ?, ?, ?, ?)");
				preparedStatement2.setString(1, utente.getEmail());
				preparedStatement2.setString(2, compratore.getCap());
				preparedStatement2.setString(3, compratore.getIndirizzo());
				preparedStatement2.setString(4, compratore.getCivico());
				preparedStatement2.setString(5, compratore.getCellulare());

				preparedStatement2.executeUpdate();

			} else {
				UtenteVenditore venditore = (UtenteVenditore) utente;
				preparedStatement2 = connection.prepareStatement(

						"INSERT INTO " + IDM_Utente.TABLE_NAME3
								+ " (EMAIL, PARTITAIVA, TELEFONO, RAGIONESOCIALE) VALUES (?, ?, ?, ?)");
				preparedStatement2.setString(1, utente.getEmail());
				preparedStatement2.setString(2, venditore.getPartitaiva());
				preparedStatement2.setString(3, venditore.getTelefono());
				preparedStatement2.setString(4, venditore.getRagionesociale());

				preparedStatement2.executeUpdate();

			}

			connection.commit();

		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento dell'utente: " + e.getMessage());
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
	}

	public synchronized Utente doLoad(String email) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		String nome = null;
		String cognome = null;
		String tipo = null;

		String selectSQL = "SELECT * FROM " + IDM_Utente.TABLE_NAME1 + " WHERE EMAIL = ?";

		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				nome = rs.getString("nome");
				cognome = rs.getString("cognome");
				tipo = rs.getString("tipo_utente");
			}

			preparedStatement.close();

			if (tipo.equals("compratore")) {
				UtenteCompratore compratore = new UtenteCompratore();
				String selectSQL2 = "SELECT * FROM " + IDM_Utente.TABLE_NAME2 + " WHERE EMAIL = ?";
				preparedStatement2 = connection.prepareStatement(selectSQL2);
				preparedStatement2.setString(1, email);
				ResultSet rs2 = preparedStatement2.executeQuery();

				if (rs2.next()) {
					compratore.fillUtente(compratore, nome, cognome, email, rs2.getString("cap"),
							rs2.getString("indirizzo"), rs2.getString("civico"), rs2.getString("cellulare"));

				}

				preparedStatement2.close();
				connection.commit();
				ConnectionPool.releaseConnection(connection);
				return compratore;
			} else {
				UtenteVenditore venditore = new UtenteVenditore();
				String selectSQL2 = "SELECT * FROM " + IDM_Utente.TABLE_NAME3 + " WHERE EMAIL = ?";
				preparedStatement2 = connection.prepareStatement(selectSQL2);
				preparedStatement2.setString(1, email);
				ResultSet rs2 = preparedStatement2.executeQuery();

				if (rs2.next()) {
					venditore.fillUtente(venditore, nome, cognome, email, rs2.getString("partitaiva"),
							rs2.getString("telefono"), rs2.getString("ragionesociale"));
				}
					
				preparedStatement2.close();
				connection.commit();
				ConnectionPool.releaseConnection(connection);
				return venditore;
			}
		} catch (SQLException e) {

			throw e;
		} finally {

			if (preparedStatement2 != null) {
				preparedStatement2.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				ConnectionPool.releaseConnection(connection);
			}
		}
	}

	@Override
	public synchronized boolean doCheck(String email) throws SQLException {
		boolean flag = true;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		String searchSQL = "SELECT CASE WHEN EXISTS (SELECT * FROM " + IDM_Utente.TABLE_NAME1
				+ " WHERE EMAIL = ?) THEN 1 ELSE 0 END";

		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(searchSQL);

			preparedStatement.setString(1, email);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int result = resultSet.getInt(1);
				flag = (result == 1);
			}

			connection.commit();

		} finally {

			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			ConnectionPool.releaseConnection(connection);
		}

		return flag;
	}

	public synchronized boolean doLogin(String email, String password) throws SQLException {
		boolean flag = false;

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String rand = getRandom(email);

		String searchSQL = "SELECT CASE WHEN EXISTS (SELECT * FROM " + IDM_Utente.TABLE_NAME1
				+ " WHERE EMAIL = ? AND PASSWORD = ?) THEN 1 ELSE 0 END";

		try {

			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(searchSQL);

			preparedStatement.setString(1, email);

			@SuppressWarnings("static-access")
			String password_hashed = PasswordHasher.hashPassword(password + rand);

			preparedStatement.setString(2, password_hashed);

			ResultSet rs = preparedStatement.executeQuery();

			connection.commit();

			if (rs.next()) {
				if (rs.getInt(1) == 1) {
					flag = true;
				} else
					flag = false;
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				ConnectionPool.releaseConnection(connection);
			}
		}

		return flag;
	}

	public synchronized String getRandom(String email) {
		String rand = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String randomSQL = "SELECT * FROM " + IDM_Utente.TABLE_NAME1 + " WHERE EMAIL = ?";

		try {

			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(randomSQL);

			preparedStatement.setString(1, email);

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				rand = rs.getString("salt");
			}

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				ConnectionPool.releaseConnection(connection);
			}
		}

		return rand;
	}

	public synchronized String doSearch(String email) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs2 = null;

		String tipoUtente = null;

		try {
			connection = ConnectionPool.getConnection();
			String selectSQL = "SELECT TIPO_UTENTE FROM " + IDM_Utente.TABLE_NAME1 + " WHERE EMAIL = ?";
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);
			rs2 = preparedStatement.executeQuery();

			if (rs2.next()) {
				tipoUtente = rs2.getString("TIPO_UTENTE");
			}

			connection.commit();
		} finally {

			if (rs2 != null) {
				rs2.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		return tipoUtente;
	}

	public synchronized void doChange(String email, String tipoUtente, Utente utente) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;

		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(
					"UPDATE " + IDM_Utente.TABLE_NAME1 + " SET EMAIL=?, NOME=?, COGNOME=? WHERE EMAIL = ?"

			);

			preparedStatement.setString(1, utente.getEmail());
			preparedStatement.setString(2, utente.getNome());
			preparedStatement.setString(3, utente.getCognome());
			preparedStatement.setString(4, email);
			preparedStatement.executeUpdate();

			if ("compratore".equals(tipoUtente)) {
				UtenteCompratore compratore = (UtenteCompratore) utente;

				preparedStatement2 = connection.prepareStatement("UPDATE " + IDM_Utente.TABLE_NAME2
						+ " SET  CAP=?, INDIRIZZO=?, CIVICO=?, CELLULARE=? WHERE EMAIL = ?");

				preparedStatement2.setString(1, compratore.getCap());
				preparedStatement2.setString(2, compratore.getIndirizzo());
				preparedStatement2.setString(3, compratore.getCivico());
				preparedStatement2.setString(4, compratore.getCellulare());
				preparedStatement2.setString(5, utente.getEmail());

				preparedStatement2.executeUpdate();

			} else {
				UtenteVenditore venditore = (UtenteVenditore) utente;
				preparedStatement2 = connection.prepareStatement(

						"UPDATE " + IDM_Utente.TABLE_NAME3
								+ " SET  PARTITAIVA=?, TELEFONO=?, RAGIONESOCIALE=? WHERE EMAIL = ?");

				preparedStatement2.setString(1, venditore.getPartitaiva());
				preparedStatement2.setString(2, venditore.getTelefono());
				preparedStatement2.setString(3, utente.getEmail());
				preparedStatement2.executeUpdate();

			}

			connection.commit();

		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento dell'utente: " + e.getMessage());
		} finally {
			ConnectionPool.releaseConnection(connection);
		}

	}

}
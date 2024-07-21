package it.unisa.model;
import java.sql.Connection;
import java.sql.SQLException;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class IDM_General {
    
    private String TABLE_NAME;
    private Connection connection;
    

    public IDM_General(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
        this.connection = null;
    }

    private boolean createConnection() throws SQLException {
        try {
            this.connection = ConnectionPool.getConnection();
            return true;
        } catch (SQLException e) {
            System.out.println("Error creating connection: " + e.getMessage());
            return false;
        }
    }

    private void releaseConnection() {
        if (this.connection != null) {
            ConnectionPool.releaseConnection(connection);
        }
    }

    public boolean insertData(List<Object> params) throws SQLException {
        if (!this.createConnection()) {
            System.out.println("Connection not established.");
            return false;
        }

        // Costruzione della parte della query per i nomi delle colonne
        StringBuilder columnNames = new StringBuilder();
        // Costruzione della parte della query per i valori
        StringBuilder values = new StringBuilder();

        for (int i = 0; i < params.size(); i += 2) {
            if (i > 0) {
                columnNames.append(", ");
                values.append(", ");
            }
            columnNames.append(params.get(i));
            values.append("?");
        }

        String query = "INSERT INTO " + TABLE_NAME + " (" + columnNames + ") VALUES (" + values + ")";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            // Impostazione dei valori dei parametri
            for (int i = 0; i < params.size() / 2; i++) {
                statement.setObject(i + 1, params.get(i * 2 + 1));
            }
            int rowsInserted = statement.executeUpdate();
            this.connection.commit();
            
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            this.releaseConnection();
        }
    }


    public boolean updateData(int id, List<Object> params) throws SQLException {
    	if (!this.createConnection()) {
            System.out.println("Connection not established.");
            return false;
        }
    	//"UPDATE " + IDM_Utente.TABLE_NAME1 + " SET EMAIL=?, NOME=?, COGNOME=? WHERE EMAIL = ?"
        // Costruzione della parte della query per i nomi delle colonne
        StringBuilder values = new StringBuilder();

        for (int i = 0; i < params.size(); i += 2) {
            
            values.append(params.get(i));
            values.append("=?");
            if(i+2<params.size()) {
            	values.append(", ");
            }
            
        }
        
        
        String query = "UPDATE " + TABLE_NAME + " SET " + values + "WHERE ...";
		
        
        return false; //messo per togliere l'errore
		
		
    }
        
    
    public List<Object> searchData(List<Object> searchParams) throws SQLException {
        List<Object> objectList = new ArrayList<>();

        if (!createConnection()) {
            System.out.println("Connection not established.");
            return objectList;
        }

        // Constructing the search query
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM ").append(TABLE_NAME).append(" WHERE ");

        // Assuming searchParams contains pairs of columnName and corresponding search value
        for (int i = 0; i < searchParams.size(); i += 2) {
            if (i > 0) {
                queryBuilder.append(" AND ");
            }
            queryBuilder.append(searchParams.get(i)).append(" = ?");
        }

        String searchQuery = queryBuilder.toString();

        try {
            PreparedStatement statement = connection.prepareStatement(searchQuery);

            // Set search parameters
            for (int i = 0; i < searchParams.size() / 2; i++) {
                statement.setObject(i + 1, searchParams.get(i * 2 + 1));
            }

            ResultSet resultSet = statement.executeQuery();
            this.connection.commit();

            while (resultSet.next()) {
                // Process each row of the result set
                Object obj = createObjectFromResultSet(resultSet);
                objectList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection();
        }

        return objectList;
    }
    
    public boolean deleteData(int id) throws SQLException {
        if (!this.createConnection()) {
            System.out.println("Connection not established.");
            return false;
        }

        String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            this.connection.commit();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
        	this.releaseConnection();
        }
    }

    public List<Object> retrieveObjects() throws SQLException {
        List<Object> objectList = new ArrayList<>();

        if (!createConnection()) {
            System.out.println("Connection not established.");
            return objectList;
        }

        String query = "SELECT * FROM " + TABLE_NAME;

        try {
        	PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            this.connection.commit();

            while (resultSet.next()) {
                // Process each row of the result set
                // Create an object for each row and add it to the list
                Object obj = createObjectFromResultSet(resultSet);
                objectList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseConnection();
        }

        return objectList;
    }

	protected abstract Object createObjectFromResultSet(ResultSet resultSet);

}

	
	
	
	



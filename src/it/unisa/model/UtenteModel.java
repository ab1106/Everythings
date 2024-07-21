/*
 * OLD CODE START
 *
 * package it.unisa.model;


import java.sql.SQLException;


public interface UtenteModel {
	
	public void doSave(Utente utente, String password_bean, String tipo_utente) throws SQLException;
	public boolean doCheck(String email) throws SQLException;
	public  boolean doLogin(String email,String password) throws SQLException;
	public String getRandom(String email);
	public Utente doLoad(String email) throws SQLException;

	
}
*
*OLD CODE END
*
*/

// NEW CODE START
package it.unisa.model;
import java.sql.SQLException;

public interface UtenteModel {

    void doSave(Utente utente, String password_bean, String tipo_utente) throws SQLException;

    boolean doCheck(String email) throws SQLException;

    boolean doLogin(String email, String password) throws SQLException;

    String getRandom(String email);

    Utente doLoad(String email) throws SQLException;
    
    String doSearch(String email) throws SQLException;

    void doChange(String email, String tipoUtente,Utente utente);
}


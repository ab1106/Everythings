package it.unisa.model;

import java.io.Serializable;

public abstract class Utente implements Serializable {

	private static final long serialVersionUID = 1L;

	String nome;
	String cognome;
	String email;

	public Utente() {
		nome = "";
		cognome = "";
		email = "";

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Utente [nome=" + nome + ", cognome=" + cognome + ", email=" + email + "]";
	}
	

}
 
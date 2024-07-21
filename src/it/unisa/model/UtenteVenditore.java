package it.unisa.model;

public class UtenteVenditore extends Utente {

	private static final long serialVersionUID = 1L;

	String partitaiva;
	String telefono;
	String ragionesociale;

	public UtenteVenditore() {
		super();
		partitaiva = "";
		telefono = "";
		ragionesociale = "";
	}

	public String getPartitaiva() {
		return partitaiva;
	}

	public void setPartitaiva(String partitaiva) {
		this.partitaiva = partitaiva;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getRagionesociale() {
		return ragionesociale;
	}

	public void setRagionesociale(String ragionesociale) {
		this.ragionesociale = ragionesociale;
	}

	public void fillUtente(UtenteVenditore bean, String nome, String cognome, String email, String partitaiva,
			String telefono, String ragionesociale) {

		bean.setNome(nome);
		bean.setCognome(cognome);
		bean.setEmail(email);
		bean.setPartitaiva(partitaiva);
		bean.setTelefono(telefono);
		bean.setRagionesociale(ragionesociale);

	}
}

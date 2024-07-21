package it.unisa.model;

public class UtenteCompratore extends Utente {

	private static final long serialVersionUID = 1L;

	String cap;
	String indirizzo;
	String civico;
	String cellulare;

	public UtenteCompratore() {
		super();
		cap = "";
		indirizzo = "";
		civico = "";
		cellulare = "";
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	@Override
	public String toString() {
		return super.toString() + "UtenteCompratore [cap=" + cap + ", indirizzo=" + indirizzo + ", civico=" + civico
				+ ", cellulare=" + cellulare + "]";
	}

	public void fillUtente(UtenteCompratore bean, String nome, String cognome, String email, String cap,
			String indirizzo, String civico, String cellulare) {

		bean.setNome(nome);
		bean.setCognome(cognome);
		bean.setEmail(email);
		bean.setCap(cap);
		bean.setIndirizzo(indirizzo);
		bean.setCivico(civico);
		bean.setCellulare(cellulare);

	}

}

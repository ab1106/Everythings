package it.unisa.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

import javax.servlet.http.Part;

public class Prodotto implements Serializable {

	private static final long serialVersionUID = 1L;

	int id;
	String email;
	String nome;
	String descrizione;
	double prezzo;
	int disponibilita;
	byte[] immagine;

	public Prodotto() {
		id = 0;
		email = "";
		nome = "";
		descrizione = "";
		prezzo = 0;
		disponibilita = 0;
		immagine = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public int getDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}

	public byte[] getImmagine() {
		return immagine;
	}

	public void setImmagine(byte[] immagine) {
		this.immagine = immagine;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Prodotto prodotto = (Prodotto) o;
		return id == prodotto.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public void fillProduct(Prodotto prodotto, int id, String email, String nome, String descrizione, double prezzo,
			int disponibilita, Blob blob) throws SQLException {

		prodotto.setId(id);
		prodotto.setEmail(email);
		prodotto.setNome(nome);
		prodotto.setDescrizione(descrizione);
		prodotto.setPrezzo(prezzo);
		prodotto.setDisponibilita(disponibilita);

		if (blob != null) {
			byte[] imageByte = blob.getBytes(1, (int) blob.length());
			prodotto.setImmagine(imageByte);
		} else {
			byte[] emptyImage = new byte[0];
			prodotto.setImmagine(emptyImage);
		}

	}
	
	public void fillProductNOBLOB(Prodotto prodotto, int id, String email, String nome, String descrizione, double prezzo,
			int disponibilita, Part filePart) throws SQLException, IOException {

		prodotto.setId(id);
		prodotto.setEmail(email);
		prodotto.setNome(nome);
		prodotto.setDescrizione(descrizione);
		prodotto.setPrezzo(prezzo);
		prodotto.setDisponibilita(disponibilita);
		InputStream immagineStream = filePart.getInputStream();
		byte[] immagine = immagineStream.readAllBytes();
		prodotto.setImmagine(immagine);

	}

	public void fillProductNOID(Prodotto prodotto, String email, String nome, String descrizione, double prezzo,
			int disponibilita, Part filePart) throws SQLException, IOException {

		prodotto.setEmail(email);
		prodotto.setNome(nome);
		prodotto.setDescrizione(descrizione);
		prodotto.setPrezzo(prezzo);
		prodotto.setDisponibilita(disponibilita);
		InputStream immagineStream = filePart.getInputStream();
		byte[] immagine = immagineStream.readAllBytes();
		prodotto.setImmagine(immagine);

	}

	@Override
	public String toString() {
		return "Prodotto [id=" + id + ", email=" + email + ", nome=" + nome + ", descrizione=" + descrizione
				+ ", prezzo=" + prezzo + ", disponibilita=" + disponibilita + ", immagine=" + Arrays.toString(immagine)
				+ "]";
	}

}
 

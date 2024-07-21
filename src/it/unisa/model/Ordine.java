package it.unisa.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import it.unisa.model.Prodotto;

public class Ordine implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idOrdine;
	private String compratore;
	private String dataOrdine;
	private int totaleOrdine;
	byte[] carrello;

	public Ordine() {
		this.idOrdine = 0;
		this.compratore = null;
		this.dataOrdine = null;
		this.totaleOrdine = 0;
		this.carrello = null;

	}

	public int getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}

	public String getCompratore() {
		return compratore;
	}

	public void setCompratore(String compratore) {
		this.compratore = compratore;
	}

	public String getDataOrdine() {
		return dataOrdine;
	}

	public void setDataOrdine(String dataOrdine) {
		this.dataOrdine = dataOrdine;
	}

	public int getTotaleOrdine() {
		return totaleOrdine;
	}

	public void setTotaleOrdine(int totaleOrdine) {
		this.totaleOrdine = totaleOrdine;
	}

	public byte[] getCarrello() {
		return carrello;
	}

	public void setCarrello(byte[] carrello) {
		this.carrello = carrello;
	}

	public byte[] creaFattura(Carrello carrello, int totale, String compratore, String data) {
		try {
			Document document = new Document();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();

			Paragraph header = new Paragraph("Fattura d'acquisto\n\n");
			header.setAlignment(Element.ALIGN_CENTER);
			document.add(header);
			Paragraph customerInfo = new Paragraph(
					"Data: " + data + "\n" + "Email del compratore: " + compratore + "\n\n");
			document.add(customerInfo);

			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100);

			PdfPCell cell = new PdfPCell(new Phrase("Prodotto"));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Quantità"));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Prezzo"));
			table.addCell(cell);

			for (Map.Entry<Prodotto, Integer> entry : carrello.getProdotti().entrySet()) {
				Prodotto prodotto = entry.getKey();
				int quantita = entry.getValue();
				double prezzo = prodotto.getPrezzo();
				double totaleProdotto = quantita * prezzo;

				table.addCell(prodotto.getNome());
				table.addCell(String.valueOf(quantita));
				table.addCell(String.format("%.2f €", totaleProdotto));
			}

			document.add(table);

			Paragraph total = new Paragraph("\nTotale: " + totale + " €");
			total.setAlignment(Element.ALIGN_RIGHT);
			document.add(total);

			document.close();

			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void visualizzaPDF(HttpServletResponse response, byte[] pdfData, int mode) {

		response.setContentType("application/pdf");
		if (mode == 1) {
			response.setHeader("Content-Disposition", "inline; filename=fattura.pdf");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=fattura.pdf");
		}
		try (ByteArrayInputStream bis = new ByteArrayInputStream(pdfData)) {
			OutputStream out = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = bis.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fillOrdine(Ordine bean,int id,String compratore,String data,int totale,Blob blob) throws SQLException {
		bean.setIdOrdine(id);
		bean.setCompratore(compratore);
		bean.setDataOrdine(data);
		bean.setTotaleOrdine(totale);
		
		byte[] carrelloByte = blob.getBytes(1, (int) blob.length());
		bean.setCarrello(carrelloByte);

		if (bean.getCarrello() == null) {
			byte[] emptyCarrello = new byte[0];
			bean.setCarrello(emptyCarrello);
		}
	}
	
	public byte[] creaCarrello(Carrello carrello) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            
            oos.writeObject(carrello);
            return bos.toByteArray();
        }
    }
	
	public Carrello deserializeCarrello(byte[] serializedCarrello) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(serializedCarrello);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            
            return (Carrello) ois.readObject();
        }
    }

}

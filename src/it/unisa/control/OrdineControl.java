package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import it.unisa.model.Carrello;
import it.unisa.model.IDM_Ordine;
import it.unisa.model.Ordine;
import it.unisa.model.UtenteCompratore;
import java.text.SimpleDateFormat;

public class OrdineControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	IDM_Ordine model = new IDM_Ordine();

	public OrdineControl() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		String action = request.getParameter("action");

		if (action.equalsIgnoreCase("acquisto")) {
			
			if((Carrello) session.getAttribute("carrello")!=null){
				
			

				Date dataAttuale = new Date();
				Ordine ordine = new Ordine();
	
				ordine.setCompratore(request.getParameter("compratore"));
				ordine.setTotaleOrdine(Integer.parseInt(request.getParameter("totale")));
				ordine.setDataOrdine(sdf.format(dataAttuale));
				ordine.setCarrello(ordine.creaCarrello((Carrello) session.getAttribute("carrello")));

				
	
				try {
					
						model.updateProducts((Carrello) session.getAttribute("carrello"));
						model.importa(ordine);
						((Carrello) session.getAttribute("carrello")).resetCart();
						action = "fine";
				} catch (SQLException e) {
	
					e.printStackTrace();
				}
	
				
	
				
			}else {
				action="doRegistration";
			}
		} else if (action.equalsIgnoreCase("visualizza")) {

			UtenteCompratore utentecompratore = (UtenteCompratore) session.getAttribute("utente");

			try {
				Ordine ordinex= model.doRetrieveById(Integer.parseInt(request.getParameter("id_ordine")), utentecompratore.getEmail());
				ordinex.visualizzaPDF(response,
								(model.doRetrieveById(Integer.parseInt(request.getParameter("id_ordine")),
										utentecompratore.getEmail())).creaFattura(ordinex.deserializeCarrello(ordinex.getCarrello()), ordinex.getTotaleOrdine(), ordinex.getCompratore(), ordinex.getDataOrdine()),
								1);
			} catch (SQLException e) {

				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (action.equalsIgnoreCase("scarica")) {
			UtenteCompratore utentecompratore = (UtenteCompratore) session.getAttribute("utente");

			try {
				Ordine ordinex= model.doRetrieveById(Integer.parseInt(request.getParameter("id_ordine")), utentecompratore.getEmail());
				ordinex.visualizzaPDF(response,
								(model.doRetrieveById(Integer.parseInt(request.getParameter("id_ordine")),
										utentecompratore.getEmail())).creaFattura(ordinex.deserializeCarrello(ordinex.getCarrello()), ordinex.getTotaleOrdine(), ordinex.getCompratore(), ordinex.getDataOrdine()),
								2);
			} catch (SQLException e) {

				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (action.equalsIgnoreCase("fine")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Carrello.jsp");
			dispatcher.forward(request, response);
		}else if (action.equalsIgnoreCase("doRegistration")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Registrazione.jsp");
			dispatcher.forward(request, response);
		}else if (action.equalsIgnoreCase("acquistoNegato")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Carrello.jsp");
			dispatcher.forward(request, response);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

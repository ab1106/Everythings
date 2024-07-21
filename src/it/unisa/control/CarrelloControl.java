
package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import it.unisa.model.Carrello;
import it.unisa.model.IDM_Prodotto;
import it.unisa.model.Prodotto;

public class CarrelloControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Carrello cart = new Carrello();

	Prodotto prodotto = new Prodotto();

	IDM_Prodotto idmprod = new IDM_Prodotto();

	public CarrelloControl() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		String action = request.getParameter("action");
		if ("addCart".equalsIgnoreCase(action) || "addCart1".equalsIgnoreCase(action)) {
		    Carrello cart = (Carrello) session.getAttribute("carrello");
		    session.setAttribute("dispo", null);

		    int idprod = Integer.parseInt(request.getParameter("product"));
		    Prodotto prodotto = null;
		    try {
		        prodotto = idmprod.doRetrieveById(idprod);
		    } catch (SQLException e) {
		        
		        e.printStackTrace();
		    }

		    if (prodotto != null) {
		    	if(cart.getProdotti().containsKey(prodotto)) {
			    	Iterator<Map.Entry<Prodotto, Integer>> iterator = cart.getProdotti().entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry<Prodotto, Integer> entry = iterator.next();
						 if(entry.getKey().getId()==idprod) {
							if(entry.getValue()<prodotto.getDisponibilita()) {
								cart.aggiungiProdotto(prodotto);
								session.setAttribute("carrello", cart);
								session.setAttribute("prodottoFromControl", prodotto);
							}else {
								session.setAttribute("dispo", "1");
							}
						}
					}
		    	}else {
		    		cart.aggiungiProdotto(prodotto);
		    		session.setAttribute("carrello", cart);
					session.setAttribute("prodottoFromControl", prodotto);
		    	}
			        
			        
		    } else {
		        // Gestione del caso in cui il prodotto non è stato trovato
		    }
		    
		    if ("addCart".equalsIgnoreCase(action)) {
	            action = "prod_added";
	        } else if ("addCart1".equalsIgnoreCase(action)) {
	            action = "prod_added1";
	        }
		    
		    
		}else if (action.equalsIgnoreCase("toCheck")) {
			int somma = Integer.parseInt(request.getParameter("total"));

			session.setAttribute("totale", somma);

			action = "checkOut";

		} else if (action.equalsIgnoreCase("delete1")) {
	
				cart = (Carrello) session.getAttribute("carrello");
		

			int idprod = Integer.parseInt(request.getParameter("idprod"));

			cart.rimuovi1Prodotto(idprod);

	
				session.setAttribute("carrello", cart);


			action = "deleted";

		} else if (action.equalsIgnoreCase("delete")) {

		
				cart = (Carrello) session.getAttribute("carrello");


			int idprod = Integer.parseInt(request.getParameter("idprod"));

			cart.rimuoviProdotto(idprod);

		
				session.setAttribute("carrello", cart);


			action = "deleted";
		}

		if (action.equalsIgnoreCase("prod_added")) {
			response.sendRedirect("Prodotto.jsp");
		} else if (action.equalsIgnoreCase("checkOut")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Carrello.jsp");
			dispatcher.forward(request, response);
		} else if (action.equalsIgnoreCase("deleted")) {
			response.sendRedirect("Carrello.jsp");
		}else if (action.equalsIgnoreCase("prod_added1")) {
			response.sendRedirect("Carrello.jsp");
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}






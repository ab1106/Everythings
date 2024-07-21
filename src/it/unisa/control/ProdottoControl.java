
package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import it.unisa.model.IDM_Prodotto;
import it.unisa.model.Prodotto;
import it.unisa.model.ProdottoModel;

public class ProdottoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static ProdottoModel model = new IDM_Prodotto();

	public ProdottoControl() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		String action = request.getParameter("action");

		try {
			if (action.equalsIgnoreCase("addProduct")) {

				Prodotto prodotto = new Prodotto();

				prodotto.fillProductNOID(prodotto, request.getParameter("email1"), request.getParameter("nomeprodotto"),
						request.getParameter("descrizione"), Double.parseDouble(request.getParameter("prezzo")),
						Integer.parseInt(request.getParameter("disponibilita")), request.getPart("immagine"));

				model.addProdotto(prodotto);

			} else if (action.equalsIgnoreCase("change")) {

				Prodotto prodotto = new Prodotto();

				
				prodotto.fillProductNOBLOB(prodotto, Integer.parseInt(request.getParameter("idProd")),
						request.getParameter("email1"), request.getParameter("mod_nomeprodotto"),
						request.getParameter("mod_descrizione"), Double.parseDouble(request.getParameter("mod_prezzo")),
						Integer.parseInt(request.getParameter("mod_disponibilita")), request.getPart("mod_immagine"));

				model.changeProdotto(prodotto, Integer.parseInt(request.getParameter("idProd")));

			} else if (action.equalsIgnoreCase("delete1")) {

				int id = Integer.parseInt(request.getParameter("checkbox"));

				model.deleteProdotto(id);
			} else if (action.equalsIgnoreCase("deleteMORE")) {

				String idArrayString = request.getParameter("idArray");
				String[] idArrayStrings = idArrayString.split(",");
				int[] ids = new int[idArrayStrings.length];
				if (idArrayString != null) {
					for (int i = 0; i < idArrayStrings.length; i++) {
						try {
							ids[i] = Integer.parseInt(idArrayStrings[i]);
							System.out.println(ids[i]);
						} catch (NumberFormatException e) {

							e.printStackTrace(); 
						}
					}

				}
				model.deleteProdotti(ids);

			} else if (action.equalsIgnoreCase("toProductID")) {

				int id = Integer.parseInt(request.getParameter("id_product"));

				session.setAttribute("prodottoFromControl", model.doRetrieveById(id));

			} else if (action.equalsIgnoreCase("toProductNAME")) {

				String name = request.getParameter("name_product");

				session.setAttribute("prodottoFromControl", model.doRetrieveByName(name));

			}else if(action.equalsIgnoreCase("cercaProdotti")) {
				//System.out.println("prova");
				String search= request.getParameter("searchString"); 
				//System.out.println(search);
				Collection<Prodotto> products = model.searchProducts(search);
				System.out.println(products.size());
				session.setAttribute("productsSearched", products);
				
				action="toSearch";
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		if (action.equalsIgnoreCase("addProduct")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/AreaVenditore.jsp");
			dispatcher.forward(request, response);
		} else if (action.equalsIgnoreCase("change")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/AreaVenditore.jsp");
			dispatcher.forward(request, response);
		} else if (action.equalsIgnoreCase("delete1")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/AreaVenditore.jsp");
			dispatcher.forward(request, response);
		} else if (action.equalsIgnoreCase("deleteMORE")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/AreaVenditore.jsp");
			dispatcher.forward(request, response);
		} else if (action.equalsIgnoreCase("toProductID")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Prodotto.jsp");
			dispatcher.forward(request, response);
		} else if (action.equalsIgnoreCase("toProductNAME")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Prodotto.jsp");
			dispatcher.forward(request, response);
		}else if (action.equalsIgnoreCase("toSearch")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RisultatoRicerca.jsp");
			dispatcher.forward(request, response);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

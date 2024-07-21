
package it.unisa.control;

import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.model.UtenteVenditore;
import it.unisa.model.Carrello;
import it.unisa.model.IDM_Utente;
import it.unisa.model.UtenteCompratore;
import it.unisa.model.UtenteModel;

public class UtenteControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static UtenteModel model = new IDM_Utente();

	public UtenteControl() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		try {

			if (action.equalsIgnoreCase("logout")) {
				HttpSession session = request.getSession();
				//session.setAttribute("utente", null);
				//((Carrello) session.getAttribute("carrello")).resetCart();
				session.invalidate();
				
				response.sendRedirect("Login.jsp");
	            return; 

			}
			if (action.equalsIgnoreCase("register")) {
				String email = request.getParameter("email");

				if (!(model.doCheck(email))) {
					String tipo_utente = request.getParameter("tipo_utente");

					if ("compratore".equals(tipo_utente)) {

						UtenteCompratore bean = new UtenteCompratore();
						bean.fillUtente(bean, request.getParameter("nome"), request.getParameter("cognome"), email,
								request.getParameter("cap"), request.getParameter("indirizzo"),
								request.getParameter("civico"), request.getParameter("cellulare"));

						model.doSave(bean, request.getParameter("password"), tipo_utente);

					} else {

						UtenteVenditore bean = new UtenteVenditore();

						bean.fillUtente(bean, request.getParameter("nome"), request.getParameter("cognome"), email,
								request.getParameter("piva"), request.getParameter("telefono"),
								request.getParameter("ragione"));

						model.doSave(bean, request.getParameter("password"), tipo_utente);

					}

					action = "benvenuto";
					HttpSession session = request.getSession();
					if(session.getAttribute("carrelloguest")==null) {
						Carrello carrello = new Carrello(email);

						session.setAttribute("carrello", carrello);
					}else {
						session.setAttribute("carrello", session.getAttribute("carrelloguest"));
					}
					session.setAttribute("utente", model.doLoad(email));

				} else {
					request.setAttribute("message", "email_presente");
					action = "do_login";
				}
			} else if (action.equalsIgnoreCase("do_login")) {
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				if (model.doLogin(email, password)) {
					action = "benvenuto";
					HttpSession session = request.getSession();

					session.setAttribute("utente", model.doLoad(email));

					if (model.doLoad(email) instanceof UtenteCompratore) {
						if(session.getAttribute("carrelloguest")==null) {
							Carrello carrello = new Carrello(email);

							session.setAttribute("carrello", carrello);
						}else {
							session.setAttribute("carrello", session.getAttribute("carrelloguest"));
						}
					}

				} else {
					request.setAttribute("message", "login_fail");
				}
			} else if (action.equalsIgnoreCase("change")) {

				boolean check = false;
				String email = request.getParameter("email");
				String firstEmail = request.getParameter("firstEmail");

				String tipoUtente = model.doSearch(firstEmail);

				if (email == firstEmail) {
					check = true;
				}

				if (!(model.doCheck(email))) {

					if (tipoUtente.equalsIgnoreCase("compratore")) {

						UtenteCompratore bean = new UtenteCompratore();

						bean.fillUtente(bean, request.getParameter("nome"), request.getParameter("cognome"), email,
								request.getParameter("cap"), request.getParameter("indirizzo"),
								request.getParameter("civico"), request.getParameter("cellulare"));

						model.doChange(firstEmail, tipoUtente, bean);

					} else if (tipoUtente.equalsIgnoreCase("venditore")) {

						UtenteVenditore bean = new UtenteVenditore();

						bean.fillUtente(bean, request.getParameter("nome"), request.getParameter("cognome"), email,
								request.getParameter("piva"), request.getParameter("telefono"),
								request.getParameter("ragione"));

						model.doChange(firstEmail, tipoUtente, bean);

					}

					HttpSession session = request.getSession();
					session.invalidate();
					action = "do_logout";

				} else if (check) {

					if (tipoUtente == "compratore") {

						UtenteCompratore bean = new UtenteCompratore();

						bean.fillUtente(bean, request.getParameter("nome"), request.getParameter("cognome"), email,
								request.getParameter("cap"), request.getParameter("indirizzo"),
								request.getParameter("civico"), request.getParameter("cellulare"));

						model.doChange(firstEmail, tipoUtente, bean);

					} else {

						UtenteVenditore bean = new UtenteVenditore();

						bean.fillUtente(bean, request.getParameter("nome"), request.getParameter("cognome"), email,
								request.getParameter("piva"), request.getParameter("telefono"),
								request.getParameter("ragione"));

						model.doChange(firstEmail, tipoUtente, bean);

					}

					HttpSession session = request.getSession();
					session.invalidate();
					action = "do_logout";

				} else {
					request.setAttribute("message", "email_presente");

					if (tipoUtente.equalsIgnoreCase("compratore")) {

						action = "change_compratore";
					} else if (tipoUtente.equalsIgnoreCase("venditore")) {
						action = "change_venditore";
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		if (action.equalsIgnoreCase("do_login")) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
		} else if (action.equalsIgnoreCase("benvenuto")) {
			response.sendRedirect("homepage.jsp");
		} else if (action.equalsIgnoreCase("change_compratore")) {
			response.sendRedirect("AreaCompratore.jsp");
		} else if (action.equalsIgnoreCase("change_venditore")) {
			response.sendRedirect("AreaVenditore.jsp");
		} 
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}


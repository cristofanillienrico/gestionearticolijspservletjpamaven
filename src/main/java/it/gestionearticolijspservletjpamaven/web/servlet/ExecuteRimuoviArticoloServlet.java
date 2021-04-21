package it.gestionearticolijspservletjpamaven.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.gestionearticolijspservletjpamaven.model.Articolo;
import it.gestionearticolijspservletjpamaven.service.ArticoloService;
import it.gestionearticolijspservletjpamaven.service.MyServiceFactory;
import it.gestionearticolijspservletjpamaven.utility.UtilityArticoloForm;

@WebServlet("/ExecuteRimuoviArticoloServlet")
public class ExecuteRimuoviArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteRimuoviArticoloServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idArticoloParam = request.getParameter("idArticolo");
		System.out.println(idArticoloParam);

		ArticoloService serviceArticolo = MyServiceFactory.getArticoloServiceInstance();

		if (!NumberUtils.isCreatable(idArticoloParam)) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			request.setAttribute("errorMessage", "Attenzione si è verificato un erroreeeee.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		try {
			Articolo articoloDaEliminare = serviceArticolo.caricaSingoloElemento(Long.parseLong(idArticoloParam));
			serviceArticolo.rimuovi(articoloDaEliminare);
			request.setAttribute("listaArticoliAttribute", MyServiceFactory.getArticoloServiceInstance().listAll());
			request.setAttribute("successMessage", "Operazione effettuata con successo");
		} catch (Exception e) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher("/articolo/results.jsp").forward(request, response);
	}
}

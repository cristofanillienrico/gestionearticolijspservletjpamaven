package it.gestionearticolijspservletjpamaven.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.gestionearticolijspservletjpamaven.model.Articolo;
import it.gestionearticolijspservletjpamaven.service.ArticoloService;
import it.gestionearticolijspservletjpamaven.service.MyServiceFactory;
import it.gestionearticolijspservletjpamaven.utility.UtilityArticoloForm;

@WebServlet("/ExecuteModificaArticoloServlet")
public class ExecuteModificaArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteModificaArticoloServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// estraggo input
		String codiceInputParam = request.getParameter("codice");
		String descrizioneInputParam = request.getParameter("descrizione");
		String prezzoInputStringParam = request.getParameter("prezzo");
		String dataArrivoStringParam = request.getParameter("dataArrivo");

		String idArticoloParam = request.getParameter("idArticolo");

		ArticoloService serviceArticolo = MyServiceFactory.getArticoloServiceInstance();

		// questa variabile mi serve in quanto sfrutto in un colpo la validazione
		// della data ed il suo parsing che non posso fare senza un try catch
		// a questo punto lo incapsulo in un metodo apposito
		Date dataArrivoParsed = UtilityArticoloForm.parseDateArrivoFromString(dataArrivoStringParam);

		// valido input tramite apposito metodo e se la validazione fallisce torno in
		// pagina
		if (!UtilityArticoloForm.validateInput(codiceInputParam, descrizioneInputParam, prezzoInputStringParam,
				dataArrivoStringParam) || dataArrivoParsed == null) {

			Integer provvisorio = 0;
			if (!prezzoInputStringParam.isEmpty()) {
				provvisorio = Integer.parseInt(prezzoInputStringParam);
			}
			request.setAttribute("modifica_articolo_attr", new Articolo(Long.parseLong(idArticoloParam),
					codiceInputParam, descrizioneInputParam, provvisorio, dataArrivoParsed));
			request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
			request.getRequestDispatcher("/articolo/modifica.jsp").forward(request, response);
			return;
		}

		try {
			Articolo articoloInstance = serviceArticolo.caricaSingoloElemento(Long.parseLong(idArticoloParam));
			articoloInstance.setCodice(codiceInputParam);
			articoloInstance.setDataArrivo(dataArrivoParsed);
			articoloInstance.setDescrizione(descrizioneInputParam);
			articoloInstance.setPrezzo(Integer.parseInt(prezzoInputStringParam));
			serviceArticolo.aggiorna(articoloInstance);
			request.setAttribute("listaArticoliAttribute", MyServiceFactory.getArticoloServiceInstance().listAll());
			request.setAttribute("successMessage", "Operazione effettuata con successo");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/articolo/modifica.jsp").forward(request, response);
			return;
		}

		// andiamo ai risultati
		request.getRequestDispatcher("/articolo/results.jsp").forward(request, response);

	}

}

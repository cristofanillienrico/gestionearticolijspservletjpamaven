<!doctype html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="it.gestionearticolijspservletjpamaven.model.Articolo"%>
<html lang="it">
<head>
	<jsp:include page="../header.jsp" />
	<title>Modifica Articolo</title>
	
	<!-- style per le pagine diverse dalla index -->
    <link href="./assets/css/global.css" rel="stylesheet">
    
</head>
<body>
	<jsp:include page="../navbar.jsp" />
	
	<main role="main" class="container">
	
		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none': ''}" role="alert">
		  ${errorMessage}
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
		
		<% Articolo articoloInPagina = (Articolo)request.getAttribute("modifica_articolo_attr"); %>
		
		<div class='card'>
		    <div class='card-header'>
		        <h5>Modifica l'articolo</h5> 
		    </div>
		    <div class='card-body'>

					<h6 class="card-title">I campi con <span class="text-danger">*</span> sono obbligatori</h6>

					<form method="post" action="ExecuteModificaArticoloServlet" novalidate="novalidate">
					
						<div class="form-row">
							<div class="form-group col-md-6">
								<label>Codice <span class="text-danger" >*</span></label>
								<input type="text" name="codice" id="codice" class="form-control" value="<%=articoloInPagina.getCodice()!=null?articoloInPagina.getCodice():"" %>" required>
							</div>
							
							<div class="form-group col-md-6">
								<label>Descrizione <span class="text-danger">*</span></label>
								<input type="text" name="descrizione" id="descrizione" class="form-control" value="<%=articoloInPagina.getDescrizione()!=null?articoloInPagina.getDescrizione():"" %>" required>
							</div>
						</div>
						
						<div class="form-row">	
							<div class="form-group col-md-6">
								<label>Prezzo <span class="text-danger">*</span></label>
								<input type="number" class="form-control" name="prezzo" id="prezzo" value="<%=articoloInPagina.getPrezzo()!=null?articoloInPagina.getPrezzo():"" %>" required>
							</div>
							<div class="form-group col-md-3">
								<label>Data di Arrivo<span class="text-danger">*</span></label>
                        		<input class="form-control" id="dataArrivo" type="date"  value = "<%=articoloInPagina.getDataArrivo()!=null? new SimpleDateFormat("yyyy-MM-dd").format(articoloInPagina.getDataArrivo()):""%>"
                            		title="formato : gg/mm/aaaa"  name="dataArrivo" required>
							</div>
							
						</div>
							
						<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
					
						<input type="hidden"   name="idArticolo"   value="<%= articoloInPagina.getId()%>">

					</form>

		    
		    
			<!-- end card-body -->			   
		    </div>
		</div>	
	
	
	<!-- end container -->	
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>
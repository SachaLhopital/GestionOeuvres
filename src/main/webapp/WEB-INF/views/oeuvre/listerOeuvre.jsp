<%@include file="../commun/header.jsp"%>

<div class="row">
    <div class="col-sm-2">
        <a href="../index.jsp"><span class="fa fa-home fa-2x"></span></a>
    </div>
</div>

<div class="row text-center">
    <h3>Listing&nbsp;des Oeuvres</h3>
</div>

<%-- Listing Oeuvres de prêt --%>
<div class="row">
    <div class="col-md-12">
        <div class="panel-body">
            <table class="table table-hover table-striped table-bordered">
                <caption>Tableau des Oeuvres de pr&ecirc;t</caption>
                <TR>
                    <TH>Titre</TH>
                    <TH>Proprietaire</TH>
                    <th></th>
                </TR>

                <c:forEach items="${mesOeuvresPret}" var="item">
                    <tr>
                        <td>${item.titreOeuvrepret}</td>
                        <td>${item.proprietaire.nomProprietaire} ${item.proprietaire.prenomProprietaire}</td>
                        <td>
                            <a href="ControleurOeuvre?action=detailOeuvrePret&id=${item.idOeuvrepret}">
                                <button type="button" class="btn btn-info" aria-label="Left Align">
                                    <span class="fa fa-pencil" aria-hidden="true"></span>
                                </button>
                            </a>
                            <a href="ControleurOeuvre?action=deleteOeuvrePret&id=${item.idOeuvrepret}">
                                <button type="button" class="btn btn-danger" aria-label="Left Align">
                                    <span class="fa fa-times" aria-hidden="true"></span>
                                </button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>

<%-- Listing Oeuvres de Vente --%>
<div class="row">
    <div class="col-md-12">
        <div class="panel-body">
            <table class="table table-hover table-striped table-bordered">
                <caption>Tableau des Oeuvres de Vente</caption>
                <TR>
                    <TH>Titre</TH>
                    <TH>Etat</TH>
                    <th>Prix</th>
                    <th>Propri&eacute;taire</th>
                    <th></th>
                </TR>

                <c:forEach items="${mesOeuvresVente}" var="item">
                    <tr>
                        <td>${item.titreOeuvrevente}</td>
                        <td>${item.etatOeuvrevente}</td>
                        <td>${item.prixOeuvrevente}</td>
                        <td>${item.proprietaire.nomProprietaire} ${item.proprietaire.prenomProprietaire}</td>
                        <td>
                            <a href="ControleurOeuvre?action=detailOeuvreVente&id=${item.idOeuvrevente}">
                                <button type="button" class="btn btn-info" aria-label="Left Align">
                                    <span class="fa fa-pencil" aria-hidden="true"></span>
                                </button>
                            </a>
                            <a href="ControleurOeuvre?action=deleteOeuvreVente&id=${item.idOeuvrevente}">
                                <button type="button" class="btn btn-danger" aria-label="Left Align">
                                    <span class="fa fa-times" aria-hidden="true"></span>
                                </button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>

<%@include file="../commun/footer.jsp"%>
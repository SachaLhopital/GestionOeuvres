<%@include file="../commun/header.jsp"%>

<div class="row">
    <div class="col-sm-2">
        <a href="/"><span class="fa fa-home fa-2x"></span></a>
    </div>
    <div class="col-sm-8"></div>
    <div class="col-sm-2">
        <a href="/proprietaires/ajouterProprietaire.htm">
            <button type="button" class="btn btn-primary" aria-label="Left Align">
                <span class="fa fa-user-plus" aria-hidden="true">&nbsp;Ajouter un Propri&eacute;taire</span>
            </button>
        </a>
    </div>
</div>

<div class="row text-center">
    <h3>Listing&nbsp;des Propri&eacute;taires</h3>
</div>

<%-- Listing Propriétaires --%>
<div class="row">
    <div class="col-md-12">
        <div class="panel-body">
            <table class="table table-hover table-striped table-bordered">
                <caption>Tableau des Propri&eacute;taires</caption>
                <TR>
                    <TH>Nom</TH>
                    <TH>Pr&eacute;nom</TH>
                    <th></th>
                </TR>

                <c:forEach items="${mesProprietaires}" var="item">
                    <tr>
                        <td>${item.nomProprietaire}</td>
                        <td>${item.prenomProprietaire}</td>
                        <td>
                            <a href="/oeuvres/ajouterOeuvre/pret/${item.idProprietaire}">
                                <button type="button" class="btn btn-primary" aria-label="Left Align">
                                    <span class="fa fa-plus" aria-hidden="true">&nbsp;Oeuvre de pr&ecirc;t</span>
                                </button>
                            </a>
                            <a href="/oeuvres/ajouterOeuvre/vente/${item.idProprietaire}">
                                <button type="button" class="btn btn-primary" aria-label="Left Align">
                                    <span class="fa fa-plus" aria-hidden="true">&nbsp;Oeuvre de vente</span>
                                </button>
                            </a>
                            <a href="/proprietaires/detailProprietaire/${item.idProprietaire}">
                                <button type="button" class="btn btn-info" aria-label="Left Align">
                                    <span class="fa fa-pencil" aria-hidden="true"></span>
                                </button>
                            </a>
                            <a href="/proprietaires/deleteProprietaire/${item.idProprietaire}">
                                <button type="button" class="btn btn-danger" aria-label="Left Align">
                                    <span class="fa fa-user-times" aria-hidden="true"></span>
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
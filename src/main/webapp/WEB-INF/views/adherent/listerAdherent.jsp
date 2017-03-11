<%@include file="../commun/header.jsp"%>

<div class="row">
    <div class="col-sm-2">
        <a href="../index.jsp"><span class="fa fa-home fa-2x"></span></a>
    </div>
    <div class="col-sm-8"></div>
    <div class="col-sm-2">
        <a href="ajouterAdherent.htm">
            <button type="button" class="btn btn-primary" aria-label="Left Align">
                <span class="fa fa-user-plus" aria-hidden="true">&nbsp;Ajouter un Adh&eacute;rent</span>
            </button>
        </a>
    </div>
</div>

<div class="row text-center">
    <h3>Listing&nbsp;des Adh&eacute;rents</h3>
</div>

<%-- Listing Adhérents --%>
<div class="row">
    <div class="col-md-12">
        <div class="panel-body">
            <table class="table table-hover table-striped table-bordered">
                <caption>Tableau des Adh&eacute;rents</caption>
                <TR>
                    <TH>Numero</TH>
                    <TH>Nom</TH>
                    <TH>Pr&eacute;nom</TH>
                    <TH>Ville</TH>
                    <th></th>
                </TR>

                <c:forEach items="${mesAdherents}" var="item">
                    <tr>
                        <td>${item.idAdherent}</td>
                        <td>${item.nomAdherent}</td>
                        <td>${item.prenomAdherent}</td>
                        <td>${item.villeAdherent}</td>
                        <td>
                            <a href="ajouterReservation.htm" id="${item.idAdherent}">
                                <button type="button" class="btn btn-info" aria-label="Left Align">
                                    <span class="fa fa-plus" aria-hidden="true">&nbsp;Ajouter une R&eacute;servation</span>
                                </button>
                            </a>
                            <a href="detailAdherent.htm" id="${item.idAdherent}">
                                <button type="button" class="btn btn-info" aria-label="Left Align">
                                    <span class="fa fa-pencil" aria-hidden="true"></span>
                                </button>
                            </a>
                            <a href="supprimerAdherent.htm" id="${item.idAdherent}">
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
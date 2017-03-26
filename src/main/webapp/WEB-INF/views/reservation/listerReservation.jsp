<%@include file="../commun/header.jsp"%>

<div class="row">
    <div class="col-sm-2">
        <a href="/"><span class="fa fa-home fa-2x"></span></a>
    </div>
    <div class="col-sm-8"></div>
</div>

<div class="row text-center">
    <h3>Listing des R&eacute;servations</h3>
</div>

<%-- Listing Réservations --%>
<div class="row">
    <div class="col-md-12">
        <div class="panel-body">
            <table class="table table-hover table-striped table-bordered">
                <caption>Tableau des R&eacute;servations</caption>
                <TR>
                    <TH>Date</TH>
                    <TH>Adh&eacute;rent</TH>
                    <TH>Oeuvre</TH>
                    <th></th>
                </TR>

                <c:forEach items="${mesReservations}" var="item">
                    <tr>
                        <td><fmt:formatDate value="${item.date}" pattern="dd-MM-yyyy"></fmt:formatDate></td>
                        <td>${item.adherent.nomAdherent} ${item.adherent.prenomAdherent}</td>
                        <td>${item.oeuvrevente.titreOeuvrevente}</td>
                        <td>
                            <a href="/reservations/deleteReservation/<fmt:formatDate value="${item.date}" pattern="yyyy-MM-dd"></fmt:formatDate>/${item.oeuvrevente.idOeuvrevente}">
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

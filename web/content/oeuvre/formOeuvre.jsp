<%@include file="../commun/header.jsp"%>

<div class="row">
    <div class="col-sm-2">
        <a href="ControleurOeuvre?action=listerOeuvre"><span class="fa fa-picture-o fa-2x"></span></a>
        <a href="ControleurProprietaire?action=listerProprietaire"><span class="fa fa-tasks fa-2x"></span></a>
    </div>
</div>
<br/><br/>
<div class="panel panel-primary">

    <div class="panel-heading">
        Formulaire oeuvre de ${type} pour ${proprietaire.nomProprietaire} ${proprietaire.prenomProprietaire}
    </div>

    <form name='identification' method="post" action="${actionSubmit}" onsubmit="return teste()">

        <input type="hidden" name="propId" value="${proprietaire.idProprietaire}">
        <input type="hidden" name="type" value="${type}">

        <div class="panel-body">

            <%-- PRET --%>
            <c:if test="${type == 'pret'}">
                <input type="hidden" name="oeuvreId" value="${oeuvre.idOeuvrepret}">

                <div class="form-group">
                    <label class="col-sm-3">Titre de l'oeuvre :</label>
                    <input type="text" name="txttitre" value="${oeuvre.titreOeuvrepret}" id ="titrepret">
                </div>
            </c:if>

            <%-- VENTE --%>
            <c:if test="${type == 'vente'}">
                <input type="hidden" name="oeuvreId" value="${oeuvre.idOeuvrevente}">

                <div class="form-group">
                    <label class="col-sm-3">Titre de l'oeuvre :</label>
                    <input type="text" name="txttitre" value="${oeuvre.titreOeuvrevente}" id ="titrevente">
                </div>

                <div class="form-group">
                    <label class="col-sm-3">Etat de l'oeuvre : </label>
                    <select name="txtetat">
                        <c:forEach items="${enumValues}" var="etat">
                            <option value="${etat}" ${oeuvre.etatOeuvrevente == etat ? 'selected' : ''}>&nbsp;${etat}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label class="col-sm-3">Prix de l'oeuvre : </label>
                    <input type="text" name="txtprix" value="${oeuvre.prixOeuvrevente}" id ="prix">
                </div>
            </c:if>
        </div>

        <div class="panel-footer">
            <button type="submit" name="bt" class="btn btn-primary">Enregistrer</button>
        </div>
    </form>
</div>

<SCRIPT language="Javascript" type="text/javascript">
    <script type="text/javascript" src="js/foncControle.js"></script>

<%@include file="/content/commun/footer.jsp"%>

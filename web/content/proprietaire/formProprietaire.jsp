<%@include file="../commun/header.jsp"%>

<div class="row">
    <div class="col-sm-2">
        <a href="ControleurProprietaire?action=listerProprietaire"><span class="fa fa-tasks fa-2x"></span></a>
    </div>
</div>
<br/><br/>
<div class="panel panel-primary">

    <div class="panel-heading">Ajout d'un propri&eacute;taire</div>

    <form name='identification' method="post" action="${actionSubmit}" onsubmit="return teste()">
        <input type="hidden" name="txtId" value="${proprietaire.idProprietaire}">
        <div class="panel-body">
            <div class="form-group">
                <label class="col-sm-3">Nom du propri&eacute;taire :</label>
                <input type="text" name="txtnom" value="${proprietaire.nomProprietaire}" id ="nom">
            </div>

            <div class="form-group">
                <label class="col-sm-3">Prenom du propri&eacute;taire : </label>
                <input type="text" name="txtprenom" value="${proprietaire.prenomProprietaire}" id ="prenom">
            </div>
        </div>

        <div class="panel-footer">
            <button type="submit" name="bt" class="btn btn-primary">Enregistrer</button>
        </div>
    </form>
</div>

<SCRIPT language="Javascript" type="text/javascript">
<script type="text/javascript" src="js/foncControle.js"></script>

<%@include file="/content/commun/footer.jsp"%>
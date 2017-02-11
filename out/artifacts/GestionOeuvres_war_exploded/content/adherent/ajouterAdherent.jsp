<%@include file="../commun/header.jsp"%>

<div class="panel panel-primary">

    <div class="panel-heading">Ajout d'un adh&eacute;rent</div>

    <form name='identification' method="post" action="ControleurAdherent?action=insererAdherent" onsubmit="return teste()">
        <div class="panel-body">
            <div class="form-group">
                <label class="col-sm-3">Nom de l'adherent :</label>
                <input type="text" name="txtnom" value="" id ="nom">
            </div>

            <div class="form-group">
                <label class="col-sm-3">Prenom de l'adherent : </label>
                <input type="text" name="txtprenom"  id ="prenom">
            </div>

            <div class="form-group">
                <label class="col-sm-3">Ville de l'adherent : </label>
                <input type="text" name="txtville" id ="ville">
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
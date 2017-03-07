<%@include file="../commun/header.jsp"%>

<div class="row">
    <div class="col-sm-2">
        <a href="ControleurAdherent?action=listerAdherent"><span class="fa fa-users fa-2x"></span></a>
    </div>
</div>
<br/>
<div class="panel panel-primary">

    <div class="panel-heading">Formulaire adh&eacute;rent</div>

    <form name='identification' method="post" action="${actionSubmit}" onsubmit="return teste()">
        <input type="hidden" name="txtId" value="${adherent.idAdherent}">
        <div class="panel-body">
            <div class="form-group">
                <label class="col-sm-3">Nom de l'adherent :</label>
                <input type="text" name="txtnom" value="${adherent.nomAdherent}" id ="nom">
            </div>

            <div class="form-group">
                <label class="col-sm-3">Prenom de l'adherent : </label>
                <input type="text" name="txtprenom" value="${adherent.prenomAdherent}" id ="prenom">
            </div>

            <div class="form-group">
                <label class="col-sm-3">Ville de l'adherent : </label>
                <input type="text" name="txtville" value="${adherent.villeAdherent}" id ="ville">
            </div>
        </div>

        <div class="panel-footer">
            <button type="submit" name="bt" class="btn btn-primary">Enregistrer</button>
        </div>
    </form>
</div>

<SCRIPT language="Javascript" type="text/javascript">
    function teste(){
        if(document.identification.txtnom.value == ""){
            alert("Veuillez entrer un nom d'adhérent.")
            return false;
        }

        if(document.identification.txtprenom.value == ""){
            alert("Veuillez entrer un prénom d'adhérent.");
            return false;
        }

        if(document.identification.txtville.value == ""){
            alert("Veuillez entrer une ville.");
            return false;
        }
        return true;
    }
</script>

<%@include file="/content/commun/footer.jsp"%>
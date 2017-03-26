<%@include file="../commun/header.jsp"%>

<div class="row">
    <div class="col-sm-2">
        <a href="/adherents/listerAdherent.htm"><span class="fa fa-users fa-2x"></span></a>
    </div>
</div>
<br/>
<div class="panel panel-primary">

    <div class="panel-heading">Formulaire de r&eacute;servation</div>

    <form name='identification' method="post" action="${actionSubmit}" onsubmit="return teste()">

        <input type="hidden" name="idAdherent" value="${adherent.idAdherent}">

        <div class="panel-body">
            <div class="form-group">
                <label class="col-sm-3">Quelle oeuvre souhaitez-vous emprunter ? </label>
                <select name="txtOeuvre">
                    <c:forEach items="${oeuvresvente}" var="oeuvre">
                        <option value="${oeuvre.idOeuvrevente}">&nbsp;${oeuvre.titreOeuvrevente}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="panel-footer">
            <button type="submit" name="bt" class="btn btn-primary">Enregistrer</button>
        </div>
    </form>
</div>

<SCRIPT language="Javascript" type="text/javascript">
    function teste() {
        if (document.identification.txtOeuvre.value == "") {
            alert("Impossible de reserver une oeuvre inexistante");
            return false;
        }
        return true;
    }
</script>

<%@include file="../commun/footer.jsp"%>

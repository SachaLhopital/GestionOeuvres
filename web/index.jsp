<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="refresh" content="0;URL=javascript:fermer();">

	<link rel="stylesheet" href="/ressources/bootstrap-3.3.7-dist/css/bootstrap.css">
	<link rel="stylesheet" href="/ressources/font-awesome-4.7.0/css/font-awesome.min.css">

	<title>Expo : Médiathèque De POLYTECH</title>
</head>


<script language="JavaScript">
	function fermer() {

	}
</script>

<body>
	<div class="text-center">
        <h1> Médiathèque de POLYTECH </h1>
	</div>
	<div class="text-center">
		<h2>Gestion de l'exposition 2016</h2>
	</div>
    <div class="col-md-2 col-sm-1 hidden-xs display-table-cell v-align box" id="navigation">
        <div class="logo">
            Logo ?
        </div>
        <ul class="nav nav-pills nav-stacked">
            <li class="active"><a href="#"><i class="fa fa-home" aria-hidden="true"></i>&nbsp;<span class="hidden-xs hidden-sm">Adhérents</span></a></li>
            <li><a href="#"><i class="fa fa-tasks" aria-hidden="true"></i>&nbsp;<span class="hidden-xs hidden-sm">Propriétaires d'oeuvres</span></a></li>
            <li><a href="#"><i class="fa fa-bar-chart" aria-hidden="true"></i>&nbsp;<span class="hidden-xs hidden-sm">Oeuvres</span></a></li>
            <li><a href="#"><i class="fa fa-bookmark-o" aria-hidden="true"></i>&nbsp;<span class="hidden-xs hidden-sm">Réservations</span></a></li>
        </ul>
    </div>
    &nbsp;
	<p align="left">
		<font color="#004080" face="Arial"><u>Ancien menu :</u></font>
	</p>
	<ul>
		<li><a href="Controleur?action=ajouterAdherent"><font
				face="Arial">Ajout Adhérent</font></a></li>
		<li><a href="Controleur?action=listerAdherent"><font
				face="Arial">lister les adhérents</font></a><font face="Arial"> </font></li>
		<li><a href="javascript:fermer()"><font face="Arial">Quitter</font></a><font
			face="Arial"> </font></li>
	</ul>
</body>
</html>
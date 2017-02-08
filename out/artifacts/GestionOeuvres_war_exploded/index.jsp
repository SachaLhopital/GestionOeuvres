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
	<p align="center"></p>
	<p align="center">
		<font face="Arial" size="4"><u>Médiathèque de POLYTECH </u></font>
	</p>
	<p align="center">
		<font color="#004080" face="Arial" size="4">Gestion de
			l'exposition 2016</font>
	</p>
	<p align="left">
		<font color="#004080" face="Arial"><u>Sélectionnez la
				fonctionnalité voulue:</u></font>
	</p>
	<ul>
		<li><a href="ControleurAdherent?action=ajouterAdherent"><font
				face="Arial">Ajout Adhérent</font></a></li>
		<li><a href="ControleurAdherent?action=listerAdherent"><font
				face="Arial">lister les adhérents</font></a><font face="Arial"> </font></li>
		<li><a href="javascript:fermer()"><font face="Arial">Quitter</font></a><font
			face="Arial"> </font></li>
	</ul>
</body>
</html>
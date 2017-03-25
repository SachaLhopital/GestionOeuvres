<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="refresh" content="0;URL=javascript:fermer();">

    <link rel="stylesheet" href="../../../resources/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" href="../../../resources/font-awesome-4.7.0/css/font-awesome.min.css">

    <title>Expo : Médiathèque De POLYTECH</title>
</head>

<body>

    <%-- Imports JSP --%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

    <%-- Site Header --%>
    <div class="row">
        <div class="col-md-2">
            <img src="../../../resources/images/logo.jpg" alt="Logo Polytech">
        </div>
        <div class="col-md-10 text-center">
            <h1>M&eacute;diath&egrave;que de POLYTECH</h1>
            <h2>Gestion de l'exposition 2016</h2>
        </div>
    </div>
    <hr>

    <%-- Left Menu --%>
    <div class="row">
        <div class="col-md-2 col-sm-1 hidden-xs display-table-cell v-align box" id="navigation">
            <ul class="nav nav-pills nav-stacked">
                <li>
                    <a href="/">
                        <i class="fa fa-home" aria-hidden="true"></i>
                        &nbsp;
                        <span class="hidden-xs hidden-sm">Accueil</span>
                    </a>
                </li>
                <li>
                    <a href="/adherents/listerAdherent.htm">
                        <i class="fa fa-users" aria-hidden="true"></i>
                        &nbsp;
                        <span class="hidden-xs hidden-sm">Adh&eacute;rents</span>
                    </a>
                </li>
                <li>
                    <a href="/proprietaires/listerProprietaire.htm">
                        <i class="fa fa-tasks" aria-hidden="true"></i>
                        &nbsp;
                        <span class="hidden-xs hidden-sm">Propri&eacute;taires</span>
                    </a>
                </li>
                <li>
                    <a href="/oeuvres/listerOeuvre.htm">
                        <i class="fa fa-picture-o" aria-hidden="true"></i>
                        &nbsp;
                        <span class="hidden-xs hidden-sm">Oeuvres</span>
                    </a>
                </li>
                <li>
                    <a href="/reservations/listerReservation.htm">
                        <i class="fa fa-bookmark-o" aria-hidden="true"></i>
                        &nbsp;
                        <span class="hidden-xs hidden-sm">R&eacute;servations</span>
                    </a>
                </li>
            </ul>
        </div>
        <div class="col-md-10 col-sm-1 container">
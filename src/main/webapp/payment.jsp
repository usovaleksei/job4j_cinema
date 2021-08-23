<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.cinema.store.PsqlStore" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <title>Ticket payment</title>
</head>
<body>
<%
    int id = Integer.parseInt(request.getParameter("sessionId"));
    String filmName = PsqlStore.instOf().findFilmById(id).getName();
%>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<script src="/job4j_cinema/js/payment.js"></script>
<form>
    <input type="hidden" id="sessionId" name="sessionId" value="">
    <input type="hidden" id="rowId" name="rowId" value="">
    <input type="hidden" id="cellId" name="cellId" value="">
</form>
<div class="container">
    <div class="row pt-3">
        <h5>
            <%="Название фильма: " + filmName%>
        </h5>
    </div>
    <div class="row pt-3">
        <h5 id="head3">
        </h5>
    </div>
    <div class="row">
        <form>
            <div class="form-group">
                <label for="username">ФИО</label>
                <input type="text" class="form-control" id="username" placeholder="ФИО">
            </div>
            <div class="form-group">
                <label for="phone">Номер телефона</label>
                <input type="text" class="form-control" id="phone" placeholder="Номер телефона">
            </div>
           <%-- <div class="form-group">
                <label for="email">Электронная почта</label>
                <input type="text" class="form-control" id="email" placeholder="Электронная почта">
            </div>--%>
            <button type="button" class="btn btn-success" onclick="btnClick();">Оплатить</button>
        </form>
    </div>
</div>
</body>
</html>
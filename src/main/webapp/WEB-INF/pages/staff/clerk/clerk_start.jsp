<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Ticket Office / Search screening </title>

    <!-- Bootstrap -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.3/css/bootstrap-select.min.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- Bootstrap to work locally -->
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.3/js/bootstrap-select.min.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Movie7Theater</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="/clerk/search_page">Search screening</a></li>
            <li><a href="/clerk/booking_page">Booking</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <p class="navbar-text">You are logged in as <b class="text-danger">${login}</b></p>
            <li><a href="/clerk/clerk_logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
        </ul>
    </div>
</nav>

<br/>
<div class="container">

    <form action="/clerk/get_screenings" method="post">
        <div class="row">
            <div class="col-md-offset-3 col-md-1"><i>Up to 1</i></div>
            <div class="col-md-4">
                <select name="address" id="address" class="selectpicker" required>
                    <option value="-1">All Addresses</option>
                    <c:forEach var="address" items="${addressMap}">
                        <option value="${address.key}">${address.value} </option>
                    </c:forEach>
                </select>
                <i>Address</i>
            </div>
        </div>
        <br/>
        <br/>
        <div class="row">
            <div class="col-md-offset-3 col-md-1"><i>Up to 3</i></div>
            <div class="col-md-4">
                <select name="date" id="date" class="selectpicker" required multiple data-max-options="3">
                    <option value="-1">All Dates</option>
                    <c:forEach var="date" items="${dateList}">
                        <fmt:formatDate var="yyyyMMdd" value="${date}" pattern="yyyy-MM-dd" />
                        <fmt:formatDate var="EEEMMMdyyyy" value="${date}" pattern="EEE, MMM d (yyyy)" />
                        <option value="${yyyyMMdd}">${EEEMMMdyyyy}</option>
                    </c:forEach>
                </select>
                <i>Date</i>
            </div>
        </div>
        <br/>
        <br/>
        <div class="row">
            <div class="col-md-offset-3 col-md-1"><i>Up to 1</i></div>
            <div class="col-md-4">
                <select name="showtime" id="showtime" class="selectpicker" required>
                    <option value="-1">All Showtimes</option>
                    <c:forEach var="showtime" items="${clerk_showtimeRangeMap}">
                        <option value="${showtime.key}">${showtime.value}</option>
                    </c:forEach>
                </select>
                <i>Showtime</i>
            </div>
        </div>
        <br/>
        <br/>
        <div class="row">
            <div class="col-md-offset-3 col-md-1"><i>Up to 3</i></div>
            <div class="col-md-4">
                <select name="movie" id="movie" class="selectpicker" required multiple data-max-options="3">
                    <option value="-1">All Movies</option>
                    <c:forEach var="movie" items="${movieMap}">
                        <option value="${movie.key}">${movie.value} </option>
                    </c:forEach>
                </select>
                <i>Movie</i>
            </div>
        </div>
        <br/>
        <br/>
        <div class="row">
            <div class="col-md-offset-4 col-md-4">
                <button class="btn btn-primary">Search Movie Screenings</button>
            </div>
        </div>
    </form>
</div>

<br/>
<br/>
<br/>
<br/>

</body>
</html>

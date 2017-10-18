<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Ticket Office / Tickets selection</title>

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

    <style type="text/css">
        .btn span.glyphicon {
            opacity: 0;
        }
        .btn.active.btn-success span.glyphicon {
            opacity: 1;
        }
    </style>

</head>
<body>

<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Movie7Theater</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="/clerk/search_page">Search screening</a></li>
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
    <br>
    <h2 class="text-center btn-primary">${message}</h2>

    <table class="table table-condensed">
        <tr>
            <td>Movie title:</td>
            <td>${screeningTicketsInfo.movieTitle}</td>
        </tr>
        <tr>
            <td>Address:</td>
            <td>${screeningTicketsInfo.address}</td>
        </tr>
        <tr>
            <td>Date:</td>
            <td>${screeningTicketsInfo.date}</td>
        </tr>
        <tr>
            <td>Time:</td>
            <td>${screeningTicketsInfo.time}</td>
        </tr>
        <tr>
            <td><b>Price:</b></td>
            <td><b>$${screeningTicketsInfo.price}</b></td>
        </tr>
    </table>
</div>

<div class="container">
    <div class="row">
        <div class="col-md-offset-2 col-md-2">
            <button class="btn btn-success" style="width: 30px; height: 30px;" disabled></button> - available ticket
        </div>
        <div class="col-md-2">
            <button class="btn btn-danger" style="width: 30px; height: 30px;" disabled></button> - sold ticket
        </div>
        <div class="col-md-2">
            <button class="btn btn-warning" style="width: 30px; height: 30px;" disabled></button> - booked ticket
        </div>
        <div class="col-md-2">
            <button class="btn btn-default" style="width: 30px; height: 30px;" disabled></button> - non-available
        </div>
    </div>
</div>

<br/>

<div class="container-fluid">
    <form action="/clerk/select_tickets" method="POST">
        <input type="hidden" name="screening_id" value="${screeningTicketsInfo.screeningId}"/>
        <c:set var="maxRow" value="${screeningTicketsInfo.maxRow}" scope="request"></c:set>
        <c:set var="maxColumn" value="${screeningTicketsInfo.maxColumn}" scope="request"></c:set>
        <c:set var="ticketList" value="${screeningTicketsInfo.ticketList}" scope="request"></c:set>
        <c:set var="iter" value="${ticketList.iterator()}" scope="request"></c:set>
        <div class="text-center">
            <c:forEach begin="1" end="${maxRow}" varStatus="row">
                <div class="btn-group" data-toggle="buttons" style="margin-bottom: 2px;">
                    <c:forEach begin="1" end="${maxColumn}" varStatus="column">
                        <c:if test="${iter.hasNext()}"> <c:set var="ticket" value="${iter.next()}" scope="request"/> </c:if>
                        <label class="btn ${ticket.bootstrapClass()} ${ticket.disabled()}" style="margin: auto 0; padding: 0; width: 40px;">
                            <input type="checkbox" autocomplete="off" name="ticket" value="${ticket.id}" ${ticket.disabled()} >
                            <span class="glyphicon glyphicon-ok"></span><br/>
                            <small>${row.count}-${column.count}</small>
                        </label>
                        <c:remove var="ticket" scope="request"></c:remove>
                    </c:forEach>
                </div>
                <br/>
            </c:forEach>
        </div>
        <c:remove var="maxRow" scope="request"></c:remove>
        <c:remove var="maxColumn" scope="request"></c:remove>
        <c:remove var="ticketsList" scope="request"></c:remove>
        <c:remove var="iter" scope="request"></c:remove>
        <br/>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <button class="btn btn-primary btn-block">Select tickets</button>
                </div>
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

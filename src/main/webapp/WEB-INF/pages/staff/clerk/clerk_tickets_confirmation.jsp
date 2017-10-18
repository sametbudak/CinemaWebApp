<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Ticket Office / Tickets confirmation</title>

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
    <h3 class="text-center">${error}</h3>
    <h3 class="text-center">Tickets are waiting for payment!</h3>
    <h3 class="text-center bg-primary">Total amount is $${amount}.</h3>
    <br/>
    <br/>
    <form action="/clerk/pay_tickets" method="post">
        <c:forEach var="ticketId" items="${ticketIdList}">
            <input type="hidden" name="ticket" value="${ticketId}">
        </c:forEach>
        <div class="row">
            <div class="col-md-offset-4 col-md-4">
                <button class="btn btn-primary btn-block">Confirm Payment</button>
            </div>
        </div>
    </form>
    <br/>
    <form action="/clerk/cancel_tickets" method="post">
        <c:forEach var="ticketId" items="${ticketIdList}">
            <input type="hidden" name="ticket" value="${ticketId}">
        </c:forEach>
        <input type="hidden" name="screening_id" value="${screening_id}">
        <div class="row">
            <div class="col-md-offset-4 col-md-4">
                <button class="btn btn-danger btn-block">Cancel</button>
            </div>
        </div>
    </form>
    <br/>
    <c:forEach items="${pendingTicketInfoList}" var="ticket" varStatus="loop">
        <table class="table table-bordered">
            <tr>
                <td>Price:</td>
                <td>$${ticket.price}</td>
            </tr>
            <tr>
                <td>Address:</td>
                <td>${ticket.address}</td>
            </tr>
            <tr>
                <td>Movie:</td>
                <td>${ticket.movieTitle}</td>
            </tr>
            <tr>
                <td>Date:</td>
                <td>${ticket.movieDate}</td>
            </tr>
            <tr>
                <td>Time:</td>
                <td>${ticket.movieTime}</td>
            </tr>
            <tr>
                <td>Duration:</td>
                <td>${ticket.duration}</td>
            </tr>
            <tr>
                <td>Place:</td>
                <td>${ticket.place}</td>
            </tr>
        </table>
        <br/>
    </c:forEach>
</div>

<br/>
<br/>
<br/>
<br/>

</body>
</html>
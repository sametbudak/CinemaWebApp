<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Ticket Office / Find Booking </title>

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
            <li class="active"><a href="/clerk/booking_page">Booking</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <p class="navbar-text">You are logged in as <b class="text-danger">${login}</b></p>
            <li><a href="/clerk/clerk_logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
        </ul>
    </div>
</nav>

<br/>
<br/>
<br/>

<div class="container">

    <c:choose>
        <c:when test="${isFound ne null and isFound eq true}">
            <form action="/clerk/pay_booked_tickets" method="post">
                <h3 class="text-center bg-primary">Total amount is $${amount}.</h3>
                <c:forEach items="${ticketDtoList}" var="ticket" varStatus="loop">
                    <c:if test="${loop.count eq 1}">
                        <h4>Ticket's booking for ${ticket.email}, code ${ticket.code}:</h4><br>
                        <input type="hidden" name="email" value="${ticket.email}">
                    </c:if>
                    <br/>
                    <table class="table table-bordered">
                        <caption>Booked ${ticket.bookedAtTime}</caption>
                        <tr>
                            <td>Price:</td>
                            <td>$${ticket.price}</td>
                        </tr>
                        <tr>
                            <td>Address:</td>
                            <td>${ticket.theaterName}, ${ticket.cityAndDistrict}, ${ticket.streetAndNumber}</td>
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
                    <input type="hidden" name="ticket" value="${ticket.ticketId}">
                </c:forEach>
                <br/>
                <br/>
                <div class="row">
                <div class="col-md-offset-4 col-md-4">
                    <button class="btn btn-primary btn-block">Confirm payment</button>
                </div>
                </div>
            </form>

        </c:when>
        <c:otherwise>
            <form action="/clerk/find_booked_tickets" method="post">
                <c:if test="${isFound ne null and isFound eq false}">
                    <div class="form-group">
                        <h4 class="bg-danger text-center">No booked tickets for email ${email}</h4>
                    </div>
                </c:if>
                <div class="form-group">
                    <label class="control-label col-md-4 text-right" for="email">Email</label>
                    <div class="col-md-4">
                        <input type="email" class="form-control" name="email" id="email" placeholder="email" required>
                    </div>
                </div>
                <br/>
                <br/>
                <div class="form-group">
                    <div class="col-md-offset-4 col-md-4">
                        <button class="btn btn-primary btn-block">Get tickets</button>
                    </div>
                </div>
            </form>

        </c:otherwise>
    </c:choose>

</div>

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>

</body>
</html>

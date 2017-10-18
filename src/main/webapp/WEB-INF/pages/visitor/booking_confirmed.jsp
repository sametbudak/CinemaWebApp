<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title> Booking confirmation </title>

    <!-- Bootstrap -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<c:out value="/static/css/cinema-v.css" />" rel="stylesheet" type="text/css" >

    <!-- Bootstrap to work locally -->
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.3/js/bootstrap-select.min.js"></script>

</head>
<body>

<!-- Navigation -->
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Movie7Theater</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li><a href="/">Home</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/showtimes" class="btn btn-default">Showtimes</a></li>
            </ul>
        </div>
    </div>
</nav>

<div id="content">

    <div class="container">
        <br>
        <c:choose>
            <c:when test="${codeConfirmationErrorMsg ne null}">
                <h2 class="text-center bg-danger" style="color: red">${codeConfirmationErrorMsg}</h2>
            </c:when>
            <c:otherwise>
                <h3 class="text-center">Your booking is confirmed!</h3>
                <h3 class="text-center bg-primary">Total amount is $${amount}. Booked tickets must be redeemed within 24 hours!</h3>
            </c:otherwise>
        </c:choose>

        <hr/>
        <br/>
        <c:forEach items="${bookedTicketInfoList}" var="ticket" varStatus="loop">
            <c:if test="${loop.count eq 1}"><h4>Ticket's booking for ${ticket.email}, code ${ticket.code}:</h4><br> </c:if>
            <table class="table table-bordered">
                <caption>Booked ${ticket.bookingTime}</caption>
                <tr>
                    <th>Booking code:</th>
                    <th>${ticket.bookingId}</th>
                </tr>
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
        </c:forEach>
    </div>

</div>
<div class="container-fluid" id="footer">
    <!-- Footer -->
    <footer>
        <div class="container">
            <br/>
            <div class="row" id="contacts">
                <div class="col-md-12">
                    <p>Phone: ${phone}</p>
                    <p>Skype: ${skype}</p>
                    <p>Email: ${email}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <jsp:useBean id="currentDate" class="java.util.Date" />
                    <fmt:formatDate var="year" value="${currentDate}" pattern="yyyy" />
                    <p class="text-center">Copyright &copy; Your Website ${year}</p>
                </div>
            </div>
        </div>
        <!-- /.row -->
    </footer>
</div>
</body>
</html>

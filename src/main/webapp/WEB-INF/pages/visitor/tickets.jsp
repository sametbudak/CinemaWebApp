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
    <title> Tickets </title>

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

    <script>
        $(document).ready(function() {
            $("form").submit(function(e){
                var count = 0;
                $(".btn.active.btn-success").each(function(){
                    count += 1;
                });
                if(count >3 || count <1) {
                    alert("You have selected " + count +" ticket(s).\n Only 1-3 tickets are allowed to be selected for booking!");
                    e.preventDefault(e);
                }
            });
        });
    </script>

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
        <div class="row">
            <div class="col-md-12">
                <h1>Booking tickets </h1>
                <c:if test="${ticketsErrorMsg ne null}">
                    <h2 class="text-center bg-danger" style="color: red">${ticketsErrorMsg}</h2>
                </c:if>
                <br/>
                <h3>Booked tickets must be redeemed within 24 hours!</h3>
            </div>
        </div>
    </div>

    <div class="container">
        <table class="table table-condensed">
            <tr>
                <td>Movie title:</td>
                <td>${screeningTicketsInfo.movieTitle}</td>
            </tr>
            <tr>
                <td>Movie duration:</td>
                <td>${screeningTicketsInfo.hours}HR ${screeningTicketsInfo.minutes}MIN </td>
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
        <br/>
        <div class="row bg-danger">
            <div class="col-md-12">
                <h4 class="text-center">Maximum 3 tickets allowed for booking!</h4>
            </div>
        </div>
        <br/>
    </div>
    <div class="container-fluid">
        <form action="/book_tickets" method="POST">
            <input type="hidden" name="screening_id" value="${screeningTicketsInfo.screeningId}"/>
            <c:set var="maxRow" value="${screeningTicketsInfo.maxRow}" scope="request"></c:set>
            <c:set var="maxColumn" value="${screeningTicketsInfo.maxColumn}" scope="request"></c:set>
            <c:set var="ticketsList" value="${screeningTicketsInfo.ticketList}" scope="request"></c:set>
            <c:set var="iter" value="${ticketsList.iterator()}" scope="request"></c:set>
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
            <div class="container">
                <div class="form-group">
                    <label class="control-label" for="email">Type in e-mail to send you verification code:</label>
                    <input type="email" name="email" class="form-control" id="email" required>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <button class="btn btn-info btn-block">Book tickets</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <br/>
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

    </footer>
</div>

</body>
</html>

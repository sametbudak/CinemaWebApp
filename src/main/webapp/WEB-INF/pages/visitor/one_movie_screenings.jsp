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
    <title> Showtimes </title>

    <!-- Bootstrap -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<c:out value="/static/css/cinema-v.css" />" rel="stylesheet" type="text/css" >
    <!-- Custom Select CSS -->
    <link href="<c:out value="/static/css/custom-select-v.css" />" rel="stylesheet" type="text/css" >

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
        // example from http://jsfiddle.net/N7Xpb/1/
        $(document).ready(function() {

            var optarray_time =$("#time").children('option').map(function () {
                return {
                    "id":this.id,
                    "option" : "<option value='" +this.value + "' id='" + this.id + "'>" + this.text + "</option>"
                }
            });

            var none_option = "<option>None Showtimes</option>";

            $("#address, #date").change(function() {
                //alert("inside of address change");
                $("#time").children('option').remove();

                var addressId = $("#address option:selected").attr("id");
                addressId = addressId.replace("address", "");

                var dateId = $("#date option:selected").attr("id");
                dateId = dateId.replace("date", "");

                var addoptarr_time = [];
                for(i=0; i<optarray_time.length; i++) {
                    var optionId = optarray_time[i].id;
                    if(optionId.indexOf(addressId+dateId)>-1) {
                        addoptarr_time.push(optarray_time[i].option);
                    }
                }
                if(addoptarr_time.length == 0) {
                    $("#time").html(none_option);
                } else {
                    $("#time").html(addoptarr_time.join(''));
                }
            }).change();

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

<!-- Page Content -->
<div id="content">
    <div class="container">

        <div class="row">
            <div class="col-md-12">
                <h1 class="text-center">Movie Showtimes</h1>
            </div>
        </div>

        <br/>

        <form name="booking_${showtimes.movieId}" action="/list_tickets" method="POST">
            <input type="hidden" name="movie_id" value="${showtimes.movieId}" />
            <input type="hidden" name="movie_title" value="${showtimes.movieTitle}" />
            <h3>${showtimes.movieTitle}, <small>${showtimes.hours()}HR ${showtimes.minutes()}MIN</small>
                <a href="/movie/${showtimes.movieId}" class="btn btn-lg btn-default">See More</a>
            </h3>
            <c:choose>
                <c:when test="${nonAvailable}"><h4 class="text-center bg-danger">No Showtimes available!</h4></c:when>
                <c:otherwise><h4>Select from left to right:</h4></c:otherwise>
            </c:choose>

            <div class="row">
                <div class="col-md-4">
                    <span class="glyphicon glyphicon-list"></span>
                    <label>
                        <select name="address_id" id="address" class="custom-select">
                            <option></option>
                            <c:forEach items="${showtimes.addressIdValueMapperList}" var="address">
                                <option id="${address.addressHtmlId}_address">${address.addressHtmlValue}</option>
                            </c:forEach>
                        </select>
                    </label>
                    <i>Address</i>
                </div>
                <div class="col-md-4">
                    <span class="glyphicon glyphicon-calendar"></span>
                    <label>
                        <select name="date" id="date" class="custom-select">
                            <option></option>
                            <c:forEach items="${showtimes.dateIdValueMapperList}" var="date">
                                <option id="${date.dateHtmlId}_date">${date.dateHtmlValue}</option>
                            </c:forEach>
                        </select>
                    </label>
                    <i>Date</i>
                </div>
                <div class="col-md-4">
                    <span class="glyphicon glyphicon-time"></span>
                    <label>
                        <select name="screening_id" id="time" class="custom-select">
                            <c:forEach items="${showtimes.screeningIdKeyValueMapperList}" var="time">
                                <option id="${time.screeningHtmlId}_time" value="${time.screeningHtmlKey}">${time.screeningHtmlValue}</option>
                            </c:forEach>
                        </select>
                    </label>
                    <i>Time</i>
                </div>
            </div>
             <br/>
            <div class="row">
                <div class="col-md-offset-4 col-md-4">
                    <button class="btn btn-info btn-block" <c:if test="${nonAvailable}">disabled</c:if> > See/book available tickets</button>
                </div>
            </div>

        </form>

        <hr>
    </div>
</div>

<div class="container-fluid" id="footer">
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

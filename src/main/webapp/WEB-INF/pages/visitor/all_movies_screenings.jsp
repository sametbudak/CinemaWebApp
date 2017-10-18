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
    <title> Screenings search </title>

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

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.3/css/bootstrap-select.min.css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.3/js/bootstrap-select.min.js"></script>

    <script>

        // example from http://jsfiddle.net/N7Xpb/1/
        $(document).ready(function() {

            var optarray_time =$("#time").children('option').map(function () {
                return {
                    "id":this.id,
                    "option" : "<option value='" +this.value + "' id='" + this.id + "'>" + this.text + "</option>"
                }
            });

            var none_option = "<option>None showtimes</option>";

            $("#address").change(function() {
                //alert("inside of address change");
                $("#time").children('option').remove();

                var addressId = $("#address option:selected").attr("id");
                addressId = addressId.replace("address", "");

                var addoptarr_time = [];
                for(i=0; i<optarray_time.length; i++) {
                    var optionId = optarray_time[i].id;
                    if(optionId.indexOf(addressId)>-1) {
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
                <li class="active"><a href="/showtimes" class="btn btn-default">Showtimes</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Page Content -->
<div id="content">
<div class="container">
    <div>
    <form name="booking_filter" action="/filter_showtimes" method="POST">
        <div class="row bg-primary">
            <br/>
            <div class="col-md-3">
                <button class="btn btn-info btn-block"><span class="glyphicon glyphicon-filter"></span> Filter By: </button>
            </div>
            <div class="col-md-3">
                <span class="glyphicon glyphicon-list"></span>
                <select name="address_id" class="selectpicker">
                    <option value="-1">All Addresses</option>
                    <c:forEach items="${addressesMap}" var="address">
                        <option value="${address.key}" <c:if test="${selected_address eq address.value}"> selected </c:if> >${address.value}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-3">
                <span class="glyphicon glyphicon-calendar"></span>
                <select name="date" class="selectpicker">
                    <c:set var="count" value="1" scope="request"/>
                    <c:forEach items="${dateList}" var="date">
                        <fmt:formatDate var="yyyyMMdd" value="${date}" pattern="yyyy-MM-dd" />
                        <fmt:formatDate var="EEEMMMd" value="${date}" pattern="EEE, MMM d" />
                        <option value="${yyyyMMdd}" <c:if test="${selected_date eq yyyyMMdd}"> selected </c:if> >
                            <c:if test="${count eq 1}"> Today,<c:set var="count" value="-1" scope="request"/></c:if> ${EEEMMMd}
                        </option>
                    </c:forEach>
                    <c:remove var="count" scope="request"/>
                </select>
            </div>
            <div class="col-md-3">
                <span class="glyphicon glyphicon-film"></span>
                <select name="movie_id" class="selectpicker">
                    <option value="-1">All Movies</option>
                    <c:forEach items="${moviesMap}" var="movie">
                        <option value="${movie.key}" <c:if test="${selected_movie eq movie.value}"> selected </c:if> >${movie.value}</option>
                    </c:forEach>
                </select>
            </div>
            <br/>
            <br/>
            <br/>
         </div>
    </form>

    </div>
    <br/>
    <div class="row">
        <div class="col-md-2">
            <h4> Movies filtered by</h4>
        </div>
        <div class="col-md-10">
            <table>
                <tr>
                    <td><p>Address: </p></td>
                    <td>
                        <p>
                        <c:choose>
                            <c:when test="${selected_address eq null}">All addresses</c:when>
                            <c:otherwise>${selected_address}</c:otherwise>
                        </c:choose>
                        </p>
                    </td>
                </tr>
                <tr>
                    <td><p>Date: </p></td>
                    <td><p>${selected_date}</p></td>
                </tr>
                <tr>
                    <td><p>Movie: </p></td>
                    <td>
                        <p>
                            <c:choose>
                                <c:when test="${selected_movie eq null}">All movies</c:when>
                                <c:otherwise>${selected_movie}</c:otherwise>
                            </c:choose>
                        </p>
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <br/>
    <div id="movie_showtime_selection">
        <c:if test="${showtimesList == null or showtimesList.isEmpty()}">
            <h4>None has been found.</h4>
        </c:if>
        <c:forEach items="${showtimesList}" var="showtimes">
            <div class="well">
            <form name="booking_${showtimes.movieId}" action="/list_tickets" method="POST">
                <h3>${showtimes.movieTitle}, <small>${showtimes.hours()} HR ${showtimes.minutes()} MIN</small>
                    <a href="/movie/${showtimes.movieId}" class="btn btn-lg btn-default">See More</a>
                </h3>
                <fmt:formatDate var="MMMdyyyyEEEE" value="${showtimes.date}" pattern="MMM d, yyyy (EEEE)" />
                <h4>Date: ${MMMdyyyyEEEE}</h4>
                <br/>
                <p>Select the address, then time.</p>
                <div class="row">
                    <div class="col-md-4">
                        <span class="glyphicon glyphicon-list"></span>
                        <label>
                            <select id="address" name="address" class="custom-select">
                                <c:forEach items="${showtimes.addressIdValueMapperList}" var="address">
                                    <option id="${address.addressHtmlId}_address" >${address.addressHtmlValue}</option>
                                </c:forEach>
                            </select>
                        </label>
                        <i>Address</i>
                    </div>
                    <div class="col-md-4">
                        <span class="glyphicon glyphicon-time"></span>
                        <label>
                            <select id="time" name="screening_id" class="custom-select">
                                <c:forEach items="${showtimes.screeningIdKeyValueMapperList}" var="screening">
                                    <option id="${screening.screeningHtmlId}_time" value="${screening.screeningHtmlKey}">${screening.screeningHtmlValue}</option>
                                </c:forEach>
                            </select>
                        </label>
                        <i>Time</i>
                    </div>
                    <div class="col-md-4">
                        <button class="btn btn-info btn-block">See/book available tickets</button>
                    </div>
                </div>
                <br/>
            </form>
            </div>
        </c:forEach>
    </div>
</div>
<!-- /#Page Content  -->
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
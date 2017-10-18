<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Ticket Office / Screenings </title>

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

    <!-- https://codepen.io/wizly/pen/BlKxo -->
    <style>
        .exTab1 .nav-tabs > li.active > a, .exTab1 .tab-content {
            /*background-color: #f1f1f1;*/
        }
        .table-borderless td, .table-borderless th {
            border: none !important;
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

    <!--
    <div class="text-center">
        <table class="table table-borderless">
            <caption>Search criteria:</caption>
            <tr>
                <td>Address:</td>
                <td></td>
            </tr>
            <tr>
                <td>Date:</td>
                <td></td>
            </tr>
            <tr>
                <td>Showtime:</td>
                <td></td>
            </tr>
            <tr>
                <td>Movie:</td>
                <td></td>
            </tr>
        </table>
    </div>
    -->
    <c:if test="${screeningsDtoList eq null or screeningsDtoList.isEmpty()}">
        <h3 class="text-danger">None movie screenings satisfy search criteria!</h3>
    </c:if>

    <c:forEach var="screening" items="${screeningsDtoList}">
        <div class="container">
            <div>
                <h2 style="color: dodgerblue"> <b>${screening.movie.title}</b> </h2>
            </div>
            <div class="exTab1">
                <ul class="nav nav-tabs">
                    <li class="active">
                        <a  href="#1_${screening.movie.id}" data-toggle="tab">Showtime selection</a>
                    </li>
                    <li><a href="#2_${screening.movie.id}" data-toggle="tab">Movie description</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane active" id="1_${screening.movie.id}">
                        <c:if test="${screening.addressShowtimeList eq null or screening.addressShowtimeList.isEmpty()}">
                            <h4 class="text-danger">None!</h4>
                        </c:if>
                        <c:forEach var="address" items="${screening.addressShowtimeList}">
                            <br/>
                            <div class="container">
                                <h4> <b> ADDRESS: </b> ${address.addressStr}
                                <c:if test="${address.screeningDateList eq null or address.screeningDateList.isEmpty()}">
                                    <span class="bg-danger"> None Screenings!</span>
                                </c:if>
                                </h4>
                            </div>
                            <hr/>

                            <c:forEach var="date" items="${address.screeningDateList}">

                                    <div class="row">
                                        <div class="col-md-2 col-lg-offset-1">
                                            <h4><b><i>${date.dateStr}:</i></b></h4>
                                        </div>
                                        <c:choose>

                                            <c:when test="${date.screeningTimeList.isEmpty()}">
                                        <div class="col-md-1">
                                            <p class="text-danger">NONE</p>
                                        </div>
                                            </c:when>

                                            <c:otherwise>
                                        <form action="/clerk/list_tickets" method="post" name="form_${screening.movie.id}_${address.addressId}_${date.dateStr}">
                                                <div class="col-md-1">
                                                    <c:forEach var="showtime" items="${date.screeningTimeList}">
                                                        <div class="radio">
                                                            <label>
                                                                <input type="radio" name="screening_id" value="${showtime.screeningId}">
                                                                    ${showtime.getTimeAsHHmm()}
                                                            </label>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                                <div class="col-md-2">
                                                    <button type="submit" class="btn btn-primary">Show tickets</button>
                                                </div>
                                        </form>
                                            </c:otherwise>

                                        </c:choose>
                                    </div>


                                <hr/>
                            </c:forEach>
                        </c:forEach>
                    </div>

                    <div class="tab-pane" id="2_${screening.movie.id}">
                        <br>
                        <table class="table table-borderless">
                            <tr>
                                <td><strong>Title:</strong> </td>
                                <td>${screening.movie.title} </td>
                            </tr>
                            <tr>
                                <td><strong>Year:</strong> </td>
                                <td>${screening.movie.releaseYear} </td>
                            </tr>
                            <tr>
                                <td><strong>Genre:</strong> </td>
                                <td>
                                    <c:forEach items="${screening.movie.genresListToEnumList()}" var="genre" varStatus="loop" >
                                        <c:out value="${genre}"/><c:if test="${not loop.last}"><span>,</span></c:if>
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr>
                                <td><strong>Country:</strong> </td>
                                <td>${screening.movie.country} </td>
                            </tr>
                            <tr>
                                <td><strong>Duration:</strong> </td>
                                <td>${screening.movie.minutes} min</td>
                            </tr>
                            <tr>
                                <td><strong>Directed By:</strong> </td>
                                <td>${screening.movie.directedBy}</td>
                            </tr>
                            <tr>
                                <td><strong>Screenplay:</strong> </td>
                                <td>${screening.movie.screenplay}</td>
                            </tr>
                            <tr>
                                <td><strong>Cast:</strong> </td>
                                <td>${screening.movie.cast}</td>
                            </tr>
                            <tr>
                                <td><strong>Description:</strong> </td>
                                <td>${screening.movie.description}</td>
                            </tr>
                        </table>
                    </div>
                </div>

            </div>
        </div>
    </c:forEach>

</div>

</body>
</html>


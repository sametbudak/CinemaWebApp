<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title> Admin / Add Movie </title>

    <!-- Bootstrap -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <!-- Bootstrap Core CSS -->
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Movie7Theater</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Movie
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="/admin/get_add_movie_page">Add</a></li>
                    <li><a href="/admin/list_movies/0">Edit/Delete</a></li>
                </ul>
            </li>
            <li class="dropdown active">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Screening Scheduler
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="/admin/get_scheduler/0">Current week(only view)</a></li>
                    <li><a href="/admin/get_scheduler/1">Coming week 1</a></li>
                    <li><a href="/admin/get_scheduler/2" class="btn btn-link disabled">Coming week 2</a></li>
                    <li><a href="/admin/get_scheduler/3" class="btn btn-link disabled">Coming week 3</a></li>
                    <li><a href="/admin/get_scheduler/4" class="btn btn-link disabled">Coming week 4</a></li>
                    <li><a href="/admin/get_scheduler/5" class="btn btn-link disabled">Coming week 5</a></li>
                    <li><a href="/admin/get_scheduler/6" class="btn btn-link disabled">Coming week 6</a></li>
                    <li><a href="/admin/get_scheduler/7" class="btn btn-link disabled">Coming week 7</a></li>
                    <li><a href="/admin/get_scheduler/8" class="btn btn-link disabled">Coming week 8</a></li>
                    <li><a href="/admin/get_scheduler/9" class="btn btn-link disabled">Coming week 9</a></li>
                    <li><a href="/admin/get_scheduler/10" class="btn btn-link disabled">Coming week 10</a></li>
                    <li><a href="/admin/list_screenings/0">Delete</a></li>
                </ul>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <p class="navbar-text">You are logged in as <b class="text-danger">${login}</b></p>
            <li><a href="/admin/admin_logout"><span class="glyphicon glyphicon-log-out"></span>Logout</a></li>
        </ul>
    </div>
</nav>

<div class="container">
    <br>
    <h2 class="text-center bg-primary">${message}</h2>
    <h2 class="text-center text-danger">Screenings</h2>
    <br/>
    <table class="table table-bordered">
        <tr>
            <th>ID</th>
            <th>Address</th>
            <th>Room</th>
            <th>Date</th>
            <th>Time</th>
            <th>Movie</th>
            <th>Delete</th>
        </tr>
        <c:forEach var="screening" items="${listableScreeningInfoList}">
            <tr>
                <td>${screening.id}</td>
                <td>${screening.address}</td>
                <td>${screening.room}</td>
                <td>${screening.date}</td>
                <td>${screening.time}</td>
                <td>${screening.movieTitle}</td>
                <td>
                    <c:choose>
                        <c:when test="${screening.deleteIsDisabled}">
                            <a class="btn btn-default" href="#" disabled> Delete </a>
                            ${screening.ticketMessage}
                        </c:when>
                        <c:otherwise>
                            <c:url var="deleteUrl" value="/admin/delete_screening/page${activePage}/screening${screening.id}"></c:url>
                            <a class="btn btn-default" href="${deleteUrl}"> Delete </a>
                        </c:otherwise>
                    </c:choose>

                </td>
            </tr>
        </c:forEach>
    </table>

    <div class="pagination text-center">
        <ul class="pagination">
            <li><a href="#">&laquo;</a></li>
            <c:forEach begin="${fromPage}" end="${tillPage}" varStatus="loop">
                <li <c:if test="${loop.current eq activePage}">class="active"</c:if> ><a href="/admin/list_screenings/${loop.current}">${loop.current+1}</a></li>
            </c:forEach>
            <li><a href="#">&raquo;</a></li>
        </ul>
    </div>

</div>

</body>
</html>
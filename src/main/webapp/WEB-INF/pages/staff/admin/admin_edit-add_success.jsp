<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title> Admin </title>

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
            <li class="dropdown">
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
    <br>
    <br>
    <table class="table table-bordered text-center">
        <tr>
            <td>ID: </td>
            <td> ${movie.id}</td>
        </tr>
        <tr>
            <td>Movie Status: </td>
            <td> ${movie.movieStatus}</td>
        </tr>
        <tr>
            <td>Movie Title: </td>
            <td> ${movie.title}</td>
        </tr>
        <tr>
            <td>Duration, min: </td>
            <td> ${movie.minutes}</td>
        </tr>
        <tr>
            <td>Release year: </td>
            <td> ${movie.releaseYear}</td>
        </tr>
        <tr>
            <td>Directed By: </td>
            <td> ${movie.directedBy}</td>
        </tr>
        <tr>
            <td>Screenplay: </td>
            <td> ${movie.screenplay}</td>
        </tr>
        <tr>
            <td>Cast: </td>
            <td> ${movie.cast}</td>
        </tr>
        <tr>
            <td>Country: </td>
            <td> ${movie.country}</td>
        </tr>
        <tr>
            <td>Description: </td>
            <td> ${movie.description}</td>
        </tr>
        <tr>
            <td>Genre: </td>
            <td>
                <c:forEach var="genre" items="${movie.genresListToEnumList()}" varStatus="loop">
                    ${genre}<c:if test="${not loop.last}">, </c:if>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td>Poster: </td>
            <td>
                <img src="<c:url value="/public/movies${movie.reducedPosterPath()}"/>" alt="${movie.title}" height="400em">
            </td>
        </tr>
        <tr>
            <td>Trailer: </td>
            <td>
                <video controls id="${movie.id}video" style="width:100%;height:auto;frameborder:0;">
                    <source src="<c:url value="/public/movies${movie.reducedTrailerPath()}"/>" type="video/mp4">
                    Your broser doesn't support HTML5 video tag.
                </video>
            </td>
        </tr>
    </table>

</div>

</body>
</html>
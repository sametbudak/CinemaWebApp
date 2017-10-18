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
    <title> Movie Information </title>

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
    <!-- Page Heading -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="text-center">Movie details</h1>
            </div>
        </div>
    </div>
    <!-- /.row -->

    <!-- Container (Movies Section) -->
    <div class="container">
    <div class="row">
            <div class="col-md-3">
                <img class="img-thumbnail" src="<c:url value="/public/movies${movie.reducedPosterPath()}"/>" alt="${movie.title}" width="100%" height="400px">
            </div>
            <div class="col-md-6">
                <table id="movie-details" class="table table-condensed" style="border: none; border-collapse: collapse;">
                    <tr>
                        <td><strong>Title:</strong> </td>
                        <td>${movie.title} </td>
                    </tr>
                    <tr>
                        <td><strong>Year:</strong> </td>
                        <td>${movie.releaseYear} </td>
                    </tr>
                    <tr>
                        <td><strong>Genre:</strong> </td>
                        <td>
                            <c:forEach items="${genresStrList}" var="genre" varStatus="loop" >
                                <c:out value="${genre}"/><c:if test="${not loop.last}"><span>,</span></c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td><strong>Country:</strong> </td>
                        <td>${movie.country} </td>
                    </tr>
                    <tr>
                        <td><strong>Duration:</strong> </td>
                        <td>${movie.minutes} min</td>
                    </tr>
                    <tr>
                        <td><strong>Directed By:</strong> </td>
                        <td>${movie.directedBy}</td>
                    </tr>
                    <tr>
                        <td><strong>Screenplay:</strong> </td>
                        <td>${movie.screenplay}</td>
                    </tr>
                    <tr>
                        <td><strong>Cast:</strong> </td>
                        <td>${movie.cast}</td>
                    </tr>
                    <tr>
                        <td><strong>Description:</strong> </td>
                        <td>${movie.description}</td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <a href="/showtimes_movie/${movie.id}" class="btn btn-info btn-block">Get Tickets</a>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <br/>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <video controls id="${movie.id}video" style="width:100%;height:auto;frameborder:0;">
                    <source src="/public/movies${movie.reducedTrailerPath()}" type="video/mp4">
                    Your broser doesn't support HTML5 video tag.
                </video>
            </div>
        </div>
    </div>
    <br/>
</div>
<!-- /.container -->

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
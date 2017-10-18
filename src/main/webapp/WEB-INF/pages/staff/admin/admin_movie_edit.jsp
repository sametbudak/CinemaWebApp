<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title> Admin / Edit Movie </title>

    <!-- Bootstrap -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- Bootstrap Core CSS -->
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script>
        $(document).ready(function() {
            $("form").submit(function(e){
                var count = 0;
                $(".genre_checkbox:checkbox:checked").each(function(){
                    count += 1;
                });
                if(count <1) {
                    alert("Select genre(s)!");
                    e.preventDefault();
                }
            });
        });
    </script>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Movie7Theater</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="dropdown active">
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
    <h2 class="text-center text-danger">Edit movie</h2>
    <br>
    <form class="form-horizontal" name="edited_movie" action="/admin/edit_movie" method="post" enctype="multipart/form-data">

        <c:set var="movie" value="${editableMovieDto.movie}" scope="request"></c:set>
        <c:set var="readonly" value="${editableMovieDto.readonlyFields}" scope="request"></c:set>

        <input type="hidden" name="id" value="${movie.id}" required>

        <div class="form-group">
            <label class="control-label col-sm-2">Status:</label>
            <div class="col-sm-8">
                <div class="radio">
                    <c:forEach var="status" items="${statusList}">
                        <label><input type="radio" name="movieStatus" value="${status}" required <c:if test="${movie.movieStatus eq status}"> checked</c:if> ${readonly.movieStatus}> ${status} </label>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2" for="title">Movie Title:</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" name="title" id="title" placeholder="Movie Title" required value="${movie.title}" ${readonly.title}>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2" for="minutes">Duration, min:</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" name="minutes" id="minutes" placeholder="Movie Duration, min" required value="${movie.minutes}" ${readonly.minutes}>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2" for="release_year">Release year:</label>
            <div class="col-sm-8">
                <input type="number" min="1900" class="form-control" name="releaseYear" id="release_year" placeholder="YYYY" required value="${movie.releaseYear}" ${readonly.releaseYear}>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2" for="directed_by">Directed By:</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" name="directedBy" id="directed_by" placeholder="Directed By" required value="${movie.directedBy}" ${readonly.directedBy}>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2" for="screenplay">Screenplay:</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" name="screenplay" id="screenplay" placeholder="Screenplay" required value="${movie.screenplay}" ${readonly.screenplay}>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2" for="cast">Cast:</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" name="cast" id="cast" placeholder="Cast" required value="${movie.cast}" ${readonly.cast}>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2" for="country">Country:</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" name="country" id="country" placeholder="Country" required value="${movie.country}" ${readonly.country}>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2" for="description">Description:</label>
            <div class="col-sm-8">
                <textarea class="form-control" rows="5" name="description" id="description" placeholder="Description" required ${readonly.description}>${movie.description}</textarea>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2">Genre:</label>
            <div class="col-sm-8">
                <c:forEach var="genre" items="${editableMovieDto.checkedGenreList}">
                    <div class="checkbox">
                        <label><input type="checkbox" class="genre_checkbox" name="genres_param" value="${genre.genreEnum}" ${genre.checked} ${readonly.genres}>${genre.genreEnum}</label>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2" for="poster">Poster(.jpg):</label>
            <div class="col-sm-8">
                <input type="file" accept="image/jpeg" class="form-control" name="poster_param" id="poster" placeholder="Select poster" ${readonly.poster}>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2" for="trailer">Trailer(.mp4):</label>
            <div class="col-sm-8">
                <input type="file" accept="video/mp4" class="form-control" name="trailer_param" id="trailer" placeholder="Select trailer" ${readonly.trailer}>
            </div>
        </div>

        <br>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-8">
                <button class="btn btn-danger btn-block">Update</button>
            </div>
        </div>
    </form>

</div>

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

</body>
</html>
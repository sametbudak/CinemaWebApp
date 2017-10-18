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
    <title> Home </title>

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

    <script type="javascript">
        $(document).ready(function() {
            $("[id$='myModal']").on('shown.bs.modal', function() {
                $(this).find("[id$='video']")[0].play();
            });
            $("[id$='myModal']").on('hidden.bs.modal', function() {
                $(this).find("[id$='video']")[0].pause();
            });
            $("[id$='myModal_close']").click(function() {
                console.info("clicked " + this);
                var movie_id = $(this).id;
                movie_id = movie_id.replace("myModal_close", "");
                var video_id = movie_id+"video";
                console.info("video id = " + video_id)
                alert("video id = " + video_id);
                $("[id="+video_id+"]").pause();
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
                <li class="active"><a href="/">Home</a></li>
                <li><a href="#movies">Movies</a></li>
                <li><a href="#addresses">Our Cinemas</a></li>
                <li><a href="#contacts">Contacts</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/showtimes" class="btn btn-default">Showtimes</a></li>
            </ul>
        </div>
    </div>
</nav>


<div id="content">
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
            <li data-target="#myCarousel" data-slide-to="3"></li>
            <li data-target="#myCarousel" data-slide-to="4"></li>
        </ol>

        <!-- Wrapper for slides -->
        <div class="carousel-inner" role="listbox">
            <div class="item active">
                <img src="<c:url value="/static/img/slider/ultrascreendlx.jpg" />" alt="ultrascreendlx" width="1024" height="500">
                <div class="carousel-caption">
                    <h3>Ultrascreen dlx</h3>
                    <p>The atmosphere in Ultrascreen dlx is lorem ipsum.</p>
                </div>
            </div>

            <div class="item">
                <img src="<c:url value="/static/img/slider/dreamloungers.jpg"/>" alt="dreamloungers" width="1024" height="800">
                <div class="carousel-caption">
                    <h3>Dreamloungers</h3>
                    <p>The atmosphere in Dreamloungers dlx is lorem ipsum.</p>
                </div>
            </div>

            <div class="item">
                <img src="<c:url value="/static/img/slider/superscreendlx.jpg"/>" alt="superscreendlx" width="1024" height="800">
                <div class="carousel-caption">
                    <h3>Superscreen dlx</h3>
                    <p>The atmosphere in Superscreen dlx is lorem ipsum.</p>
                </div>
            </div>

            <div class="item">
                <img src="<c:url value ="/static/img/slider/food.jpg"/>" alt="food" width="1024" height="800">
                <div class="carousel-caption">
                    <h3>Superscreen dlx</h3>
                    <p>The food in Theater restaurant is lorem ipsum.</p>
                </div>
            </div>

            <div class="item">
                <img src="http://placehold.it/2040x800&text=Slider" alt="Slider" width="1024" height="800">
                <div class="carousel-caption">
                    <h3>Lorem ipsum</h3>
                    <p>The atmosphere is lorem ipsum.</p>
                </div>
            </div>
        </div>

        <!-- Left and right controls -->
        <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>

    <!-- Page Content -->
    <div class="container" id="movies">
        <div class="row">
            <div class="col-md-12">
                <h1 class="page-header">Movie7Theater <small>Kiev</small></h1>
            </div>
        </div>

        <h2 class="text-center">Movies at the box office</h2>
        <br>

        <c:set var="count" value="0" scope="request" />
        <c:forEach items="${movies}" var="movie" varStatus="loop">
            <c:set var="count" value="${count+1}" scope="request" />
            <c:if test="${count%4 eq 1}"> <div class="row" style="height: auto"> </c:if>
            <div class="col-md-3">
                <div class="thumbnail" style="height: 100%">
                    <img src="<c:url value="/public/movies${movie.reducedPosterPath()}"/>" alt="${movie.title}" width="100%" height="70%">
                    <div class="caption" style="height: auto">
                        <a href="/showtimes_movie/${movie.id}" class="btn btn-primary btn-block">Get Tickets</a>
                        <table>
                            <tr>
                                <td class="table-first-col-width"><strong>Title:</strong> </td>
                                <td> ${movie.title} </td>
                            </tr>
                            <tr>
                                <td class="table-first-col-width"><strong>Year:</strong> </td>
                                <td> ${movie.releaseYear} </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <!-- Button trigger modal -->
                                    <a href="#" type="button" class="btn btn-link" data-toggle="modal" data-target="#${movie.id}myModal">
                                        Watch trailer
                                    </a> |
                                    <a href="/movie/${movie.id}" class="btn btn-link">See More</a>
                                </td>
                            </tr>
                        </table>
                    </div>

                    <!-- Modal -->
                    <div class="modal fade" id="${movie.id}myModal" tabindex="-1" role="dialog" aria-labelledby="${movie.id}myModalLabel">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" id="${movie.id}myModal_close" data-dismiss="modal" aria-label="close"><span aria-hidden="true">x</span></button>
                                    <h4 class="modal-title">${movie.title}</h4>
                                </div>
                                <div class="modal-body">
                                    <video controls id="${movie.id}video" style="width:100%;height:auto;frameborder:0;">
                                        <source src="<c:url value="/public/movies${movie.reducedTrailerPath()}"/>" type="video/mp4">
                                        Your broser doesn't support HTML5 video tag.
                                    </video>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${count%4 eq 0 or loop.last}"> </div> </c:if>
        </c:forEach>
        <c:remove var="count" scope="request" />
    </div>

    <div id="addresses" class="container">
        <hr>
        <h3 class="text-center text-capitalize">Our cinemas</h3>
        <br>
        <c:forEach items="${addresses}" var="address">
            <div class="row">
                <div class="col-md-6">
                    <div class="thumbnail">
                        <img src="<c:url value="/public/addresses${address.reducedPicturePath()}"/>" alt="picture" width="100%" height="70%">
                    </div>
                </div>
                <div class="col-md-6">
                    <h4><c:out value="${address.getFullAddress()}" /></h4>
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                        Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
                        Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
                        Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum
                    </p>
                </div>
            </div>
            <br>
            <br>
        </c:forEach>
    </div>
    <br/>

</div>

<div class="container-fluid" id="footer">
    <footer>
        <div class="container foo">
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

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
    <title> Confirmed </title>

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

<!-- Page Content -->
<div id="content">
<div class="container">
    <!-- Display the countdown timer in an element -->
    <div class="row">
        <div class="col-md-4"><h3 class="text-right">Session expires in: </h3></div>
        <div class="col-md-2"><h3 class="text-center bg-danger" id="timer"></h3></div>
    </div>

    <br/>
    <form action="/confirm_booking" method="post" class="form-horizontal">
        <input type="hidden" name="email" value="${email}">
        <div class="form-group">
            <label class="control-label col-md-4" for="code">Type in here the code sent to you via e-mail:</label>
            <div class="col-md-8">
                <input type="text" class="form-control" name="code" id="code">
            </div>
        </div>
        <div class="row">
            <div class="col-md-3 col-md-offset-4">
                <button type="submit" class="btn btn-info btn-block">Confirm</button>
            </div>
        </div>
    </form>
</div>
</div>
<script>
    var countDown = 60*3 * 1000;
    var now = - 1000;

    // Update the count down every 1 second
    var x = setInterval(function() {

        now = now + 1000;

        // Find the distance between now an the count down date
        var distance = countDown - now;

        // Time calculations for minutes and seconds
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);

        // Display the result in the element with id="demo"
        document.getElementById("timer").innerHTML = minutes + "m " + seconds + "s ";

        // If the count down is finished, write some text
        if (distance < 0) {
            clearInterval(x);
            document.getElementById("timer").innerHTML = "EXPIRED";
            var inputs = document.getElementsByTagName("input");
            var i;
            for (i = 0; i < inputs.length; i++) {
                inputs[i].disabled = true;
            }
            var buttons = document.getElementsByTagName("button");
            for (i = 0; i < buttons.length; i++) {
                buttons[i].disabled = true;
            }
        }
    }, 1000);
</script>
</body>
</html>

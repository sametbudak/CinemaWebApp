<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title> 404 </title>

    <!-- Bootstrap -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css">

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

    <style type="text/css">
        /* Error Page Inline Styles */
        body {
            padding-top: 20px;
        }
        /* Layout */
        .jumbotron {
            font-size: 21px;
            font-weight: 200;
            line-height: 2.1428571435;
            color: inherit;
            padding: 10px 0px;
        }
        /* Everything but the jumbotron gets side spacing for mobile-first views */
        .body-content {
            padding-left: 15px;
            padding-right: 15px;
        }
        /* Main marketing message and sign up button */
        .jumbotron {
            text-align: center;
            background-color: transparent;
        }
        .jumbotron .btn {
            font-size: 21px;
            padding: 14px 24px;
        }
        /* Colors */
        .green {color:#5cb85c;}
        .red {color:#d9534f;}
    </style>

</head>
<body>

<div class="container">

    <!-- Jumbotron -->
    <div class="jumbotron">
        <h1 class="red"><i class="fa fa-frown-o red"></i> 404 </h1>
        <p class="lead">We couldn't find what you're looking for on <em><span id="display-domain"></span></em>.</p>
        <p><a onclick=javascript:checkSite(); class="btn btn-default btn-lg"><span class="green">Go Back</span></a>

            <script type="text/javascript">

                function checkSite(){
                    /*var currentSite = window.location.hostname;
                    var protocol = window.location.protocol;
                    var port = location.port;
                    if(port.length == 0) {
                        window.location = protocol + "//" + currentSite;
                    } else {
                        window.location = protocol + "//" + currentSite + ":" + port;
                    }*/
                    history.back()
                }

            </script>
        </p>
    </div>

</div>

<div class="container">
    <div class="body-content">
        <div class="row">
            <div class="col-md-6">
                <h2>What happened?</h2>
                <p class="lead">A 404 error status implies that the file or page that you're looking for could not be found.</p>
            </div>

            <div class="col-md-6">
                <h2>What can I do?</h2>
                <p class="lead">Please use your browser's back button and check that you're in the right place.</p>
            </div>
        </div>

    </div>

</div>

</body>
</html>

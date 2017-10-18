<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title> Admin / Screening Scheduler </title>

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

    <!-- https://www.codeproject.com/Tips/890021/Advanced-CSS-Styling-of-HTML-SELECT-Element -->
    <style type="text/css">

        td:nth-child(4) {
            width: 800px !important;
        }

        table, tr, td {
            border: 2px solid whitesmoke !important;
        }
        th {
            border: 1px solid #c1c1c1 !important;
        }

        .header_row {
            background-color: #555555;
            color: #5bc0de;
        }
        .date_row {
            background-color: #222222;
            color: whitesmoke;
        }

        .address_1_row {
            background-color: #e0e0e0;
        }

        .address_2_row {
            background-color: #faebcc;
        }

        .address_3_row {
            background-color: #dcf8c4;
        }

        .custom
        {
            height                   : 2.5em;
            line-height              : 1em;
            vertical-align           : middle;
            padding-right            : 1.5em;
            text-indent              : 0.2em;
            text-align               : left;
            box-shadow               : inset 0 0 1px #eeeeee;
            border                   : 1px solid #c7c7c7;
            -moz-border-radius       : 3px;
            -webkit-border-radius    : 3px;
            border-radius            : 3px;
            -webkit-appearance       : none;
            -moz-appearance          : none;
            appearance               : none;  /*IMPORTANT*/
            font-family              : Arial,  Calibri, Tahoma, Verdana;
            font-size                : 1.0em;
            font-weight              : 100;
            color                    : #424242;
            cursor                   : pointer;
            outline                  : none;
        }
        .custom-input {
            width                    : 3em;
        }
        .custom-select {
            width                    : 20em;
        }
        select::-ms-expand {display: none;} /*FOR IE*/
        select option
        {
            padding             : 0.3em;
            font-size           : 1em;
            font-weight         : normal;
        }
        select option[selected]{ font-weight:bold}
        select option:nth-child(even) { background-color:#f5f5f5; }
        select:hover {font-weight: 300;}
        input:focus, select:focus {
            border: 1px solid #37b2e9;
            box-shadow: 0 0 2px 2px #bfd5ff;
            font-weight: 200;
        }

        /*LABEL FOR SELECT*/
        label { position: relative; display: inline-block;}
        label::after
        {
            /*'AIRPLANE' GLYPGH on PULL-DOWN BUTTON (U+021E3)*/
            content                 : "\23f7";
            position                : absolute;
            top                     : 0;
            right                   : 0;
            bottom                  : 0;
            width                   : 1.5em;
            line-height             : 2em;
            vertical-align          : middle;
            text-align              : center;
            color                   : #5d5d5d;
            font-size               : 1.2em;
            -moz-border-radius       : 0 3px 3px 0;
            -webkit-border-radius    : 0 3px 3px 0;
            border-radius           : 0 3px 3px 0;
            pointer-events          : none;
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
    <br/>
    <h2 class="text-center bg-primary">${message}</h2>
    <div class="row">
        <div class="col-md-offset-2 col-md-2">
            <h5>*Active movies : </h5>
        </div>
        <div class="col-md-5">
            <c:forEach var="movie" items="${activeMovieInfoList}">
                <p> ${movie.movieTitle}, ${movie.minutes}min </p>
            </c:forEach>
        </div>
    </div>
    <div class="row">
        <div class="col-md-offset-2">
            <p><i>* change movie's status to non-active to delete it from scheduling</i></p>
        </div>
    </div>

    <br>
    <c:if test="${disabled eq 'disabled'}"><h2 class="text-center bg-primary">Current week can only be viewed, but not edited!</h2></c:if>
    <h1 class="text-center text-info"> Movie screening scheduler</h1>
    <h4 class="text-center text-info"><i>for week ${schedulingInfo.dateList.get(0)} (Monday) - ${schedulingInfo.dateList.get(6)} (Sunday)</i></h4>
    <br>

    <p><span class="address_1_row" style="padding-right: 30px; border: 1px solid grey"></span> - address: No.10 A.Saburova Street, Obolonskyi District, Kiev </p>
    <p><span class="address_2_row" style="padding-right: 30px; border: 1px solid grey"></span> - address: No.23 Metrobudovskaya Street, Pecherskyi District, Kiev </p>
    <p><span class="address_3_row" style="padding-right: 30px; border: 1px solid grey"></span> - address: No.3 Priluzhnaya Street, Shevchenkivskyi District, Kiev </p>
    <br/>
    <form action="/admin/add_screenings" method="post">
        <fieldset ${disabled}>
        <input type="hidden" name="week" value="${week}">

        <c:set var="count" value="0" scope="request"></c:set>

        <table class="table table-bordered">
            <tr class="header_row">
                <th><h4 class="text-center">Address</h4></th>
                <th><h4 class="text-center">Room</h4></th>
                <th><h4 class="text-center">Number of seats</h4></th>
                <th><h4 class="text-center">Ticket's price,USD / Time / Movie</h4></th>
            </tr>
            <c:forEach var="date" items="${schedulingInfo.dateList}">
                <tr class="text-center date_row">
                    <fmt:setLocale value="en_US" scope="request"/>
                    <fmt:formatDate var="formattedDate" value="${date}" pattern="d MMM, yyyy (EEEE)" />
                    <td colspan="4"><h4>DATE: <b>${formattedDate}</b></h4></td>
                </tr>
                <c:forEach var="address" items="${schedulingInfo.addressAndRoomList}">
                    <c:forEach var="room" items="${address.roomDtoList}">
                        <tr class="address_${address.addressId}_row">
                            <td> ${address.addressStr} </td>
                            <td> ${room.roomTitle} </td>
                            <td> ${room.numberOfSeats}</td>
                            <td>
                                <c:forEach var="time" items="${schedulingInfo.timeRangeList}">
                                    <c:set var="taken" value="${schedulingInfo.getScreening(room.roomId, date, time)}" scope="request"></c:set>
                                    $<input type="number" class="custom custom-input" min="1" max="50" name="price_${count}" value="${schedulingInfo.price}">
                                    ${time} <label><select class="custom custom-select" name="movie_date_time_room_${count}" <c:if test="${taken ne null}">disabled</c:if>>
                                        <option></option>
                                        <c:forEach var="movie" items="${activeMovieInfoList}">
                                            <option value="movie=${movie.movieId};date_time=${date} ${time.from};room=${room.roomId}"> ${movie.movieTitle}, ${movie.minutes}min </option>
                                        </c:forEach>
                                    </select></label>
                                    <c:if test="${taken ne null}">
                                        <span style="color: red"><i>scheduled, </i> <b>${taken.movieTitle}, ${taken.minutes}min</b></span>
                                    </c:if>
                                    <br/>
                                    <c:set var="count" value="${count+1}" scope="request"></c:set>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </c:forEach>
        </table>

        <input type="hidden" name="count" value="${count}">

        <br>
        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-6">
                <button type="submit" class="btn btn-danger btn-block">Save</button>
            </div>
        </div>
        </fieldset>
    </form>
</div>
<br>
<br>
<br>
<br>
<br>
<br>
</body>
</html>
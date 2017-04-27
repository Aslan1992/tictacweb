<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Game</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <style>
        table, th, td {
            border: 2px solid black;
            border-collapse: collapse;
        }

        th, td {
            text-align: center;
            width: 60px;
            height: 60px;
            font-size: large;
        }
        #btn {
            -moz-box-shadow: 0px 1px 0px 0px #fff6af;
            -webkit-box-shadow: 0px 1px 0px 0px #fff6af;
            box-shadow: 0px 1px 0px 0px #fff6af;
            background-color:#ffec64;
            -moz-border-radius:6px;
            -webkit-border-radius:6px;
            border-radius:6px;
            border:1px solid #ffaa22;
            display:inline-block;
            cursor:pointer;
            color:#333333;
            font-family:Arial;
            font-size:15px;
            font-weight:bold;
            padding:6px 24px;
            text-decoration:none;
            text-shadow:0px 1px 0px #ffee66;
        }
        #btn:hover {
            background-color:#ffab23;
        }
        #btn:active {
            position:relative;
            top:1px;
        }
    </style>
</head>
<body>
<h4><div id="gameInfo"></div></h4>
<h4>Creator - 'X', Incomer - 'O'</h4>
<h4>Player: ${player}</h4>
Last step was made by:
<span id="who"></span>
<table id="gameBoard">
    <tr>
        <td id="00"></td>
        <td id="01"></td>
        <td id="02"></td>
    </tr>
    <tr>
        <td id="10"></td>
        <td id="11"></td>
        <td id="12"></td>
    </tr>
    <tr>
        <td id="20"></td>
        <td id="21"></td>
        <td id="22"></td>
    </tr>
</table>
<br>
<button id="btn" onclick="replay()" style="display: none">try again</button>
<script>
    var gameBoard = $("gameBoard");
    var url = window.location.toString();
    var params = url.split('=');
    var gameName = (params[1] != null) ? params[1] : "${gameName}";
    const BOARD_SIZE = 3;
    var game = new Game(gameName, initArray());
    gameBoard.onmouseup = listenMouseUp;

    function $(elementId) {
        return document.getElementById(elementId);
    }

    function Game(name, state) {
        this.name = name;
        this.state = state;
        this.victoryItems = null;
    }

    function Item(i, j) {
        this.i = i;
        this.j = j;
    }

    function listenMouseUp(e) {
        e = e || window.event;
        var elementId = (e.target || e.srcElement).id;
        var item = game.state[line(elementId)][column(elementId)];
        if (item === "") {
            game.state[line(elementId)][column(elementId)] = ("${player}" === "creator") ? "X" : "0";
        }
        toServer(game, "${player}");
        gameBoard.onmouseup = null;
    }

    function whoMadeStep() {
        var http = new XMLHttpRequest();
        http.open('GET', '/whoMadeStep?name=' + gameName, false);
        http.send();
        $("who").innerHTML = http.responseText;
    }

    function toServer(myObject, playerRole) {
        var http = new XMLHttpRequest();
        http.open('POST', '/toServer?playerRole=' + playerRole, false);
        http.setRequestHeader('Content-Type', 'application/json');
        http.send(JSON.stringify(myObject));
    }

    var timer = setTimeout(fromServer, 1000);

    function fromServer() {
        var http = new XMLHttpRequest();
        http.open('GET', '/fromServer?name=' + gameName, false);
        http.send();
        game = JSON.parse(http.responseText);
        whoMadeStep();
        drawOnUi(game.state);
        if (game.victoryItems !== null || allItemsIsFilled(game.state)) {

            if (game.victoryItems !== null) {
                drawVictoryItemsOnUI(game.victoryItems);
            }

            clearTimeout(timer);
            gameBoard.onmouseup = null;
            $("btn").style.display = "inline";
        } else {
            if ($("who").innerHTML !== "${player}") {
                gameBoard.onmouseup = listenMouseUp;
            }
            timer = setTimeout(fromServer, 1000);
        }
    }

    var timerId = setTimeout(currentGameProcessInfo, 1000);

    function currentGameProcessInfo() {
        var http = new XMLHttpRequest();
        http.open("GET", "/currentGameProcessInfo?name=" + gameName, false);
        http.send();
        $("gameInfo").innerHTML = http.responseText;
        if (http.responseText.includes("in progress")) {
            clearTimeout(timerId);
        } else {
            timerId = setTimeout(currentGameProcessInfo, 1000);
        }
    }

    function initArray() {
        var a = new Array(BOARD_SIZE);
        for (var i = 0; i < BOARD_SIZE; i++) {
            a[i] = new Array(BOARD_SIZE);
        }

        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                a[i][j] = "";
            }
        }
        return a;
    }

    function allItemsIsFilled(array) {
        var k = 0;
        for(var i = 0; i < BOARD_SIZE; i++) {
            for(var j = 0; j < BOARD_SIZE; j++) {
                if (array[i][j] !== "") {
                    k++;
                }
            }
        }
        return (k === 9);
    }

    function replay() {
        clearUI();
        $("btn").style.display = "none";
        game = new Game(gameName, initArray());
        toServer(game, "${player}");
        timer = setTimeout(fromServer, 1000);
        gameBoard.onmouseup = listenMouseUp;
    }

    function drawVictoryItemsOnUI(items) {
        for (var i = 0; i < BOARD_SIZE; i++) {
            $(items[i].i + "" + items[i].j).style.backgroundColor = "lightgreen";
        }
    }

    function drawOnUi(stateArr) {
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                $(i + "" + j).innerHTML = stateArr[i][j];
            }
        }
    }

    function clearUI() {
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                $(i + "" + j).innerHTML = "";
                $(i + "" + j).style.backgroundColor = "white";
            }
        }
    }

    function line(str) {
        return parseInt(str.charAt(0));
    }

    function column(str) {
        return parseInt(str.charAt(1));
    }
</script>
</body>
</html>


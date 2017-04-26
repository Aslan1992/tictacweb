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
<button onclick="replay()">qwert</button>
<script>

    var gameBoard = $("gameBoard");

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

    //    Item.prototype.toString = function () {
    //        return this.i + "" + this.j;
    //    };

    var url = window.location.toString();
    var params = url.split('=');
    var gameName = (params[1] != null) ? params[1] : "${gameName}";
    const BOARD_SIZE = 3;

    var game = new Game(gameName, initArray());

    gameBoard.onmouseup = listenMouseUp;

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

    function line(str) {
        return parseInt(str.charAt(0));
    }

    function column(str) {
        return parseInt(str.charAt(1));
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
        if (game.victoryItems !== null) {
            drawVictoryItemsOnUI(game.victoryItems);
            clearTimeout(timer);
            gameBoard.onmouseup = null;
        } else {
            if ($("who").innerHTML !== "${player}") {
                gameBoard.onmouseup = listenMouseUp;
            }
            timer = setTimeout(fromServer, 1000);
        }
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


    function replay() {
        clearUI();
        game = new Game(gameName, initArray());
        toServer(game, "${player}");
        timer = setTimeout(fromServer, 1000);
        gameBoard.onmouseup = listenMouseUp;
    }

    function clearUI() {
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                $(i + "" + j).innerHTML = "";
                $(i + "" + j).style.backgroundColor = "white";
            }
        }
    }
</script>
</body>
</html>


<html >
<head>
    <meta charset="utf-8">
    <title>Крестики-нолики</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
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
    button {
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
    button:hover {
        background-color:#ffab23;
    }
    button:active {
        position:relative;
        top:1px;
    }

</style>
<body>
<h2>Игра крестики-нолики</h2>
<h4>Чтобы начать нажмите кнопку новая игра.</h4>
<h4>Для того чтобы делать ходы используйте цифры на клавиатуре справа</h4>
<button onclick="newGame();">New game</button>
<p></p>
<table>
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
<div id="div"></div>
<script type="text/javascript">
    var array = ["aslan", "max"];

    function newGame() {
        var http = new XMLHttpRequest();
        http.open('POST', '/sendBoardStateToServer', true);
        http.send(JSON.stringify(array));
    }
//    var xflag = true;
//    var boardState;
//    const BOARD_SIZE = 3;
//
//    function newGame() {
//        clearUI();
//        initBoard();
//        var http = new XMLHttpRequest();
//        document.body.onmouseup = function(e) {
//            e = e || window.event;
//            var elementId = (e.target || e.srcElement).id;
//            var value = (xflag) ? "X" : "O";
//            if ($(elementId).innerHTML == "") {
//                $(elementId).innerHTML = value;
//                xflag = !xflag;
//                updateCurrentBoardState();
//                http.open('POST', '/sendBoardStateToServer', true);
//                http.send(boardState);
//            }
//            console.log(boardState.toString());
//        }
//    }
//
//    function initBoard() {
//        boardState = new Array(BOARD_SIZE);
//        for(var i = 0; i < boardState.length; i++) {
//            boardState[i] = new Array(BOARD_SIZE);
//        }
//    }
//
//    function updateCurrentBoardState() {
//        for(var i = 0; i < BOARD_SIZE; i++) {
//            for(j = 0; j < BOARD_SIZE; j++) {
//                boardState[i][j] = $(i+""+j).innerHTML
//            }
//        }
//    }
//
//    function clearUI () {
//        for(var i = 0; i < BOARD_SIZE; i++) {
//            for (var j = 0; j < BOARD_SIZE; j++) {
//                $(i + "" + j).innerHTML = "";
//                $(i + "" + j).style.backgroundColor = "white";
//            }
//        }
//    }
//
//    function $(id) {
//        return document.getElementById(id);
//    }

</script>
</body>
</html>

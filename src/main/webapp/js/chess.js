var board = null;
var game = {turn: 'w', possibleMoves: null, status: null};
var whiteSquareGrey = '#a9a9a9'
var blackSquareGrey = '#696969'

function isNotCorrectFigureColor(piece) {
    if ((game.turn === 'w' && piece.startsWith('b')) ||
        (game.turn === 'b' && piece.startsWith('w'))) {
        return true
    }
    return false;
}

function onDragStart(source, piece, position, orientation) {
    if(isNotCorrectFigureColor(piece)) {
        return false;
    }
}

function isMoveLegal(source, target, response) {
    if(source === target || response == null) {
        return false;
    }

    for(var i = 0; i < response.length; i++) {
        if(response[i] != null && target === response[i]) {
            return true;
        }
    }

    return false;
}

function onDrop(source, target, piece, newPos, oldPos, orientation) {
    removeGreySquares()
    if(target == 'offboard') {
        return 'snapback';
    }

    if(!isMoveLegal(source, target, game.possibleMoves)) {
        console.log('test1');
        return 'snapback'
    }
    console.log('test2');
    flip();
    return 'drop';
}

function getInformationFromBackend(square, piece, turn) {
    return $.ajax({
        url: "EmailForm",
        type: "GET",
        data: {
            square: square,
            piece: piece,
            turn: turn
        },
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async: false,
        success: function (res) {
            console.log('Comunication with backend: OK');
            setPossibleMoves(res);
            console.log(game.possibleMoves)
        },
        error: function (obj) {
            setPossibleMoves(null);
            alert('Error! ' + JSON.stringify(obj));
        }
    })
}

function setPossibleMoves(possibleMoves) {
    game.possibleMoves = possibleMoves;
}

function onMouseoutSquare (square, piece) {
    removeGreySquares();
    setPossibleMoves(null);
}

function onMouseoverSquare (square, piece) {
    if(piece === false || isNotCorrectFigureColor(piece)) {
        return;
    }
    getInformationFromBackend(square, piece, game.turn);

    greySquare(square)
    for (var i = 0; i < game.possibleMoves.length; i++) {
        greySquare(game.possibleMoves[i])
    }
}

function greySquare (square) {
    console.log('square:========' + square);
    var $square = $('#myBoard .square-' + square)

    var background = whiteSquareGrey
    if ($square.hasClass('black-3c85d')) {
        background = blackSquareGrey
    }

    $square.css('background', background)
}

function removeGreySquares () {
    $('#myBoard .square-55d63').css('background', '')
}

function onSnapEnd () {
    // board.position('start')
}

function flip() {
    if(game.turn === 'w') {
        game.turn = 'b'
    } else {
        game.turn = 'w'
    }
}
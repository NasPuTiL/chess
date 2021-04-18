var board = null;
var game = {turn: 'w', possibleMoves: null, status: null};
var whiteSquareGrey = '#a9a9a9'
var blackSquareGrey = '#696969'
var position = {
    d6: 'wR'
}

function onInit() {
    return $.ajax({
        url: "initData",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async: false,
        success: function (res) {
            console.log('Backend was initialized')
        },
        error: function (obj) {
            setPossibleMoves(null);
            alert('Error! ' + JSON.stringify(obj));
        }
    })
}


function isNotCorrectFigureColor(piece) {
    if ((game.turn === 'w' && piece.startsWith('b')) ||
        (game.turn === 'b' && piece.startsWith('w'))) {
        return true
    }
    return false;
}

function onDragStart(source, piece, position, orientation) {
    if (isNotCorrectFigureColor(piece)) {
        return false;
    }
}

function isMoveLegal(source, target, response) {
    if (source === target || response == null) {
        return false;
    }

    for (var i = 0; i < response.length; i++) {
        if (response[i] != null && target === response[i]) {
            return true;
        }
    }

    return false;
}

function refreshDataInBackend(source, target, turn, piece) {
    return $.ajax({
        url: "refreshData",
        type: "GET",
        data: {
            source: source,
            target: target,
            turn: turn
        },
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async: false,
        success: function (res) {
            console.log('Communication with backend: OK');
            setPossibleMoves(res);
            checkCastle(source, target);
            flightBetting(source, target, res);
            isMate(res);
        },
        error: function (obj) {
            setPossibleMoves(null);
            alert('Error! ' + JSON.stringify(obj));
        }
    })
}
function pawnAvans(source, target, piece) {
    if((piece === 'bP' || piece === 'wP')){
        console.log('TST: 1');
        if(target.match('.0') || target.match('.8')){
            console.log('TST: '+ piece);
            var templateTarget;
            var templateSource;
            if(piece === 'bP') {
                templateTarget = ",\""+ source +"\":\"bP\"";
                templateSource = ",\""+ target +"\":\"bQ\"";
            } else {
                templateTarget = ",\""+ source +"\":\"wP\"";
                templateSource = ",\""+ target +"\":\"wQ\"";
            }
            console.log('TK:1 ' + templateTarget);
            console.log('TK:2 ' + templateSource);
            var pos = JSON.stringify(board.position());
            console.log('TK:3 ' + pos );
            pos = pos.replace(templateTarget, templateSource);
            console.log('TK:4 ' + pos );
            board.position(JSON.parse(pos), false)

            return true;
        }
    }
}

function isMate(res) {
    let isMate = JSON.stringify(res["IS_MATE"]);
    if(isMate != null) {
        window.alert(isMate);
    }
}

function checkCastle(source, target) {
    if (source === 'e1' || source === 'e8') {
        if (target === 'c1') {
            board.move('a1-d1')
        } else if (target === 'g1') {
            board.move('h1-f1')
        } else if (target === 'c8') {
            board.move('a8-d8')
        } else if (target === 'g8') {
            board.move('h8-f8')
        }
    }
}

function flightBetting(source, target, res) {
    let flyBitTarget = JSON.stringify(res["FLY_BEAT"]);
    if(flyBitTarget != null) {
        var template = ","+ flyBitTarget +":\"bP\"";
        var pos = JSON.stringify(board.position()).replace(template, '');
        board.position(JSON.parse(pos), false)
    }
}

function onDrop(source, target, piece, newPos, oldPos, orientation) {
    removeGreySquares()
    if (target == 'offboard') {
        return 'snapback';
    }

    if (!isMoveLegal(source, target, game.possibleMoves)) {
        return 'snapback'
    }
    flip();
    refreshDataInBackend(source, target, game.turn, piece);
    if(pawnAvans(source, target, piece)) {
        return 'trash';
    }
    return 'drop';
}

function getInformationFromBackend(square, piece, turn) {
    return $.ajax({
        url: "getPossibleMoves",
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
            console.log('Communication with backend: OK');
            setPossibleMoves(res);
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

function onMouseoutSquare(square, piece) {
    removeGreySquares();
    setPossibleMoves(null);
}

function onMouseoverSquare(square, piece) {
    if (piece === false || isNotCorrectFigureColor(piece)) {
        return;
    }
    getInformationFromBackend(square, piece, game.turn);

    greySquare(square)
    for (var i = 0; i < game.possibleMoves.length; i++) {
        greySquare(game.possibleMoves[i])
    }
}

function greySquare(square) {
    var $square = $('#myBoard .square-' + square)

    var background = whiteSquareGrey
    if ($square.hasClass('black-3c85d')) {
        background = blackSquareGrey
    }

    $square.css('background', background)
}

function removeGreySquares() {
    $('#myBoard .square-55d63').css('background', '')
}

function flip() {
    if (game.turn === 'w') {
        game.turn = 'b'
    } else {
        game.turn = 'w'
    }
}

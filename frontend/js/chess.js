function onDragStart(source, piece, position, orientation) {
    dragRequest(source, piece, position);
    if ((orientation === 'white' && piece.search(/^w/) === -1) ||
        (orientation === 'black' && piece.search(/^b/) === -1)) {
        return false
    }

    console.log('Drag started:')
    console.log('Source: ' + source)
    console.log('Piece: ' + piece)
    console.log('Position: ' + Chessboard.objToFen(position))
    console.log('Orientation: ' + orientation)
    console.log('~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~')
}

function onDrop(source, target, piece, newPos, oldPos, orientation) {
    //return checkOnDrop(source, target, orientation);
    if ((orientation === 'white' && piece.search(/^w/) === -1) ||
       (orientation === 'black' && piece.search(/^b/) === -1)) {
        return 'drop'
    }

    console.log('Source: ' + source)
    console.log('Target: ' + target)
    console.log('Piece: ' + piece)
    console.log('New position: ' + Chessboard.objToFen(newPos))
    console.log('Old position: ' + Chessboard.objToFen(oldPos))
    console.log('Orientation: ' + orientation)
    console.log('~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~')
}

window.greet = function greet(name, element) {
    console.log("Hi, " + name);
    element.$server.greet("server");
}

function dragRequest(source, piece, orientation, element) {
    let request = new XMLHttpRequest();
    request.open("GET", "https://jsonplaceholder.typicode.com/todos/1");
    request.send();
    request.onload = () => {
        console.log(request);
        if(request.status === 200) {
            console.log("Well Done");
        } else {
            console.log("Something not working :/");
        }
    }
}
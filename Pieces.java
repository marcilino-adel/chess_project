class King {
    /*
    moves:
        moves one square diagonally or adjacently
    eats:
        the same as his movements
    special:
        castle - moves two positions towards a corner provided that the rook and king haven't moved yet and the king is not in check
     */
}

class Queen {
    /*
    moves:
        moves any number of squares diagonally or adjacently
    eats:
        same as her movement
     */
}

class Pawn {
    /*
    moves:
        1. On his first move, can move one or two square forward
        2. Move only one square forward
    eats:
        either directly or diagonally in front
    special:
        can be promoted to other pieces when it reaches the end of the grid
     */
}

class Knight {
    /*
    moves:
        moves 2 squares vertically or horizontally, then 3 squares in the perpendicular direction
    eats:
        same as his movement
    special:
        can jump over pieces
     */
}

class Bishop {
    /*
    moves:
        1. moves one square to the left or right
        2. move (up to) 3 squares diagonally
    eats:
        same as his movement
    special:
        can jump over pieces
     */
}

class Rook {
    /*
    moves:
        moves any number of square in the vertical and horizontal axis
    eats:
        same as his movement
    special:
        castle
     */
}

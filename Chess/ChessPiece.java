package Chess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static Chess.gameEngine.*;

public abstract class ChessPiece {
    protected final int iconSize = 50;
    public Color color;
    public Coord position;
    public boolean hasMoved;
    public ChessPiece(Color color, int x, int y) {
        this.color = color;
        this.position = new Coord(x, y);
        this.hasMoved = false;
    }

    public abstract ArrayList<ArrayList<Coord>> availableMoves();

    public abstract ImageIcon getPieceIcon();

    protected ArrayList<ArrayList<Coord>> findAvailableMoves(int[] deltaX, int[] deltaY) {
        ArrayList<ArrayList<Coord>> allMoves = new ArrayList<>();
        for (int i = 0; i < deltaX.length; i++) {
            ArrayList<Coord> movesInDirection = new ArrayList<>();
            int newX = this.position.x + deltaX[i];
            int newY = this.position.y + deltaY[i];
            while (newY <= 7 && newY >= 0 && newX <= 7 && newX >= 0) {
                movesInDirection.add(new Coord(newX, newY));
                newX += deltaX[i];
                newY += deltaY[i];
            }
            allMoves.add(movesInDirection);
        }
        return allMoves;
    }

    protected ArrayList<ArrayList<Coord>> findAvailableMoves(int[] deltaX, int[] deltaY, int limit) {
        ArrayList<ArrayList<Coord>> allMoves = new ArrayList<>();
        for (int i = 0; i < deltaX.length; i++) {
            ArrayList<Coord> movesInDirection = new ArrayList<>();
            int newX = this.position.x + deltaX[i];
            int newY = this.position.y + deltaY[i];
            int count = 0;
            while (newY <= 7 && newY >= 0 && newX <= 7 && newX >= 0 && count < limit) {
                movesInDirection.add(new Coord(newX, newY));
                newX += deltaX[i];
                newY += deltaY[i];
                count++;
            }
            allMoves.add(movesInDirection);
        }
        return allMoves;
    }

    protected ArrayList<ArrayList<Coord>> filterAvailableMovesByState(ArrayList<ArrayList<Coord>> legalMoves) {
        ArrayList<ArrayList<Coord>> toReturn = new ArrayList<>();
        ArrayList<Coord> moves;
        for (var moveList: legalMoves) {
            moves = new ArrayList<>();
            for (int i = 0; i < moveList.size(); i++) {
                moves.add(moveList.get(i));
                if (virtualBoard[moveList.get(i).y][moveList.get(i).x] != null) {
                    // remove the rest of the moves in this direction
                    break;
                }
            }
            toReturn.add(moves);
        }
        return toReturn;
    }

    public static ArrayList<ArrayList<Coord>> filterAvailableMovesByCheck(ArrayList<ArrayList<Coord>> legalMoves) {
        Coord kingPos = null;

        ArrayList<ArrayList<Coord>> toReturn = new ArrayList<>();

        // removing all moves for the selected piece if it results in a check after the move
        for (var moveList: legalMoves) {
            ArrayList<Coord> directionToReturn = new ArrayList<>();
            for (Coord move: moveList) {
                boolean valid = true;
                virtualMove(selectedPiece.position, move);

                // finding current player's king
                boolean found = false;
                for (int row = 0; row < boardSize; row++) {
                    if (found) break;
                    for (int col = 0; col < boardSize; col++) {
                        if (virtualBoard[row][col] != null && virtualBoard[row][col].color == currentPlayer && virtualBoard[row][col].getClass() == King.class) {
                            kingPos = new Coord(col, row);
                            found = true;
                            break;
                        }
                    }
                }

                valid = !isInCheck(kingPos);

                setVirtualBoard();
                if (valid)
                    directionToReturn.add(move);
            }
            toReturn.add(directionToReturn);
        }
        return toReturn;
    }
}


class Pawn extends ChessPiece {

    private final int direction;

    public Pawn(Color color, int x, int y) {
        super(color, x, y);
        direction = color == Color.white ? -1 : 1;
    }
    @Override
    public ArrayList<ArrayList<Coord>> availableMoves() {
        ArrayList<ArrayList<Coord>> allMoves = new ArrayList<>();
        ArrayList<Coord> moves = new ArrayList<>();
        moves.add(new Coord(position.x, position.y + direction));
        if (!this.hasMoved) {
            moves.add(new Coord(position.x, position.y + 2 * direction));
        }
        allMoves.add(moves);
        int newX = position.x + 1;
        int newY = position.y + direction;
        moves = new ArrayList<>();
        if (newX >= 0 && newX <= 7 && newY >= 0 && newY <= 7) {
            if (squares[newY][newX].piece != null && squares[newY][newX].piece.color != this.color) {
                moves.add(new Coord(newX, newY));
                allMoves.add(moves);
            }

        }
        moves = new ArrayList<>();
        newX = position.x - 1;
        if (newX >= 0 && newX <= 7 && newY >= 0 && newY <= 7) {
            if (squares[newY][newX].piece != null && squares[newY][newX].piece.color != this.color) {
                moves.add(new Coord(newX, newY));
                allMoves.add(moves);
            }
        }
        return filterAvailableMovesByState(allMoves);
    }

    @Override
    public ImageIcon getPieceIcon() {
        try {
            if (color == Color.black) {
                return new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/blackPawn.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            } else {
                return new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/whitePawn.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            }
        } catch (IOException exception) {
            return null;
        }
    }
}

class Rook extends ChessPiece {
    public Rook(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public ArrayList<ArrayList<Coord>> availableMoves() {
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        return filterAvailableMovesByState(findAvailableMoves(dx, dy));
    }
    public ImageIcon getPieceIcon() {
        try {
            if (color == Color.black) {
                return new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/blackRook.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            } else {
                return new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/whiteRook.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            }
        } catch (IOException exception) {
            return null;
        }
    }
}

class Knight extends ChessPiece {
    public Knight(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public ArrayList<ArrayList<Coord>> availableMoves() {
        int[] dx = {3, -3, 2, -2, 2, 3, -2, -3};
        int[] dy = {2, 2, 3, 3, -3, -2, -3, -2};
        return filterAvailableMovesByState(findAvailableMoves(dx, dy, 1));
    }
    public ImageIcon getPieceIcon() {
        try {
            if (color == Color.black) {
                return new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/blackKnight.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            } else {
                return new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/whiteKnight.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            }
        } catch (IOException exception) {
            return null;
        }
    }
}

class Bishop extends ChessPiece {
    public Bishop(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public ArrayList<ArrayList<Coord>> availableMoves() {
        int[] dx = {1, -1, 1, -1};
        int[] dy = {1, 1, -1, -1};
        ArrayList<ArrayList<Coord>> allMoves = findAvailableMoves(dx, dy, 3);
        // horizontal movement
        int newX = this.position.x + 1;
        if (newX >= 0 && newX <= 7) {
            // add (x, newY)
            ArrayList<Coord> toAdd = new ArrayList<>();
            toAdd.add(new Coord(newX, this.position.y));
            allMoves.add(toAdd);
        }
        newX = this.position.x - 1;
        if (newX >= 0 && newX <= 7) {
            ArrayList<Coord> toAdd = new ArrayList<>();
            toAdd.add(new Coord(newX, this.position.y));
            allMoves.add(toAdd);
        }
        return allMoves;
    }
    public ImageIcon getPieceIcon() {
        try {
            if (color == Color.black) {
                return new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/blackBishop.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            } else {
                return new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/whiteBishop.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            }
        } catch (IOException exception) {
            return null;
        }
    }
}

class Queen extends ChessPiece {
    public Queen(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public ArrayList<ArrayList<Coord>> availableMoves() {
        int[] dx = {0, 0, 1, -1, 1, 1, -1, -1};
        int[] dy = {1,-1, 0, 0, 1, -1, 1, -1};
        return filterAvailableMovesByState(findAvailableMoves(dx, dy));
    }
    public ImageIcon getPieceIcon() {
        try {
            if (color == Color.black) {
                return new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/blackQueen.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            } else {
                return new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/whiteQueen.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            }
        } catch (IOException exception) {
            return null;
        }
    }
}

class King extends ChessPiece {
    public King(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public ArrayList<ArrayList<Coord>> availableMoves() {
        int[] dx = {0, 0, 1, -1, 1, 1, -1, -1};
        int[] dy = {1, -1, 0, 0, 1, -1, 1, -1};
        return filterAvailableMovesByState(findAvailableMoves(dx, dy, 1));
    }

    public ImageIcon getPieceIcon() {
        try {
            if (color == Color.black) {
                return new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/blackKing.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            } else {
                return new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/whiteKing.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            }
        } catch (IOException exception) {
            return null;
        }
    }
}
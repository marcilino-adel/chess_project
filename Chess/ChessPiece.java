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
    public Coordinate position;
    public boolean hasMoved;
    public ChessPiece(Color color, int x, int y) {
        this.color = color;
        this.position = new Coordinate(x, y);
        this.hasMoved = false;
    }

    public abstract ArrayList<ArrayList<Coordinate>> availableMoves();

    public abstract ImageIcon getPieceIcon();

    protected ArrayList<ArrayList<Coordinate>> findAvailableMoves(int[] deltaX, int[] deltaY) {
        ArrayList<ArrayList<Coordinate>> allMoves = new ArrayList<>();
        for (int i = 0; i < deltaX.length; i++) {
            ArrayList<Coordinate> movesInDirection = new ArrayList<>();
            int newX = this.position.x + deltaX[i];
            int newY = this.position.y + deltaY[i];
            while (newY <= 7 && newY >= 0 && newX <= 7 && newX >= 0) {
                movesInDirection.add(new Coordinate(newX, newY));
                newX += deltaX[i];
                newY += deltaY[i];
            }
            allMoves.add(movesInDirection);
        }
        return allMoves;
    }

    protected ArrayList<ArrayList<Coordinate>> findAvailableMoves(int[] deltaX, int[] deltaY, int limit) {
        ArrayList<ArrayList<Coordinate>> allMoves = new ArrayList<>();
        for (int i = 0; i < deltaX.length; i++) {
            ArrayList<Coordinate> movesInDirection = new ArrayList<>();
            int newX = this.position.x + deltaX[i];
            int newY = this.position.y + deltaY[i];
            int count = 0;
            while (newY <= 7 && newY >= 0 && newX <= 7 && newX >= 0 && count < limit) {
                movesInDirection.add(new Coordinate(newX, newY));
                newX += deltaX[i];
                newY += deltaY[i];
                count++;
            }
            allMoves.add(movesInDirection);
        }
        return allMoves;
    }

    protected ArrayList<ArrayList<Coordinate>> filterAvailableMovesByState(ArrayList<ArrayList<Coordinate>> legalMoves) {
        ArrayList<ArrayList<Coordinate>> toReturn = new ArrayList<>();
        ArrayList<Coordinate> moves;
        for (var moveList: legalMoves) {
            moves = new ArrayList<>();
            for (Coordinate coordinate : moveList) {
                moves.add(coordinate);
                if (virtualBoard[coordinate.y][coordinate.x] != null) {
                    break;
                }
            }
            toReturn.add(moves);
        }
        return toReturn;
    }

    public static ArrayList<ArrayList<Coordinate>> filterAvailableMovesByCheck(ArrayList<ArrayList<Coordinate>> legalMoves) {
        Coordinate kingPos = null;

        ArrayList<ArrayList<Coordinate>> toReturn = new ArrayList<>();

        // removing all moves for the selected piece if it results in a check after the move
        for (var moveList: legalMoves) {
            ArrayList<Coordinate> directionToReturn = new ArrayList<>();
            for (Coordinate move: moveList) {
                boolean valid;
                virtualMove(selectedPiece.position, move);

                // finding current player's king
                boolean found = false;
                for (int row = 0; row < BOARD_SIZE; row++) {
                    if (found) break;
                    for (int col = 0; col < BOARD_SIZE; col++) {
                        if (virtualBoard[row][col] != null && virtualBoard[row][col].color == currentPlayer && virtualBoard[row][col].getClass() == King.class) {
                            kingPos = new Coordinate(col, row);
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
    public ArrayList<ArrayList<Coordinate>> availableMoves() {
        ArrayList<ArrayList<Coordinate>> allMoves = new ArrayList<>();
        ArrayList<Coordinate> moves = new ArrayList<>();
        if (position.y + direction >= 0 && position.y + direction <= 7)
            moves.add(new Coordinate(position.x, position.y + direction));
        if (!this.hasMoved) {
            moves.add(new Coordinate(position.x, position.y + 2 * direction));
        }
        allMoves.add(moves);
        int newX = position.x + 1;
        int newY = position.y + direction;
        moves = new ArrayList<>();
        if (newX >= 0 && newX <= 7 && newY >= 0 && newY <= 7) {
            if (virtualBoard[newY][newX] != null && virtualBoard[newY][newX].color != this.color) {
                moves.add(new Coordinate(newX, newY));
                allMoves.add(moves);
            }

        }
        moves = new ArrayList<>();
        newX = position.x - 1;
        if (newX >= 0 && newX <= 7 && newY >= 0 && newY <= 7) {
            if (squares[newY][newX].piece != null && squares[newY][newX].piece.color != this.color) {
                moves.add(new Coordinate(newX, newY));
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
    public ArrayList<ArrayList<Coordinate>> availableMoves() {
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
    public ArrayList<ArrayList<Coordinate>> availableMoves() {
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
    public ArrayList<ArrayList<Coordinate>> availableMoves() {
        int[] dx = {1, -1, 1, -1};
        int[] dy = {1, 1, -1, -1};
        ArrayList<ArrayList<Coordinate>> allMoves = findAvailableMoves(dx, dy, 3);
        // horizontal movement
        int newX = this.position.x + 1;
        if (newX >= 0 && newX <= 7) {
            // add (x, newY)
            ArrayList<Coordinate> toAdd = new ArrayList<>();
            toAdd.add(new Coordinate(newX, this.position.y));
            allMoves.add(toAdd);
        }
        newX = this.position.x - 1;
        if (newX >= 0 && newX <= 7) {
            ArrayList<Coordinate> toAdd = new ArrayList<>();
            toAdd.add(new Coordinate(newX, this.position.y));
            allMoves.add(toAdd);
        }
        return allMoves;

//        return filterAvailableMovesByState(allMoves);
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
    public ArrayList<ArrayList<Coordinate>> availableMoves() {
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
    public ArrayList<ArrayList<Coordinate>> availableMoves() {
        int[] dx = {0, 0, 1, -1, 1, 1, -1, -1};
        int[] dy = {1, -1, 0, 0, 1, -1, 1, -1};
        return filterAvailableMovesByState(findAvailableMoves(dx, dy, 1));
    }

    public ArrayList<Coordinate> castlingMoves() {
        ArrayList<Coordinate> toReturn = new ArrayList<>();
        if (!this.hasMoved && !isInCheck(this.position)) { // && not in check
            int[] rookDx = {-1, 1};
            for (int direction: rookDx) {
                int rookX = (direction == -1) ? 0 : 7;
                ChessPiece rook = squares[this.position.y][rookX].piece;
                if (rook instanceof Rook && rook.color == this.color && !rook.hasMoved) {
                    boolean valid = true;
                    int count = 0;
                    for (int j = this.position.x + direction; j != rookX; j += direction) {
                        if (squares[this.position.y][j].piece != null) {
                            valid = false;
                            break;
                        }
                        if (count < 2) {
                            count++;
                            virtualMove(this.position, new Coordinate(j, this.position.y));
                            if (isInCheck(new Coordinate(j, this.position.y))) {
                                valid = false;
                                break;
                            }
                        }
                    }
                    if (valid) {
                        toReturn.add(new Coordinate(this.position.x + 2 * direction, this.position.y));
                    }
                }
            }
        }
        return toReturn;
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
package Chess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static Chess.gameEngine.squares;

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

    public abstract ArrayList<Coord> availableMoves();

    public abstract ImageIcon getPieceIcon();

    protected ArrayList<Coord> findAvailableMoves(int[] deltaX, int[] deltaY) {
        ArrayList<Coord> moves = new ArrayList<>();
        for (int i = 0; i < deltaX.length; i++) {
            int newX = this.position.x + deltaX[i];
            int newY = this.position.y + deltaY[i];
            while (newY <= 7 && newY >= 0 && newX <= 7 && newX >= 0) {
                moves.add(new Coord(newX, newY));
                newX += deltaX[i];
                newY += deltaY[i];
            }
        }
        return moves;
    }

    protected ArrayList<Coord> findAvailableMoves(int[] deltaX, int[] deltaY, int limit) {
        ArrayList<Coord> moves = new ArrayList<>();
        for (int i = 0; i < deltaX.length; i++) {
            int newX = this.position.x + deltaX[i];
            int newY = this.position.y + deltaY[i];
            int count = 0;
            while (newY <= 7 && newY >= 0 && newX <= 7 && newX >= 0 && count < limit) {
                moves.add(new Coord(newX, newY));
                newX += deltaX[i];
                newY += deltaY[i];
                count++;
            }
        }
        return moves;
    }
}


class Pawn extends ChessPiece {

    private final int direction;

    public Pawn(Color color, int x, int y) {
        super(color, x, y);
        direction = color == Color.white ? -1 : 1;
    }
    @Override
    public ArrayList<Coord> availableMoves() {
        ArrayList<Coord> moves = new ArrayList<>();
        moves.add(new Coord(position.x, position.y + direction));
        if (!this.hasMoved) {
            moves.add(new Coord(position.x, position.y + 2 * direction));
        }
        int newX = position.x + 1;
        int newY = position.y + direction;
        if (newX <= 7 && newY >=0 && newY <= 7) {
            if (squares[newY][newX].piece != null && squares[newY][newX].piece.color != this.color) moves.add(new Coord(newX, newY));
            newX = position.x - 1;
            if (newX >= 0)
                if (squares[newY][newX].piece != null && squares[newY][newX].piece.color != this.color) moves.add(new Coord(newX, newY));
        }
        return moves;
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
    public ArrayList<Coord> availableMoves() {
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        return findAvailableMoves(dx,dy);
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
    public ArrayList<Coord> availableMoves() {
        int[] dx = {3, -3, 2, -2, 2, 3, -2, -3};
        int[] dy = {2, 2, 3, 3, -3, -2, -3, -2};
        return findAvailableMoves(dx, dy, 1);
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
    public ArrayList<Coord> availableMoves() {
        int[] dx = {1, -1, 1, -1};
        int[] dy = {1, 1, -1, -1};
        return findAvailableMoves(dx, dy, 3);
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
    public ArrayList<Coord> availableMoves() {
        int[] dx = {0, 0, 1, -1, 1, 1, -1, -1};
        int[] dy = {1,-1, 0, 0, 1, -1, 1, -1};
        return findAvailableMoves(dx,dy);
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
    public ArrayList<Coord> availableMoves() {
        int[] dx = {0, 0, 1, -1, 1, 1, -1, -1};
        int[] dy = {1, -1, 0, 0, 1, -1, 1, -1};
        return findAvailableMoves(dx, dy, 1);
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
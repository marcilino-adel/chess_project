package Chess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
}

class Pawn extends ChessPiece {

    public Pawn(Color color, int x, int y) {
        super(color, x, y);
    }
    @Override
    public ArrayList<Coord> availableMoves() {
        return null;
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
        ArrayList<Coord> moves = new ArrayList<>();
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        for (int i = 0; i < dx.length; i++) {
            int newX = this.position.x + dx[i];
            int newY = this.position.y + dy[i];
            while (newY <= 7 && newY >= 0 && newX <= 7 && newX >= 0) {
                moves.add(new Coord(newX, newY));
                newX += dx[i];
                newY += dy[i];
            }
        }
        return moves;
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
        return null;
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
        ArrayList<Coord> moves = new ArrayList<>();
        int[] dx = {1, -1, 1, -1};
        int[] dy = {1, 1, -1, -1};
        for (int i = 0; i < dx.length; i++){
            int newx = this.position.x + dx[i];
            int newy = this.position.y + dy[i];
            while (newy <= 7 && newy >= 0 && newx <= 7 && newx >= 0){
                moves.add(new Coord(newx, newy));
                newx += dx[i];
                newy += dy[i];
            }
        }
        return moves;
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
        ArrayList<Coord> moves = new ArrayList<>();
        int[] dx={0,0,1,-1,1,1,-1,-1};
        int[] dy={1,-1,0,0,1,-1,1,-1};
        for (int i=0;i<=dx.length;i++){
            int newx=this.position.x;
            int newy=this.position.y;
            while (newy <= 7 && newy >= 0 && newx <= 7 && newx >= 0){
                moves.add(new Coord(newx,newy));
                newx+=dx[i];
                newy+=dy[i];
            }
        }
        return moves;
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
        return null;
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
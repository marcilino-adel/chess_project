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
                return new ImageIcon(ImageIO.read(new File("/Media/Icons/blackPawn.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            } else {
                return new ImageIcon(ImageIO.read(new File("/Media/Icons/whitePawn.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
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
        return null;
    }
    public ImageIcon getPieceIcon() {
        try {
            if (color == Color.black) {
                return new ImageIcon(ImageIO.read(new File("/Media/Icons/blackRook.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            } else {
                return new ImageIcon(ImageIO.read(new File("/Media/Icons/whiteRook.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
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
                return new ImageIcon(ImageIO.read(new File("/Media/Icons/blackKnight.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            } else {
                return new ImageIcon(ImageIO.read(new File("/Media/Icons/whiteKnight.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
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
        return null;
    }
    public ImageIcon getPieceIcon() {
        try {
            if (color == Color.black) {
                return new ImageIcon(ImageIO.read(new File("/Media/Icons/blackBishop.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            } else {
                return new ImageIcon(ImageIO.read(new File("/Media/Icons/whiteBishop.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
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
        return null;
    }
    public ImageIcon getPieceIcon() {
        try {
            if (color == Color.black) {
                return new ImageIcon(ImageIO.read(new File("/Media/Icons/blackQueen.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            } else {
                return new ImageIcon(ImageIO.read(new File("/Media/Icons/whiteQueen.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
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
                return new ImageIcon(ImageIO.read(new File("/Media/Icons/blackKing.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            } else {
                return new ImageIcon(ImageIO.read(new File("/Media/Icons/whiteKing.png")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            }
        } catch (IOException exception) {
            return null;
        }
    }
}
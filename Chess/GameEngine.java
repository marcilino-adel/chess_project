package Chess;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameEngine extends JPanel {
    public static ChessPiece whiteKing;
    public static ChessPiece blackKing;
    public static final int boardSize = 8;
    public static Color currentPlayer = Color.white;
    public static ChessPiece selectedPiece;
    public static ArrayList<Coord> legalMoves;
    public static JPanel playingBoard;
    public static ChessSquare[][] squares;

    public GameEngine() {

    }
}

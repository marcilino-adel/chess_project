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

    public GameEngine() { squares=new ChessSquare[8][8];
        playingBoard = new JPanel(new GridLayout(8, 8));
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                squares[i][j] = new ChessSquare();
                if ((i + j) % 2 == 0) {
                    squares[i][j].setBackground(Color.WHITE);
                } else {
                    squares[i][j].setBackground(Color.BLACK);
                }
                playingBoard.add(squares[i][j]);
            }

        }
        JFrame window=new JFrame();
        window.getContentPane().add(playingBoard);
        window.setSize(600,600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);


    }

}
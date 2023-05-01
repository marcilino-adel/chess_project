package Chess;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class gameEngine extends JFrame {
    public static final int boardSize = 8;
    public static Color currentPlayer = Color.white;
    public static ChessPiece selectedPiece;
    public static ArrayList<Coord> legalMoves;
    public static JPanel playingBoard;
    public static JPanel informationBoard;
    public static ChessSquare[][] squares = new ChessSquare[boardSize][boardSize];

    public gameEngine() {
        setTitle("Chess Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        playingBoard = new JPanel(new GridLayout(boardSize, boardSize));
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                squares[i][j] = new ChessSquare();
                if ((i + j) % 2 != 0) {
                    squares[i][j].setBackground(Color.WHITE);
                } else {
                    squares[i][j].setBackground(Color.BLACK);
                }
                squares[i][j].piece = new Rook(Color.black, j, i);
                playingBoard.add(squares[i][j]);
                squares[i][j].piece=new Rook(Color.white,j,i);
            }
            }


        add(playingBoard);
        setVisible(true);

    }

    public static void main(String[] args) {
        new gameEngine();
    }
}
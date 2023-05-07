package Chess;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class gameEngine extends JFrame {
    public static final int boardSize = 8;
    public static Color currentPlayer = Color.white;
    public static ChessPiece selectedPiece;
    public static ArrayList<Coord> legalMoves;
    public static JPanel playingBoard;
    public static JPanel informationPanel;
    private static JPanel deadBlackPanel = new JPanel(new GridLayout(3, 5));
    private static JPanel deadWhitePanel = new JPanel(new GridLayout(3, 5));
    private static JLabel[][] deadWhiteLabels = new JLabel[3][5];
    private static JLabel[][] deadBlackLabels = new JLabel[3][5];
    public static ChessSquare[][] squares = new ChessSquare[boardSize][boardSize];

    public gameEngine() {
        setTitle("Chess Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        GridLayout layout = new GridLayout(boardSize, boardSize);
        playingBoard = new JPanel(layout);

        // initialize board layout
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                squares[row][col] = new ChessSquare(col, row);
                if ((row + col) % 2 != 0) {
                    squares[row][col].setBackground(Color.black);
                } else {
                    squares[row][col].setBackground(Color.white);
                }
                playingBoard.add(squares[row][col]);
               if (row == 1)
                   squares[row][col].piece=new Pawn(Color.black,col,row);
               else if (row == 6) {
                   squares[row][col].piece=new Pawn(Color.white,col,row);
               }
               else if(row == 0 && (col == 0 || col == 7))
                   squares[row][col].piece=new Rook(Color.black,col,row);
               else if (row == 0 && (col == 1 || col == 6))
                   squares[row][col].piece=new Knight(Color.black,col,row);
               else if(row == 0 && (col == 2 || col == 5))
                   squares[row][col].piece=new Bishop(Color.black,col,row);
               else if(row == 0 && col == 4)
                   squares[row][col].piece=new King(Color.black,col,row);
               else if(row == 0)
                   squares[row][col].piece=new Queen(Color.black,col,row);
               // Set the second half
               else if(row == 7 && (col == 0 || col == 7))
                   squares[row][col].piece=new Rook(Color.white,col,row);
               else if(row == 7 && (col == 1 || col == 6))
                       squares[row][col].piece=new Knight(Color.white,col,row);
               else if(row == 7 && (col == 2 || col == 5))
                   squares[row][col].piece=new Bishop(Color.white,col,row);
               else if(row == 7 && col == 4)
                   squares[row][col].piece=new King(Color.white,col,row);
               else if(row == 7)
                   squares[row][col].piece=new Queen(Color.white,col,row);

            }
        }

        add(playingBoard);
        setVisible(true);

        // initialize information panel
        informationPanel = new JPanel(new GridLayout(4, 1));
        JLabel blackLabel = new JLabel("Player 1");
        JLabel whiteLabel = new JLabel("Player 2");

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 5; col++) {
                deadBlackLabels[row][col] = new JLabel();
                deadWhiteLabels[row][col] = new JLabel();
                deadWhitePanel.add(deadWhiteLabels[row][col]);
                deadBlackPanel.add(deadBlackLabels[row][col]);
            }
        }
        informationPanel.add(blackLabel);
        informationPanel.add(deadBlackPanel);
        informationPanel.add(deadWhitePanel);
        informationPanel.add(whiteLabel);
        add(informationPanel, BorderLayout.EAST);
    }

    public static void movePiece(Coord initPos, Coord finalPos) {
        if (squares[finalPos.y][finalPos.x].piece != null) killPiece(finalPos);
        selectedPiece.hasMoved = true;
        squares[finalPos.y][finalPos.x].piece = selectedPiece;
        selectedPiece.position = finalPos;
        squares[initPos.y][initPos.x].piece = null;
        deColorAvailableMoves();
        selectedPiece = null;
        currentPlayer = currentPlayer == Color.black ? Color.white : Color.black;
        playingBoard.revalidate();
        playingBoard.repaint();
    }
    public static void colorAvailableMoves() {
        // modify further to color red or green based on the state of the board
        for (Coord move: legalMoves) {
            squares[move.y][move.x].setBackground(Color.green);
        }
        playingBoard.repaint();
        playingBoard.revalidate();
    }
    public static void deColorAvailableMoves() {
        for (Coord move: legalMoves) {
            squares[move.y][move.x].setBackground((move.y + move.x) % 2 != 0 ? Color.black : Color.white);
        }
        legalMoves = null;
    }
    private static void killPiece(Coord finalPos) {
        boolean found = false;
        Image icon = squares[finalPos.y][finalPos.x].piece.getPieceIcon().getImage();
        Image resizedIcon = icon.getScaledInstance(20, 30, Image.SCALE_SMOOTH);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 5; col++) {
                if (currentPlayer == Color.white) {
                    if (deadBlackLabels[row][col].getIcon() == null) {
                        deadBlackLabels[row][col].setIcon(new ImageIcon(resizedIcon));
                        found = true;
                        break;
                    }
                } else {
                    if (deadWhiteLabels[row][col].getIcon() == null) {
                        deadWhiteLabels[row][col].setIcon(new ImageIcon(resizedIcon));
                        found = true;
                        break;
                    }
                }
            }
            if (found) break;
        }
    }
}
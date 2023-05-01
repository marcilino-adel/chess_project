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
                playingBoard.add(squares[i][j]);
               if(i==1)
                   squares[i][j].piece=new Pawn(Color.white,j,i);
               else if (i==6) {
                   squares[i][j].piece=new Pawn(Color.black,j,i);
               }
               else if(i==0&&(j==0||j==7))
                   squares[i][j].piece=new Rook(Color.white,i,j);
               else if(i==0&&(j==1||j==6))
                   squares[i][j].piece=new Knight(Color.white,i,j);
               else if(i==0&&(j==2||j==5))
                   squares[i][j].piece=new Bishop(Color.white,i,j);
               else if(i==0&&j==3)
                   squares[i][j].piece=new King(Color.white,i,j);
               else if(i==0&&j==4)
                   squares[i][j].piece=new Queen(Color.white,i,j);
                   //Set the second half
               else if(i==7&&(j==0||j==7))
                   squares[i][j].piece=new Rook(Color.black,i,j);
               else if(i==7&&(j==1||j==6))
                   squares[i][j].piece=new Knight(Color.black,i,j);
               else if(i==7&&(j==2||j==5))
                   squares[i][j].piece=new Bishop(Color.black,i,j);
               else if(i==7&&j==3)
                   squares[i][j].piece=new King(Color.black,i,j);
               else if(i==7&&j==4)
                   squares[i][j].piece=new Queen(Color.black,i,j);

            }
            }


        add(playingBoard);
        setVisible(true);

    }

    public static void main(String[] args) {
        new gameEngine();
    }
}
package Chess;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


import static Chess.ChessPiece.filterAvailableMovesByCheck;

public class gameEngine extends JFrame {
    public static boolean isCastling;
    public static final int CHECKMATE = 0;
    public static final int TIMEOUT = 1;
    public static final int STALEMATE = 2;
    public static final int BOARD_SIZE = 8;
    public static Color currentPlayer = Color.white;
    public static ChessPiece selectedPiece;
    public static ArrayList<ArrayList<Coord>> legalMoves;
    public static JPanel playingBoard;
    public static JPanel informationPanel;
    private static MyTimer blackTimer = new MyTimer();
    private static MyTimer whiteTimer = new MyTimer();
    private static JPanel deadBlackPanel = new JPanel(new GridLayout(3, 5));
    private static JPanel deadWhitePanel = new JPanel(new GridLayout(3, 5));
    private static JLabel[][] deadWhiteLabels = new JLabel[3][5];
    private static JLabel[][] deadBlackLabels = new JLabel[3][5];
    public static ChessSquare[][] squares = new ChessSquare[BOARD_SIZE][BOARD_SIZE];
    public static ChessPiece[][] virtualBoard = new ChessPiece[BOARD_SIZE][BOARD_SIZE];

    public gameEngine() {
        setTitle("Chess Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        GridLayout layout = new GridLayout(BOARD_SIZE, BOARD_SIZE);
        playingBoard = new JPanel(layout);

        // initialize board layout
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
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
        informationPanel = new JPanel(new GridLayout(6, 1));
        JLabel blackLabel = new JLabel("player 1");
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
        informationPanel.add(blackTimer);
        informationPanel.add(whiteTimer);
        informationPanel.add(deadWhitePanel);
        informationPanel.add(whiteLabel);
        add(informationPanel, BorderLayout.EAST);
    }

    public static void movePiece(Coord initPos, Coord finalPos) {
        // moves the piece, updates the board, checks for checkmate, changes the current player and plays a sound effect
        if (isCastling) {
            // call castling move method
            castle(initPos, finalPos);
        } else {
            SoundEffect toPlay;
            if (squares[finalPos.y][finalPos.x].piece != null) {
                killPiece(finalPos);
                toPlay = new SoundEffect("capture.wav");
            } else toPlay = new SoundEffect("move.wav");
            selectedPiece.hasMoved = true;
            squares[finalPos.y][finalPos.x].piece = selectedPiece;
            selectedPiece.position = finalPos;
            squares[initPos.y][initPos.x].piece = null;
            toPlay.play();
        }
        deColorAvailableMoves();
        selectedPiece = null;
        currentPlayer = currentPlayer == Color.black ? Color.white : Color.black;
        playingBoard.revalidate();
        playingBoard.repaint();

        // change timers
        if (currentPlayer == Color.white) {
            whiteTimer.unpause();
            blackTimer.pause();
        } else {
            whiteTimer.pause();
            blackTimer.unpause();
        }

        // check for checkmate
        setVirtualBoard();
        if (isCheckMate()) {
            endGame(CHECKMATE);
        }
    }

    public static void colorAvailableMoves() {
        for (var moveList: legalMoves) {
            for (Coord move : moveList) {
                squares[move.y][move.x].setBackground(Color.green);
            }
        }
        playingBoard.repaint();
        playingBoard.revalidate();
    }

    public static void deColorAvailableMoves() {
        for (var moveList: legalMoves) {
            for (Coord move : moveList) {
                squares[move.y][move.x].setBackground((move.y + move.x) % 2 != 0 ? Color.black : Color.white);
            }
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

    private static void promotePawn() {

    }

    public static void setVirtualBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                virtualBoard[row][col] = squares[row][col].piece;
            }
        }
    }
    public static void virtualMove(Coord initPos, Coord finalPos) {
        if (virtualBoard[finalPos.y][finalPos.x] != null && virtualBoard[finalPos.y][finalPos.x].color == virtualBoard[initPos.y][initPos.x].color)
            return;
        virtualBoard[finalPos.y][finalPos.x] = virtualBoard[initPos.y][initPos.x];
        virtualBoard[initPos.y][initPos.x] = null;
    }
    private static boolean isCheckMate() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (virtualBoard[row][col] != null && virtualBoard[row][col].color == currentPlayer) {
                    selectedPiece = virtualBoard[row][col];
                    ArrayList<ArrayList<Coord>> availableMoves = selectedPiece.availableMoves();
                    availableMoves = filterAvailableMovesByCheck(availableMoves);
                    for (var list: availableMoves) {
                        if (list.size() != 0) {
                            selectedPiece = null;
                            return false;
                        }
                    }
                }
            }
        }
        selectedPiece = null;
        return true;
    }
    public static boolean isInCheck(Coord kingPos) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (virtualBoard[row][col] != null && virtualBoard[row][col].color != currentPlayer) {
                    ArrayList<ArrayList<Coord>> moves = virtualBoard[row][col].availableMoves();
                    for (var list: moves) {
                        for (var move: list) {
                            if (move.y == kingPos.y && move.x == kingPos.x) return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public static void endGame(int condition) {
        if (condition == CHECKMATE) {
            // end by checkmate
            if (currentPlayer == Color.white) {
                JOptionPane.showMessageDialog(null, "Black wins by checkmate");
            } else {
                JOptionPane.showMessageDialog(null, "White wins by checkmate");
            }
        } else if (condition == TIMEOUT) {
            // end by timeout
            if (currentPlayer == Color.white) {
                JOptionPane.showMessageDialog(null, "Black wins by timeout");
            } else {
                JOptionPane.showMessageDialog(null, "White wins by timeout");
            }
        } else if (condition == STALEMATE) {
            // end by stalemate
            JOptionPane.showMessageDialog(null, "STALEMATE, we're all winners here");
        }
        // call a method to
        // disable everything, buttons, timers ....
        stopEverything();
    }
    private static void stopEverything() {
        blackTimer.pause();
        whiteTimer.pause();
        for (var row: squares) {
            for (var square: row) {
                square.setEnabled(false);
            }
        }
    }
    private static void castle(Coord initPos, Coord finalPos) {
        // add a new soundtrack for castling
        selectedPiece.hasMoved = true;
        squares[finalPos.y][finalPos.x].piece = selectedPiece;
        selectedPiece.position = finalPos;
        squares[initPos.y][initPos.x].piece = null;
        if (finalPos.x == 6) {
            // king side castling
            squares[finalPos.y][5].piece = squares[finalPos.y][7].piece;
            squares[finalPos.y][7].piece = null;
            squares[finalPos.y][5].piece.position = new Coord(5, finalPos.y);
            squares[finalPos.y][5].piece.hasMoved = true;
        } else {
            // queen side castling
            squares[finalPos.y][3].piece = squares[finalPos.y][0].piece;
            squares[finalPos.y][0].piece = null;
            squares[finalPos.y][3].piece.position = new Coord(3, finalPos.y);
            squares[finalPos.y][3].piece.hasMoved = true;
        }

        SoundEffect toPlay = new SoundEffect("move.wav");
        toPlay.play();
    }
    public static void main(String[] argv) {
        new gameEngine();
    }
}
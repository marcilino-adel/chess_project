package Chess;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
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
    public static ArrayList<ArrayList<Coordinate>> legalMoves;
    public static JPanel playingBoard;
    public static JPanel informationPanel;
    private static final MyTimer blackTimer = new MyTimer();
    private static final MyTimer whiteTimer = new MyTimer();
    private static final JPanel deadBlackPanel = new JPanel(new GridLayout(3, 5));
    private static final JPanel deadWhitePanel = new JPanel(new GridLayout(3, 5));
    private static final JLabel[][] deadWhiteLabels = new JLabel[3][5];
    private static final JLabel[][] deadBlackLabels = new JLabel[3][5];
    private static final JLayeredPane layeredPane = new JLayeredPane();
    private static final JPanel promotionPanel = new JPanel();
    private static final JPanel endGamePanel = new JPanel();
    public static ChessSquare[][] squares = new ChessSquare[BOARD_SIZE][BOARD_SIZE];
    public static ChessPiece[][] virtualBoard = new ChessPiece[BOARD_SIZE][BOARD_SIZE];

    public gameEngine() {
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Chess/Media/Font/Kitami.ttf"));
        } catch (Exception ignored) {}

        setTitle("Chess Game");
        setResizable(false);
        setLayout(null);
        setSize(900, 672);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (customFont != null) {
            blackTimer.setFont(customFont);
        }
        blackTimer.setForeground(Color.white);
        if (customFont != null) {
            whiteTimer.setFont(customFont);
        }
        whiteTimer.setForeground(Color.black);

        JLabel background = new JLabel();
        try {
            background.setIcon(new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/mainBoardBg.png"))));
        } catch (Exception ignored) {}
        background.setBounds(0, 0, getWidth(), getHeight());

        layeredPane.setBounds(0, -15, getWidth(), getHeight());
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        GridLayout layout = new GridLayout(BOARD_SIZE, BOARD_SIZE);
        playingBoard = new JPanel(layout);

        // initialize board layout
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                squares[row][col] = new ChessSquare(col, row);
                squares[row][col].setBackground(new Color(1, 1, 1,0));
                squares[row][col].setBorder(null);
                squares[row][col].setOpaque(false);
                playingBoard.add(squares[row][col]);
               if (row == 1)
                   squares[row][col].piece = new Pawn(Color.black, col, row);
               else if (row == 6) {
                   squares[row][col].piece = new Pawn(Color.white, col, row);
               }
               else if(row == 0 && (col == 0 || col == 7))
                   squares[row][col].piece = new Rook(Color.black, col, row);
               else if (row == 0 && (col == 1 || col == 6))
                   squares[row][col].piece = new Knight(Color.black, col, row);
               else if(row == 0 && (col == 2 || col == 5))
                   squares[row][col].piece = new Bishop(Color.black, col, row);
               else if(row == 0 && col == 4)
                   squares[row][col].piece = new King(Color.black, col, row);
               else if(row == 0)
                   squares[row][col].piece = new Queen(Color.black, col, row);
               // Set the second half
               else if(row == 7 && (col == 0 || col == 7))
                   squares[row][col].piece = new Rook(Color.white, col, row);
               else if(row == 7 && (col == 1 || col == 6))
                       squares[row][col].piece = new Knight(Color.white, col, row);
               else if(row == 7 && (col == 2 || col == 5))
                   squares[row][col].piece = new Bishop(Color.white, col, row);
               else if(row == 7 && col == 4)
                   squares[row][col].piece = new King(Color.white, col, row);
               else if(row == 7)
                   squares[row][col].piece = new Queen(Color.white, col, row);

            }
        }
        playingBoard.setPreferredSize(new Dimension(593, getHeight()));
        playingBoard.setOpaque(false);
        playingBoard.setBorder(new EmptyBorder(66, 50, 64, 0));


        // initialize information panel
        informationPanel = new JPanel(new GridLayout(3, 1, 20, 30));
        informationPanel.setPreferredSize(new Dimension(300, getHeight()));
        informationPanel.setBorder(new EmptyBorder(81, 0, 74, 0));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 5; col++) {
                deadBlackLabels[row][col] = new JLabel();
                deadWhiteLabels[row][col] = new JLabel();
                deadWhitePanel.add(deadWhiteLabels[row][col]);
                deadBlackPanel.add(deadBlackLabels[row][col]);
            }
        }

        deadWhitePanel.setOpaque(false);
        deadBlackPanel.setOpaque(false);

        informationPanel.add(deadWhitePanel);

        JPanel timersPanel = new JPanel(new GridLayout(2, 1));
        timersPanel.add(blackTimer);
        timersPanel.add(whiteTimer);
        timersPanel.setOpaque(true);
        timersPanel.setBackground(Color.darkGray);
        informationPanel.add(timersPanel);

        informationPanel.add(deadBlackPanel);

        informationPanel.setOpaque(false);


        JPanel bothPanels = new JPanel(new BorderLayout(28, 0));
        bothPanels.setBounds(0, 0, getWidth(), getHeight());
        bothPanels.add(playingBoard, BorderLayout.WEST);
        bothPanels.add(informationPanel, BorderLayout.EAST);
        bothPanels.setOpaque(false);
        layeredPane.add(bothPanels, JLayeredPane.PALETTE_LAYER);

        add(layeredPane);
        setVisible(true);


        // set up Promotion Panel



        // set up End Game Panel

    }

    public static void movePiece(Coordinate initPos, Coordinate finalPos) {
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
            selectedPiece.position = finalPos;
            squares[finalPos.y][finalPos.x].piece = selectedPiece;
            squares[initPos.y][initPos.x].piece = null;
            toPlay.play();
        }
        playingBoard.revalidate();
        playingBoard.repaint();
        if (selectedPiece instanceof Pawn) {
            promotePawn();
        }
        deColorAvailableMoves();
        selectedPiece = null;
        currentPlayer = currentPlayer == Color.black ? Color.white : Color.black;

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
            for (Coordinate move : moveList) {
                squares[move.y][move.x].setBorder(new LineBorder(Color.green, 7));
                if (squares[move.y][move.x].piece != null && squares[move.y][move.x].piece.color == currentPlayer) {
                    squares[move.y][move.x].setBorder(new LineBorder(Color.red, 7));
                }
            }
        }
        playingBoard.repaint();
        playingBoard.revalidate();
    }

    public static void deColorAvailableMoves() {
        for (var moveList: legalMoves) {
            for (Coordinate move : moveList) {
                squares[move.y][move.x].setBorder(null);
            }
        }
        legalMoves = null;
    }

    private static void killPiece(Coordinate finalPos) {
        boolean found = false;
        ImageIcon icon = squares[finalPos.y][finalPos.x].piece.getPieceIcon();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 5; col++) {
                if (currentPlayer == Color.white) {
                    if (deadBlackLabels[2 - row][col].getIcon() == null) {
                        deadBlackLabels[2 - row][col].setIcon(icon);
                        found = true;
                        break;
                    }
                } else {
                    if (deadWhiteLabels[row][col].getIcon() == null) {
                        deadWhiteLabels[row][col].setIcon(icon);
                        found = true;
                        break;
                    }
                }
            }
            if (found) break;
        }
    }

    private static void promotePawn() {
        int pawnY = selectedPiece.color != Color.white ? 7 : 0;
        if (selectedPiece.position.y == pawnY) {
            // make the user select the piece to be promoted to
            Object[] options = {"Queen", "Rook", "Bishop", "Knight"};
            int selection = JOptionPane.showOptionDialog(
                    null,
                    "Choose a piece to promote to:",
                    "Promotion",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (selection == 0)
                squares[selectedPiece.position.y][selectedPiece.position.x].piece = new Queen(selectedPiece.color, selectedPiece.position.x, selectedPiece.position.y);
            else if (selection == 1)
                squares[selectedPiece.position.y][selectedPiece.position.x].piece = new Rook(selectedPiece.color, selectedPiece.position.x, selectedPiece.position.y);
            else if (selection == 2)
                squares[selectedPiece.position.y][selectedPiece.position.x].piece = new Bishop(selectedPiece.color, selectedPiece.position.x, selectedPiece.position.y);
            else
                squares[selectedPiece.position.y][selectedPiece.position.x].piece = new Knight(selectedPiece.color, selectedPiece.position.x, selectedPiece.position.y);
        }
    }

    public static void setVirtualBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                virtualBoard[row][col] = squares[row][col].piece;
            }
        }
    }

    public static void virtualMove(Coordinate initPos, Coordinate finalPos) {
        if (virtualBoard[finalPos.y][finalPos.x] != null && virtualBoard[finalPos.y][finalPos.x].color == virtualBoard[initPos.y][initPos.x].color)
            return;
        virtualBoard[finalPos.y][finalPos.x] = virtualBoard[initPos.y][initPos.x];
        virtualBoard[initPos.y][initPos.x] = null;
    }

    public static boolean isCheckMate() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (virtualBoard[row][col] != null && virtualBoard[row][col].color == currentPlayer) {
                    selectedPiece = virtualBoard[row][col];
                    ArrayList<ArrayList<Coordinate>> availableMoves = selectedPiece.availableMoves();
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

    public static boolean isInCheck(Coordinate kingPos) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (virtualBoard[row][col] != null && virtualBoard[row][col].color != currentPlayer) {
                    ArrayList<ArrayList<Coordinate>> moves = virtualBoard[row][col].availableMoves();
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
//        for (var row: squares) {
//            for (var square: row) {
//                square.setEnabled(false);
//            }
//        }
    }

    private static void castle(Coordinate initPos, Coordinate finalPos) {
        // add a new soundtrack for castling
        selectedPiece.hasMoved = true;
        squares[finalPos.y][finalPos.x].piece = selectedPiece;
        selectedPiece.position = finalPos;
        squares[initPos.y][initPos.x].piece = null;
        if (finalPos.x == 6) {
            // king side castling
            squares[finalPos.y][5].piece = squares[finalPos.y][7].piece;
            squares[finalPos.y][7].piece = null;
            squares[finalPos.y][5].piece.position = new Coordinate(5, finalPos.y);
            squares[finalPos.y][5].piece.hasMoved = true;
        } else {
            // queen side castling
            squares[finalPos.y][3].piece = squares[finalPos.y][0].piece;
            squares[finalPos.y][0].piece = null;
            squares[finalPos.y][3].piece.position = new Coordinate(3, finalPos.y);
            squares[finalPos.y][3].piece.hasMoved = true;
        }

        SoundEffect toPlay = new SoundEffect("move.wav");
        toPlay.play();
    }

    public static void main(String[] argv) {
        new gameEngine();
    }
}
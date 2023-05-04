package Chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Chess.gameEngine.*;

public class ChessSquare extends JButton implements ActionListener {
    public ChessPiece piece;
    public Coord position;

    public ChessSquare(int x, int y) {
        this.position = new Coord(x, y);
       this.addActionListener(this);
    }

    public ImageIcon getIcon() {
        if (this.piece != null) return this.piece.getPieceIcon();
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (selectedPiece == null) {
            if (this.piece != null) {
                selectedPiece = this.piece;
                legalMoves = selectedPiece.availableMoves();
                for (Coord move: legalMoves) {
                    squares[move.y][move.x].setBackground(Color.green);
                }
            }
            return;
        }
        if (this.piece != null) {
            if (this.piece.color == currentPlayer) {
                selectedPiece = this.piece;
                for (Coord move: legalMoves) {
                    squares[move.y][move.x].setBackground(((move.x + move.y) % 2 != 0 ? Color.black : Color.white));
                }
                legalMoves = selectedPiece.availableMoves();
                for (Coord move : legalMoves) {
                    squares[move.y][move.x].setBackground(Color.green);
                }
                return;
            }
        };
        boolean found = false;
        for (Coord move : legalMoves) {
            if (move.x == this.position.x && move.y == this.position.y) {
                found = true;
                break;
            }
            }
        if (!found) {
            squares[selectedPiece.position.y][selectedPiece.position.x].shakeButton();
            return;
        }
        squares[selectedPiece.position.y][selectedPiece.position.x].piece = null;
        for (Coord move: legalMoves) {
            squares[move.y][move.x].setBackground(((move.x + move.y) % 2 != 0 ? Color.black : Color.white));
        }
        legalMoves = null;
        // add this.piece to an array of eaten pieces
        this.piece = selectedPiece;
        selectedPiece.position = this.position;
        selectedPiece = null;
        currentPlayer = currentPlayer == Color.black ? Color.white : Color.black;
        playingBoard.revalidate();
        playingBoard.repaint();
    }

    public void shakeButton() {
        int delay = 50; // delay in milliseconds
        int numShakes = 7;
        int dx = 3;
        Timer timer = new Timer(delay, new ActionListener() {
            int count = 0;
            int sign = 1;
            final int originalX = getLocation().x;
            public void actionPerformed(ActionEvent e) {
                setLocation(originalX + sign * dx, getLocation().y);
                count++;
                if (count == numShakes) {
                    ((Timer) e.getSource()).stop();
                    setLocation(originalX, getLocation().y);
                }
                sign = -sign;
            }
        });
        timer.start();
    }
}

package Chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Chess.gameEngine.*;
import static Chess.ChessPiece.filterAvailableMovesByCheck;

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
        setVirtualBoard();
        if (selectedPiece == null) {
            if (this.piece != null && this.piece.color == currentPlayer) {
                selectedPiece = this.piece;
                legalMoves = selectedPiece.availableMoves();
                legalMoves = filterAvailableMovesByCheck(legalMoves);
                gameEngine.colorAvailableMoves();
            }
            return;
        }
        if (this.piece != null) {
            if (this.piece.color == currentPlayer) {
                selectedPiece = this.piece;
                gameEngine.deColorAvailableMoves();
                legalMoves = selectedPiece.availableMoves();
                legalMoves = filterAvailableMovesByCheck(legalMoves);
                gameEngine.colorAvailableMoves();
                return;
            }
        }
        if (this.getBackground() != Color.green) {
            squares[selectedPiece.position.y][selectedPiece.position.x].shakeButton();
            return;
        }
        gameEngine.movePiece(selectedPiece.position, this.position);
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

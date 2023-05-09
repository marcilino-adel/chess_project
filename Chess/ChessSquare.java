package Chess;

import javax.swing.*;
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
        // set the selected piece if the square selected contains a piece of the right color
        if (selectedPiece == null) {
            if (this.piece != null && this.piece.color == currentPlayer) {
                selectedPiece = this.piece;
                legalMoves = selectedPiece.availableMoves();
                legalMoves = filterAvailableMovesByCheck(legalMoves);
                if (selectedPiece instanceof King) {
                    legalMoves.add(((King) selectedPiece).castlingMoves());
                }
                gameEngine.colorAvailableMoves();
            }
            return;
        }
        if (this.piece != null && this.piece.color == currentPlayer) {
            selectedPiece = this.piece;
            gameEngine.deColorAvailableMoves();
            legalMoves = selectedPiece.availableMoves();
            legalMoves = filterAvailableMovesByCheck(legalMoves);
            if (selectedPiece instanceof King) {
                legalMoves.add(((King) selectedPiece).castlingMoves());
            }
            gameEngine.colorAvailableMoves();
            return;
        }

        // search for this square in the selected piece's legal moves
        boolean found = false;
        for (int i = 0; i < legalMoves.size(); i++) {
            if (found) break;
            for (int j = 0; j < legalMoves.get(i).size(); j++) {
                if (legalMoves.get(i).get(j).y == this.position.y && legalMoves.get(i).get(j).x == this.position.x) {
                    isCastling = false;
                    if (selectedPiece instanceof King && i == legalMoves.size() - 1 && j == legalMoves.get(i).size() - 1) {
                        isCastling = true;
                    }
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            squares[selectedPiece.position.y][selectedPiece.position.x].shakeButton();
            return;
        }
        gameEngine.movePiece(selectedPiece.position, this.position);
    }

    private void shakeButton() {
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

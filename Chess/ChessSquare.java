package Chess;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Chess.gameEngine.*;

public class ChessSquare extends JButton implements ActionListener {
    // Ibrahim was here 8:30 pm
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
            if (this.piece != null)
            selectedPiece = this.piece;
            return;
        }
        squares[selectedPiece.position.y][selectedPiece.position.x].piece = null;
        // add this.piece to an array of eaten pieces
        this.piece = selectedPiece;
        selectedPiece.position = this.position;
        selectedPiece = null;
        playingBoard.revalidate();
        playingBoard.repaint();
    }
}

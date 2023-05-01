package Chess;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChessSquare extends JButton implements MouseListener {
    // Ibrahim was here 8:30 pm
    public ChessPiece piece;
    public Coord position;

    public ChessSquare() {}

    public ImageIcon getIcon() {
        if (this.piece != null) return this.piece.getPieceIcon();
        return null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

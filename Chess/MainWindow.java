package Chess;

import javax.swing.*;

import static Chess.GameEngine.squares;

public class MainWindow {
    public static void main (String [] args){
        JFrame mainFrame = new JFrame();
        mainFrame.setVisible(true);
        mainFrame.setSize(1280,640);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(squares[0][0]);


    }
}

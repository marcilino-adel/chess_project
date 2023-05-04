

package Chess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import static Chess.gameEngine.*;

public class TheInterfaces implements ActionListener {
    private static JFrame window;
    private static JButton rabid;
    private static JLayeredPane layeredPane;

    public static void main(String[] args) throws IOException {
        window = new JFrame();
        rabid=new JButton();
        window.setVisible(true);
        window.setLayout(null);
        window.setSize(600, 600);
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(600, 600));
        JLabel background = new JLabel(new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/firstinterface.jpg"))));
        background.setBounds(0, 0, 600, 600);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        window.setLayout(new FlowLayout());

        // make the buttons
        rabid = new JButton();
        JButton blitz = new JButton();
        JButton bullet = new JButton();
        JLabel start = new JLabel();
        start.setText("Chess");
        start.setForeground(Color.black);
        start.setFont(new Font("Mv Boli", Font.BOLD, 50));
        start.setBounds(200, 50, 200, 100);
        start.setOpaque(false);
        layeredPane.add(start, JLayeredPane.PALETTE_LAYER);

        // set the buttons
        rabid.setText("Rabid");
        rabid.setFont(new Font("comic sans", Font.BOLD, 20));
        rabid.setFocusable(false);
        rabid.setForeground(Color.white);
        rabid.setBackground(Color.black);
        rabid.setBounds(210, 200, 100, 50);
        rabid.addActionListener(new TheInterfaces());
        layeredPane.add(rabid, JLayeredPane.PALETTE_LAYER);
        window.setContentPane(layeredPane);
        window.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rabid) {
            window.dispose();
            // create a new instance of the game engine
            gameEngine game = new gameEngine();

        }
    }

}


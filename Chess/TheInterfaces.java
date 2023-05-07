package Chess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

public class TheInterfaces implements ActionListener {
    private static JFrame window;
    private static JButton rapid;
    private static JLayeredPane layeredPane;

    public static void main(String[] args) throws IOException {
        window = new JFrame();
        rapid = new JButton();
        window.setVisible(true);
        window.setLayout(null);
        window.setSize(600, 600);
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(600, 600));
        JLabel background = new JLabel(new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/firstinterface.jpg"))));
        background.setBounds(0, 0, 600, 600);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        window.setLayout(new FlowLayout());
        window.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                // update the size of the layered pane to match the window size
                layeredPane.setSize(window.getSize());
                window.setContentPane(layeredPane);
            }
        });

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // make the buttons
        rapid = new JButton();
//        JButton blitz = new JButton();
//        JButton bullet = new JButton();
        JLabel title = new JLabel();
        title.setText("Chess");
        title.setForeground(Color.black);
        title.setFont(new Font("Mv Boli", Font.BOLD, 50));
        title.setBounds(200, 50, 200, 100);
        title.setOpaque(false);
        layeredPane.add(title, JLayeredPane.PALETTE_LAYER);

        // set the buttons
        rapid.setText("Rapid");
        rapid.setFont(new Font("comic sans", Font.BOLD, 20));
        rapid.setFocusable(false);
//        rapid.setForeground(Color.white);
        rapid.setBackground(Color.black);
        rapid.setBounds(210, 200, 100, 50);
        rapid.addActionListener(new TheInterfaces());
        layeredPane.add(rapid, JLayeredPane.PALETTE_LAYER);
        window.setContentPane(layeredPane);
        window.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rapid) {
            window.dispose();

            new gameEngine();

        }
    }

}


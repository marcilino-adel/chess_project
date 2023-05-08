package Chess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;

public class TheInterfaces implements ActionListener {

    private static JFrame window;
    private static JButton rapid;
    private static JButton start;

    private static JLayeredPane layeredPane;
    private static JLayeredPane layeredPane_for_users;
    private static JTextField white_player;
    private static JTextField black_player;


    public static void main(String[] args) throws IOException {
        window = new JFrame();
        rapid = new JButton();
        window.setVisible(true);
        window.setLayout(null);
        window.setSize(600, 600);
        layeredPane = new JLayeredPane();
        layeredPane_for_users=new JLayeredPane();
        layeredPane_for_users.setPreferredSize(new Dimension(600,600));
        layeredPane.setPreferredSize(new Dimension(600, 600));
        JLabel background = new JLabel(new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/Background.jpg")).getScaledInstance(window.getWidth(),window.getHeight(),Image.SCALE_SMOOTH)));
       background.setBounds(0, 0, 600, 600);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
        //make the second interface
        JLabel background2=new JLabel(new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/Background.jpg")).getScaledInstance(window.getWidth(),window.getHeight(),Image.SCALE_SMOOTH)));
        background2.setBounds(0, 0, 600, 600);
        layeredPane_for_users.add(background2,JLayeredPane.DEFAULT_LAYER);
        JLabel namelabel1=new JLabel();
        JLabel namelabel2=new JLabel();

        namelabel1.setText("BLACK Player USERNAME :");
        namelabel1.setForeground(Color.black);
        namelabel1.setFont(new Font("normal", Font.BOLD, 20));
        namelabel1.setBounds(20, 50, 280, 50);
        namelabel1.setOpaque(false);
        layeredPane_for_users.add(namelabel1, JLayeredPane.PALETTE_LAYER);

        namelabel2.setText("WHITE Player USERNAME :");
        namelabel2.setForeground(Color.black);
        namelabel2.setFont(new Font("normal", Font.BOLD, 20));
        namelabel2.setBounds(20, 400, 280, 50);
        namelabel2.setOpaque(false);
        layeredPane_for_users.add(namelabel2, JLayeredPane.PALETTE_LAYER);

        white_player = new JTextField();
        white_player.setBounds(300, 400, 200, 50);
        white_player.setFont(new Font("normal", Font.BOLD, 20));
        white_player.setOpaque(false);
        white_player.setHorizontalAlignment(JTextField.CENTER);
        layeredPane_for_users.add(white_player,JLayeredPane.PALETTE_LAYER);

        black_player = new JTextField();
        black_player.setBounds(300, 50, 200, 50);
        black_player.setFont(new Font("normal", Font.BOLD, 20));
        black_player.setOpaque(false);
        black_player.setHorizontalAlignment(JTextField.CENTER);
        layeredPane_for_users.add(black_player,JLayeredPane.PALETTE_LAYER);

        start=new JButton();
        start.setText("START");
        start.setFont(new Font("comic sans", Font.BOLD, 20));
        start.setFocusable(false);
//        rapid.setForeground(Color.white);
        start.setForeground(Color.white);
        start.setBackground(new Color(75, 41, 2, 255));
        start.setBounds(210, 200, 100, 50);
        start.addActionListener(new TheInterfaces());
        layeredPane_for_users.add(start, JLayeredPane.PALETTE_LAYER);


        window.setLayout(new FlowLayout());
        window.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                // update the size of the layered pane to match the window size
                layeredPane.setSize(window.getSize());
                layeredPane_for_users.setSize(window.getSize());
                //window.setContentPane(layeredPane);
            }
        });

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // make the buttons
        rapid = new JButton();
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
       rapid.setForeground(Color.white);
        rapid.setBackground(new Color(75, 41, 2, 255));
        rapid.setBounds(210, 200, 100, 50);
        rapid.addActionListener(new TheInterfaces());
        layeredPane.add(rapid, JLayeredPane.PALETTE_LAYER);
        window.setContentPane(layeredPane);
        window.pack();
    }


@Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == rapid) {
        window.setContentPane(layeredPane_for_users);
        window.pack();

    } else if(e.getSource() == start) {
        String whitePlayerName = white_player.getText();
        String blackPlayerName = black_player.getText();
        try {
            File file = new File("player_names.txt");
            FileWriter writer = new FileWriter(file);
            writer.write(whitePlayerName + "\n" + blackPlayerName);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        if(whitePlayerName.isEmpty() || blackPlayerName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter names for both players");
        } else {
            window.dispose();
            new gameEngine();
        }
    }
}


}


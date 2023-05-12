package Chess;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TheInterfaces implements ActionListener {

    private static JFrame window;
    private static JButton rapid;
    private static JButton start;

    private static JLayeredPane layeredPane;
    private static JLayeredPane layeredPane_for_users;
    private static JTextField username;
    private static JPasswordField password;


    public static void main(String[] args) throws IOException {
        window = new JFrame();
        rapid = new JButton();
        window.setVisible(true);
        window.setLayout(null);
        window.setResizable(false);
        window.setBounds(320,50,900,700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        layeredPane = new JLayeredPane();
        layeredPane_for_users = new JLayeredPane();
        layeredPane_for_users.setPreferredSize(new Dimension(900, 700));
        layeredPane.setPreferredSize(new Dimension(900, 700));
        JLabel background = new JLabel(new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/Background.jpg")).getScaledInstance(window.getWidth(), window.getHeight(), Image.SCALE_SMOOTH)));
        background.setBounds(0, 0, 900, 700);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
        //make the second interface
        JLabel background2 = new JLabel(new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/Background.jpg")).getScaledInstance(window.getWidth(), window.getHeight(), Image.SCALE_SMOOTH)));
        background2.setBounds(0, 0, 900, 700);
        layeredPane_for_users.add(background2, JLayeredPane.DEFAULT_LAYER);
        JLabel nameLabel = new JLabel();
        nameLabel.setText("PLAYER USERNAME :");
        nameLabel.setForeground(Color.black);
        nameLabel.setFont(new Font("normal", Font.BOLD, 20));
        nameLabel.setBounds(170, 100, 290, 50);
        nameLabel.setOpaque(false);
        layeredPane_for_users.add(nameLabel, JLayeredPane.PALETTE_LAYER);
        ////
        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("PASSWORD :");
        passwordLabel.setForeground(Color.black);
        passwordLabel.setFont(new Font("normal", Font.BOLD, 20));
        passwordLabel.setBounds(250, 200, 290, 50);
        passwordLabel.setOpaque(false);
        layeredPane_for_users.add(passwordLabel, JLayeredPane.PALETTE_LAYER);


        username = new JTextField();
        username.setBounds(400, 100, 230, 50);
        username.setFont(new Font("normal", Font.BOLD, 20));
        username.setBorder(new LineBorder(Color.black, 5, true));
        username.setOpaque(false);
        username.setHorizontalAlignment(JTextField.CENTER);
        layeredPane_for_users.add(username, JLayeredPane.PALETTE_LAYER);

        password=new JPasswordField();
        password.setBounds(400,200,230,50);
        password.setFont(new Font("normal", Font.BOLD, 20));
        password.setBorder(new LineBorder(Color.black, 5, true));
        password.setOpaque(false);
        password.setHorizontalAlignment(JTextField.CENTER);
        layeredPane_for_users.add(password, JLayeredPane.PALETTE_LAYER);



        start = new JButton();
        start.setText("START");
        start.setFont(new Font("comic sans", Font.BOLD, 20));
        start.setFocusable(false);
//        rapid.setForeground(Color.white);
        start.setForeground(Color.white);
        start.setBackground(new Color(75, 41, 2, 255));
        start.setBounds(390, 320, 100, 50);
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

        title.setIcon(new ImageIcon(ImageIO.read(new File("Chess/Media/Icons/chess label.png")).getScaledInstance(200, 200, Image.SCALE_SMOOTH)));

        title.setBounds(340, 20, 500, 200);
        title.setOpaque(false);
        layeredPane.add(title, JLayeredPane.PALETTE_LAYER);

        // set the buttons
        rapid.setText("Rapid");
        rapid.setFont(new Font("comic sans", Font.BOLD, 20));
        rapid.setFocusable(false);
        rapid.setForeground(Color.white);
        rapid.setBackground(new Color(75, 41, 2, 255));
        rapid.setBounds(385, 200, 100, 50);
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

        } else if (e.getSource() == start) {

            String playerName = username.getText();
            String playerPassword=password.getText();

            JSONParser parser = new JSONParser();
            try {
                String jsonContent = new String(Files.readAllBytes(Paths.get("playerData.json")));
                JSONObject data = (JSONObject) parser.parse(jsonContent);
                boolean found=false;

                for (Object keyObj : data.keySet()) {
                    String key = (String) keyObj;
                    JSONObject user = (JSONObject) data.get(key);
                    String username = (String) user.get("username");
                    String password = (String) user.get("password");
                    if (playerName.equals(username)&&playerPassword.equals(password)) {
                        found = true;
                        break;
                    }
                }
                if(found){
                    window.dispose();
                    new gameEngine();
                }
                else
                    JOptionPane.showMessageDialog(null, "INVALID USERNAME AND PASSWORD");

            } catch (Exception c) {
                c.printStackTrace();
            }


        }


    }
}

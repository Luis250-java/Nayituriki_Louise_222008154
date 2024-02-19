package login;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dashboard.Dashboard;
import java.awt.Font;
import javax.swing.JProgressBar;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JPasswordField passwordField;
    private JLabel lblLogin;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/Brando_Db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = ""; // Your database password here

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login() {
    	setForeground(new Color(238, 238, 236));
        setBackground(new Color(32, 74, 135));
        setTitle("LOGIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 525, 374);
        contentPane = new JPanel();
        contentPane.setOpaque(false);
        contentPane.setRequestFocusEnabled(false);
        contentPane.setBackground(new Color(78, 154, 6));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("  USERNAME");
        lblNewLabel.setBounds(26, 69, 103, 15);
        lblNewLabel.setForeground(new Color(238, 238, 236));
        lblNewLabel.setBackground(new Color(211, 215, 207));
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(147, 61, 258, 32);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel lblPassword = new JLabel("PASSWORD");
        lblPassword.setBounds(38, 127, 103, 15);
        lblPassword.setForeground(new Color(238, 238, 236));
        lblPassword.setInheritsPopupMenu(false);
        contentPane.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(147, 119, 258, 32);
        contentPane.add(passwordField);

        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setBounds(215, 180, 117, 25);
        btnLogin.setForeground(new Color(238, 238, 236));
        btnLogin.setBackground(new Color(115, 210, 22));
        contentPane.add(btnLogin);
        
        // Action listener for the login button
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textField.getText();
                String password = new String(passwordField.getPassword());

                // Database connection and validation
                try {
                    Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                    String query = "SELECT * FROM users WHERE username=? AND password=?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, username);
                    statement.setString(2, password);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        // If user exists in the database, navigate to dashboard
                        Dashboard dashboard = new Dashboard();
                        dashboard.setVisible(true);
                        dispose(); // Close the login window
                    } else {
                        // If user doesn't exist in the database, display an error message or take appropriate action
                        System.out.println("Invalid username or password!");
                    }

                    resultSet.close();
                    statement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        lblLogin = new JLabel(" Login");
        lblLogin.setFont(new Font("Dialog", Font.BOLD, 25));
        lblLogin.setBounds(205, 0, 108, 37);
        lblLogin.setForeground(new Color(238, 238, 236));
        lblLogin.setBackground(new Color(211, 215, 207));
        contentPane.add(lblLogin);
    }
}

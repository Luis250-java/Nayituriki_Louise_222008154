 package landingpage;
 import java.awt.EventQueue;

 import javax.swing.JFrame;
 import javax.swing.JPanel;
 import javax.swing.border.EmptyBorder;

 import login.Login; // Assuming you have a Login class

 import javax.swing.JLabel;
 import java.awt.Font;
 import java.awt.Color;
 import javax.swing.JButton;
 import java.awt.event.ActionListener;
 import java.awt.event.ActionEvent;
 import javax.swing.ImageIcon;

 public class LandingPage extends JFrame {

     private static final long serialVersionUID = 1L;
     private JPanel contentPane;

     /**
      * Launch the application.
      */
     public static void main(String[] args) {
         EventQueue.invokeLater(new Runnable() {
             public void run() {
                 try {
                     LandingPage frame = new LandingPage();
                     frame.setVisible(true);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         });
     }

     /**
      * Create the frame.
      */
     public LandingPage() {
     	setBackground(new Color(32, 74, 135));
         setResizable(false);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setBounds(100, 100, 677, 448);
         contentPane = new JPanel();
         contentPane.setBackground(new Color(32, 74, 135));
         contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

         setContentPane(contentPane);
         contentPane.setLayout(null);

         JLabel lblWelcomeToEclass = new JLabel("Welcome To E-Class Management System");
         lblWelcomeToEclass.setBounds(48, 23, 591, 97);
         lblWelcomeToEclass.setBackground(new Color(32, 74, 135));
         lblWelcomeToEclass.setForeground(new Color(238, 238, 236));
         lblWelcomeToEclass.setFont(new Font("Dialog", Font.BOLD, 25));
         contentPane.add(lblWelcomeToEclass);

         JLabel lblNewLabel = new JLabel("New label");
         lblNewLabel.setIcon(new ImageIcon("/home/vg/Desktop/images.jpeg"));
         lblNewLabel.setBackground(new Color(78, 154, 6));
         lblNewLabel.setBounds(216, 117, 277, 191);
         contentPane.add(lblNewLabel);

         JButton btnNewButton = new JButton(" Click To Continue");
         btnNewButton.setForeground(new Color(238, 238, 236));
         btnNewButton.setBackground(new Color(78, 154, 6));
         btnNewButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 // Open the login window or change content to display the login page
                 Login login = new Login(); // Assuming Login is the class for your login window
                 login.setVisible(true); // Make the login window visible
                 dispose(); // Close the current window (landing page)
             }
         });
         btnNewButton.setBounds(285, 337, 159, 38);
         contentPane.add(btnNewButton);
     }
 }

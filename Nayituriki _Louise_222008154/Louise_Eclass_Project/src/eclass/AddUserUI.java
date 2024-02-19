 package eclass;

 import javax.swing.*;
 import javax.swing.table.DefaultTableModel;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.sql.*;

 public class AddUserUI extends JFrame {
     private JTextField usernameField;
     private JPasswordField passwordField;
     private JTextField phoneField;
     private JButton addUserButton;
     private JButton deleteUserButton;
     private JButton updateUserButton;
     private JTextField emailField;
     private JTable userTable;
     private DefaultTableModel tableModel;

     private static final String JDBC_URL = "jdbc:mysql://localhost:3307/Brando_Db";
     private static final String DB_USER = "root";
     private static final String DB_PASSWORD = "";

     public AddUserUI() {
         setTitle("Manage Users");
         setSize(647, 419);
         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         setLocationRelativeTo(null);

         initComponents();
         displayUsers(); // Display existing users
     }

     private void initComponents() {
         getContentPane().setLayout(null);

         addUserButton = new JButton("Add User");
         addUserButton.setBounds(486, 25, 111, 25);
         getContentPane().add(addUserButton);

         deleteUserButton = new JButton("Delete User");
         deleteUserButton.setBounds(486, 77, 111, 25);
         getContentPane().add(deleteUserButton);

         updateUserButton = new JButton("Update User");
         updateUserButton.setBounds(486, 137, 111, 25);
         getContentPane().add(updateUserButton);

         JLabel label = new JLabel("Username:");
         label.setBounds(32, 12, 128, 45);
         getContentPane().add(label);

         phoneField = new JTextField(30);
         phoneField.setBounds(168, 25, 264, 25);
         getContentPane().add(phoneField);

         usernameField = new JTextField(30);
         usernameField.setBounds(167, 119, 265, 25);
         getContentPane().add(usernameField);

         JLabel label_1 = new JLabel("Phone:");
         label_1.setBounds(32, 127, 78, 9);
         getContentPane().add(label_1);

         passwordField = new JPasswordField(30);
         passwordField.setBounds(168, 69, 264, 25);
         getContentPane().add(passwordField);

         JLabel label_2 = new JLabel("Password:");
         label_2.setBounds(32, 74, 81, 15);
         getContentPane().add(label_2);

         JLabel label_3 = new JLabel("Email:");
         label_3.setBounds(32, 171, 95, 9);
         getContentPane().add(label_3);

         emailField = new JTextField(30);
         emailField.setBounds(168, 163, 264, 25);
         getContentPane().add(emailField);

         // Initialize userTable
         tableModel = new DefaultTableModel();
         userTable = new JTable(tableModel);
         JScrollPane scrollPane = new JScrollPane(userTable);
         scrollPane.setBounds(32, 237, 583, 114);
         getContentPane().add(scrollPane);

         // Add action listener to the Add User button
         addUserButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 addUserToDatabase();
             }
         });

         // Add action listener to the Delete User button
         deleteUserButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 deleteUser();
             }
         });

         // Add action listener to the Update User button
         updateUserButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 updateUser();
             }
         });
     }

     private void displayUsers() {
         // Database connection and fetch users
         try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
             String selectQuery = "SELECT username, password, email, phone FROM users";
             try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                 ResultSet resultSet = preparedStatement.executeQuery();

                 // Clear table before adding new data
                 tableModel.setRowCount(0);

                 // Add fetched users to the table
                 while (resultSet.next()) {
                     String username = resultSet.getString("username");
                     String password = resultSet.getString("password");
                     String email = resultSet.getString("email");
                     String phone = resultSet.getString("phone");

                     Object[] rowData = {username, password, email, phone};
                     tableModel.addRow(rowData);
                 }
             }
         } catch (SQLException ex) {
             ex.printStackTrace();
             JOptionPane.showMessageDialog(this, "Error fetching users.", "Error", JOptionPane.ERROR_MESSAGE);
         }
     }

     private void addUserToDatabase() {
         // Implementation remains the same as in your code
         // ...
     }

     private void deleteUser() {
         int selectedRow = userTable.getSelectedRow();
         if (selectedRow != -1) {
             String username = (String) userTable.getValueAt(selectedRow, 0);

             try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
                 String deleteQuery = "DELETE FROM users WHERE username = ?";
                 try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                     preparedStatement.setString(1, username);
                     preparedStatement.executeUpdate();

                     // Remove the row from the table
                     tableModel.removeRow(selectedRow);
                 }
             } catch (SQLException ex) {
                 ex.printStackTrace();
                 JOptionPane.showMessageDialog(this,
                         "Error deleting user. Please try again.",
                         "Error", JOptionPane.ERROR_MESSAGE);
             }
         } else {
             JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Error", JOptionPane.ERROR_MESSAGE);
         }
     }

     private void updateUser() {
         int selectedRow = userTable.getSelectedRow();
         if (selectedRow != -1) {
             // Retrieve selected user's data from the table
             String username = (String) userTable.getValueAt(selectedRow, 0);
             String password = (String) userTable.getValueAt(selectedRow, 1);
             String email = (String) userTable.getValueAt(selectedRow, 2);
             String phone = (String) userTable.getValueAt(selectedRow, 3);

             // Show selected user's data in input fields for editing
             usernameField.setText(username);
             passwordField.setText(password);
             emailField.setText(email);
             phoneField.setText(phone);

             // Perform update operation on the selected user
             // You need to implement the update functionality here
         } else {
             JOptionPane.showMessageDialog(this, "Please select a user to update.", "Error", JOptionPane.ERROR_MESSAGE);
         }
     }

     public static void main(String[] args) {
         SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
                 new AddUserUI().setVisible(true);
             }
         });
     }
 }

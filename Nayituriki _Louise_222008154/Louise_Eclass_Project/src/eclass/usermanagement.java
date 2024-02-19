 package eclass;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class usermanagement extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtAddress;
    private JPasswordField txtPassword;
    private JTable table;

    private Connection connection;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    usermanagement frame = new usermanagement();
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
    public usermanagement() {
    	setBackground(new Color(32, 74, 135));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(32, 74, 135));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(23, 205, 665, 219);
        contentPane.add(scrollPane);

        table = new JTable();
        table.setBackground(new Color(211, 215, 207));
        scrollPane.setViewportView(table);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(78, 154, 6));
        panel.setBounds(23, 12, 647, 151);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBackground(new Color(238, 238, 236));
        lblUsername.setBounds(12, 7, 77, 15);
        panel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(90, 7, 223, 31);
        panel.add(txtUsername);
        txtUsername.setColumns(10);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(341, 73, 42, 15);
        panel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(388, 71, 225, 31);
        panel.add(txtEmail);
        txtEmail.setColumns(10);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(331, 9, 50, 15);
        panel.add(lblPhone);

        txtPhone = new JTextField();
        txtPhone.setBounds(388, 7, 225, 31);
        panel.add(txtPhone);
        txtPhone.setColumns(10);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(12, 116, 63, 15);
        panel.add(lblAddress);

        txtAddress = new JTextField();
        txtAddress.setBounds(90, 108, 223, 31);
        panel.add(txtAddress);
        txtAddress.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(12, 59, 75, 15);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(90, 57, 223, 29);
        panel.add(txtPassword);
        txtPassword.setColumns(10);
        
                JButton btnAdd = new JButton("Add");
                btnAdd.setBackground(new Color(16, 209, 236));
                btnAdd.setBounds(33, 168, 125, 25);
                contentPane.add(btnAdd);
                
                        JButton btnUpdate = new JButton("Update");
                        btnUpdate.setBackground(new Color(233, 185, 110));
                        btnUpdate.setBounds(236, 168, 133, 25);
                        contentPane.add(btnUpdate);
                        
                                JButton btnDelete = new JButton("Delete");
                                btnDelete.setBackground(new Color(204, 0, 0));
                                btnDelete.setBounds(434, 168, 133, 25);
                                contentPane.add(btnDelete);
                                btnDelete.addActionListener(e -> deleteUser());
                        btnUpdate.addActionListener(e -> updateUser());
                btnAdd.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                	}
                });
                btnAdd.addActionListener(e -> addUser());

        // Connect to the database
        connectToDatabase();
        // Display users
        displayUsers();
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3307/Brando_Db";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayUsers() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Username");
            model.addColumn("Email");
            model.addColumn("Phone");
            model.addColumn("Address");
            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address")
                });
            }
            table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addUser() {
        try {
            String username = txtUsername.getText();
            String email = txtEmail.getText();
            String phone = txtPhone.getText();
            String address = txtAddress.getText();
            String password = new String(txtPassword.getPassword());

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, email, phone, address, password) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, password);

            preparedStatement.executeUpdate();
            displayUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to update.");
            return;
        }

        try {
            int id = (int) table.getValueAt(selectedRow, 0);
            String username = txtUsername.getText();
            String email = txtEmail.getText();
            String phone = txtPhone.getText();
            String address = txtAddress.getText();
            String password = new String(txtPassword.getPassword());

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET username=?, email=?, phone=?, address=?, password=? WHERE id=?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, password);
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();
            displayUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            displayUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

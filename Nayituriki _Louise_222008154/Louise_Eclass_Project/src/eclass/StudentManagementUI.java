package eclass;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentManagementUI extends JFrame {
    private JTextField firstNameField, lastNameField, regNumberField, emailField;
    private JButton addButton, updateButton, deleteButton;
    private DefaultTableModel studentTableModel;
    private JTable studentTable;

    public StudentManagementUI() {
    	getContentPane().setBackground(new Color(32, 74, 135));
        setBackground(new Color(32, 74, 135));
        setTitle("Student Management UI");
        setSize(647, 465);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadData();
    }

    private void initComponents() {

        // Table for displaying students
        studentTableModel = new DefaultTableModel();
        studentTableModel.addColumn("First Name");
        studentTableModel.addColumn("Last Name");
        studentTableModel.addColumn("Reg Number");
        studentTableModel.addColumn("Email");
        studentTable = new JTable(studentTableModel);
        getContentPane().setLayout(null);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.setBounds(0, 35, 647, -10);
        inputPanel.setBackground(new Color(52, 101, 164));
        inputPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField(20);
        inputPanel.add(firstNameField);
        inputPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField(20);
        inputPanel.add(lastNameField);
        inputPanel.add(new JLabel("Reg Number:"));
        regNumberField = new JTextField(20);
        inputPanel.add(regNumberField);
        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        inputPanel.add(emailField);
        getContentPane().add(inputPanel);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(34, 58, 591, 244);
        getContentPane().add(scrollPane);
        updateButton = new JButton("Update");
        updateButton.setBounds(256, 334, 97, 35);
        getContentPane().add(updateButton);
        updateButton.setBackground(new Color(16, 209, 236));
        // Buttons
        addButton = new JButton("Add");
        addButton.setBounds(56, 334, 115, 35);
        getContentPane().add(addButton);
        addButton.setBackground(new Color(78, 154, 6));
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(422, 334, 104, 35);
        getContentPane().add(deleteButton);
        deleteButton.setBackground(new Color(239, 41, 41));
        deleteButton.addActionListener(e -> deleteStudent());
        
                addButton.addActionListener(e -> showAddStudentDialog());
        updateButton.addActionListener(e -> updateStudent());
        studentTable.getSelectionModel().addListSelectionListener(e -> displaySelectedStudent());
    }

    private void showAddStudentDialog() {
        JDialog addDialog = new JDialog(this, "Add Student", true);
        addDialog.getContentPane().setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("First Name:"));
        JTextField firstNameField = new JTextField();
        inputPanel.add(firstNameField);
        inputPanel.add(new JLabel("Last Name:"));
        JTextField lastNameField = new JTextField();
        inputPanel.add(lastNameField);
        inputPanel.add(new JLabel("Reg Number:"));
        JTextField regNumberField = new JTextField();
        inputPanel.add(regNumberField);
        inputPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        inputPanel.add(emailField);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            addStudent(firstNameField.getText(), lastNameField.getText(), regNumberField.getText(), emailField.getText());
            addDialog.dispose();
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> addDialog.dispose());
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        addDialog.getContentPane().add(inputPanel, BorderLayout.CENTER);
        addDialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        addDialog.pack();
        addDialog.setLocationRelativeTo(this);
        addDialog.setVisible(true);
    }

    private void loadData() {
        String url = "jdbc:mysql://localhost:3307/Brando_Db";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM student";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                studentTableModel.setRowCount(0);

                while (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String regNumber = resultSet.getString("registration_number");
                    String email = resultSet.getString("email_address");

                    Object[] row = {firstName, lastName, regNumber, email};
                    studentTableModel.addRow(row);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data from the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addStudent(String firstName, String lastName, String regNumber, String email) {
        // Insert into the database
        String url = "jdbc:mysql://localhost:3307/Brando_Db";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO student (first_name, last_name, registration_number, email_address) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, regNumber);
                preparedStatement.setString(4, email);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Student added successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadData(); // Refresh the student table
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add student.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding student to the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        // Placeholder for updating a student in the database
        // Retrieve values from input fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String regNumber = regNumberField.getText();
        String email = emailField.getText();

        // Validate input (add your validation logic here)

        // Update in the database
        String url = "jdbc:mysql://localhost:3307/Brando_Db";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE student SET first_name = ?, last_name = ?, email_address = ? WHERE registration_number = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, regNumber);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Student updated successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadData(); // Refresh the student table
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update student.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating student in the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        // Placeholder for deleting a student from the database
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String regNumber = (String) studentTableModel.getValueAt(selectedRow, 2);

        // Delete from the database
        String url = "jdbc:mysql://localhost:3307/Brando_Db";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "DELETE FROM student WHERE registration_number = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, regNumber);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Student deleted successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadData(); // Refresh the student table
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete student.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting student from the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displaySelectedStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            String firstName = (String) studentTableModel.getValueAt(selectedRow, 0);
            String lastName = (String) studentTableModel.getValueAt(selectedRow, 1);
            String regNumber = (String) studentTableModel.getValueAt(selectedRow, 2);
            String email = (String) studentTableModel.getValueAt(selectedRow, 3);

            firstNameField.setText(firstName);
            lastNameField.setText(lastName);
            regNumberField.setText(regNumber);
            emailField.setText(email);
        }
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        regNumberField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementUI().setVisible(true));
    }
}

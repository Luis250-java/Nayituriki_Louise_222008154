package eclass;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class ContentGUI extends JFrame {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/Brando_Db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private JTable table;

    public ContentGUI() {
    	getContentPane().setBackground(new Color(32, 74, 135));
    	setBackground(new Color(32, 74, 135));
        setTitle("Content Table Viewer");
        setSize(612, 440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(12, 54, 590, 253);

        getContentPane().add(scrollPane);

        // Buttons for CRUD operations
        JButton addButton = new JButton("Add");
        addButton.setBounds(61, 5, 115, 25);
        addButton.setBackground(new Color(115, 210, 22));
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(248, 5, 115, 25);
        deleteButton.setBackground(new Color(239, 41, 41));
        JButton updateButton = new JButton("Update");
        updateButton.setBounds(406, 5, 123, 25);
        updateButton.setBackground(new Color(16, 209, 236));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 335, 590, 35);
        buttonPanel.setBackground(new Color(32, 74, 135));
        buttonPanel.setLayout(null);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        getContentPane().add(buttonPanel);

        // Connect to the database and load data into the table
        refreshTable();

        // Add action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddContentDialog();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContent();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContent();
            }
        });
    }

    // Helper method to convert ResultSet to TableModel
    private static DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();

        // Column names
        int columnCount = metaData.getColumnCount();
        Vector<String> columnNames = new Vector<>();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // Data
        Vector<Vector<Object>> data = new Vector<>();
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(resultSet.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }

    // Method to refresh the table data
    private void refreshTable() {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            String sql = "SELECT * FROM content";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Populate the table model with data from the result set
            table.setModel(buildTableModel(resultSet));

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to show dialog for adding content
    private void showAddContentDialog() {
        JTextField contentIdField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField uploadDateField = new JTextField();
        JTextField authorField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Content ID:"));
        panel.add(contentIdField);
        panel.add(new JLabel("Content Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Upload Date (YYYY-MM-DD):"));
        panel.add(uploadDateField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Content",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // If OK is clicked, add content to the database
            String contentId = contentIdField.getText();
            String title = titleField.getText();
            String uploadDate = uploadDateField.getText();
            String author = authorField.getText();

            if (contentId.isEmpty() || title.isEmpty() || uploadDate.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                String sql = "INSERT INTO content (`contentId`, `contentTitle`, `uploadDate`, `author`) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, contentId);
                preparedStatement.setString(2, title);
                preparedStatement.setString(3, uploadDate);
                preparedStatement.setString(4, author);

                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Content added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                preparedStatement.close();
                connection.close();
                refreshTable(); // Refresh table after adding content
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while adding content.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Implement methods for CRUD operations
    // Example methods:

    private void deleteContent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                String sql = "DELETE FROM content WHERE `contentId`=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, (int) table.getValueAt(selectedRow, 0));

                preparedStatement.executeUpdate();

                preparedStatement.close();
                connection.close();
                refreshTable(); // Refresh table after deleting content
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateContent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                String sql = "UPDATE content SET `contentId`=?, `uploadDate`=?, `author`=? WHERE `contentId`=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, "UpdatedValue1");
                preparedStatement.setString(2, "UpdatedValue2");
                preparedStatement.setString(3, "UpdatedValue3");
                preparedStatement.setInt(4, (int) table.getValueAt(selectedRow, 0));

                preparedStatement.executeUpdate();

                preparedStatement.close();
                connection.close();
                refreshTable(); // Refresh table after updating content
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ContentGUI contentGUI = new ContentGUI();
            contentGUI.setVisible(true);
        });
    }
}

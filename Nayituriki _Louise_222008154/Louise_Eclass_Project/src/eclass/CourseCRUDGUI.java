package eclass;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.Color;

public class CourseCRUDGUI extends JFrame {

    private JTextField courseIdField, courseTitleField, startDateField, endDateField, enrolledStudentField, materialsField;
    private JTextArea resultArea;
    

    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/Brando_Db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    public CourseCRUDGUI() {
        super("Course CRUD Application");
        setBackground(new Color(32, 74, 135));

        // Initialize the database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel();
        panel.setBackground(new Color(32, 74, 135));

        courseIdField = new JTextField(10);
        courseIdField.setBounds(257, 39, 190, 19);
        courseTitleField = new JTextField(20);
        courseTitleField.setBounds(41, 39, 196, 19);
        startDateField = new JTextField(10);
        startDateField.setBounds(254, 97, 196, 19);
        endDateField = new JTextField(10);
        endDateField.setBounds(41, 97, 196, 19);
        enrolledStudentField = new JTextField(15);
        enrolledStudentField.setBounds(254, 159, 196, 19);
        materialsField = new JTextField(20);
        materialsField.setBounds(41, 159, 196, 19);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JButton readButton = new JButton("Read");
        readButton.setBackground(new Color(114, 159, 207));
        readButton.setBounds(245, 203, 70, 25);
        JButton updateButton = new JButton("Update");
        updateButton.setBackground(new Color(16, 209, 236));
        updateButton.setBounds(147, 203, 86, 25);
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(204, 0, 0));
        deleteButton.setBounds(329, 203, 81, 25);

        readButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                readCourse();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCourse();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteCourse();
            }
        });
        panel.setLayout(null);

        JLabel label = new JLabel("Course ID:");
        label.setForeground(new Color(238, 238, 236));
        label.setBounds(257, 12, 73, 15);
        panel.add(label);
        panel.add(courseIdField);
        JLabel label_1 = new JLabel("Course Title:");
        label_1.setForeground(new Color(238, 238, 236));
        label_1.setBounds(41, 12, 90, 15);
        panel.add(label_1);
        panel.add(courseTitleField);
        JLabel label_2 = new JLabel("Start Date:");
        label_2.setForeground(new Color(238, 238, 236));
        label_2.setBounds(254, 70, 79, 15);
        panel.add(label_2);
        panel.add(startDateField);
        JLabel label_3 = new JLabel("End Date:");
        label_3.setForeground(new Color(238, 238, 236));
        label_3.setBackground(new Color(238, 238, 236));
        label_3.setBounds(41, 70, 69, 15);
        panel.add(label_3);
        panel.add(endDateField);
        JLabel label_4 = new JLabel("Enrolled Student:");
        label_4.setForeground(new Color(238, 238, 236));
        label_4.setBounds(254, 141, 124, 15);
        panel.add(label_4);
        panel.add(enrolledStudentField);
        JLabel label_5 = new JLabel("Course Materials:");
        label_5.setForeground(new Color(238, 238, 236));
        label_5.setBounds(41, 141, 126, 15);
        panel.add(label_5);
        panel.add(materialsField);
        panel.add(readButton);
        panel.add(updateButton);
        
                JButton createButton = new JButton("Create");
                createButton.setBackground(new Color(115, 210, 22));
                createButton.setBounds(53, 203, 82, 25);
                
                        createButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                createCourse();
                            }
                        });
                        
                                panel.add(createButton);
        panel.add(deleteButton);

        JLabel label_6 = new JLabel("Result:");
        label_6.setForeground(new Color(238, 238, 236));
        label_6.setBounds(22, 261, 50, 15);
        panel.add(label_6);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(0, 319, 498, 144);
        panel.add(scrollPane);

        getContentPane().add(panel);
        setSize(498, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    private void createCourse() {
        try (Connection connection = getConnection()) {
            int courseId = Integer.parseInt(courseIdField.getText());
            String courseTitle = courseTitleField.getText();
            String startDate = startDateField.getText();
            String endDate = endDateField.getText();
            String enrolledStudent = enrolledStudentField.getText();
            String materials = materialsField.getText();

            String query = "INSERT INTO course (course_id, course_title, start_date, end_date, enrolled_student, course_materials) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, courseId);
                preparedStatement.setString(2, courseTitle);
                preparedStatement.setString(3, startDate);
                preparedStatement.setString(4, endDate);
                preparedStatement.setString(5, enrolledStudent);
                preparedStatement.setString(6, materials);
                preparedStatement.executeUpdate();
                resultArea.setText("Course created successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void displayAllCourses() {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM course";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Display courses in a table format
                StringBuilder table = new StringBuilder("Course ID\tCourse Title\tStart Date\tEnd Date\tEnrolled Student\tCourse Materials\n");
                while (resultSet.next()) {
                    table.append(resultSet.getInt("course_id")).append("\t")
                         .append(resultSet.getString("course_title")).append("\t")
                         .append(resultSet.getString("start_date")).append("\t")
                         .append(resultSet.getString("end_date")).append("\t")
                         .append(resultSet.getString("enrolled_student")).append("\t")
                         .append(resultSet.getString("course_materials")).append("\n");
                }
                resultArea.setText(table.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void readCourse() {
        try (Connection connection = getConnection()) {
            int courseId = Integer.parseInt(courseIdField.getText());
            String query = "SELECT * FROM course WHERE course_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, courseId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        resultArea.setText("Course ID: " + resultSet.getInt("course_id") + "\n"
                                + "Course Title: " + resultSet.getString("course_title") + "\n"
                                + "Start Date: " + resultSet.getString("start_date") + "\n"
                                + "End Date: " + resultSet.getString("end_date") + "\n"
                                + "Enrolled Student: " + resultSet.getString("enrolled_student") + "\n"
                                + "Course Materials: " + resultSet.getString("course_materials"));
                    } else {
                        resultArea.setText("Course not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCourse() {
        try (Connection connection = getConnection()) {
            int courseId = Integer.parseInt(courseIdField.getText());
            String newCourseTitle = courseTitleField.getText();
            String newStartDate = startDateField.getText();
            String newEndDate = endDateField.getText();
            String newEnrolledStudent = enrolledStudentField.getText();
            String newMaterials = materialsField.getText();

            String query = "UPDATE course SET course_title=?, start_date=?, end_date=?, enrolled_student=?, course_materials=? WHERE course_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, newCourseTitle);
                preparedStatement.setString(2, newStartDate);
                preparedStatement.setString(3, newEndDate);
                preparedStatement.setString(4, newEnrolledStudent);
                preparedStatement.setString(5, newMaterials);
                preparedStatement.setInt(6, courseId);
                preparedStatement.executeUpdate();
                resultArea.setText("Course updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCourse() {
        try (Connection connection = getConnection()) {
            int courseId = Integer.parseInt(courseIdField.getText());
            String query = "DELETE FROM course WHERE course_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, courseId);
                preparedStatement.executeUpdate();
                resultArea.setText("Course deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CourseCRUDGUI();
            }
        });
    }
}

 
 package dashboard;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import eclass.AddUserUI;
import eclass.AssessmentManagementUI;
import eclass.AttendanceTrackingUI;
import eclass.ContentGUI;
import eclass.CourseCRUDGUI;
import eclass.StudentManagementUI;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Dashboard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Dashboard frame = new Dashboard();
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
    public Dashboard() {
    	setBackground(new Color(32, 74, 135));
        setResizable(false);
        setForeground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 652, 457);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(32, 74, 135));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Welcome To Dashboard");
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        lblNewLabel.setForeground(new Color(238, 238, 236));
        lblNewLabel.setBounds(204, 0, 328, 54);
        contentPane.add(lblNewLabel);

        JButton btnAssessment = new JButton("Assessment");
        btnAssessment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AssessmentManagementUI assessmentUI = new AssessmentManagementUI();
                assessmentUI.setVisible(true);
            }
        });
        btnAssessment.setFont(new Font("Dialog", Font.BOLD, 15));
        btnAssessment.setForeground(new Color(238, 238, 236));
        btnAssessment.setBackground(new Color(193, 125, 17));
        btnAssessment.setBounds(12, 100, 194, 146);
        contentPane.add(btnAssessment);

        JButton btnAttendance = new JButton("Attendance");
        btnAttendance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AttendanceTrackingUI attendanceUI = new AttendanceTrackingUI();
                attendanceUI.setVisible(true);
            }
        });
        btnAttendance.setFont(new Font("Dialog", Font.BOLD, 15));
        btnAttendance.setForeground(new Color(238, 238, 236));
        btnAttendance.setBackground(new Color(78, 154, 6));
        btnAttendance.setBounds(228, 100, 175, 146);
        contentPane.add(btnAttendance);

        JButton btnContent = new JButton("Content");
        btnContent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ContentGUI contentUI = new ContentGUI();
                contentUI.setVisible(true);
            }
        });
        btnContent.setFont(new Font("Dialog", Font.BOLD, 15));
        btnContent.setForeground(new Color(238, 238, 236));
        btnContent.setBackground(new Color(114, 159, 207));
        btnContent.setBounds(427, 101, 193, 144);
        contentPane.add(btnContent);

        JButton btnCourse = new JButton("Course");
        btnCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CourseCRUDGUI courseUI = new CourseCRUDGUI();
                courseUI.setVisible(true);
            }
        });
        btnCourse.setFont(new Font("Dialog", Font.BOLD, 15));
        btnCourse.setForeground(new Color(211, 215, 207));
        btnCourse.setBackground(new Color(52, 101, 164));
        btnCourse.setBounds(12, 258, 194, 133);
        contentPane.add(btnCourse);

        JButton btnStudents = new JButton("Students");
        btnStudents.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StudentManagementUI studentUI = new StudentManagementUI();
                studentUI.setVisible(true);
            }
        });
        btnStudents.setFont(new Font("Dialog", Font.BOLD, 15));
        btnStudents.setForeground(new Color(255, 255, 255));
        btnStudents.setBackground(new Color(237, 212, 0));
        btnStudents.setBounds(228, 258, 175, 133);
        contentPane.add(btnStudents);

        JButton btnUsers = new JButton("Users");
        btnUsers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddUserUI userUI = new AddUserUI();
                userUI.setVisible(true);
            }
        });
        btnUsers.setFont(new Font("Dialog", Font.BOLD, 15));
        btnUsers.setForeground(new Color(238, 238, 236));
        btnUsers.setBackground(new Color(233, 185, 110));
        btnUsers.setBounds(427, 258, 193, 133);
        contentPane.add(btnUsers);
    }
}

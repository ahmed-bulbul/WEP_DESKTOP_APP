package osdetect;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.sql.*;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import osdetect.custom.DialogLoader;

/**
 *
 * @author Walton
 */
public class Registration extends JFrame {

    private static final long serialVersionUID = 1;
    private static DialogLoader dialogLoader;

    private JTextField stNameTxtField, scNameTxtField, phoneField, emailTxtField;
    //private JFormattedTextField phoneField;
    private JComboBox classComboBox, groupComboBox;
    private final JButton btnNewButton;
    private JLabel label;
    private final JPanel contentPane;

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Registration().setVisible(true);
        });
    }

    /**
     * Create the frame.
     */
    public Registration() {

        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setType(Type.UTILITY);
        setBounds(700, 200, 550, 580);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        //setContentPane(contentPane);
        contentPane.setLayout(null);

        // Dailog fro user interaction
        dialogLoader = new DialogLoader(contentPane);
        add(dialogLoader.Init());

        JLabel lblNewLabel = new JLabel("Registration Form");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        lblNewLabel.setBounds(170, 20, 250, 70);
        contentPane.add(lblNewLabel);

        JLabel lblStudentName = new JLabel("Student Name (*)");
        lblStudentName.setBackground(Color.BLACK);
        lblStudentName.setForeground(Color.BLACK);
        lblStudentName.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblStudentName.setBounds(80, 100, 200, 30);
        contentPane.add(lblStudentName);

        JLabel lblSchoolName = new JLabel("School Name (*)");
        lblSchoolName.setBackground(Color.BLACK);
        lblSchoolName.setForeground(Color.BLACK);
        lblSchoolName.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblSchoolName.setBounds(80, 150, 200, 30);
        contentPane.add(lblSchoolName);

        JLabel lblClass = new JLabel("Class (*)");
        lblClass.setForeground(Color.BLACK);
        lblClass.setBackground(Color.CYAN);
        lblClass.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblClass.setBounds(80, 200, 200, 30);
        contentPane.add(lblClass);

        JLabel lblGroup = new JLabel("Group");
        lblGroup.setForeground(Color.BLACK);
        lblGroup.setBackground(Color.CYAN);
        lblGroup.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblGroup.setBounds(80, 250, 200, 30);
        contentPane.add(lblGroup);

        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setForeground(Color.BLACK);
        lblPhone.setBackground(Color.CYAN);
        lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblPhone.setBounds(80, 300, 200, 30);
        contentPane.add(lblPhone);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setForeground(Color.BLACK);
        lblEmail.setBackground(Color.CYAN);
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblEmail.setBounds(80, 350, 200, 30);
        contentPane.add(lblEmail);

        JLabel lblVersion = new JLabel("Version");
        lblVersion.setForeground(Color.BLACK);
        lblVersion.setBackground(Color.CYAN);
        lblVersion.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblVersion.setBounds(80, 400, 200, 30);
        contentPane.add(lblVersion);

        stNameTxtField = new JTextField();
        stNameTxtField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        stNameTxtField.setBounds(230, 100, 200, 30);
        contentPane.add(stNameTxtField);
        stNameTxtField.setColumns(10);

        scNameTxtField = new JTextField();
        scNameTxtField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        scNameTxtField.setBounds(230, 150, 200, 30);
        contentPane.add(scNameTxtField);

        String classList[] = {"Class 5", "Class 6", "Class 7", "Class 8", "Class 9", "Class 10"};
        classComboBox = new JComboBox(classList);
        classComboBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
        classComboBox.setBounds(230, 200, 200, 30);
        contentPane.add(classComboBox);

        String groupList[] = {"Science", "Arts", "Commerce"};
        groupComboBox = new JComboBox(groupList);
        groupComboBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
        groupComboBox.setBounds(230, 250, 200, 30);
        groupComboBox.setEnabled(false);
        contentPane.add(groupComboBox);

        //MaskFormatter mf = new MaskFormatter("###########");
        phoneField = new JTextField();
        phoneField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        phoneField.setBounds(230, 300, 200, 30);
        contentPane.add(phoneField);

        emailTxtField = new JTextField();
        emailTxtField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        emailTxtField.setBounds(230, 350, 200, 30);
        contentPane.add(emailTxtField);

        // Version selection
        JRadioButton englishButton = new JRadioButton("English", true);
        englishButton.setActionCommand(englishButton.getText());
        englishButton.setFont(new Font("Tahoma", Font.PLAIN, 17));

        JRadioButton banglaButton = new JRadioButton("Bangla");
        banglaButton.setActionCommand(banglaButton.getText());
        banglaButton.setFont(new Font("Tahoma", Font.PLAIN, 17));

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(englishButton);
        group.add(banglaButton);

        //Register a listener for the radio buttons.
        englishButton.addActionListener(groupListener());
        banglaButton.addActionListener(groupListener());

        //Put the radio buttons in a column in a panel.
        JPanel radioPanel = new JPanel(new GridLayout(1, 0));
        radioPanel.add(englishButton);
        radioPanel.add(banglaButton);
        radioPanel.setBounds(230, 400, 200, 30);
        contentPane.add(radioPanel);

        btnNewButton = new JButton("Register");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton.setBounds(330, 460, 100, 30);
        contentPane.add(btnNewButton);

        classComboBox.addItemListener((ItemEvent e) -> {
            // if the state combobox is changed
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String[] strClass = classComboBox.getSelectedItem().toString().split(" ");
                int classNum = Integer.parseInt(strClass[1]);
                if (classNum >= 9) {
                    groupComboBox.setEnabled(true);
                } else {
                    groupComboBox.setEnabled(false);
                }
            }
        });

        groupComboBox.addItemListener((ItemEvent e) -> {
            // if the state combobox is changed
            if (e.getStateChange() == ItemEvent.SELECTED) {
                System.out.println(groupComboBox.getSelectedItem());
            }
        });

        btnNewButton.addActionListener((ActionEvent e) -> {
            String name1 = stNameTxtField.getText();
            String school = scNameTxtField.getText();
            String phone = phoneField.getText();
            String email = emailTxtField.getText();
            String version = group.getSelection().getActionCommand();
            String mac = HardwareUtil.getMacAddress();
            String group1;
            String classes = classComboBox.getSelectedItem().toString();
            String[] strClass = classes.split(" ");
            int classNum = Integer.parseInt(strClass[1]);
            if (classNum >= 9) {
                group1 = groupComboBox.getSelectedItem().toString();
            } else {
                group1 = null;
            }
            //String cDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            //System.out.println(dateTime);
            if (!name1.isEmpty() && !school.isEmpty() && !classes.isEmpty()) {
                dialogLoader.show();
                runInBackground(() -> {
                    int IS_FORM_FILL_UP = Utility.isRegistered(mac);
                    if (IS_FORM_FILL_UP == 1) {
                        dialogLoader.dismiss();
                        JOptionPane.showMessageDialog(null, "Already registered.");
                    } else {
                        proceedRegistration(name1, school, classes, group1, phone, email, version, mac);
                    }
                });
            } else {
                JOptionPane.showMessageDialog(null, "All (*) fields are required.");
            }
        });
    }

    private ActionListener groupListener() {
        return (ActionEvent e) -> {
            //System.out.println(e.getActionCommand());
        };
    }

    private static void proceedRegistration(String name, String school, String classes,
            String group, String phone, String email, String version, String mac) {

        int primaryKey = Utility.getPrimaryKey(mac);

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO STUDENTS values(null,?,?,?,?,?,?,?,SYSDATE,'SYSTEM',SYSDATE,'SYSTEM', ?)");
            pstmt.setString(1, name);
            pstmt.setString(2, school);
            pstmt.setString(3, classes);
            pstmt.setString(4, group);
            pstmt.setString(5, phone);
            pstmt.setString(6, email);
            pstmt.setString(7, version);
            pstmt.setInt(8, primaryKey);
            pstmt.executeUpdate();

            // Update Status OF HW_INFO 
            updateFormFillUpStatus(mac);
        } catch (SQLException ex) {
        }
    }

    private static void updateFormFillUpStatus(String mac) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement("Update HW_INFO Set IS_FORM_FILL_UP = 1 WHERE MAC = ?");
            pstmt.setString(1, mac);
            boolean success = pstmt.executeUpdate() != 0;
            dialogLoader.dismiss();

            if (success) {
                JOptionPane.showMessageDialog(null, "Registration successfull.");
            } else {
                JOptionPane.showMessageDialog(null, "Something went wrong!");
            }
        } catch (SQLException ex) {
        }
    }

    protected static synchronized void runInBackground(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
    }
}

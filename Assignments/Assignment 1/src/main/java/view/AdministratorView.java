package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdministratorView extends JFrame {

    private JLabel lUsername;
    private JLabel lPassword;
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton createEmpl;
    private JButton readEmpl;
    private JButton updateEmpl;
    private JButton deleteEmpl;
    //private JButton generateReps;
    private JLabel helloMessage1;
    private JLabel helloMessage2;
    private Color buttonColor = new Color(200, 116, 222);

    private JLabel img;


    public AdministratorView() throws HeadlessException {
        setSize(500, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(null);
        setTitle("Administrator");
        setBounds(10,10,410,600);
        add(helloMessage1);
        helloMessage1.setBounds(159, 40,300,30);
        helloMessage1.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        add(helloMessage2);
        helloMessage2.setBounds(109, 69,300,30);
        helloMessage2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        add(lUsername);
        lUsername.setBounds(20,150,200,30);
        add(tfUsername);
        tfUsername.setBounds(180,150,180,30);
        add(lPassword);
        lPassword.setBounds(20,220,200,30);
        add(tfPassword);
        tfPassword.setBounds(180,220,180,30);
        add(createEmpl);
        createEmpl.setBounds(30,300,160,30);
        createEmpl.setBackground(buttonColor);
        add(readEmpl);
        readEmpl.setBounds(200,300,160,30);
        readEmpl.setBackground(buttonColor);
        add(updateEmpl);
        updateEmpl.setBounds(30,340,160,30);
        updateEmpl.setBackground(buttonColor);
        add(deleteEmpl);
        deleteEmpl.setBounds(200,340,160,30);
        deleteEmpl.setBackground(buttonColor);
//        add(generateReps);
//        generateReps.setBounds(48,390,300,30);
//        generateReps.setBackground(buttonColor);
        add(img);
        img.setBounds(170,413,300,180);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void initializeFields() {

        helloMessage1 = new JLabel("HELLO,");
        helloMessage2 = new JLabel("ADMINISTRATOR!");
        lUsername = new JLabel("EMPLOYEE USERNAME");
        tfUsername = new JTextField();
        lPassword = new JLabel("EMPLOYEE PASSWORD");
        tfPassword = new JPasswordField();
        tfPassword.setEchoChar('*');
        createEmpl = new JButton("Create Employee");
        readEmpl = new JButton("Read Employee");
        updateEmpl = new JButton("Update Employee");
        deleteEmpl = new JButton("Delete Employee");
        //generateReps = new JButton("Generate Reports");
        img = new JLabel(new ImageIcon("C:\\Users\\user\\Desktop\\t/download.png"));

    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }


    public void setCreateEmplButtonListener(ActionListener createEmplButtonListener) {
        createEmpl.addActionListener(createEmplButtonListener);
    }

    public void setReadEmplButtonListener(ActionListener setReadEmplButtonListener) {
        readEmpl.addActionListener(setReadEmplButtonListener);
    }

    public void setUpdateEmplEmplButtonListener(ActionListener setUpdateEmplEmplButtonListener) {
        updateEmpl.addActionListener(setUpdateEmplEmplButtonListener);
    }

    public void setDeleteEmplmplButtonListener(ActionListener setDeleteEmplmplButtonListener) {
        deleteEmpl.addActionListener(setDeleteEmplmplButtonListener);
    }


//    public void setGenerateRepsButtonListener(ActionListener setGenerateRepsButtonListener) {
//        generateReps.addActionListener(setGenerateRepsButtonListener);
//    }

    public void setVisible() {
        this.setVisible(true);
    }

}
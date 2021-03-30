package view;

import model.dto.AccountDTO;
import model.dto.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdministratorView extends JFrame {

    private JLabel lUsername;
    private JLabel lPassword;
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton createEmployee;
    private JButton readEmployee;
    private JButton updateEmployee;
    private JButton deleteEmployee;
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
        add(createEmployee);
        createEmployee.setBounds(30,300,160,30);
        createEmployee.setBackground(buttonColor);
        add(readEmployee);
        readEmployee.setBounds(200,300,160,30);
        readEmployee.setBackground(buttonColor);
        add(updateEmployee);
        updateEmployee.setBounds(30,340,160,30);
        updateEmployee.setBackground(buttonColor);
        add(deleteEmployee);
        deleteEmployee.setBounds(200,340,160,30);
        deleteEmployee.setBackground(buttonColor);
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
        createEmployee = new JButton("Create Employee");
        readEmployee = new JButton("Read Employee");
        updateEmployee = new JButton("Update Employee");
        deleteEmployee = new JButton("Delete Employee");
        img = new JLabel(new ImageIcon("C:\\Users\\user\\Desktop\\t/download.png"));
    }

    public UserDTO getUserDTO(){

        String username = tfUsername.getText();
        String password = tfPassword.getText();

        return new UserDTO(
                username,
                password
        );
    }

    public String getUsername(){ return tfUsername.getText(); }

    public void setCreateEmployeeButtonListener(ActionListener createEmplButtonListener) { createEmployee.addActionListener(createEmplButtonListener); }

    public void setReadEmployeeButtonListener(ActionListener setReadEmplButtonListener) { readEmployee.addActionListener(setReadEmplButtonListener); }

    public void setUpdateEmployeeButtonListener(ActionListener setUpdateEmplEmplButtonListener) { updateEmployee.addActionListener(setUpdateEmplEmplButtonListener); }

    public void setDeleteEmployeeButtonListener(ActionListener setDeleteEmplmplButtonListener) { deleteEmployee.addActionListener(setDeleteEmplmplButtonListener); }

    public void setVisible() {
        this.setVisible(true);
    }

}
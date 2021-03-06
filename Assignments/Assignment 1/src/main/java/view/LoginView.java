package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JLabel username;
    private JLabel password;
    private JButton btnLogin;
    private JButton btnRegister;
    private JButton btnReset;
    private JCheckBox showPassword;
    private JLabel helloMessage1;
    private JLabel helloMessage2;

    private Color buttonColor = new Color(95, 150, 215);

    public LoginView() throws HeadlessException {
        setSize(500, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(null);
        setTitle("Login Form");
        setBounds(10,10,370,600);
        add(helloMessage1);
        helloMessage1.setBounds(97, 40,300,30);
        helloMessage1.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        add(helloMessage2);
        helloMessage2.setBounds(70, 69,300,30);
        helloMessage2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        add(username);
        username.setBounds(50,150,100,30);
        add(tfUsername);
        tfUsername.setBounds(150,150,150,30);
        add(password);
        password.setBounds(50,220,100,30);
        add(tfPassword);
        tfPassword.setBounds(150,220,150,30);
        add(showPassword);
        showPassword.setBounds(150,250,150,30);
        add(btnLogin);
        btnLogin.setBounds(50,300,100,30);
        btnLogin.setBackground(buttonColor);
        add(btnRegister);
        btnRegister.setBounds(230, 510,100,30);
        btnRegister.setBackground(buttonColor);
        add(btnReset);
        btnReset.setBounds(200,300,100,30);
        btnReset.setBackground(buttonColor);

        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        helloMessage1 = new JLabel("WELCOME TO");
        helloMessage2 = new JLabel(" INTERNET BANKING!");
        username = new JLabel("USERNAME:");
        tfUsername = new JTextField();
        password = new JLabel("PASSWORD:");
        tfPassword = new JPasswordField();
        tfPassword.setEchoChar('*');
        btnLogin = new JButton("LOGIN");
        btnRegister = new JButton("REGISTER");
        btnReset = new JButton("RESET");
        showPassword = new JCheckBox("Show Password:");
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public void setUsername(String newUser) { tfUsername.setText(newUser); }

    public void setPassword(String newPassword) { tfPassword.setText(newPassword); }

    public JCheckBox showPassword(){
        return showPassword;
    }

    public JPasswordField getTfPassword(){
        return tfPassword;
    }

    public JButton reset() { return btnReset;}

    public void setLoginButtonListener(ActionListener loginButtonListener) { btnLogin.addActionListener(loginButtonListener); }

    public void setRegisterButtonListener(ActionListener registerButtonListener) { btnRegister.addActionListener(registerButtonListener);}

    public void setResetButtonListener(ActionListener resetButtonListener) { btnReset.addActionListener(resetButtonListener); }

    public void setShowPassButtonListener(ActionListener showPassButtonListener) { showPassword.addActionListener(showPassButtonListener); }

    public void setVisible() {
        this.setVisible(true);
    }


}

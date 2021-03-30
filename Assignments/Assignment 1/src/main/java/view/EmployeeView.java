package view;

import model.dto.AccountDTO;
import model.dto.ClientDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeView extends JFrame {

    private JLabel lClientName;
    private JLabel lIdentityCardNb;
    private JLabel lCNP;
    private JLabel lAddress;
    private JTextField tfClientName;
    private JTextField tfIdentityCardNb;
    private JTextField tfCNP;
    private JTextField tfAddress;
    private Choice client;
    private JButton btnClient;
    private JLabel lIdentificationNb;
    private JLabel lType;
    private JLabel lAmount;
    private JLabel lDate;
    private JTextField tfIdentificationNb;
    private JTextField tfType;
    private JTextField tfAmount;
    private JTextField tfDate;
    private Choice account;
    private JButton btnAccount;
    private  JLabel lSender;
    private JTextField tfSender;
    private JLabel lReceiver;
    private JTextField tfReceiver;
    private JLabel lTransferAmount;
    private JTextField tfTransferAmount;
    private JButton btnTransfer;
    private JLabel lClientBill;
    private JTextField tfClientBill;
    private JLabel lAmountBill;
    private JTextField tfAmountBill;
    private JButton btnBills;
    private JTextArea tableView;


    private Color buttonColor = new Color(76, 234, 85);

    public EmployeeView() throws HeadlessException {
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(null);
        setTitle("Employee");
        setBounds(10, 10, 410, 1100);
        add(lClientName);
        lClientName.setBounds(35, 40, 200, 20);
        add(tfClientName);
        tfClientName.setBounds(160, 40, 200, 27);
        add(lIdentityCardNb);
        lIdentityCardNb.setBounds(30, 77, 200, 20);
        add(tfIdentityCardNb);
        tfIdentityCardNb.setBounds(160, 77, 200, 27);
        add(lCNP);
        lCNP.setBounds(60, 116, 200, 20);
        add(tfCNP);
        tfCNP.setBounds(160, 116, 200, 27);
        add(lAddress);
        lAddress.setBounds(50, 155, 200, 20);
        add(tfAddress);
        tfAddress.setBounds(160, 155, 200, 27);
        client.add("Add Client");
        client.add("Update Client");
        client.add("View Client");
        add(client);
        client.setBounds(20,210,150,30);
        add(btnClient);
        btnClient.setBounds(180,210,200,20);
        btnClient.setBackground(buttonColor);
        add(lIdentificationNb);
        lIdentificationNb.setBounds(20,280,200,20);
        add(tfIdentificationNb);
        tfIdentificationNb.setBounds(180, 280, 200, 27);
        add(lType);
        lType.setBounds(39, 318, 200, 20);
        add(tfType);
        tfType.setBounds(180,318, 200, 27);
        add(lAmount);
        lAmount.setBounds(26, 355, 200, 20);
        add(tfAmount);
        tfAmount.setBounds(180,355, 200, 27);
        add(lDate);
        lDate.setBounds(26, 391, 200, 20);
        add(tfDate);
        tfDate.setBounds(180,391,200,27);
        account.add("Create Account");
        account.add("Update Account");
        account.add("Delete Account");
        account.add("View Account");
        add(account);
        account.setBounds(20,450,150,30);
        add(btnAccount);
        btnAccount.setBounds(180,450,200,20);
        btnAccount.setBackground(buttonColor);
        add(lSender);
        lSender.setBounds(15, 510, 200, 20);
        add(tfSender);
        tfSender.setBounds(20, 530, 70, 27);
        add(lReceiver);
        lReceiver.setBounds(158, 510, 200, 20);
        add(tfReceiver);
        tfReceiver.setBounds(163, 530, 70, 27);
        add(lTransferAmount);
        lTransferAmount.setBounds(320, 510, 200, 20);
        add(tfTransferAmount);
        tfTransferAmount.setBounds(310, 530, 70, 27 );
        add(btnTransfer);
        btnTransfer.setBounds(140, 566, 110, 20);
        btnTransfer.setBackground(buttonColor);
        add(lClientBill);
        lClientBill.setBounds(53, 628, 200, 20);
        add(tfClientBill);
        tfClientBill.setBounds(40, 650, 130, 27);
        add(lAmountBill);
        lAmountBill.setBounds(222,628, 200, 20);
        add(tfAmountBill);
        tfAmountBill.setBounds(220, 648, 130, 27);
        add(btnBills);
        btnBills.setBounds(130,688, 140, 20);
        btnBills.setBackground(buttonColor);
        add(tableView);
        tableView.setBounds(12,720, 400, 90);
        tableView.setEditable(false);
        //tfIdentificationNb.setEditable(false);
        this.setSize(450, 800);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void initializeFields() {

        lClientName = new JLabel("CLIENT NAME");
        tfClientName = new JTextField();
        lIdentityCardNb = new JLabel("IDENTITY CARD");
        tfIdentityCardNb = new JTextField();
        lCNP = new JLabel("CNP");
        tfCNP = new JTextField();
        lAddress = new JLabel("ADDRESS");
        tfAddress = new JTextField();
        client = new Choice();
        btnClient = new JButton("Perform Client Op");
        lIdentificationNb = new JLabel("CARD IDENTIFICATION");
        tfIdentificationNb = new JTextField();
        lType = new JLabel("ACCOUNT TYPE");
        tfType = new JTextField();
        lAmount = new JLabel("AMOUNT OF MONEY");
        tfAmount = new JTextField();
        lDate  = new JLabel("DATE OF CREATION");
        tfDate = new JTextField();
        account = new Choice();
        btnAccount = new JButton("Perform Account Op");
        lSender = new JLabel("SENDER CARD ID");
        tfSender = new JTextField();
        lReceiver = new JLabel("RECEIVER CARD ID");
        tfReceiver = new JTextField();
        lTransferAmount = new JLabel("AMOUNT");
        tfTransferAmount = new JTextField();
        btnTransfer = new JButton("TRANSFER");
        lClientBill = new JLabel("CLIENT ID CARD");
        tfClientBill = new JTextField();
        lAmountBill = new JLabel("AMOUNT OF MONEY");
        tfAmountBill = new JTextField();
        btnBills = new JButton("PAY UTILITIES");
        tableView = new JTextArea("Information: ");

    }

    public long getClientCNP(){

        String cnp = tfCNP.getText().trim();
        if(cnp.equals("")){
            return 0;

        } else{
            return Long.parseLong(cnp);
        }
    }
    public long getAccountIdentification(){
        String accountIdentificationNr = tfIdentificationNb.getText().trim();
        if (accountIdentificationNr.equals("")) {
            return 0;

        } else{
            return Long.parseLong(accountIdentificationNr);
        }
    }

    public long getAccountIdentification_Utilities(){
        String accountIdentificationNr = tfClientBill.getText().trim();
        if(accountIdentificationNr.equals("")){
            return 0;

        } else {
            return Long.parseLong(accountIdentificationNr);
        }
    }

    public int getAmount_Bills(){
        String amountBills = tfAmountBill.getText().trim();
        if(amountBills.equals("")) {
            return 0;

        } else{
            return Integer.parseInt(amountBills);
        }
    }

    public long getAccountIdentification_Sender(){
        String accountIdentificationSender = tfSender.getText().trim();
        if(accountIdentificationSender.equals("")) {
            return 0;

        } else {
            return Long.parseLong(accountIdentificationSender);
        }
    }

    public long getAccountIdentification_Receiver(){
        String accountIdentificationReceiver = tfReceiver.getText().trim();
        if(accountIdentificationReceiver.equals("")) {
            return 0;

        } else {
            return Long.parseLong(accountIdentificationReceiver);
        }
    }

    public int getAmount_Transfer(){
        String amountTransfer = tfTransferAmount.getText().trim();
        if(amountTransfer.equals("")){
            return 0;

        } else{
            return Integer.parseInt(amountTransfer);
        }
    }

    public Choice getClientChoice() {
        return client;
    }

    public Choice getAccountChoice() {
        return account;
    }

    public void clearFieldsClient(){
        tfClientName.setText("");
        tfIdentityCardNb.setText("");
        tfCNP.setText("");
        tfAddress.setText("");
    }

    public void clearFieldsAccount(){
        tfClientName.setText("");
        tfIdentificationNb.setText("");
        tfAmount.setText("");
        tfType.setText("");
        tfDate.setText("");
    }

    public void clearFieldsTransfer(){
        tfSender.setText("");
        tfReceiver.setText("");
        tfTransferAmount.setText("");
    }

    public void clearFieldsBills(){
        tfClientBill.setText("");
        tfAmountBill.setText("");
    }

    public ClientDTO getClientDTO() {

        String name = tfClientName.getText();
        String clientIdentificationNr = tfIdentityCardNb.getText().trim();
        String cnp = tfCNP.getText().trim();
        String address = tfAddress.getText();
            return new ClientDTO(
                    name,
                    Long.parseLong(clientIdentificationNr),
                    Long.parseLong(cnp),
                    address
            );
    }

    public AccountDTO getAccountDTO(){

        String accountIdentity = tfIdentificationNb.getText().trim();
        String amountOfMoney = tfAmount.getText().trim();
        String accountType = tfType.getText();
        String accountDate = tfDate.getText();

        return new AccountDTO(
                Long.parseLong(accountIdentity),
                accountType,
                Integer.parseInt(amountOfMoney),
                accountDate
        );
    }

    public void setTableView(String text){
        tableView.setText("Information:");
        tableView.append(text);
    }

    public void setBtnClientButtonListener(ActionListener setBtnClientButtonListener) { btnClient.addActionListener(setBtnClientButtonListener); }

    public void setBtnAccountButtonListener(ActionListener setBtnAccountButtonListener) { btnAccount.addActionListener(setBtnAccountButtonListener); }

    public void setBtnTransferButtonListener(ActionListener setBtnTransferButtonListener) { btnTransfer.addActionListener(setBtnTransferButtonListener); }

    public void setBtnBillsButtonListener(ActionListener setBtnBillsButtonListener) { btnBills.addActionListener(setBtnBillsButtonListener); }

    public void setVisible() {
        this.setVisible(true);
    }
}


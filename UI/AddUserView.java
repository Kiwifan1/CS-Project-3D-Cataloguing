package UI;

import Logic.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension;

public class AddUserView extends JFrame implements ActionListener {
    ConnectLogic logic;
    AuditLog auditLog;
    AppUser appUser;

    JPanel mainPanel;

    JButton addUserButton;
    JButton exitButton;
    JTextField userField;
    JPasswordField passField;
    JLabel userLabel;
    JLabel passLabel;
    JLabel message;

    public AddUserView(ConnectLogic logic, AuditLog auditLog) {
        super("Add User");

        this.logic = logic;
        this.auditLog = auditLog;

        appUser = new AppUser(logic);

        setSize(300, 170);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        makePanel();

        this.setVisible(true);
    }

    public void makePanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        userLabel = new JLabel("Username: ");
        passLabel = new JLabel("Password: ");
        message = new JLabel(" ");

        userField = new JTextField();
        passField = new JPasswordField();

        userField.setPreferredSize(new Dimension(100, 20));
        passField.setPreferredSize(new Dimension(100, 20));
        userField.setMaximumSize(userField.getPreferredSize());
        passField.setMaximumSize(passField.getPreferredSize());

        addUserButton = new JButton("Add User");
        exitButton = new JButton("Exit");
        addUserButton.addActionListener(this);
        exitButton.addActionListener(this);

        JPanel userPanel = new JPanel();
        userPanel.add(userLabel);
        userPanel.add(userField);
        JPanel passPanel = new JPanel();
        passPanel.add(passLabel);
        passPanel.add(passField);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addUserButton);
        buttonPanel.add(exitButton);
        JPanel messagePanel = new JPanel();
        messagePanel.add(message);

        mainPanel.add(userPanel);
        mainPanel.add(passPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(messagePanel);

        this.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addUserButton) {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (username.equals("") || password.equals("")) {
                message.setText("Please enter a username and password.");
            } else if (appUser.addUser(username, password)) {
                message.setForeground(Color.GREEN);
                message.setText("User added successfully.");
                auditLog.log("Added user " + username + " to the database.", username);
            } else if (appUser.userExists(username)) {
                message.setForeground(Color.RED);
                message.setText("User already exists.");
            } else {
                message.setForeground(Color.RED);
                message.setText("Error adding user.");
            }
        } else if (e.getSource() == exitButton) {
            this.dispose();
            new LoginView(this.logic, this.auditLog);
        }
    }
}

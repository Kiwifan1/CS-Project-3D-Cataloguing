/**
 * Name: Joshua Venable
 * Class: CPSC 321, Fall 2022
 * Date: 11/23/2022
 * Programming Assigment: 3D Cataloguing
 * Description: 
 * Notes: 
 * TODO: Implement Way to add user, fix layout of user and password fields
 * 
 **/

package UI;

import Logic.*;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.event.*;

public class LoginView extends JFrame implements ActionListener {

    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    ConnectLogic logic;
    Login login;
    AuditLog auditLog;

    JPanel mainPanel;

    JButton loginButton;
    JButton newUserButton;
    JTextField userField;
    JPasswordField passField;
    JLabel userLabel;
    JLabel passLabel;
    JLabel message;

    LibraryView libraryView;

    public LoginView() {
        super("Sign In");

        logic = new ConnectLogic();
        login = new Login(logic);
        auditLog = new AuditLog(logic);

        auditLog.cleanup();

        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        makePanel();

        this.setVisible(true);
    }

    public LoginView(ConnectLogic logic, AuditLog auditLog) {
        super("Sign In");

        this.logic = logic;
        this.auditLog = auditLog;
        login = new Login(logic);

        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        makePanel();

        this.setVisible(true);
    }

    private void makeInteractables() {

        // make labels for password and username
        userLabel = new JLabel("Username: ");
        passLabel = new JLabel("Password: ");

        userField = new JTextField(10);
        passField = new JPasswordField(10);

        // make login button
        loginButton = new JButton("Login");
        newUserButton = new JButton("New User");

        userField.addActionListener(this);
        passField.addActionListener(this);
        loginButton.addActionListener(this);
        newUserButton.addActionListener(this);

        // make login message
        message = new JLabel();
    }

    private void makePanel() {
        mainPanel = new JPanel(new GridLayout(3, 1));

        makeInteractables();

        mainPanel.add(userLabel);
        mainPanel.add(userField);

        mainPanel.add(passLabel);
        mainPanel.add(passField);

        mainPanel.add(message);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(loginButton);
        buttonPanel.add(newUserButton);

        mainPanel.add(buttonPanel);

        this.add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String user = userField.getText();
            char[] pass = passField.getPassword();
            String password = new String(pass);

            if (login.login(user, password)) {
                userField.setText("");
                passField.setText("");

                message.setForeground(Color.GREEN);
                message.setText("Login Successful");
                auditLog.log("Login", user);
                this.dispose();
                libraryView = new LibraryView(this.logic, this.login, this.auditLog);
            } else {
                message.setForeground(Color.RED);
                message.setText("Login Failed");
            }
        } else if (e.getSource() == newUserButton) {
            this.dispose();
            new AddUserView(this.logic, this.auditLog);
        }
    }
}

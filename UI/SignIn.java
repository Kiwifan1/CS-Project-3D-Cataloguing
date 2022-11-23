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

import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.*;

public class SignIn extends JFrame {

    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    ConnectLogic logic;
    Login login;

    JPanel mainPanel;

    JButton loginButton;
    JTextField userField;
    JPasswordField passField;
    JLabel userLabel;
    JLabel passLabel;

    public SignIn() {
        super("Sign In");

        logic = new ConnectLogic();
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        makePanel();

        this.setVisible(true);
    }

    private void makeInteractables() {

        userLabel = new JLabel("Username: ");
        passLabel = new JLabel("Password: ");
        
        userField = new JTextField(10);
        passField = new JPasswordField(10);

        loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            System.out.println("User pressed Login");
            String user = userField.getText();
            char[] pass = passField.getPassword();
            String password = "";
            for (int i = 0; i < pass.length; i++) {
                password += pass[i];
                pass[i] = '\0'; // security
            }
            boolean checkUser = login.login(user, password);
            
            if(checkUser) {
                // go to home screen
            } else {
                // let know incorrect
            }
        });
    }

    private void makePanel() {
        JPanel userPanel = new JPanel();
        JPanel passPanel = new JPanel();
        mainPanel = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // need to change to different layout
        makeInteractables();
        
        userPanel.add(userLabel, BorderLayout.WEST);
        userPanel.add(userField, BorderLayout.EAST);
        
        passPanel.add(passLabel, BorderLayout.WEST);
        passPanel.add(passField, BorderLayout.EAST);

        mainPanel.add(userPanel);
        mainPanel.add(passPanel);

        mainPanel.add(loginButton, BorderLayout.CENTER);

        this.setContentPane(mainPanel);
    }
}

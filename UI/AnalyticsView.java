package UI;

import Logic.*;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.event.*;

public class AnalyticsView extends BoilerPlateView implements ActionListener {

    ConnectLogic logic;
    Login login;
    JPanel mainPanel;

    public AnalyticsView(ConnectLogic logic, Login login) {
        super("Analytics");

        this.logic = logic;
        this.login = login;

        mainPanel = new JPanel();

        this.add(mainPanel);

        addMenuListeners();

        this.setVisible(true);
    }

    /**
     * Creates the auditt log panel
     */
    private void createAuditPanel() {
        
    }

    @Override
    protected void addMenuListeners() {
        logout.addActionListener(e -> {
            this.dispose();
            new LoginView(this.logic);
        });

        library.addActionListener(e -> {
            this.dispose();
            new LibraryView(this.logic, this.login);
        });

        addItem.addActionListener(e -> {
            this.dispose();
            new AddView(this.logic, this.login);
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

}

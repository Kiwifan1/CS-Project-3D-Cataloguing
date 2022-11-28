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

    JPanel mainPanel;

    public AnalyticsView(ConnectLogic logic) {
        super("Analytics");

        mainPanel = new JPanel();

        createSidePanel();
        
        this.add(mainPanel);

        this.setVisible(true);
    }

    // analytics view will have a list of quantitative analysis items on the left-hand side
    private void createSidePanel() {
        // create side panel
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // add side panel to main panel
        mainPanel.add(sidePanel, BorderLayout.WEST);
    }

    // in the center of the screen, there will be a display area for graphs
    private void createDisplayArea() {
        // create display area
        JPanel displayArea = new JPanel();
        displayArea.setLayout(new BoxLayout(displayArea, BoxLayout.Y_AXIS));
        displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // add display area to main panel
        mainPanel.add(displayArea, BorderLayout.CENTER);
    }
    
    // on the bottom, there will be an audit log
    private void createAuditLog() {
        // create audit log
        JPanel auditLog = new JPanel();
        auditLog.setLayout(new BoxLayout(auditLog, BoxLayout.Y_AXIS));
        auditLog.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // add audit log to main panel
        mainPanel.add(auditLog, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}

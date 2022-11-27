package UI;

import Logic.*;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.event.*;

public class AnalyticsView extends BoilerPlateView implements ActionListener {

    JPanel mainPanel;

    public AnalyticsView() {
        super("Analytics");

        mainPanel = new JPanel();
        createAnalyticsPanel();
        createGraphPanel();
        createAuditLogPanel();

        this.add(mainPanel);

        this.setVisible(true);
    }

    // add left panel that contains analytics
    private void createAnalyticsPanel() {
        // create panel
        JPanel analyticsPanel = new JPanel();
        analyticsPanel.setLayout(new BoxLayout(analyticsPanel, BoxLayout.Y_AXIS));
        analyticsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // create search bar
        JPanel searchBar = new JPanel();
        searchBar.setLayout(new BoxLayout(searchBar, BoxLayout.X_AXIS));
        searchBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // create search bar text field
        JTextField searchField = new JTextField(20);
        searchField.setMaximumSize(searchField.getPreferredSize());
        searchField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchField.addActionListener(this);

        // create search bar button
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this);

        // add search bar components to search bar
        searchBar.add(searchField);
        searchBar.add(searchButton);

        // add search bar to panel
        analyticsPanel.add(searchBar);

        // add panel to main panel
        mainPanel.add(analyticsPanel);
    }

    // add right panel that contains graphs
    private void createGraphPanel() {
        // create panel
        JPanel graphPanel = new JPanel();
        graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
        graphPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // add panel to main panel
        mainPanel.add(graphPanel);
    }

    // add bottom panel that contains audit log
    private void createAuditLogPanel() {
        // create panel
        JPanel auditLogPanel = new JPanel();
        auditLogPanel.setLayout(new BoxLayout(auditLogPanel, BoxLayout.Y_AXIS));
        auditLogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // add panel to main panel
        mainPanel.add(auditLogPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}

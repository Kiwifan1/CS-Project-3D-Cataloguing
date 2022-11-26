package UI;

import Logic.*;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.event.*;

public class LibraryView extends BoilerPlateView implements ActionListener {

    JPanel mainPanel;

    public LibraryView() {
        super("Library");

        mainPanel = new JPanel();

        createSearchParams();
        createDisplayArea();

        this.add(mainPanel);

        this.setVisible(true);
    }

    private void createSearchParams() {
        // create search parameters
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

        // create search parameters
        JPanel searchParams = new JPanel();
        searchParams.setLayout(new BoxLayout(searchParams, BoxLayout.X_AXIS));
        searchParams.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // create search parameters labels
        JLabel searchParamLabel = new JLabel("Search Parameters: ");
        JLabel searchParamLabel2 = new JLabel("Search Parameters: ");
        JLabel searchParamLabel3 = new JLabel("Search Parameters: ");

        // create search parameters text fields
        JTextField searchParamField = new JTextField(10);
        JTextField searchParamField2 = new JTextField(10);
        JTextField searchParamField3 = new JTextField(10);

        // add search parameters components to search parameters
        searchParams.add(searchParamLabel);
        searchParams.add(searchParamField);
        searchParams.add(searchParamLabel2);
        searchParams.add(searchParamField2);
        searchParams.add(searchParamLabel3);
        searchParams.add(searchParamField3);

        // add search bar and search parameters to search panel
        searchPanel.add(searchBar);
        searchPanel.add(searchParams);

        // add search panel to main panel
        mainPanel.add(searchPanel);

    }

    private void createDisplayArea() {
        // create large display area where results will be displayed
        JPanel displayArea = new JPanel();
        displayArea.setLayout(new BoxLayout(displayArea, BoxLayout.Y_AXIS));
        displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // create display area title
        JLabel displayAreaTitle = new JLabel("Results: ");

        // create display area text area
        JTextArea displayAreaText = new JTextArea(20, 50);
        displayAreaText.setEditable(false);
        displayAreaText.setLineWrap(true);
        displayAreaText.setWrapStyleWord(true);
        displayAreaText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // add display area title and text area to display area
        displayAreaTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        displayArea.add(displayAreaTitle);
        displayArea.add(displayAreaText);

        // add display area to main panel
        mainPanel.add(displayArea);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}

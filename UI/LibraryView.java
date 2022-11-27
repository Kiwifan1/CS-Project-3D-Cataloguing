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

        searchParamLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchParamLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchParamLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);

        // create search parameters checkbox panels
        JPanel searchParamPanel = new JPanel();
        searchParamPanel.setLayout(new BoxLayout(searchParamPanel, BoxLayout.Y_AXIS));
        searchParamPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel searchParamPanel2 = new JPanel();
        searchParamPanel2.setLayout(new BoxLayout(searchParamPanel2, BoxLayout.Y_AXIS));
        searchParamPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel searchParamPanel3 = new JPanel();
        searchParamPanel3.setLayout(new BoxLayout(searchParamPanel3, BoxLayout.Y_AXIS));
        searchParamPanel3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // create search parameters text fields
        JTextField searchParamField = new JTextField(20);
        searchParamField.setMaximumSize(searchParamField.getPreferredSize());
        searchParamField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchParamField.addActionListener(this);

        JTextField searchParamField2 = new JTextField(20);
        searchParamField2.setMaximumSize(searchParamField2.getPreferredSize());
        searchParamField2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField searchParamField3 = new JTextField(20);
        searchParamField3.setMaximumSize(searchParamField3.getPreferredSize());
        searchParamField3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        searchParamPanel.add(searchParamLabel);
        searchParamPanel.add(searchParamField);
        searchParamPanel2.add(searchParamLabel2);
        searchParamPanel2.add(searchParamField2);
        searchParamPanel3.add(searchParamLabel3);
        searchParamPanel3.add(searchParamField3);

        // add search parameters components to search parameters
        searchParams.add(searchParamPanel);
        searchParams.add(searchParamPanel2);
        searchParams.add(searchParamPanel3);

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

        // create display area panel where results will be displayed in a scrollable grid layout
        JPanel displayAreaPanel = new JPanel();
        displayAreaPanel.setLayout(new GridLayout(0, 5));
        displayAreaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // add sample data into display area panel
        for (int i = 0; i < 20; i++) {
            JPanel result = new JPanel();
            result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
            result.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel resultTitle = new JLabel("Title: " + i);
            JLabel resultAuthor = new JLabel("Author: " + i);
            JLabel resultGenre = new JLabel("Genre: " + i);
            JLabel resultYear = new JLabel("Year: " + i);

            result.add(resultTitle);
            result.add(resultAuthor);
            result.add(resultGenre);
            result.add(resultYear);

            displayAreaPanel.add(result);
        }

        // create display area scroll pane
        JScrollPane displayAreaScrollPane = new JScrollPane(displayAreaPanel);
        displayAreaScrollPane.setPreferredSize(new Dimension(700, 300));

        // make display area scroll pane scrollable
        displayAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        displayAreaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // add display area title and scroll area to display area
        displayAreaTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        displayArea.add(displayAreaTitle);
        displayArea.add(displayAreaScrollPane);

        // add display area to main panel
        mainPanel.add(displayArea);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}

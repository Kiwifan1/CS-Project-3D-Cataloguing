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

    static final int SCROLL_WIDTH = 180;
    static final int SCROLL_HEIGHT = 150;
    static final int DISPLAY_WIDTH = 700;
    static final int DISPLAY_HEIGHT = 250;

    ConnectLogic logic;
    Publisher publisher;
    Release release;
    Asset asset;
    Attribute attribute;

    JPanel mainPanel;
    JPanel searchBar;

    JScrollPane publisherScroll;
    JScrollPane releaseScroll;
    JScrollPane scaleScroll;
    JScrollPane attributeScroll;

    public LibraryView(ConnectLogic logic) {
        super("Library");

        this.logic = logic;
        publisher = new Publisher(logic);
        release = new Release(logic);
        attribute = new Attribute(logic);
        asset = new Asset(logic);

        mainPanel = new JPanel();

        createSearchParams();
        createDisplayArea();

        this.add(mainPanel);

        addMenuListeners();

        this.setVisible(true);
    }

    private void createSearchParams() {
        // create search parameters
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        createSearchBar();
        createScrollPanes();
        createScrollPanes();

        // add search bar and search parameters to search panel
        searchPanel.add(searchBar);

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.X_AXIS));

        scrollPanel.add(publisherScroll);
        scrollPanel.add(releaseScroll);
        scrollPanel.add(scaleScroll);
        scrollPanel.add(attributeScroll);

        // add search panel to main panel
        mainPanel.add(searchPanel);
        mainPanel.add(scrollPanel);

    }

    /**
     * Creates the search bar for typing in names
     */
    private void createSearchBar() {
        // create search bar
        searchBar = new JPanel();
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
    }

    /**
     * Creates the scroll panes for searching criteria
     */
    private void createScrollPanes() {
        createPublisherPane();
        createReleasePane();
        createScalePane();
        createAttributePane();
    }

    private void createPublisherPane() {
        ArrayList<String> publishers = publisher.getAllPublishers();

        JPanel publisherPanel = new JPanel();
        publisherPanel.setLayout(new BoxLayout(publisherPanel, BoxLayout.Y_AXIS));

        for (String publisher : publishers) {
            JCheckBox publisherBox = new JCheckBox(publisher);
            publisherBox.addActionListener(this);
            publisherPanel.add(publisherBox);
        }

        publisherScroll = new JScrollPane(publisherPanel);
        publisherScroll.setBorder(BorderFactory.createTitledBorder("Publishers"));
        publisherScroll.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
        publisherScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        publisherScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void createReleasePane() {
        String[] publishers = getChecked(publisherScroll);

        ArrayList<String> releases = release.getReleaseFromPub(publishers);

        JPanel releasePanel = new JPanel();
        releasePanel.setLayout(new BoxLayout(releasePanel, BoxLayout.Y_AXIS));

        for (String release : releases) {
            JCheckBox releaseBox = new JCheckBox(release);
            releaseBox.addActionListener(this);
            releasePanel.add(releaseBox);
        }

        releaseScroll = new JScrollPane(releasePanel);
        releaseScroll.setBorder(BorderFactory.createTitledBorder("Releases"));
        releaseScroll.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
        releaseScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        releaseScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void createScalePane() {
        ArrayList<String> scales = asset.getAllScales();

        JPanel scalePanel = new JPanel();
        scalePanel.setLayout(new BoxLayout(scalePanel, BoxLayout.Y_AXIS));

        for (String scale : scales) {
            JCheckBox scaleBox = new JCheckBox(scale);
            scaleBox.addActionListener(this);
            scalePanel.add(scaleBox);
        }

        scaleScroll = new JScrollPane(scalePanel);
        scaleScroll.setBorder(BorderFactory.createTitledBorder("Scales"));
        scaleScroll.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
        scaleScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scaleScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void createAttributePane() {
        ArrayList<String> attributes = attribute.getAllAttributes();

        JPanel attributePanel = new JPanel();
        attributePanel.setLayout(new BoxLayout(attributePanel, BoxLayout.Y_AXIS));

        for (String attribute : attributes) {
            JCheckBox attributeBox = new JCheckBox(attribute);
            attributeBox.addActionListener(this);
            attributePanel.add(attributeBox);
        }

        attributeScroll = new JScrollPane(attributePanel);
        attributeScroll.setBorder(BorderFactory.createTitledBorder("Attributes"));
        attributeScroll.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
        attributeScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        attributeScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    /**
     * Creates the display area for the search results
     */
    private void createDisplayArea() {
        // create large display area where results will be displayed
        JPanel displayArea = new JPanel();
        displayArea.setLayout(new BoxLayout(displayArea, BoxLayout.Y_AXIS));
        displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // create display area title
        JLabel displayAreaTitle = new JLabel("Results: ");

        // create display area panel where results will be displayed in a scrollable
        // grid layout
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
        displayAreaScrollPane.setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));

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

    /**
     * Checks all the checkboxes in a scroll pane and returns the text of the ones
     * that are checked.
     * 
     * @param scroll The scroll pane to check
     * @return Returns an ArrayList of the text of the checked checkboxes, if
     *         nothing is checked, return ['*']
     */
    public String[] getChecked(JScrollPane scroll) {
        ArrayList<String> checked = new ArrayList<String>();

        JPanel panel = (JPanel) scroll.getViewport().getView();

        for (Component component : panel.getComponents()) {
            JCheckBox box = (JCheckBox) component;

            if (box.isSelected()) {
                checked.add(box.getText());
            }
        }

        return checked.toArray(new String[checked.size()]);
    }

    /**
     * Updates the release scroll based upon the publishers chosen
     * 
     * @param publishers The publishers that have been chosen
     */
    private void updateReleaseScroll(String[] publishers) {
        ArrayList<String> releases = release.getReleaseFromPub(publishers);

        JPanel releaseBox = new JPanel();
        releaseBox.setLayout(new BoxLayout(releaseBox, BoxLayout.Y_AXIS));

        for (String release : releases) {
            JCheckBox releaseCheck = new JCheckBox(release);
            releaseCheck.addActionListener(this);
            releaseBox.add(releaseCheck);
        }

        releaseScroll.setViewportView(releaseBox);
    }

    /**
     * Updates the scale scroll based upon the release chosen
     * 
     * @param releases The release ids that have been chosen
     */
    private void updateScaleScroll(int[] releaseIds) {
        ArrayList<String> scales = asset.getScalesFromRelease(releaseIds);

        JPanel scaleBox = new JPanel();
        scaleBox.setLayout(new BoxLayout(scaleBox, BoxLayout.Y_AXIS));

        for (String scale : scales) {
            JCheckBox scaleCheck = new JCheckBox(scale);
            scaleCheck.addActionListener(this);
            scaleBox.add(scaleCheck);
        }

        scaleScroll.setViewportView(scaleBox);
    }

    private int[] getReleaseIds(String[] publishers) {
        ArrayList<Integer> ids = release.getRidsFromPublishers(publishers);
        return ids.stream().mapToInt(i -> i).toArray();
    }

    @Override
    protected void addMenuListeners() {
        logout.addActionListener(e -> {
            this.dispose();
            new LoginView(this.logic);
        });

        analytics.addActionListener(e -> {
            this.dispose();
            new AnalyticsView(this.logic);
        });

        addItem.addActionListener(e -> {
            this.dispose();
            new AddView(this.logic);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof JCheckBox) {
            JCheckBox box = (JCheckBox) e.getSource();
            String[] publishers = getChecked(publisherScroll);
            int[] releaseIds = getReleaseIds(publishers);

            if (box.getParent() == publisherScroll.getViewport().getView()) {
                updateReleaseScroll(publishers);
                updateScaleScroll(releaseIds);
            }

            else if (box.getParent() == releaseScroll.getViewport().getView()) {
                updateScaleScroll(releaseIds);
            }
        }
    }
}

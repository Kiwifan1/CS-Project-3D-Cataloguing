/**
 * Name: Joshua Venable
 * Class: CPSC 321, Spring 2022
 * Date: 11/28/2022
 * Programming Assigment: 3D Cataloguing Software
 * Description: 
 * Notes: 
 * TODO: Fix issue where selecting release does not change scales available, if publisher with scales available also chosen
 * 
 **/

package UI;

import Logic.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
    Login login;
    Publisher publisher;
    Release release;
    Asset asset;
    Scale scale;
    Attribute attribute;

    JPanel mainPanel;
    JPanel searchBar;
    JPanel displayAreaPanel;
    JPanel scrollPanel;

    JPanel pubPanel;
    JPanel relPanel;
    JPanel scalePanel;
    JPanel attrPanel;

    JScrollPane publisherScroll;
    JScrollPane releaseScroll;
    JScrollPane scaleScroll;
    JScrollPane attributeScroll;

    JTextField pubSearch;
    JTextField relSearch;
    JTextField scaleSearch;
    JTextField attrSearch;

    AddView addView;

    HashMap<String, Boolean> pubSearchMap;
    HashMap<String, Boolean> relSearchMap;
    HashMap<String, Boolean> scaleSearchMap;
    HashMap<String, Boolean> attrSearchMap;

    public LibraryView(ConnectLogic logic, Login login) {
        super("Library");

        this.logic = logic;
        this.login = login;
        publisher = new Publisher(logic);
        release = new Release(logic);
        attribute = new Attribute(logic);
        asset = new Asset(logic);
        scale = new Scale(logic);

        mainPanel = new JPanel();

        addView = new AddView(this.logic, this.login);
        addView.setVisible(false);

        createSearchBoxes();
        createSearchParams();
        createDisplayArea();

        this.add(mainPanel);

        addMenuListeners();

        this.setVisible(true);
    }

    /**
     * Gets the search boxes from the addView (better than copying code)
     */
    private void createSearchBoxes() {
        JTextField[] searchBoxes = addView.getSearchBoxes();

        pubSearch = searchBoxes[0];
        relSearch = searchBoxes[1];
        scaleSearch = searchBoxes[2];
        attrSearch = searchBoxes[3];

        pubSearchMap = new HashMap<String, Boolean>();
        relSearchMap = new HashMap<String, Boolean>();
        scaleSearchMap = new HashMap<String, Boolean>();
        attrSearchMap = new HashMap<String, Boolean>();

        addSearchListeners();
    }

    /**
     * Adds search box listeners to the search boxes
     */
    private void addSearchListeners() {

        ArrayList<String> publishers = addView.getChecked(pubSearchMap);
        ArrayList<String> releases = addView.getChecked(relSearchMap);

        int[] releaseIDs = new int[releases.size()];

        for (int i = 0; i < releases.size(); i++) {
            releaseIDs[i] = Integer.parseInt(releases.get(i));
        }

        // update the publisher scroll pane as the user types
        pubSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updatePublisherScroll(pubSearch.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                updatePublisherScroll(pubSearch.getText());
            }

            public void insertUpdate(DocumentEvent e) {
                updatePublisherScroll(pubSearch.getText());
            }
        });

        // update the release scroll pane as the user types

        relSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updateReleaseScroll(relSearch.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                updateReleaseScroll(relSearch.getText());
            }

            public void insertUpdate(DocumentEvent e) {
                updateReleaseScroll(relSearch.getText());
            }
        });

        // update the scale scroll pane as the user types

        scaleSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updateScaleScroll(scaleSearch.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                updateScaleScroll(scaleSearch.getText());
            }

            public void insertUpdate(DocumentEvent e) {
                updateScaleScroll(scaleSearch.getText());
            }
        });

        // update the attribute scroll pane as the user types

        attrSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updateAttributeScroll(attrSearch.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                updateAttributeScroll(attrSearch.getText());
            }

            public void insertUpdate(DocumentEvent e) {
                updateAttributeScroll(attrSearch.getText());
            }
        });

    }

    /**
     * Creates the search parameters for the library view
     */
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

        // make search panels
        scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.X_AXIS));

        pubPanel = new JPanel();
        pubPanel.setLayout(new BoxLayout(pubPanel, BoxLayout.Y_AXIS));

        relPanel = new JPanel();
        relPanel.setLayout(new BoxLayout(relPanel, BoxLayout.Y_AXIS));

        scalePanel = new JPanel();
        scalePanel.setLayout(new BoxLayout(scalePanel, BoxLayout.Y_AXIS));

        attrPanel = new JPanel();
        attrPanel.setLayout(new BoxLayout(attrPanel, BoxLayout.Y_AXIS));

        // add search parameters to respective panels
        pubPanel.add(pubSearch);
        pubPanel.add(publisherScroll);

        relPanel.add(relSearch);
        relPanel.add(releaseScroll);

        scalePanel.add(scaleSearch);
        scalePanel.add(scaleScroll);

        attrPanel.add(attrSearch);
        attrPanel.add(attributeScroll);

        // add panels to scroll panel
        scrollPanel.add(pubPanel);
        scrollPanel.add(relPanel);
        scrollPanel.add(scalePanel);
        scrollPanel.add(attrPanel);

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
        makePublisherPane(false);
        makeReleasePane(false);
        makeScalePane(false);
        makeAttributePane(false);
    }

    /**
     * Creates the scroll pane for publishers
     */
    private void makePublisherPane(boolean isSearch) {
        ArrayList<String> publishers;

        if (isSearch) {
            publishers = publisher.getPublishers(pubSearch.getText());
        } else {
            publishers = publisher.getAllPublishers();
        }

        JPanel publisherPanel = new JPanel();
        publisherPanel.setLayout(new BoxLayout(publisherPanel, BoxLayout.Y_AXIS));

        for (String publisher : publishers) {
            JCheckBox publisherBox = new JCheckBox(publisher);

            if (!pubSearchMap.containsKey(publisher)) {
                pubSearchMap.put(publisher, false);
            }

            publisherBox.addActionListener(e -> {
                pubSearchMap.put(publisher, publisherBox.isSelected());
                this.actionPerformed(e);
            });
            publisherPanel.add(publisherBox);
        }

        publisherScroll = new JScrollPane(publisherPanel);
        publisherScroll.setBorder(BorderFactory.createTitledBorder("Publishers"));
        publisherScroll.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
        publisherScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        publisherScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // add previously selected publishers
        for (String publisher : pubSearchMap.keySet()) {
            for (Component component : publisherPanel.getComponents()) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.getText().equals(publisher)) {
                    checkBox.setSelected(pubSearchMap.get(publisher));
                }
            }
        }
    }

    /*
     * Creates the scroll pane for releases
     */
    private void makeReleasePane(boolean isSearch) {
        ArrayList<String> publishers = addView.getChecked(pubSearchMap);

        ArrayList<String[]> releases;

        if (isSearch) {
            releases = release.getReleaseFromNameAndPub(relSearch.getText(),
                    publishers.toArray(new String[publishers.size()]));
        } else {
            releases = release.getReleaseFromPub(publishers.toArray(new String[publishers.size()]));
        }

        JPanel releasePanel = new JPanel();
        releasePanel.setLayout(new BoxLayout(releasePanel, BoxLayout.Y_AXIS));

        for (String[] release : releases) {
            ReleaseCheckBox releaseBox = new ReleaseCheckBox(release[0], Integer.parseInt(release[1]));

            if (!relSearchMap.containsKey(release[1])) {
                relSearchMap.put(release[1], false);
            }

            releaseBox.addActionListener(e -> {
                relSearchMap.put(release[1], releaseBox.isSelected());
                this.actionPerformed(e);
            });

            releasePanel.add(releaseBox);
        }

        releaseScroll = new JScrollPane(releasePanel);
        releaseScroll.setBorder(BorderFactory.createTitledBorder("Releases"));
        releaseScroll.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
        releaseScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        releaseScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // add previously selected releases
        for (String release : relSearchMap.keySet()) {
            for (Component component : releasePanel.getComponents()) {
                ReleaseCheckBox checkBox = (ReleaseCheckBox) component;
                if (checkBox.getReleaseID() == Integer.parseInt(release)) {
                    checkBox.setSelected(relSearchMap.get(release));
                }
            }
        }
    }

    /**
     * Creates the scroll pane for scales
     */
    private void makeScalePane(boolean isSearch) {
        ArrayList<String> assetScales;
        ArrayList<String> searchScales = new ArrayList<>();
        ArrayList<String> releases = addView.getChecked(relSearchMap);
        ArrayList<String> publisherScales = addView.getChecked(pubSearchMap);

        int[] releaseIDs = new int[releases.size()];

        for (int i = 0; i < releases.size(); i++) {
            releaseIDs[i] = Integer.parseInt(releases.get(i));
        }

        JPanel scalePanel = new JPanel();
        scalePanel.setLayout(new BoxLayout(scalePanel, BoxLayout.Y_AXIS));

        if (isSearch) {
            searchScales = scale.getScales(scaleSearch.getText());
        }

        assetScales = asset.getScalesFromRelease(releaseIDs);
        publisherScales = asset.getScalesFromPub(publisherScales.toArray(new String[publisherScales.size()]));

        for (String scale : assetScales) {
            if (!isSearch || searchScales.contains(scale) && publisherScales.contains(scale)) {
                JCheckBox scaleBox = new JCheckBox(scale);

                if (!scaleSearchMap.containsKey(scale)) {
                    scaleSearchMap.put(scale, false);
                }

                scaleBox.addActionListener(e -> {
                    scaleSearchMap.put(scale, scaleBox.isSelected());
                    this.actionPerformed(e);
                });
                scalePanel.add(scaleBox);
            }
        }

        scaleScroll = new JScrollPane(scalePanel);
        scaleScroll.setBorder(BorderFactory.createTitledBorder("Scales"));
        scaleScroll.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
        scaleScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scaleScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // add previously selected scales
        for (String scale : scaleSearchMap.keySet()) {
            for (Component component : scalePanel.getComponents()) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.getText().equals(scale)) {
                    checkBox.setSelected(scaleSearchMap.get(scale));
                }
            }
        }
    }

    /**
     * Creates the scroll pane for attributes
     */
    private void makeAttributePane(boolean isSearch) {
        ArrayList<String> attributes = new ArrayList<>();

        if (isSearch) {
            attributes = attribute.getAttributes(attrSearch.getText());
        } else {
            attributes = attribute.getAllAttributes();
        }

        JPanel attributePanel = new JPanel();
        attributePanel.setLayout(new BoxLayout(attributePanel, BoxLayout.Y_AXIS));

        for (String attribute : attributes) {
            JCheckBox attributeBox = new JCheckBox(attribute);

            if (!attrSearchMap.containsKey(attribute)) {
                attrSearchMap.put(attribute, false);
            }

            attributeBox.addActionListener(e -> {
                attrSearchMap.put(attribute, attributeBox.isSelected());
                this.actionPerformed(e);
            });
            attributePanel.add(attributeBox);
        }

        attributeScroll = new JScrollPane(attributePanel);
        attributeScroll.setBorder(BorderFactory.createTitledBorder("Attributes"));
        attributeScroll.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
        attributeScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        attributeScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // add previously selected attributes
        for (String attribute : attrSearchMap.keySet()) {
            for (Component component : attributePanel.getComponents()) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.getText().equals(attribute)) {
                    checkBox.setSelected(attrSearchMap.get(attribute));
                }
            }
        }
    }

    /**
     * Updates the publisher scroll
     * 
     * @param name the name of the publisher to search for
     */
    private void updatePublisherScroll(String name) {
        boolean isSearch = (name == null || name.equals("") ? false : true);
        makePublisherPane(isSearch);
        pubPanel.remove(1);
        pubPanel.add(publisherScroll, 1);
        pubPanel.revalidate();
        pubPanel.repaint();
    }

    /**
     * Updates the release scroll
     * 
     * @param name the name of the release to search for
     */
    private void updateReleaseScroll(String name) {
        boolean isSearch = (name == null || name.equals("") ? false : true);
        makeReleasePane(isSearch);
        relPanel.remove(1);
        relPanel.add(releaseScroll, 1);
        relPanel.revalidate();
        relPanel.repaint();
    }

    /**
     * Updates the scale scroll
     * 
     * @param name the name of the scale to search for
     */
    private void updateScaleScroll(String name) {
        boolean isSearch = (name == null || name.equals("") ? false : true);
        makeScalePane(isSearch);
        scalePanel.remove(1);
        scalePanel.add(scaleScroll, 1);
        scalePanel.revalidate();
        scalePanel.repaint();
    }

    /**
     * Updates the attribute scroll
     * 
     * @param name the name of the attribute to search for
     */
    private void updateAttributeScroll(String name) {
        boolean isSearch = (name == null || name.equals("") ? false : true);
        makeAttributePane(isSearch);
        attrPanel.remove(1);
        attrPanel.add(attributeScroll, 1);
        attrPanel.revalidate();
        attrPanel.repaint();
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
        displayAreaPanel = new JPanel();
        displayAreaPanel.setLayout(new GridLayout(0, 5));
        displayAreaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        createResults(displayAreaPanel);

        // create display area scroll pane
        JScrollPane displayAreaScrollPane = new JScrollPane(displayAreaPanel);
        displayAreaScrollPane.setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));

        // make display area scroll pane scrollable
        displayAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // displayAreaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // add display area title and scroll area to display area
        displayAreaTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        displayArea.add(displayAreaTitle);
        displayArea.add(displayAreaScrollPane);

        // add display area to main panel
        mainPanel.add(displayArea);
    }

    /**
     * Creates the results for the display area panel
     * 
     * @param displayAreaPanel the panel to add the results to
     */
    private void createResults(JPanel displayAreaPanel) {
        ArrayList<String> publishers = addView.getChecked(pubSearchMap);
        ArrayList<String> releaseIDs = addView.getChecked(relSearchMap);
        ArrayList<String> scales = addView.getChecked(scaleSearchMap);
        ArrayList<String> attributes = addView.getChecked(attrSearchMap);

        int[] releaseIDsInt = new int[releaseIDs.size()];

        for (int i = 0; i < releaseIDs.size(); i++) {
            releaseIDsInt[i] = Integer.parseInt(releaseIDs.get(i));
        }

        ArrayList<Entity> results = asset.getAssets(publishers.toArray(new String[publishers.size()]), releaseIDsInt,
                scales.toArray(new String[scales.size()]), attributes.toArray(new String[attributes.size()]));

        displayAreaPanel.removeAll();

        // for every entity in the results, create a new result panel and add it to the
        // display
        for (Entity entity : results) {
            JPanel assetPanel = new JPanel();
            assetPanel.setLayout(new BoxLayout(assetPanel, BoxLayout.Y_AXIS));
            assetPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            String path = entity.getFilePath();
            String name = entity.getName();
            if (path.length() > 10) {
                path = path.substring(0, 10) + "...";
            }
            if (name.length() > 20) {
                name = name.substring(0, 20) + "...";
            }

            JLabel assetName = new JLabel(name);
            JLabel assetScale = new JLabel(entity.getScale());
            JLabel assetPath = new JLabel(path);

            assetName.setAlignmentX(Component.CENTER_ALIGNMENT);
            assetScale.setAlignmentX(Component.CENTER_ALIGNMENT);
            assetPath.setAlignmentX(Component.CENTER_ALIGNMENT);

            assetPanel.add(assetName);
            assetPanel.add(assetScale);
            assetPanel.add(assetPath);

            // if clicked twice, open asset
            assetPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        // open path in file explorer
                        try {
                            Desktop.getDesktop().open(new File(entity.getFilePath()));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });

            displayAreaPanel.add(assetPanel);
        }

        displayAreaPanel.revalidate();
        displayAreaPanel.repaint();
    }

    @Override
    protected void addMenuListeners() {
        logout.addActionListener(e -> {
            this.dispose();
            new LoginView(this.logic);
        });

        analytics.addActionListener(e -> {
            this.dispose();
            new AnalyticsView(this.logic, this.login);
        });

        addItem.addActionListener(e -> {
            this.dispose();
            addView.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof JCheckBox) {
            JCheckBox box = (JCheckBox) e.getSource();
            ArrayList<String> releases = addView.getChecked(relSearchMap);

            int[] releaseIds = new int[releases.size()];

            for (int i = 0; i < releases.size(); i++) {
                releaseIds[i] = Integer.parseInt(releases.get(i));
            }

            if (box.getParent() == publisherScroll.getViewport().getView()) {

                updateReleaseScroll(relSearch.getText());
                updateScaleScroll(scaleSearch.getText());
            } else if (box.getParent() == releaseScroll.getViewport().getView()) {
                updateScaleScroll(scaleSearch.getText());
            } else if (box.getParent() == scaleScroll.getViewport().getView()) {
                updateScaleScroll(scaleSearch.getText());
            } else if (box.getParent() == attributeScroll.getViewport().getView()) {
                updateScaleScroll(attrSearch.getText());
            }
        }
        createResults(displayAreaPanel);
    }
}

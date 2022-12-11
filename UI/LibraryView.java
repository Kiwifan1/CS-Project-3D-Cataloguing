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
    static final int EDIT_WIDTH = 300;
    static final int EDIT_HEIGHT = 30;

    private ConnectLogic logic;
    private Login login;
    private AuditLog auditLog;
    private Publisher publisher;
    private AssetRelease release;
    private Asset asset;
    private Scale scale;
    private Attribute attribute;

    private JFrame editFrame;
    private JPanel editPanel;

    private Entity selectedAsset;
    private JPanel selectedPanel;

    private JPanel mainPanel;
    private JPanel searchBar;
    private JPanel displayAreaPanel;
    private JPanel scrollPanel;

    private JPanel pubPanel;
    private JPanel relPanel;
    private JPanel scalePanel;
    private JPanel attrPanel;

    private JScrollPane publisherScroll;
    private JScrollPane releaseScroll;
    private JScrollPane scaleScroll;
    private JScrollPane attributeScroll;

    private JTextField searchField;
    private JTextField pubSearch;
    private JTextField relSearch;
    private JTextField scaleSearch;
    private JTextField attrSearch;

    private HashMap<String, Boolean> pubSearchMap;
    private HashMap<String, Boolean> relSearchMap;
    private HashMap<String, Boolean> scaleSearchMap;
    private HashMap<String, Boolean> attrSearchMap;

    public LibraryView(ConnectLogic logic, Login login, AuditLog auditLog) {
        super("Library");

        this.logic = logic;
        this.login = login;
        this.auditLog = auditLog;

        publisher = new Publisher(logic);
        release = new AssetRelease(logic);
        attribute = new Attribute(logic);
        asset = new Asset(logic);
        scale = new Scale(logic);

        mainPanel = new JPanel();

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
        pubSearch = new JTextField();
        pubSearch.setPreferredSize(new Dimension(120, 20));
        pubSearch.setMaximumSize(pubSearch.getPreferredSize());

        relSearch = new JTextField();
        relSearch.setPreferredSize(new Dimension(120, 20));
        relSearch.setMaximumSize(relSearch.getPreferredSize());

        scaleSearch = new JTextField();
        scaleSearch.setPreferredSize(new Dimension(120, 20));
        scaleSearch.setMaximumSize(scaleSearch.getPreferredSize());

        attrSearch = new JTextField();
        attrSearch.setPreferredSize(new Dimension(120, 20));
        attrSearch.setMaximumSize(attrSearch.getPreferredSize());

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

        ArrayList<String> releases = getChecked(relSearchMap);

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
        searchField = new JTextField(20);
        searchField.setToolTipText("Search for a model");

        searchField.setPreferredSize(new Dimension(110, 20));
        searchField.setMaximumSize(searchField.getPreferredSize());

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                createResults(displayAreaPanel, searchField.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                createResults(displayAreaPanel, searchField.getText());
            }

            public void insertUpdate(DocumentEvent e) {
                createResults(displayAreaPanel, searchField.getText());
            }
        });

        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // add search bar components to search bar
        searchBar.add(searchLabel);
        searchBar.add(searchField);
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
        ArrayList<String> publishers = getChecked(pubSearchMap);

        ArrayList<Release> releases;

        if (isSearch) {
            releases = release.getReleaseFromNameAndPub(relSearch.getText(),
                    publishers.toArray(new String[publishers.size()]));
        } else {
            releases = release.getReleaseFromPubs(publishers.toArray(new String[publishers.size()]));
        }

        JPanel releasePanel = new JPanel();
        releasePanel.setLayout(new BoxLayout(releasePanel, BoxLayout.Y_AXIS));

        for (Release release : releases) {
            ReleaseCheckBox releaseBox = new ReleaseCheckBox(release.getName(), release.getId());

            if (!relSearchMap.containsKey(release.getId() + "")) {
                relSearchMap.put(release.getId() + "", false);
            }

            releaseBox.addActionListener(e -> {
                relSearchMap.put(release.getId() + "", releaseBox.isSelected());
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
        ArrayList<String> releases = getChecked(relSearchMap);
        ArrayList<String> publisherScales = getChecked(pubSearchMap);

        int[] releaseIDs = new int[releases.size()];

        for (int i = 0; i < releases.size(); i++) {
            releaseIDs[i] = Integer.parseInt(releases.get(i));
        }

        JPanel scalePanel = new JPanel();
        scalePanel.setLayout(new BoxLayout(scalePanel, BoxLayout.Y_AXIS));

        if (isSearch) {
            searchScales = scale.getScales(scaleSearch.getText());
        }
        if (releaseIDs.length > 0) {
            assetScales = asset.getScalesFromRelease(releaseIDs);
            publisherScales = asset.getScalesFromPub(publisherScales.toArray(new String[publisherScales.size()]));
        } else {
            assetScales = scale.getAllScales();
        }

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
        int scrollPos = publisherScroll.getVerticalScrollBar().getValue();

        makePublisherPane(isSearch);

        pubPanel.remove(1);
        pubPanel.add(publisherScroll, 1);
        publisherScroll.getVerticalScrollBar().setValue(scrollPos);
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
        int scrollPos = releaseScroll.getVerticalScrollBar().getValue();

        makeReleasePane(isSearch);

        relPanel.remove(1);
        relPanel.add(releaseScroll, 1);
        releaseScroll.getVerticalScrollBar().setValue(scrollPos);
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
        int scrollPos = scaleScroll.getVerticalScrollBar().getValue();

        makeScalePane(isSearch);
        scalePanel.remove(1);
        scalePanel.add(scaleScroll, 1);
        scaleScroll.getVerticalScrollBar().setValue(scrollPos);
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
        int scrollPos = attributeScroll.getVerticalScrollBar().getValue();

        makeAttributePane(isSearch);
        attrPanel.remove(1);
        attrPanel.add(attributeScroll, 1);
        attributeScroll.getVerticalScrollBar().setValue(scrollPos);
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

        createResults(displayAreaPanel, searchField.getText());

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
     * @param search           the name of the search
     */
    private void createResults(JPanel displayAreaPanel, String search) {
        ArrayList<String> publishers = getChecked(pubSearchMap);
        ArrayList<String> releaseIDs = getChecked(relSearchMap);
        ArrayList<String> scales = getChecked(scaleSearchMap);
        ArrayList<String> attributes = getChecked(attrSearchMap);

        ArrayList<Entity> results;

        String[] pubArr = publishers.toArray(new String[publishers.size()]);
        String[] scaleArr = scales.toArray(new String[scales.size()]);
        String[] attrArr = attributes.toArray(new String[attributes.size()]);

        int[] releaseIDsInt = new int[releaseIDs.size()];

        for (int i = 0; i < releaseIDs.size(); i++) {
            releaseIDsInt[i] = Integer.parseInt(releaseIDs.get(i));
        }

        if (search == null || search.equals("")) {
            results = asset.getAssets(pubArr, releaseIDsInt, scaleArr, attrArr);
        } else {
            results = asset.getAssets(pubArr, releaseIDsInt, scaleArr, attrArr, search);
        }

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
            if (name.length() > 15) {
                name = name.substring(0, 15) + "...";
            }

            JLabel assetName = new JLabel(name);
            JLabel assetScale = new JLabel(entity.getScale());
            JLabel assetPath = new JLabel(path);

            assetName.setAlignmentX(Component.CENTER_ALIGNMENT);
            assetScale.setAlignmentX(Component.CENTER_ALIGNMENT);
            assetPath.setAlignmentX(Component.CENTER_ALIGNMENT);

            assetPanel.add(assetName);
            assetPanel.add(assetPath);
            assetPanel.add(assetScale);

            // if clicked twice, open asset
            assetPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // if single clicked, select asset, and highlight it
                    if (e.getClickCount() == 1) {
                        if (selectedPanel != null) {
                            selectedPanel.setBackground(Color.WHITE);
                        }
                        selectedAsset = entity;
                        selectedPanel = assetPanel;
                        assetPanel.setBackground(Color.YELLOW);

                        // if right clicked, give option to edit or delete asset
                        if (SwingUtilities.isRightMouseButton(e)) {
                            JPopupMenu popup = new JPopupMenu();
                            JMenuItem edit = new JMenuItem("Edit");
                            JMenuItem delete = new JMenuItem("Delete");

                            popup.add(edit);
                            popup.add(delete);
                            popup.show(e.getComponent(), e.getX(), e.getY());

                            edit.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    // check if user already has edit asset open
                                    if (editFrame != null) {
                                        editFrame.dispose();
                                    }
                                    editAsset();
                                    createResults(displayAreaPanel, searchField.getText());
                                }
                            });
                            delete.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    boolean success = asset.removeAsset(selectedAsset.getFilePath());
                                    if (success) {
                                        auditLog.log("Deleted asset " + selectedAsset.getName(), login.getCurrUser());
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Asset could not be deleted");
                                    }
                                    createResults(displayAreaPanel, searchField.getText());
                                }
                            });
                        }

                        // if double clicked, open asset
                    } else if (e.getClickCount() == 2 && !SwingUtilities.isRightMouseButton(e)) {
                        // open asset
                        try {
                            Desktop.getDesktop().open(new File(entity.getFilePath()));
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(null, "Asset could not be opened");
                        }
                    }
                }
            });

            displayAreaPanel.add(assetPanel);
        }

        displayAreaPanel.revalidate();
        displayAreaPanel.repaint();
    }

    /**
     * Edits the selected asset
     */
    private void editAsset() {
        // add fields to panel

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));

        JPanel scalePanel = new JPanel();
        scalePanel.setLayout(new BoxLayout(scalePanel, BoxLayout.X_AXIS));

        JPanel pathPanel = new JPanel();
        pathPanel.setLayout(new BoxLayout(pathPanel, BoxLayout.X_AXIS));

        JPanel publisherPanel = new JPanel();
        publisherPanel.setLayout(new BoxLayout(publisherPanel, BoxLayout.X_AXIS));

        JPanel releasePanel = new JPanel();
        releasePanel.setLayout(new BoxLayout(releasePanel, BoxLayout.X_AXIS));

        JPanel attributePanel = new JPanel();
        attributePanel.setLayout(new BoxLayout(attributePanel, BoxLayout.X_AXIS));

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.X_AXIS));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(selectedAsset.getName());

        JLabel scaleLabel = new JLabel("Scale:");
        JComboBox scaleBox = new JComboBox(scale.getAllScales().toArray());
        scaleBox.setSelectedItem(selectedAsset.getScale());

        JLabel pathLabel = new JLabel("Path:");
        JTextField pathField = new JTextField(selectedAsset.getFilePath());

        // release
        JLabel releaseLabel = new JLabel("Release:");
        ArrayList<Release> possibleReleases = release.getReleaseFromPub(selectedAsset.getPublisher());
        ArrayList<String> releaseNames = new ArrayList<String>();
        for (Release release : possibleReleases) {
            releaseNames.add(release.getName());
        }
        JComboBox releaseBox = new JComboBox(releaseNames.toArray());

        // publisher
        JLabel publisherLabel = new JLabel("Publisher:");
        JComboBox publisherBox = new JComboBox(publisher.getAllPublishers().toArray());
        publisherBox.setSelectedItem(selectedAsset.getPublisher());

        // on publisher update, update release
        publisherBox.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            String publisher = (String) cb.getSelectedItem();
            ArrayList<Release> releases = release.getReleaseFromPub(publisher);
            releaseNames.clear();
            for (Release release : releases) {
                releaseNames.add(release.getName());
            }
            releaseBox.setModel(new DefaultComboBoxModel(releaseNames.toArray()));

            // if the release box has the old release, select it
            if (releaseNames.contains(selectedAsset.getRelease())) {
                releaseBox.setSelectedItem(selectedAsset.getRelease());
            }
        });

        // attributes
        JLabel attributesLabel = new JLabel("Attributes:");
        ArrayList<String> oldAttributes = selectedAsset.getAttributes();
        ArrayList<String> allAttributes = attribute.getAllAttributes();

        JCheckBox[] attributeBoxes = new JCheckBox[allAttributes.size()];

        for (int i = 0; i < allAttributes.size(); i++) {
            attributeBoxes[i] = new JCheckBox(allAttributes.get(i));
            if (oldAttributes.contains(allAttributes.get(i))) {
                attributeBoxes[i].setSelected(true);
            }
        }

        ComboCheckBox attributeBox = new ComboCheckBox(attributeBoxes);

        // description
        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField(selectedAsset.getDescription());

        // create panel to hold all the fields
        editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
        editPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // set the size of the fields
        nameField.setPreferredSize(new Dimension(EDIT_WIDTH, EDIT_HEIGHT));
        scaleBox.setPreferredSize(new Dimension(EDIT_WIDTH, EDIT_HEIGHT));
        pathField.setPreferredSize(new Dimension(EDIT_WIDTH, EDIT_HEIGHT));
        publisherBox.setPreferredSize(new Dimension(EDIT_WIDTH, EDIT_HEIGHT));
        releaseBox.setPreferredSize(new Dimension(EDIT_WIDTH, EDIT_HEIGHT));
        attributeBox.setPreferredSize(new Dimension(EDIT_WIDTH, EDIT_HEIGHT));
        descriptionField.setPreferredSize(new Dimension(EDIT_WIDTH, EDIT_HEIGHT));

        nameField.setMaximumSize(nameField.getPreferredSize());
        scaleBox.setMaximumSize(scaleBox.getPreferredSize());
        pathField.setMaximumSize(pathField.getPreferredSize());
        publisherBox.setMaximumSize(publisherBox.getPreferredSize());
        releaseBox.setMaximumSize(releaseBox.getPreferredSize());
        attributeBox.setMaximumSize(attributeBox.getPreferredSize());
        descriptionField.setMaximumSize(descriptionField.getPreferredSize());

        // add fields to panel

        namePanel.add(nameLabel);
        namePanel.add(nameField);
        scalePanel.add(scaleLabel);
        scalePanel.add(scaleBox);
        pathPanel.add(pathLabel);
        pathPanel.add(pathField);
        publisherPanel.add(publisherLabel);
        publisherPanel.add(publisherBox);
        releasePanel.add(releaseLabel);
        releasePanel.add(releaseBox);
        attributePanel.add(attributesLabel);
        attributePanel.add(attributeBox);
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(descriptionField);

        editPanel.add(namePanel);
        editPanel.add(scalePanel);
        editPanel.add(pathPanel);
        editPanel.add(publisherPanel);
        editPanel.add(releasePanel);
        editPanel.add(attributePanel);
        editPanel.add(descriptionPanel);

        // add button to save changes

        JButton saveButton = new JButton("Save");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // when save button is clicked, save changes

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // get the new values
                String path = pathField.getText();
                String name = nameField.getText();
                String scale = scaleBox.getSelectedItem().toString();
                String publisher = publisherBox.getSelectedItem().toString();
                String releaseName = releaseBox.getSelectedItem().toString();
                String description = descriptionField.getText();

                String[] publishers = { publisher };

                ArrayList<Release> releases = release.getReleaseFromNameAndPub(releaseName, publishers);

                int rid = releases.get(0).getId();

                // get the new attributes
                ArrayList<String> newAttributes = new ArrayList<String>();
                for (int i = 0; i < attributeBoxes.length; i++) {
                    if (attributeBoxes[i].isSelected()) {
                        newAttributes.add(attributeBoxes[i].getText());
                    }
                }

                // check at least one attribute is selected
                if (newAttributes.size() == 0) {
                    JOptionPane.showMessageDialog(null, "Please select at least one attribute");
                    return;
                }

                // check if path is valid in computer
                if (!(new File(path).exists())) {
                    JOptionPane.showMessageDialog(null, "Path is invalid");
                    return;
                }

                // update the asset
                boolean success = asset.editAsset(path, oldAttributes.toArray(new String[oldAttributes.size()]),
                        newAttributes.toArray(new String[newAttributes.size()]), login.getCurrUser(), name, rid,
                        scale,
                        description);

                // if successful, update the display
                if (success) {
                    auditLog.log("Edited Asset: " + selectedAsset.getName(), login.getCurrUser());
                    editFrame.dispose();
                    createResults(displayAreaPanel, searchField.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Error editing asset");
                }
            }
        });

        // Create a popout window to edit the asset
        editFrame = new JFrame("Edit Asset");
        editFrame.setSize(500, 450);
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editFrame.setLocationRelativeTo(null);
        editFrame.setLayout(new BoxLayout(editFrame.getContentPane(), BoxLayout.Y_AXIS));
        editFrame.setResizable(false);

        editPanel.add(saveButton);
        editFrame.add(editPanel);

        editFrame.setVisible(true);
    }

    /**
     * Checks all the checkboxes in a scroll pane and returns the text of the ones
     * that are checked.
     * 
     * @param map the map of checkboxes to
     * @return Returns an ArrayList of the text of the checked checkboxes
     */
    public ArrayList<String> getChecked(HashMap<String, Boolean> map) {
        ArrayList<String> checked = new ArrayList<String>();

        for (String key : map.keySet()) {
            if (map.get(key)) {
                checked.add(key);
            }
        }

        return checked;
    }

    @Override
    protected void addMenuListeners() {
        logout.addActionListener(e -> {
            auditLog.log("Logged out", login.getCurrUser());
            this.dispose();
            new LoginView(this.logic, this.auditLog);
        });

        analytics.addActionListener(e -> {
            if (login.isAdmin(login.getCurrUser())) {
                this.dispose();
                new AnalyticsView(this.logic, this.login, this.auditLog);
            } else {
                JOptionPane.showMessageDialog(null, "You do not have permission to view this page");
                auditLog.log("Attempted to view Analytics", login.getCurrUser());
            }
        });

        addItem.addActionListener(e -> {
            this.dispose();
            new AddView(this.logic, this.login, this.auditLog);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof JCheckBox) {
            JCheckBox box = (JCheckBox) e.getSource();
            ArrayList<String> releases = getChecked(relSearchMap);

            int[] releaseIds = new int[releases.size()];

            for (int i = 0; i < releases.size(); i++) {
                releaseIds[i] = Integer.parseInt(releases.get(i));
            }

            if (box.getParent() == publisherScroll.getViewport().getView()) { // publisher scroll
                updateReleaseScroll(relSearch.getText());
                updateScaleScroll(scaleSearch.getText());
            } else if (box.getParent() == releaseScroll.getViewport().getView()) { // release scroll
                updateScaleScroll(scaleSearch.getText());
            } else if (box.getParent() == scaleScroll.getViewport().getView()) { // scale scroll
                updateScaleScroll(scaleSearch.getText());
            } else if (box.getParent() == attributeScroll.getViewport().getView()) { // attribute scroll
                updateScaleScroll(scaleSearch.getText());
            }
        }
        createResults(displayAreaPanel, searchField.getText());
    }
}

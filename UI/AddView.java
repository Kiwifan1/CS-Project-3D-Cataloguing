/**
 * Name: Joshua Venable
 * Class: CPSC 321, Spring 2022
 * Date: 
 * Programming Assigment:
 * Description: 
 * Notes: 
 * TODO: fix naming of file when putting into database
 * 
 **/

package UI;

import Logic.*;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.event.*;

public class AddView extends BoilerPlateView implements ActionListener {

    ConnectLogic logic;
    Login login;
    Publisher publisher;
    Release release;
    Attribute attribute;
    Asset asset;
    Scale scale;

    JPanel homePanel;
    JPanel cataloguePanel;
    JPanel dragPanel;
    JPanel pubPanel;
    JPanel relPanel;
    JPanel scalePanel;
    JPanel attPanel;

    JTextPane dragArea;

    JTextField nameField;
    JTextField descriptionField;
    JTextField pubSearch;
    JTextField relSearch;
    JTextField scaleSearch;
    JTextField attSearch;

    JButton catalogueBtn;
    JButton addPubBtn;
    JButton addRelBtn;
    JButton addScaleBtn;
    JButton addAttBtn;

    JScrollPane attributeScroll;
    JScrollPane publisherScroll;
    JScrollPane releaseScroll;
    JScrollPane scaleScroll;

    JList<String> publisherList;
    JList<String> scaleList;
    ReleaseList releaseList;

    ArrayList<String> filePathList;

    public AddView(ConnectLogic logic, Login login) {
        super("Home");

        // SQL logic
        this.logic = logic;
        this.login = login;
        publisher = new Publisher(logic);
        release = new Release(logic);
        attribute = new Attribute(logic);
        asset = new Asset(logic);
        scale = new Scale(logic);
        filePathList = new ArrayList<String>();

        // populate the frame
        makeHomePanel();

        addMenuListeners();

        this.setVisible(true);
    }

    /**
     * Makes the interactable components for the home panel.
     */
    private void makeInteractables() {

        makePublisherScroll(false);
        makeReleaseScroll(false);
        makeScaleScroll(false);
        makeAttributeScroll(false);
        makeButtons();

    }

    /**
     * Makes the buttons for the home panel.
     */
    private void makeButtons() {
        // catalogue button
        catalogueBtn = new JButton("Catalogue Asset");
        catalogueBtn.addActionListener(this);

        // add publisher button
        addPubBtn = new JButton("Add Publisher");
        addPubBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter the name of the publisher");
            String source = JOptionPane.showInputDialog("Enter the source of the publisher");
            if (name != null && source != null) {
                publisher.addPublisher(name, source);
                updatePublisherScroll(null);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a name and source for the publisher");
            }
        });

        // add release button
        addRelBtn = new JButton("Add Release");
        addRelBtn.addActionListener(e ->

        {
            String publisher = JOptionPane.showInputDialog("Enter the name of the publisher");
            String name = JOptionPane.showInputDialog("Enter the name of the release");
            String description = JOptionPane.showInputDialog("Enter the description of the release");
            if (name != null && publisher != null) {
                int lastRID = release.getLastRID();
                if (lastRID == -1) {
                    JOptionPane.showMessageDialog(null, "Error getting last RID");
                } else {
                    release.addRelease(lastRID + 1, name, publisher, description);
                    updateReleaseScroll(null);
                }
            }
        });

        // add scale button
        addScaleBtn = new JButton("Add Scale");
        addScaleBtn.addActionListener(e ->

        {
            String name = JOptionPane.showInputDialog("Enter the name of the scale");
            if (name != null) {
                scaleScroll.add(new JLabel(name));
                updateScaleScroll(null);
            }
        });

        // add attribute button
        addAttBtn = new JButton("Add Attribute");
        addAttBtn.addActionListener(e ->

        {
            String name = JOptionPane.showInputDialog("Enter the name of the attribute");
            String description = JOptionPane.showInputDialog("Enter the description of the attribute");
            if (name != null) {
                attribute.addAttribute(name, description);
                updateAttributeScroll(null);
            }
        });

    }

    /**
     * Creates the publication scroll pane.
     * 
     * @param isSearch if the scroll pane is being created from a search
     */
    private void makePublisherScroll(boolean isSearch) {
        // publisher scroll pane
        String[] pubArray;

        if (isSearch) {
            ArrayList<String> pubList = publisher.getPublishers(pubSearch.getText());
            pubArray = pubList.toArray(new String[pubList.size()]);

        } else {
            ArrayList<String> pubList = publisher.getAllPublishers();
            pubArray = pubList.toArray(new String[pubList.size()]);
        }

        publisherList = new JList<String>(pubArray);

        JPanel publisherBox = new JPanel();
        publisherBox.setLayout(new BoxLayout(publisherBox, BoxLayout.Y_AXIS));

        publisherList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        publisherList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateReleaseScroll(null);
            }
        });

        publisherBox.add(publisherList);

        publisherScroll = new JScrollPane(publisherBox);
        publisherScroll.setBorder(BorderFactory.createTitledBorder("Publisher"));
        publisherScroll.setPreferredSize(new Dimension(180, 150));
        publisherScroll.setSize(getPreferredSize());
        publisherScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        publisherScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    /**
     * Creates the release scroll pane.
     * 
     * @param isSearch if the scroll pane is being created from a search
     */
    private void makeReleaseScroll(boolean isSearch) {
        // release scroll pane

        ArrayList<String> publisher = new ArrayList<String>();
        ArrayList<String[]> releases = new ArrayList<String[]>();

        if (publisherList.getSelectedValue() != null) {
            publisher.add(publisherList.getSelectedValue().toString());
        }

        String[] pubArray = publisher.toArray(new String[publisher.size()]);

        // if the scroll pane is being created from a search
        if (isSearch) {
            releases = release.getReleaseFromNameAndPub(relSearch.getText(), pubArray);
        } else {
            releases = release.getReleaseFromPub(pubArray);
        }
        // convert the releases to a string and int array

        String[] releaseNames = new String[releases.size()];
        int[] releaseIDs = new int[releases.size()];

        for (int i = 0; i < releases.size(); i++) {
            releaseNames[i] = releases.get(i)[0];
        }

        for (int i = 0; i < releases.size(); i++) {
            releaseIDs[i] = Integer.parseInt(releases.get(i)[1]);
        }

        JPanel releaseBox = new JPanel();
        releaseBox.setLayout(new BoxLayout(releaseBox, BoxLayout.Y_AXIS));

        releaseList = new ReleaseList(releaseNames, releaseIDs);
        releaseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        releaseBox.add(releaseList);

        releaseScroll = new JScrollPane(releaseBox);
        releaseScroll.setBorder(BorderFactory.createTitledBorder("Release"));
        releaseScroll.setPreferredSize(new Dimension(200, 150));
        releaseScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        releaseScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    /**
     * Creates the scale scroll pane.
     * 
     * @param isSearch if the scroll pane is being created from a search
     */
    private void makeScaleScroll(boolean isSearch) {
        // scale scroll pane

        ArrayList<String> scales = new ArrayList<String>();

        if (isSearch) {
            scales = scale.getScales(scaleSearch.getText());
        } else {
            scales = scale.getAllScales();
        }

        JPanel scaleBox = new JPanel();
        scaleBox.setLayout(new BoxLayout(scaleBox, BoxLayout.Y_AXIS));

        scaleList = new JList(scales.toArray());
        scaleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scaleBox.add(scaleList);

        scaleScroll = new JScrollPane(scaleBox);
        scaleScroll.setBorder(BorderFactory.createTitledBorder("Scale"));
        scaleScroll.setPreferredSize(new Dimension(200, 150));
        scaleScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scaleScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    }

    /**
     * Creates the attribute scroll pane.
     * 
     * @param isSearch if the scroll pane is being created from a search
     */
    private void makeAttributeScroll(boolean isSearch) {
        // attribute scroll pane

        ArrayList<String> attributes = new ArrayList<String>();

        if (isSearch) {
            attributes = attribute.getAttributes(attSearch.getText());
        } else {
            attributes = attribute.getAllAttributes();
        }

        JPanel attributePanel = new JPanel();
        attributePanel.setLayout(new BoxLayout(attributePanel, BoxLayout.Y_AXIS));

        for (String att : attributes) {
            JCheckBox attBox = new JCheckBox(att);
            attributePanel.add(attBox);
        }

        attributeScroll = new JScrollPane(attributePanel);
        attributeScroll.setBorder(BorderFactory.createTitledBorder("Attributes"));
        attributeScroll.setPreferredSize(new Dimension(200, 150));
        attributeScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        attributeScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    /**
     * Updates the publisher scroll
     */
    private void updatePublisherScroll(String name) {
        boolean isSearch = (name == null || name.equals("") ? false : true);
        makePublisherScroll(isSearch);
        pubPanel.remove(1);
        pubPanel.add(publisherScroll, 1);
        pubPanel.revalidate();
        pubPanel.repaint();
    }

    /**
     * Updates the release scroll pane.
     */
    private void updateReleaseScroll(String name) {
        boolean isSearch = (name == null || name.equals("") ? false : true);
        makeReleaseScroll(isSearch);
        relPanel.remove(1);
        relPanel.add(releaseScroll, 1);
        relPanel.revalidate();
        relPanel.repaint();
    }

    /**
     * Updates the scale scroll pane.
     */
    private void updateScaleScroll(String scale) {
        boolean isSearch = (scale == null || scale.equals("") ? false : true);
        makeScaleScroll(isSearch);
        scalePanel.remove(1);
        scalePanel.add(scaleScroll, 1);
        scalePanel.revalidate();
        scalePanel.repaint();
    }

    /**
     * Updates the attribute scroll pane.
     */
    private void updateAttributeScroll(String att) {
        boolean isSearch = (att == null || att.equals("") ? false : true);
        makeAttributeScroll(isSearch);
        attPanel.remove(1);
        attPanel.add(attributeScroll, 1);
        attPanel.revalidate();
        attPanel.repaint();
    }

    /**
     * Creates the home panel.
     */
    private void makeHomePanel() {
        homePanel = new JPanel(new BorderLayout());

        makeInteractables();
        makeDragPanel();
        makeCataloguePanel();

        homePanel.add(dragPanel, BorderLayout.CENTER);
        homePanel.add(cataloguePanel, BorderLayout.SOUTH);

        this.add(homePanel, BorderLayout.CENTER);
    }

    /**
     * Creates search boxes for the catalogue panel.
     */
    private void makeSearchBoxes() {
        pubSearch = new JTextField();
        pubSearch.setPreferredSize(new Dimension(180, 20));
        pubSearch.setMaximumSize(pubSearch.getPreferredSize());

        relSearch = new JTextField();
        relSearch.setPreferredSize(new Dimension(180, 20));
        relSearch.setMaximumSize(relSearch.getPreferredSize());

        scaleSearch = new JTextField();
        scaleSearch.setPreferredSize(new Dimension(180, 20));
        scaleSearch.setMaximumSize(scaleSearch.getPreferredSize());

        attSearch = new JTextField();
        attSearch.setPreferredSize(new Dimension(180, 20));
        attSearch.setMaximumSize(attSearch.getPreferredSize());

        // update the publisher scroll pane as the user types

        pubSearch.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePublisherScroll(pubSearch.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePublisherScroll(pubSearch.getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePublisherScroll(pubSearch.getText());
            }
        });

        // update the release scroll pane as the user types

        relSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateReleaseScroll(relSearch.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateReleaseScroll(relSearch.getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateReleaseScroll(relSearch.getText());
            }
        });

        // update the scale scroll pane as the user types

        scaleSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateScaleScroll(scaleSearch.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateScaleScroll(scaleSearch.getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateScaleScroll(scaleSearch.getText());
            }
        });

        // update the attribute scroll pane as the user types

        attSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateAttributeScroll(attSearch.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateAttributeScroll(attSearch.getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateAttributeScroll(attSearch.getText());
            }
        });
    }

    /**
     * Creates the panel that contains the catalogue button and the scroll panes.
     */
    private void makeCataloguePanel() {

        makeSearchBoxes();

        cataloguePanel = new JPanel();
        cataloguePanel.setLayout(new BoxLayout(cataloguePanel, BoxLayout.X_AXIS));

        pubPanel = new JPanel();
        pubPanel.setLayout(new BoxLayout(pubPanel, BoxLayout.Y_AXIS));

        pubPanel.add(pubSearch);
        pubPanel.add(publisherScroll);
        pubPanel.add(addPubBtn);

        relPanel = new JPanel();
        relPanel.setLayout(new BoxLayout(relPanel, BoxLayout.Y_AXIS));

        relPanel.add(relSearch);
        relPanel.add(releaseScroll);
        relPanel.add(addRelBtn);

        scalePanel = new JPanel();
        scalePanel.setLayout(new BoxLayout(scalePanel, BoxLayout.Y_AXIS));

        scalePanel.add(scaleSearch);
        scalePanel.add(scaleScroll);
        scalePanel.add(addScaleBtn);

        attPanel = new JPanel();
        attPanel.setLayout(new BoxLayout(attPanel, BoxLayout.Y_AXIS));

        attPanel.add(attSearch);
        attPanel.add(attributeScroll);
        attPanel.add(addAttBtn);

        cataloguePanel.add(pubPanel);
        cataloguePanel.add(relPanel);
        cataloguePanel.add(scalePanel);
        cataloguePanel.add(attPanel);
    }

    /**
     * Creates the panel that contains the drag area
     */
    private void makeDragPanel() {

        dragPanel = new JPanel();
        dragPanel.setLayout(new BoxLayout(dragPanel, BoxLayout.Y_AXIS));
        dragPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel cataloguPanel = new JPanel();
        cataloguPanel.setLayout(new BoxLayout(cataloguPanel, BoxLayout.X_AXIS));

        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setSize(50, 20);

        JLabel descriptionLabel = new JLabel("Description: ");
        descriptionLabel.setSize(50, 20);

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(100, 25));
        nameField.setMaximumSize(nameField.getPreferredSize());

        descriptionField = new JTextField();
        descriptionField.setPreferredSize(new Dimension(200, 25));
        descriptionField.setMaximumSize(descriptionField.getPreferredSize());

        makeDragArea();

        cataloguPanel.add(nameLabel);
        cataloguPanel.add(nameField);
        cataloguPanel.add(descriptionLabel);
        cataloguPanel.add(descriptionField);
        cataloguPanel.add(catalogueBtn);

        dragPanel.add(cataloguPanel);

        dragPanel.add(dragArea, BorderLayout.CENTER);
    }

    /**
     * Creates a JTextArea that can be dragged and dropped files onto.
     * 
     * @see https://stackoverflow.com/questions/811248/how-can-i-use-drag-and-drop-in-swing-to-get-file-path
     *      for how I got this to work
     */
    private void makeDragArea() {
        dragArea = new JTextPane();
        dragArea.setText("Drag and drop files here");
        dragArea.setEditable(false);

        // formatting
        dragArea.setFont(new Font("Serif", Font.BOLD, 30));
        dragArea.setEditable(false);
        dragArea.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        dragArea.setBackground(Color.LIGHT_GRAY);

        // center text
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        dragArea.getStyledDocument().setParagraphAttributes(0, dragArea.getText().length(), center, false);

        // drag and drop

        dragArea.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    if (droppedFiles.size() > 1) {
                        JOptionPane.showMessageDialog(null,
                                "Only one file can be dropped at a time, first file dropped will be added");
                    }
                    File file = droppedFiles.get(0);
                    filePathList.add(file.getAbsolutePath());

                    nameField.setText(file.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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

        library.addActionListener(e -> {
            this.dispose();
            new LibraryView(this.logic, this.login);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // if the catalogue button is pressed
        if (e.getSource() == catalogueBtn) {
            if (publisherList.getSelectedValue() != null && releaseList.getSelectedValue() != null
                    && scaleList.getSelectedValue() != null) {
                String publisher = publisherList.getSelectedValue();
                int releaseID = releaseList.getSelectedReleaseID();
                String scale = scaleList.getSelectedValue();
                String[] attributes = getChecked(attributeScroll);
                String asset = dragArea.getText();

                // adds the asset to the database and checks if it was successful
                if (asset.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please drag an asset into the box.");
                } else if (filePathList.size() == 0) {
                    JOptionPane.showMessageDialog(null, "Please drag an asset into the box.");
                } else {
                    String filePath = filePathList.get(0);
                    String name = nameField.getText();

                    if (name == null || name == "") {
                        name = filePath.split("/")[filePath.split("/").length - 1];
                    }

                    boolean success = this.asset.addAsset(filePath, attributes, login.getCurrUser(), name, releaseID,
                            scale, descriptionField.getText());
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Asset added successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Asset failed to add");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a publisher, release and scale.");
        }
    }
}

/**
 * Name: Joshua Venable
 * Class: CPSC 321, Spring 2022
 * Date: 
 * Programming Assigment:
 * Description: 
 * Notes: 
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
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.event.*;

public class AddView extends BoilerPlateView implements ActionListener {

    private ConnectLogic logic;
    private Login login;
    private AuditLog auditLog;
    private Publisher publisher;
    private AssetRelease release;
    private Attribute attribute;
    private Asset asset;
    private Scale scale;

    private JPanel homePanel;
    private JPanel cataloguePanel;
    private JPanel dragPanel;
    private JPanel pubPanel;
    private JPanel relPanel;
    private JPanel scalePanel;
    private JPanel attPanel;

    private JTextPane dragArea;

    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField pubSearch;
    private JTextField relSearch;
    private JTextField scaleSearch;
    private JTextField attSearch;

    private JButton catalogueBtn;
    private JButton addPubBtn;
    private JButton remPubBtn;
    private JButton addRelBtn;
    private JButton remRelBtn;
    private JButton addScaleBtn;
    private JButton remScaleBtn;
    private JButton addAttBtn;
    private JButton remAttBtn;

    private JScrollPane attributeScroll;
    private JScrollPane publisherScroll;
    private JScrollPane releaseScroll;
    private JScrollPane scaleScroll;

    private JList<String> publisherList;
    private JList<String> scaleList;
    private ReleaseList releaseList;

    private List<File> droppedFiles;
    private HashMap<String, Boolean> checkedAttributes;

    public AddView(ConnectLogic logic, Login login, AuditLog auditLog) {
        super("Home");

        // SQL logic
        this.logic = logic;
        this.login = login;
        this.auditLog = auditLog;

        publisher = new Publisher(logic);
        release = new AssetRelease(logic);
        attribute = new Attribute(logic);
        asset = new Asset(logic);
        scale = new Scale(logic);

        // populate the frame
        makeHomePanel();

        addMenuListeners();

        this.setVisible(true);
    }

    /**
     * Makes the interactable components for the home panel.
     */
    private void makeInteractables() {
        checkedAttributes = new HashMap<String, Boolean>();
        makeButtons();

        makePublisherScroll(false);
        makeReleaseScroll(false);
        makeScaleScroll(false);
        makeAttributeScroll(false);

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
                if (publisher.addPublisher(name, source)) {
                    auditLog.log("Publisher Added " + name, login.getCurrUser());
                    updatePublisherScroll(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Publisher already exists");
                    auditLog.log("Publisher Add Failed " + name, login.getCurrUser());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a name and source for the publisher");
            }
        });

        // remove publisher button
        remPubBtn = new JButton("Remove Publisher");
        remPubBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter the name of the publisher");
            if (name != null) {
                if (publisher.removePublisher(name)) {
                    auditLog.log("Publisher Removed " + name, login.getCurrUser());
                    updatePublisherScroll(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Publisher not found");
                    auditLog.log("Publisher Remove Failed " + name, login.getCurrUser());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a name for the publisher");
            }
        });

        // add release button
        addRelBtn = new JButton("Add Release");
        addRelBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter the name of the release");
            String description = JOptionPane.showInputDialog("Enter the description of the release");
            if (name != null) {
                int lastRID = release.getLastRID();
                if (lastRID == -1) {
                    JOptionPane.showMessageDialog(null, "Error getting last RID");
                } else if (release.addRelease(lastRID + 1, name, publisherList.getSelectedValue(), description)) {
                    auditLog.log("Release Added " + (lastRID + 1), login.getCurrUser());
                    updateReleaseScroll(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Release already exists");
                    auditLog.log("Release Add Failed " + (lastRID + 1), login.getCurrUser());
                }
            }
        });

        // remove release button
        remRelBtn = new JButton("Remove Release");
        remRelBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter the name of the release");
            String publisher = JOptionPane.showInputDialog("Enter the name of the publisher");
            String[] publishers = { publisher };
            if (name != null) {
                ArrayList<Release> releases = release.getReleaseFromNameAndPub(name, publishers);
                if (releases.size() > 1) {
                    String[] options = new String[releases.size()];
                    for (int i = 0; i < releases.size(); i++) {
                        String description = releases.get(i).getDescription();

                        if (description == null || description.equals("")) {
                            description = "No description";
                        }

                        options[i] = releases.get(i).getId() + " - " + releases.get(i).getName() + " - " + description;
                    }
                    String releaseName = (String) JOptionPane.showInputDialog(null, "Select a release to remove",
                            "Remove Release", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                    if (release != null && release.removeRelease(Integer.parseInt(releaseName.split(" - ")[0]))) {
                        auditLog.log("Release Removed " + (options[0]), login.getCurrUser());
                        updateReleaseScroll(null);
                    } else {
                        JOptionPane.showMessageDialog(null, "Release not found");
                        auditLog.log("Release Remove Failed " + (options[0]), login.getCurrUser());
                    }
                } else if (releases.size() == 1 && release.removeRelease(releases.get(0).getId())) {
                    auditLog.log("Release Removed " + releases.get(0).getId(), login.getCurrUser());
                    updateReleaseScroll(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Release not found");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a name for the release");
            }
        });

        // add scale button
        addScaleBtn = new JButton("Add Scale");
        addScaleBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter the name of the scale");
            if (name != null) {
                if (scale.addScale(name)) {
                    auditLog.log("Scale Added " + name, login.getCurrUser());
                    updateScaleScroll(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Scale already exists");
                    auditLog.log("Scale Add Failed " + name, login.getCurrUser());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a name for the scale");
            }
        });

        // remove scale button
        remScaleBtn = new JButton("Remove Scale");
        remScaleBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter the name of the scale");
            if (name != null) {
                if (scale.removeScale(name)) {
                    auditLog.log("Scale Removed " + name, login.getCurrUser());
                    updateScaleScroll(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Scale not found");
                    auditLog.log("Scale Remove Failed " + name, login.getCurrUser());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a name for the scale");
            }
        });

        // add attribute button
        addAttBtn = new JButton("Add Attribute");
        addAttBtn.addActionListener(e ->

        {
            String name = JOptionPane.showInputDialog("Enter the name of the attribute");
            String description = JOptionPane.showInputDialog("Enter the description of the attribute");
            if (name != null) {
                if (attribute.addAttribute(name, description)) {
                    auditLog.log("Attribute Added " + name, login.getCurrUser());
                    updateAttributeScroll(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Attribute already exists");
                    auditLog.log("Attribute Add Failed " + name, login.getCurrUser());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a name for the attribute");
            }
        });

        // remove attribute button
        remAttBtn = new JButton("Remove Attribute");
        remAttBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter the name of the attribute");
            if (name != null) {
                if (attribute.removeAttribute(name)) {
                    auditLog.log("Attribute Removed " + name, login.getCurrUser());
                    updateAttributeScroll(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Attribute not found");
                    auditLog.log("Attribute Remove Failed " + name, login.getCurrUser());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a name for the attribute");
            }
        });

        addPubBtn.setPreferredSize(new Dimension(85, 30));
        addPubBtn.setMaximumSize(addPubBtn.getPreferredSize());
        remPubBtn.setPreferredSize(new Dimension(85, 30));
        remPubBtn.setMaximumSize(remPubBtn.getPreferredSize());

        addRelBtn.setPreferredSize(new Dimension(85, 30));
        addRelBtn.setMaximumSize(addRelBtn.getPreferredSize());
        remRelBtn.setPreferredSize(new Dimension(85, 30));
        remRelBtn.setMaximumSize(remRelBtn.getPreferredSize());

        addScaleBtn.setPreferredSize(new Dimension(85, 30));
        addScaleBtn.setMaximumSize(addScaleBtn.getPreferredSize());
        remScaleBtn.setPreferredSize(new Dimension(85, 30));
        remScaleBtn.setMaximumSize(remScaleBtn.getPreferredSize());

        addAttBtn.setPreferredSize(new Dimension(85, 30));
        addAttBtn.setMaximumSize(addAttBtn.getPreferredSize());
        remAttBtn.setPreferredSize(new Dimension(85, 30));
        remAttBtn.setMaximumSize(remAttBtn.getPreferredSize());

        addPubBtn.setFont(new Font("Arial", Font.BOLD, 10));
        remPubBtn.setFont(new Font("Arial", Font.BOLD, 10));
        addRelBtn.setFont(new Font("Arial", Font.BOLD, 10));
        remRelBtn.setFont(new Font("Arial", Font.BOLD, 10));
        addScaleBtn.setFont(new Font("Arial", Font.BOLD, 10));
        remScaleBtn.setFont(new Font("Arial", Font.BOLD, 10));
        addAttBtn.setFont(new Font("Arial", Font.BOLD, 10));
        remAttBtn.setFont(new Font("Arial", Font.BOLD, 10));
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
        publisherScroll.setPreferredSize(new Dimension(170, 150));
        publisherScroll.setMaximumSize(publisherScroll.getPreferredSize());
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
        ArrayList<Release> releases = new ArrayList<Release>();

        if (publisherList.getSelectedValue() != null) {
            publisher.add(publisherList.getSelectedValue().toString());
        }

        if (publisher.size() == 0) {
            addRelBtn.setEnabled(false);
        } else {
            addRelBtn.setEnabled(true);
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
            releaseNames[i] = releases.get(i).getName();
        }

        for (int i = 0; i < releases.size(); i++) {
            releaseIDs[i] = releases.get(i).getId();
        }

        JPanel releaseBox = new JPanel();
        releaseBox.setLayout(new BoxLayout(releaseBox, BoxLayout.Y_AXIS));

        releaseList = new ReleaseList(releaseNames, releaseIDs);
        releaseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        releaseBox.add(releaseList);

        releaseScroll = new JScrollPane(releaseBox);
        releaseScroll.setBorder(BorderFactory.createTitledBorder("Release"));
        releaseScroll.setPreferredSize(new Dimension(170, 150));
        releaseScroll.setMaximumSize(releaseScroll.getPreferredSize());
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
        scaleScroll.setPreferredSize(new Dimension(170, 150));
        scaleScroll.setMaximumSize(scaleScroll.getPreferredSize());
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
            if (!checkedAttributes.containsKey(att)) {
                checkedAttributes.put(att, false);
            }

            attBox.addActionListener(e -> {
                checkedAttributes.put(att, attBox.isSelected());
            });

            attributePanel.add(attBox);
        }

        attributeScroll = new JScrollPane(attributePanel);
        attributeScroll.setBorder(BorderFactory.createTitledBorder("Attributes"));
        attributeScroll.setPreferredSize(new Dimension(170, 150));
        attributeScroll.setMaximumSize(attributeScroll.getPreferredSize());
        attributeScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        attributeScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // set all previously checked attributes to checked
        for (String att : checkedAttributes.keySet()) {
            for (Component c : attributePanel.getComponents()) {
                JCheckBox box = (JCheckBox) c;
                if (box.getText().equals(att)) {
                    box.setSelected(checkedAttributes.get(att));
                }
            }
        }
    }

    /**
     * Updates the publisher scroll
     * 
     * @param name the name of the publisher to search
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
     * 
     * @param name the name of the release to search
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
     * 
     * @param scale the scale to search
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
     * 
     * @param att the attribute to search
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
        pubSearch.setPreferredSize(new Dimension(120, 20));
        pubSearch.setMaximumSize(pubSearch.getPreferredSize());

        relSearch = new JTextField();
        relSearch.setPreferredSize(new Dimension(120, 20));
        relSearch.setMaximumSize(relSearch.getPreferredSize());

        scaleSearch = new JTextField();
        scaleSearch.setPreferredSize(new Dimension(120, 20));
        scaleSearch.setMaximumSize(scaleSearch.getPreferredSize());

        attSearch = new JTextField();
        attSearch.setPreferredSize(new Dimension(120, 20));
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

        // make publisher panel
        pubPanel = new JPanel();
        pubPanel.setLayout(new BoxLayout(pubPanel, BoxLayout.Y_AXIS));

        JPanel helperPanel = new JPanel();
        helperPanel.add(pubSearch);

        pubPanel.add(helperPanel);
        pubPanel.add(publisherScroll);

        helperPanel = new JPanel();
        helperPanel.setLayout(new BoxLayout(helperPanel, BoxLayout.X_AXIS));

        helperPanel.add(addPubBtn);
        helperPanel.add(remPubBtn);

        pubPanel.add(helperPanel);

        // make release panel
        relPanel = new JPanel();
        relPanel.setLayout(new BoxLayout(relPanel, BoxLayout.Y_AXIS));

        helperPanel = new JPanel();
        helperPanel.add(relSearch);

        relPanel.add(helperPanel);
        relPanel.add(releaseScroll);

        helperPanel = new JPanel();
        helperPanel.setLayout(new BoxLayout(helperPanel, BoxLayout.X_AXIS));

        helperPanel.add(addRelBtn);
        helperPanel.add(remRelBtn);

        relPanel.add(helperPanel);

        // make scale panel
        scalePanel = new JPanel();
        scalePanel.setLayout(new BoxLayout(scalePanel, BoxLayout.Y_AXIS));

        helperPanel = new JPanel();
        helperPanel.add(scaleSearch);

        scalePanel.add(helperPanel);
        scalePanel.add(scaleScroll);

        helperPanel = new JPanel();
        helperPanel.setLayout(new BoxLayout(helperPanel, BoxLayout.X_AXIS));

        helperPanel.add(addScaleBtn);
        helperPanel.add(remScaleBtn);

        scalePanel.add(helperPanel);

        // make attribute panel
        attPanel = new JPanel();
        attPanel.setLayout(new BoxLayout(attPanel, BoxLayout.Y_AXIS));

        helperPanel = new JPanel();
        helperPanel.add(attSearch);

        attPanel.add(helperPanel);
        attPanel.add(attributeScroll);

        helperPanel = new JPanel();
        helperPanel.setLayout(new BoxLayout(helperPanel, BoxLayout.X_AXIS));

        helperPanel.add(addAttBtn);
        helperPanel.add(remAttBtn);

        attPanel.add(helperPanel);

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
                    droppedFiles = (List<File>) evt.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);

                    if (droppedFiles.size() == 1) {
                        nameField.setText(droppedFiles.get(0).getName());
                    }

                    dragArea.setText("Files dropped: " + droppedFiles.size() + "\nDrag and drop files here");
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

    /**
     * Returns the search boxes
     * 
     * @return Returns an ArrayList of the search boxes
     */
    public JTextField[] getSearchBoxes() {
        JTextField[] boxes = { pubSearch, relSearch, scaleSearch, attSearch };
        return boxes;
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
                JOptionPane.showMessageDialog(this, "You do not have permission to access this page");
                auditLog.log("Attempted to view Analytics", login.getCurrUser());
            }
        });

        library.addActionListener(e -> {
            this.dispose();
            new LibraryView(this.logic, this.login, this.auditLog);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // if the catalogue button is pressed
        if (e.getSource() == catalogueBtn) {
            ArrayList<Boolean> successes = new ArrayList<Boolean>();

            if (publisherList.getSelectedValue() != null && releaseList.getSelectedValue() != null
                    && scaleList.getSelectedValue() != null) {
                int releaseID = releaseList.getSelectedReleaseID();
                String scale = scaleList.getSelectedValue();
                ArrayList<String> attList = getChecked(checkedAttributes);
                String[] attributes = attList.toArray(new String[attList.size()]);
                String asset = dragArea.getText();

                // adds the asset to the database and checks if it was successful
                if (asset.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please drag an asset into the box.");
                } else if (droppedFiles.size() == 0) {
                    JOptionPane.showMessageDialog(null, "Please drag an asset into the box.");
                } else {
                    for (File file : droppedFiles) {
                        String filePath = file.getAbsolutePath();
                        String name = file.getName();
                        String description = descriptionField.getText();

                        boolean success = this.asset.addAsset(filePath, attributes, login.getCurrUser(),
                                name,
                                releaseID,
                                scale, description);

                        successes.add(success);
                    }

                    boolean flag = true;

                    for (int i = 0; i < successes.size(); i++) {
                        if (!successes.get(i)) {
                            JOptionPane.showMessageDialog(null,
                                    "Asset: " + droppedFiles.get(i).getName() + " was not added.");
                            flag = false;
                        }
                    }
                    if (flag) {
                        auditLog.log(successes.size() + " asset(s) added", login.getCurrUser());
                        JOptionPane.showMessageDialog(null, "Asset(s) added successfully.");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a publisher, release and scale.");
        }
    }
}

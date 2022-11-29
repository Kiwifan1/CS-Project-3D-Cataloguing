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

public class AddView extends BoilerPlateView implements ActionListener {

    ConnectLogic logic;
    Publisher publisher;
    Release release;
    Attribute attribute;
    Asset asset;

    JPanel homePanel;
    JPanel cataloguePanel;
    JPanel dragPanel;

    JTextPane dragArea;

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
    JList<String> releaseList;
    JList<String> scaleList;

    public AddView(ConnectLogic logic) {
        super("Home");

        // SQL logic
        this.logic = logic;
        publisher = new Publisher(logic);
        release = new Release(logic);
        attribute = new Attribute(logic);
        asset = new Asset(logic);

        // populate the frame
        makeHomePanel();

        addMenuListeners();

        this.setVisible(true);
    }

    /**
     * Makes the interactable components for the home panel.
     */
    private void makeInteractables() {

        makePublisherScroll();
        makeReleaseScroll();
        makeScaleScroll();
        makeAttributeScroll();
        makeButtons();

    }

    private void makeButtons() {
        // catalogue button
        catalogueBtn = new JButton("Catalogue Asset");
        catalogueBtn.addActionListener(e -> {
            System.out.println("Catalogue Asset Button Pressed");
        });

        // add publisher button
        addPubBtn = new JButton("Add Publisher");
        addPubBtn.addActionListener(e -> {
            System.out.println("Add Publisher Button Pressed");
        });

        // add release button
        addRelBtn = new JButton("Add Release");
        addRelBtn.addActionListener(e -> {
            System.out.println("Add Release Button Pressed");
        });

        // add scale button
        addScaleBtn = new JButton("Add Scale");
        addScaleBtn.addActionListener(e -> {
            System.out.println("Add Scale Button Pressed");
        });

        // add attribute button
        addAttBtn = new JButton("Add Attribute");
        addAttBtn.addActionListener(e -> {
            System.out.println("Add Attribute Button Pressed");
        });
    }

    /**
     * Creates the publication scroll pane.
     */
    private void makePublisherScroll() {
        // publisher scroll pane

        ArrayList<String> publishers = publisher.getAllPublishers();

        JPanel publisherBox = new JPanel();
        publisherBox.setLayout(new BoxLayout(publisherBox, BoxLayout.Y_AXIS));

        publisherList = new JList(publishers.toArray());
        publisherList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
     */
    private void makeReleaseScroll() {
        // release scroll pane

        ArrayList<String> publisher = new ArrayList<String>();

        if (publisherList.getSelectedValue() != null) {
            publisher.add(publisherList.getSelectedValue().toString());
        }

        ArrayList<String> releases = release.getReleaseFromPub(publisher.toArray(new String[publisher.size()]));

        JPanel releaseBox = new JPanel();
        releaseBox.setLayout(new BoxLayout(releaseBox, BoxLayout.Y_AXIS));

        releaseList = new JList(releases.toArray());
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
     */
    private void makeScaleScroll() {
        // scale scroll pane

        ArrayList<String> scales = asset.getAllScales();

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
     */
    private void makeAttributeScroll() {
        // attribute scroll pane

        ArrayList<String> attributes = attribute.getAllAttributes();

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
     * Creates the panel that contains the catalogue button and the scroll panes.
     */
    private void makeCataloguePanel() {
        cataloguePanel = new JPanel();
        cataloguePanel.setLayout(new BoxLayout(cataloguePanel, BoxLayout.X_AXIS));

        JPanel pubPanel = new JPanel();
        pubPanel.setLayout(new BoxLayout(pubPanel, BoxLayout.Y_AXIS));

        pubPanel.add(publisherScroll);
        pubPanel.add(addPubBtn);

        JPanel relPanel = new JPanel();
        relPanel.setLayout(new BoxLayout(relPanel, BoxLayout.Y_AXIS));

        relPanel.add(releaseScroll);
        relPanel.add(addRelBtn);

        JPanel scalePanel = new JPanel();
        scalePanel.setLayout(new BoxLayout(scalePanel, BoxLayout.Y_AXIS));

        scalePanel.add(scaleScroll);
        scalePanel.add(addScaleBtn);

        JPanel attPanel = new JPanel();
        attPanel.setLayout(new BoxLayout(attPanel, BoxLayout.Y_AXIS));

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

        makeDragArea();

        dragPanel.add(catalogueBtn);

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
                    for (File file : droppedFiles) {
                        System.out.println(file.getAbsolutePath());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
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
            new AnalyticsView(this.logic);
        });

        library.addActionListener(e -> {
            this.dispose();
            new LibraryView(this.logic);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // if the catalogue button is pressed
        if (e.getSource() == catalogueBtn) {
            String[] attributes = getChecked(attributeScroll);
        }
    }

}

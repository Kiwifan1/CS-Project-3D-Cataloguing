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

public class AddView extends BoilerPlateView implements ActionListener {

    ConnectLogic logic;
    Attribute attribute;
    Release release;
    Asset asset;

    JPanel homePanel;
    JPanel cataloguePanel;
    JPanel dragPanel;

    JTextPane dragArea;

    JButton catalogueBtn;
    JScrollPane attributeScroll;
    JScrollPane publisherScroll;
    JScrollPane releaseScroll;
    JScrollPane scaleScroll;

    public AddView(ConnectLogic logic) {
        super("Home");

        // SQL logic
        attribute = new Attribute(logic);
        release = new Release(logic);
        asset = new Asset(logic);

        // populate the frame
        makeHomePanel();

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

        // catalogue button
        catalogueBtn = new JButton("Catalogue Asset");
        catalogueBtn.addActionListener(e -> {
            System.out.println("Catalogue Asset Button Pressed");
        });
    }

    /**
     * Creates the publication scroll pane.
     */
    private void makePublisherScroll() {
        // publisher scroll pane

        ArrayList<String> publishers = release.getAllPublishers();

        JPanel publisherBox = new JPanel();
        publisherBox.setLayout(new BoxLayout(publisherBox, BoxLayout.Y_AXIS));

        for (String publisher : publishers) {
            JCheckBox publisherCheck = new JCheckBox(publisher);
            publisherCheck.addActionListener(this);
            publisherBox.add(publisherCheck);
        }

        publisherScroll = new JScrollPane(publisherBox);
        publisherScroll.setBorder(BorderFactory.createTitledBorder("Publisher"));
        publisherScroll.setPreferredSize(new Dimension(200, 150));
        publisherScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        publisherScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    /**
     * Creates the release scroll pane.
     */
    private void makeReleaseScroll() {
        // release scroll pane

        String[] checked = getChecked(publisherScroll);

        ArrayList<String> releases = release.getReleaseFromPub(checked);

        JPanel releaseBox = new JPanel();
        releaseBox.setLayout(new BoxLayout(releaseBox, BoxLayout.Y_AXIS));

        for (String release : releases) {
            JCheckBox releaseCheck = new JCheckBox(release);
            releaseCheck.addActionListener(this);
            releaseBox.add(releaseCheck);
        }

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

        for (String scale : scales) {
            JCheckBox scaleCheck = new JCheckBox(scale);
            scaleCheck.addActionListener(this);
            scaleBox.add(scaleCheck);
        }

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

        ArrayList<String[]> attributes = attribute.getAllAttributes();

        JPanel attributePanel = new JPanel();
        attributePanel.setLayout(new BoxLayout(attributePanel, BoxLayout.Y_AXIS));

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
        cataloguePanel.setLayout(new FlowLayout());

        // create the spacing and add the buttons
        cataloguePanel.add(Box.createHorizontalGlue());
        cataloguePanel.add(publisherScroll);
        cataloguePanel.add(releaseScroll);
        cataloguePanel.add(scaleScroll);
        cataloguePanel.add(attributeScroll);
        cataloguePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        cataloguePanel.add(catalogueBtn, StyleConstants.ALIGN_CENTER);
    }

    /**
     * Creates the panel that contains the drag area
     */
    private void makeDragPanel() {

        dragPanel = new JPanel();
        dragPanel.setLayout(new BoxLayout(dragPanel, BoxLayout.Y_AXIS));
        dragPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        makeDragArea();

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
                        Icon icon = FileSystemView.getFileSystemView().getSystemIcon(file);
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
    private String[] getChecked(JScrollPane scroll) {
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

    private int[] getReleaseIds(String[] publishers) {
        ArrayList<Integer> ids = release.getRidsFromPublishers(publishers);
        return ids.stream().mapToInt(i -> i).toArray();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] publishers = getChecked(publisherScroll);

        // if the catalogue button is pressed
        if (e.getSource() == catalogueBtn) {
            String[] releases = getChecked(releaseScroll);
            String[] scales = getChecked(scaleScroll);
            String[] attributes = getChecked(attributeScroll);
        }

        // if the user clicks on a check box
        else if (e.getSource() instanceof JCheckBox) {
            JCheckBox box = (JCheckBox) e.getSource();
            int[] releaseIds = getReleaseIds(publishers);

            // if the publisher box is clicked
            if (box.getParent() == publisherScroll.getViewport().getView()) {
                updateReleaseScroll(publishers);
                updateScaleScroll(releaseIds);
            }

            // if the release box is clickedq
            else if (box.getParent() == releaseScroll.getViewport().getView()) {
                updateScaleScroll(releaseIds);
            }

            // if the scale box is clicked
            else if (box.getParent() == scaleScroll.getViewport().getView()) {
                // TODO
            }
        }
    }
}

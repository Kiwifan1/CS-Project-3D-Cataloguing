package UI;

import Logic.Attribute;
import Logic.ConnectLogic;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.*;

public class Home extends JFrame {

    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    ConnectLogic logic;
    Attribute attribute;

    JPanel homePanel;
    JPanel cataloguePanel;
    JPanel dragPanel;

    JTextPane dragArea;

    JButton catalogueBtn;
    JScrollPane attributeScroll;

    public Home() {
        super("Home");
        logic = new ConnectLogic();
        attribute = new Attribute(logic);

        // set up the frame
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // populate the frame
        makeHomePanel();

        this.setVisible(true);
    }

    private void makeInteractables() {
        // catalogue button
        catalogueBtn = new JButton("Catalogue Asset");
        catalogueBtn.addActionListener(e -> {
            System.out.println("Catalogue Asset Button Pressed");
        });

        // tag scroll pane
        attributeScroll = new JScrollPane();

        // TODO: Add checkboxes to the scroll pane
        ArrayList<String[]> attributes = attribute.getAllAttributes();

        JPanel attributePanel = new JPanel();
        attributePanel.setLayout(new BoxLayout(attributePanel, BoxLayout.Y_AXIS));

        for (String[] tag : attributes) {
            JCheckBox tagBox = new JCheckBox(tag[0]);
            attributePanel.add(tagBox);
        }

        attributeScroll = new JScrollPane(attributePanel);
        attributeScroll.setPreferredSize(new Dimension(200, 200));
    }

    private void makeHomePanel() {
        homePanel = new JPanel(new BorderLayout());

        makeInteractables();
        makeDragPanel();
        makeCataloguePanel();

        homePanel.add(dragPanel, BorderLayout.CENTER);
        homePanel.add(cataloguePanel, BorderLayout.SOUTH);

        this.add(homePanel, BorderLayout.CENTER);
    }

    private void makeCataloguePanel() {
        cataloguePanel = new JPanel();
        cataloguePanel.setLayout(new FlowLayout());

        // create the spacing and add the buttons
        cataloguePanel.add(Box.createHorizontalGlue());
        cataloguePanel.add(attributeScroll);
        cataloguePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        cataloguePanel.add(catalogueBtn);
    }

    /**
     * Creates the panel that contains the drag area
     */
    private void makeDragPanel() {

        dragPanel = new JPanel();
        dragPanel.setLayout(new BoxLayout(dragPanel, BoxLayout.Y_AXIS));
        dragPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        createDragArea();

        dragPanel.add(dragArea, BorderLayout.CENTER);
    }

    /**
     * Creates a JTextArea that can be dragged and dropped files onto.
     * 
     * @see https://stackoverflow.com/questions/811248/how-can-i-use-drag-and-drop-in-swing-to-get-file-path
     *      for how I got this to work
     */
    private void createDragArea() {
        dragArea = new JTextPane();
        dragArea.setText("Drag and drop files here");

        // formatting
        dragArea.setFont(new Font("Serif", Font.BOLD, 30));
        dragArea.setEditable(false);
        dragArea.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

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
}

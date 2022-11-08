package UI;

import Logic.ConnectLogic;
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

    JPanel homePanel;
    JPanel cataloguePanel;
    JPanel dragPanel;

    JTextPane dragArea;

    JButton catalogueBtn;
    JScrollPane tagScroll;

    public Home() {
        super("Home");
        // logic = new ConnectLogic();

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
        tagScroll = new JScrollPane();

        // add checkboxes to the scroll pane
        String[] tags = { "tag1", "tag2", "tag3", "tag4", "tag5", "tag6", "tag7", "tag8", "tag9", "tag10", "tag11",
                "tag12",
                "tag13", "tag14", "tag15", "tag16", "tag17", "tag18", "tag19", "tag20", "tag21", "tag22", "tag23",
                "tag24",
                "tag25", "tag26", "tag27", "tag28", "tag29", "tag30", "tag31", "tag32", "tag33", "tag34", "tag35",
                "tag36",
                "tag37", "tag38", "tag39", "tag40", "tag41", "tag42", "tag43", "tag44", "tag45", "tag46", "tag47",
                "tag48",
                "tag49", "tag50", "tag51", "tag52", "tag53", "tag54", "tag55", "tag56", "tag57", "tag58", "tag59",
                "tag60",
                "tag61", "tag62", "tag63", "tag64", "tag65", "tag66", "tag67", "tag68", "tag69", "tag70", "tag71",
                "tag72",
                "tag73", "tag74", "tag75", "tag76", "tag77", "tag78", "tag79", "tag80", "tag81", "tag82", "tag83",
                "tag84",
                "tag85", "tag86", "tag87", "tag88", "tag89", "tag90", "tag91", "tag92", "tag93", "tag94", "tag95",
                "tag96",
                "tag97", "tag98", "tag99", "tag100", "tag101", "tag102", "tag103", "tag104", "tag105", "tag106",
                "tag107",
                "tag108", "tag109", "tag110", "tag111", "tag112", "tag113", "tag114", "tag115", "tag116", "tag117",
                "tag118", "tag119", "tag120", "tag121", "tag122", "tag123", "tag124", "tag125", "tag126", "tag127" };

        JPanel tagPanel = new JPanel();
        tagPanel.setLayout(new BoxLayout(tagPanel, BoxLayout.Y_AXIS));

        for (String tag : tags) {
            JCheckBox tagBox = new JCheckBox(tag);
            tagPanel.add(tagBox);
        }

        tagScroll = new JScrollPane(tagPanel);
        tagScroll.setPreferredSize(new Dimension(200, 200));
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
        cataloguePanel.add(tagScroll);
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

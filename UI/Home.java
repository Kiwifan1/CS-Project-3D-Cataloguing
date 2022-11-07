package UI;

import Logic.ConnectLogic;
import java.util.List;
import java.io.*;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Font;

public class Home extends JFrame {

    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    ConnectLogic logic;

    JPanel homePanel;
    JPanel cataloguePanel;
    JTextPane dragArea;
    JButton catalogueBtn;

    public Home() {
        super("Home");
        // logic = new ConnectLogic();

        // set up the frame
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // populate the frame
        createDragArea();
        makeHomePanel();

        this.setVisible(true);
    }

    private void makeHomePanel() {
        homePanel = new JPanel(new BorderLayout());

        makeCataloguePanel();

        homePanel.add(cataloguePanel, BorderLayout.CENTER);

        this.add(homePanel, BorderLayout.CENTER);
    }

    private void makeCataloguePanel() {
        cataloguePanel = new JPanel();
        cataloguePanel.setLayout(new BoxLayout(cataloguePanel, BoxLayout.Y_AXIS));
        cataloguePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        catalogueBtn = new JButton("Catalogue Asset");
        catalogueBtn.addActionListener(e -> {
            System.out.println("Catalogue Asset Button Pressed");
        });

        cataloguePanel.add(dragArea, BorderLayout.CENTER);
        cataloguePanel.add(catalogueBtn, BorderLayout.SOUTH);
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
        dragArea.setBounds(10, 10, 100, 100);
        
        // center text
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        dragArea.getStyledDocument().setParagraphAttributes(0, dragArea.getText().length(), center, false);

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

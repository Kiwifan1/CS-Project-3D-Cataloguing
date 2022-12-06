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

public class AnalyticsView extends BoilerPlateView implements ActionListener {

    ConnectLogic logic;
    Analytics analytics;
    AuditLog auditLog;
    Login login;
    
    JPanel mainPanel;
    JPanel auditPanel;
    JPanel analysisPanel;

    public AnalyticsView(ConnectLogic logic, Login login, AuditLog auditLog) {
        super("Analytics");

        this.logic = logic;
        this.login = login;

        this.auditLog = auditLog;

        analytics = new Analytics(logic);

        mainPanel = new JPanel();

        
        addMenuListeners();
        createMainPanel();

        this.add(mainPanel);

        this.setVisible(true);
    }

    /**
     * Creates the main panel
     */
    private void createMainPanel() {
        createAnalysisPanel();
        createAuditPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(analysisPanel);
        mainPanel.add(auditPanel);
    }

    /**
     * Creates the auditt log panel
     */
    private void createAuditPanel() {
        auditPanel = new JPanel();
        auditPanel.setLayout(new BoxLayout(auditPanel, BoxLayout.Y_AXIS));
        auditPanel.setBorder(BorderFactory.createTitledBorder("Audit Log"));
        auditPanel.setPreferredSize(new Dimension(610, 500));

        // create an auto scrolling panel for the audit log
        JScrollPane scrollPane = new JScrollPane(auditPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(610, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setValue(0);
        scrollPane.setBackground(getForeground());

        // add all audit log entries to the scroll pane
        for (Log log : auditLog.getLogs()) {
            JTextPane textPane = new JTextPane();
            textPane.setEditable(false);
            textPane.setText(log.toString());
            textPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            textPane.setBackground(getForeground());
            textPane.setPreferredSize(new Dimension(600, 40));
            textPane.setMaximumSize(textPane.getPreferredSize());
            auditPanel.add(textPane);
        }
    }

    /**
     * Creates the analysis panel
     */
    private void createAnalysisPanel() {
        // create the analysis panel that will hold analytics
        analysisPanel = new JPanel();
        analysisPanel.setLayout(new BoxLayout(analysisPanel, BoxLayout.X_AXIS));

        /// create average file size panel
        JPanel averageFileSizePanel = new JPanel();
        averageFileSizePanel.setLayout(new BoxLayout(averageFileSizePanel, BoxLayout.Y_AXIS));
        averageFileSizePanel.setBorder(BorderFactory.createTitledBorder("Average File Size"));
        averageFileSizePanel.setPreferredSize(new Dimension(400, 400));

        // create the average file size label
        JLabel averageFileSizeLabel = new JLabel();
        averageFileSizeLabel.setText("Average File Size: " + (analytics.getAverageFileSize()));
        averageFileSizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        averageFileSizePanel.add(averageFileSizeLabel);
        analysisPanel.add(averageFileSizePanel);

        // create the most popular publishers panel
        JPanel mostPopularPublishersPanel = new JPanel();
        mostPopularPublishersPanel.setLayout(new BoxLayout(mostPopularPublishersPanel, BoxLayout.Y_AXIS));
        mostPopularPublishersPanel.setBorder(BorderFactory.createTitledBorder("Most Popular Publishers"));
        mostPopularPublishersPanel.setPreferredSize(new Dimension(400, 400));

        // create the most popular publishers label
        for (String publisher : analytics.getMostPopPubs(3)) {
            JLabel mostPopularPublishersLabel = new JLabel();
            mostPopularPublishersLabel.setText(publisher);
            mostPopularPublishersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mostPopularPublishersPanel.add(mostPopularPublishersLabel);
        }
        analysisPanel.add(mostPopularPublishersPanel);

        // create the most popular attributes panel
        JPanel mostPopularAttributesPanel = new JPanel();
        mostPopularAttributesPanel.setLayout(new BoxLayout(mostPopularAttributesPanel, BoxLayout.Y_AXIS));
        mostPopularAttributesPanel.setBorder(BorderFactory.createTitledBorder("Most Popular Attributes"));
        mostPopularAttributesPanel.setPreferredSize(new Dimension(400, 400));

        // create the most popular attributes label
        for (String attribute : analytics.getMostPopAttrs(3)) {
            JLabel mostPopularAttributesLabel = new JLabel();
            mostPopularAttributesLabel.setText(attribute);
            mostPopularAttributesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mostPopularAttributesPanel.add(mostPopularAttributesLabel);
        }
        analysisPanel.add(mostPopularAttributesPanel);

        // add the analysis panel to the main panel
        mainPanel.add(analysisPanel);


    }

    @Override
    protected void addMenuListeners() {
        logout.addActionListener(e -> {
            this.dispose();
            new LoginView(this.logic);
        });

        library.addActionListener(e -> {
            this.dispose();
            new LibraryView(this.logic, this.login, this.auditLog);
        });

        addItem.addActionListener(e -> {
            this.dispose();
            new AddView(this.logic, this.login, this.auditLog);
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

}

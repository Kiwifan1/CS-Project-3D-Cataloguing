package UI;

import Logic.*;

import java.util.ArrayList;
import java.util.HashMap;
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

    public static int ANALYTICS_WIDTH = 150;
    public static int ANALYTICS_HEIGHT = 75;

    ConnectLogic logic;
    Analytics analytics;
    AuditLog auditLog;
    Login login;

    JPanel mainPanel;
    JPanel auditLogPanel;
    JPanel auditPanel;
    JPanel analysisPanel;

    JScrollPane scrollPane;

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
        createInactiveUsersPanel();
        createAuditPanel();

        mainPanel.add(analysisPanel);
        mainPanel.add(auditLogPanel, BorderLayout.SOUTH);

    }

    /**
     * Creates the auditt log panel
     */
    private void createAuditPanel() {
        auditPanel = new JPanel();
        auditPanel.setLayout(new BoxLayout(auditPanel, BoxLayout.Y_AXIS));

        // add all audit log entries to the scroll pane
        for (Log log : auditLog.getLogs()) {
            JTextPane textPane = new JTextPane();
            textPane.setEditable(false);
            textPane.setText(log.toString());
            textPane.setFont(new Font("Arial", Font.PLAIN, 12));
            textPane.setBorder(BorderFactory.createEmptyBorder(5, 1, 5, 5));
            textPane.setBackground(this.getForeground());
            auditPanel.add(textPane);
        }

        // create an auto scrolling panel for the audit log
        scrollPane = new JScrollPane(auditPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Audit Log"));
        scrollPane.setPreferredSize(new Dimension(700, 250));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(this.getForeground());

        auditLogPanel = new JPanel();
        auditLogPanel.setLayout(new BoxLayout(auditLogPanel, BoxLayout.Y_AXIS));
        auditLogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        auditLogPanel.add(scrollPane);
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
        // create a centered titled border
        averageFileSizePanel.setBorder(BorderFactory.createTitledBorder("Average File Size"));
        averageFileSizePanel.setPreferredSize(new Dimension(ANALYTICS_WIDTH, ANALYTICS_HEIGHT));
        averageFileSizePanel.setMaximumSize(averageFileSizePanel.getPreferredSize());

        // create the average file size label
        JLabel averageFileSizeLabel = new JLabel();
        averageFileSizeLabel.setText(analytics.getAverageFileSize() + "MB");
        averageFileSizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        averageFileSizePanel.add(averageFileSizeLabel);

        analysisPanel.add(averageFileSizePanel);

        // create the total disk space panel
        JPanel totalDiskSpacePanel = new JPanel();
        totalDiskSpacePanel.setLayout(new BoxLayout(totalDiskSpacePanel, BoxLayout.Y_AXIS));
        totalDiskSpacePanel.setBorder(BorderFactory.createTitledBorder("Total Disk Space"));
        totalDiskSpacePanel.setPreferredSize(new Dimension(ANALYTICS_WIDTH, ANALYTICS_HEIGHT));
        totalDiskSpacePanel.setMaximumSize(totalDiskSpacePanel.getPreferredSize());

        // create the total disk space label
        JLabel totalDiskSpaceLabel = new JLabel();
        totalDiskSpaceLabel.setText(analytics.getTotalFileSize() + "MB");
        totalDiskSpaceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalDiskSpacePanel.add(totalDiskSpaceLabel);

        analysisPanel.add(totalDiskSpacePanel);

        // create the most popular publishers panel
        JPanel mostPopularPublishersPanel = new JPanel();
        mostPopularPublishersPanel.setLayout(new BoxLayout(mostPopularPublishersPanel, BoxLayout.Y_AXIS));
        mostPopularPublishersPanel.setPreferredSize(new Dimension(ANALYTICS_WIDTH, ANALYTICS_HEIGHT));
        mostPopularPublishersPanel.setMaximumSize(mostPopularPublishersPanel.getPreferredSize());
        mostPopularPublishersPanel.setBorder(BorderFactory.createTitledBorder("Most Popular Publishers"));

        // create the most popular publishers label
        for (String publisher : analytics.getMostPopPubs(3)) {
            JLabel mostPopularPublishersLabel = new JLabel();
            mostPopularPublishersLabel.setText(publisher);
            mostPopularPublishersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mostPopularPublishersLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
            mostPopularPublishersPanel.add(mostPopularPublishersLabel);
        }
        analysisPanel.add(mostPopularPublishersPanel);

        // create the most popular attributes panel
        JPanel mostPopularAttributesPanel = new JPanel();
        mostPopularAttributesPanel.setLayout(new BoxLayout(mostPopularAttributesPanel, BoxLayout.Y_AXIS));
        mostPopularAttributesPanel.setPreferredSize(new Dimension(ANALYTICS_WIDTH, ANALYTICS_HEIGHT));
        mostPopularAttributesPanel.setMaximumSize(mostPopularAttributesPanel.getPreferredSize());
        mostPopularAttributesPanel.setBorder(BorderFactory.createTitledBorder("Most Popular Attributes"));

        // create the most popular attributes label
        for (String attribute : analytics.getMostPopAttrs(3)) {
            JLabel mostPopularAttributesLabel = new JLabel();
            mostPopularAttributesLabel.setText(attribute);
            mostPopularAttributesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mostPopularAttributesLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
            mostPopularAttributesPanel.add(mostPopularAttributesLabel);
        }

        analysisPanel.add(mostPopularAttributesPanel);
    }

    /**
     * Creates the inactive users panel
     */
    private void createInactiveUsersPanel() {
        JPanel inactiveUsersPanel = new JPanel();
        inactiveUsersPanel.setLayout(new BoxLayout(inactiveUsersPanel, BoxLayout.Y_AXIS));
        inactiveUsersPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        inactiveUsersPanel.setPreferredSize(new Dimension(ANALYTICS_WIDTH, ANALYTICS_HEIGHT));
        inactiveUsersPanel.setMaximumSize(inactiveUsersPanel.getPreferredSize());

        // create the inactive users label
        JScrollPane scrollPane = new JScrollPane();
        HashMap<String, Boolean> users = analytics.getUserRecentActivity();

        for (String user : users.keySet()) {
            if (!users.get(user)) {
                JLabel inactiveUsersLabel = new JLabel();
                inactiveUsersLabel.setText(user);
                inactiveUsersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                inactiveUsersLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
                inactiveUsersLabel.setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0));
                inactiveUsersPanel.add(inactiveUsersLabel);
            }
        }

        scrollPane = new JScrollPane(inactiveUsersPanel);
        scrollPane.setPreferredSize(new Dimension(ANALYTICS_WIDTH, ANALYTICS_HEIGHT));
        scrollPane.setMaximumSize(scrollPane.getPreferredSize());
        scrollPane.setBorder(BorderFactory.createTitledBorder("Inactive Users"));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        analysisPanel.add(scrollPane);
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

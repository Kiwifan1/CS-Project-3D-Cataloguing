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
    public static int ANALYTICS_HEIGHT = 125;

    private Font auditFont = new Font("Arial", Font.PLAIN, 12);
    private Font analyticFont = new Font("Arial", Font.BOLD, 20);

    private ConnectLogic logic;
    private AppUser appUser;
    private Analytics analytics;
    private AuditLog auditLog;
    private Login login;

    private JPanel mainPanel;
    private JPanel auditLogPanel;
    private JPanel editAuditPanel;
    private JPanel auditPanel;
    private JPanel analysisPanel;

    private JScrollPane scrollPane;

    public AnalyticsView(ConnectLogic logic, Login login, AuditLog auditLog) {
        super("Analytics");

        this.logic = logic;
        this.login = login;

        this.auditLog = auditLog;

        analytics = new Analytics(logic);
        appUser = new AppUser(logic);

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
        createUsersPanel();
        createAuditPanel();
        createEditAuditPanel();

        mainPanel.add(analysisPanel);
        mainPanel.add(auditLogPanel);
        mainPanel.add(editAuditPanel);
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
            textPane.setFont(auditFont);
            textPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 3, 5));
            textPane.setBackground(this.getForeground());
            auditPanel.add(textPane);
        }

        // create an auto scrolling panel for the audit log
        scrollPane = new JScrollPane(auditPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Audit Log"));
        scrollPane.setPreferredSize(new Dimension(700, 380));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(this.getForeground());

        auditLogPanel = new JPanel();
        auditLogPanel.setLayout(new BoxLayout(auditLogPanel, BoxLayout.Y_AXIS));
        auditLogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        auditLogPanel.add(scrollPane);
    }

    /**
     * Updates the Audit Panel
     */
    private void updateAuditPanel() {
        auditPanel.removeAll();
        for (Log log : auditLog.getLogs()) {
            JTextPane textPane = new JTextPane();
            textPane.setEditable(false);
            textPane.setText(log.toString());
            textPane.setFont(auditFont);
            textPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 3, 5));
            // textPane.setBackground(this.getForeground());
            auditPanel.add(textPane);
        }
        auditPanel.revalidate();
        auditPanel.repaint();
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
        averageFileSizeLabel.setFont(analyticFont);
        averageFileSizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        averageFileSizeLabel.setBorder(BorderFactory.createEmptyBorder(ANALYTICS_HEIGHT / 4, 0, 0, 0));
        averageFileSizePanel.add(averageFileSizeLabel, BorderLayout.CENTER);

        analysisPanel.add(averageFileSizePanel);

        // create the total disk space panel
        JPanel totalDiskSpacePanel = new JPanel();
        totalDiskSpacePanel.setLayout(new BoxLayout(totalDiskSpacePanel, BoxLayout.Y_AXIS));
        totalDiskSpacePanel.setBorder(BorderFactory.createTitledBorder("Total Disk Space"));
        totalDiskSpacePanel.setPreferredSize(new Dimension(ANALYTICS_WIDTH, ANALYTICS_HEIGHT));
        totalDiskSpacePanel.setMaximumSize(totalDiskSpacePanel.getPreferredSize());

        // create the total disk space label
        JLabel totalDiskSpaceLabel = new JLabel();
        double totalDiskSpace = analytics.getTotalFileSize();
        if (totalDiskSpace > 1024) {
            totalDiskSpaceLabel.setText(Math.round((totalDiskSpace / 1024) * 100) / 100 + "GB");
        } else {
            totalDiskSpaceLabel.setText(totalDiskSpace + "MB");
        }
        totalDiskSpaceLabel.setFont(analyticFont);
        totalDiskSpaceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalDiskSpaceLabel.setBorder(BorderFactory.createEmptyBorder(ANALYTICS_HEIGHT / 4, 0, 0, 0));
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
            mostPopularPublishersLabel.setFont(analyticFont);
            mostPopularPublishersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mostPopularPublishersLabel.setBorder(BorderFactory.createEmptyBorder(ANALYTICS_HEIGHT / 4, 0, 0, 0));
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
            mostPopularAttributesLabel.setFont(analyticFont);
            mostPopularAttributesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mostPopularAttributesLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
            mostPopularAttributesPanel.add(mostPopularAttributesLabel);
        }

        analysisPanel.add(mostPopularAttributesPanel);
    }

    /**
     * Creates the edit audit panel
     */
    private void createEditAuditPanel() {
        // create the edit audit panel
        editAuditPanel = new JPanel();
        editAuditPanel.setLayout(new BoxLayout(editAuditPanel, BoxLayout.X_AXIS));
        editAuditPanel.setBorder(BorderFactory.createEmptyBorder(3, 10, 10, 10));
        editAuditPanel.setPreferredSize(new Dimension(500, 50));
        editAuditPanel.setMaximumSize(editAuditPanel.getPreferredSize());
        editAuditPanel.setBorder(BorderFactory.createTitledBorder("Edit Audit"));

        // create a button for adding an admin
        JButton addAdminButton = new JButton("Add Admin");
        addAdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String adminName = JOptionPane.showInputDialog("Enter the username of the admin to add");
                if (adminName != null) {
                    if (appUser.addAdmin(adminName)) {
                        updateAuditPanel();
                        auditLog.log("Admin added: " + adminName, login.getCurrUser());
                    } else {
                        JOptionPane.showMessageDialog(null, "Error Adding Admin", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    updateAuditPanel();
                }
            }
        });

        editAuditPanel.add(addAdminButton);

        // create a button for exporting the audit log
        JButton exportAuditLogButton = new JButton("Export Audit Log");
        exportAuditLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                auditLog.exportAuditLog();
            }
        });
        editAuditPanel.add(exportAuditLogButton);

        // Create a button for clearing the audit log
        JButton clearAuditLogButton = new JButton("Clear Audit Log");
        clearAuditLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                auditLog.clearAuditLog();
                updateAuditPanel();
            }
        });
        editAuditPanel.add(clearAuditLogButton);

        // create a selection box for how often the audit log is cleared
        String[] clearOptions = { "Daily", "Weekly", "Monthly", "Never" };
        JComboBox<String> clearAuditLogComboBox = new JComboBox<String>(clearOptions);
        clearAuditLogComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> cb = (JComboBox<String>) e.getSource();
                String clearOption = (String) cb.getSelectedItem();
                auditLog.setTimeToClean(clearOption);
            }
        });
        clearAuditLogComboBox.setSize(100, 40);
        clearAuditLogComboBox.setSelectedItem(auditLog.getTimeToClean());
        editAuditPanel.add(clearAuditLogComboBox);
    }

    /**
     * Creates the inactive users panel
     */
    private void createUsersPanel() {
        JPanel usersPanel = new JPanel();
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));
        usersPanel.setPreferredSize(new Dimension(ANALYTICS_WIDTH + 20, ANALYTICS_HEIGHT));
        usersPanel.setMaximumSize(usersPanel.getPreferredSize());
        usersPanel.setBorder(BorderFactory.createTitledBorder("Users"));

        JPanel activePanel = new JPanel();
        activePanel.setLayout(new BoxLayout(activePanel, BoxLayout.X_AXIS));

        JPanel inactivePanel = new JPanel();
        inactivePanel.setLayout(new BoxLayout(inactivePanel, BoxLayout.X_AXIS));

        JLabel activeLabel = new JLabel("Active: ");
        JLabel inactiveLabel = new JLabel("Inactive: ");

        JComboBox<String> inactiveUsersComboBox = new JComboBox<String>();
        JComboBox<String> activeUsersComboBox = new JComboBox<String>();

        // create the inactive users label
        HashMap<String, Boolean> users = analytics.getUserRecentActivity();

        for (String user : users.keySet()) {
            if (!users.get(user)) {
                JLabel inactiveUsersLabel = new JLabel();
                inactiveUsersLabel.setText(user);
                if (login.isAdmin(user)) {
                    inactiveUsersLabel.setText(user + " (Admin)");
                }
                inactiveUsersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                inactiveUsersLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
                inactiveUsersLabel.setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0));
                inactiveUsersComboBox.addItem(user);
            } else {
                JLabel activeUsersLabel = new JLabel();
                activeUsersLabel.setText(user);
                if (login.isAdmin(user)) {
                    activeUsersLabel.setText(user + " (Admin)");
                }
                activeUsersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                activeUsersLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
                activeUsersLabel.setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0));
                activeUsersComboBox.addItem(user);
            }
        }
        activeUsersComboBox.setToolTipText("Active Users");
        activeUsersComboBox.setEditable(false);
        activeUsersComboBox.setPreferredSize(new Dimension(ANALYTICS_WIDTH - 10, ANALYTICS_HEIGHT));
        activeUsersComboBox.setMaximumSize(activeUsersComboBox.getPreferredSize());

        inactiveUsersComboBox.setToolTipText("Inactive Users");
        inactiveUsersComboBox.setEditable(false);
        inactiveUsersComboBox.setPreferredSize(new Dimension(ANALYTICS_WIDTH - 14, ANALYTICS_HEIGHT));
        inactiveUsersComboBox.setMaximumSize(inactiveUsersComboBox.getPreferredSize());

        activePanel.add(activeLabel);
        activePanel.add(activeUsersComboBox);

        inactivePanel.add(inactiveLabel);
        inactivePanel.add(inactiveUsersComboBox);

        usersPanel.add(activePanel);
        usersPanel.add(inactivePanel);

        analysisPanel.add(usersPanel);
    }

    @Override
    protected void addMenuListeners() {
        logout.addActionListener(e -> {
            auditLog.log("Logged out", login.getCurrUser());
            this.dispose();
            new LoginView(this.logic, this.auditLog);
        });

        library.addActionListener(e -> {
            this.dispose();
            new LibraryView(this.logic, this.login, this.auditLog);
        });

        addItem.addActionListener(e -> {
            this.dispose();
            new AddFileView(this.logic, this.login, this.auditLog);
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

}

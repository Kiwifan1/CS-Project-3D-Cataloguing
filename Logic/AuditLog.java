package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class AuditLog {

    private Connection cn;
    private String timeToClean;

    public AuditLog(ConnectLogic logic) {
        this.cn = logic.getConnection();
        // read the time to clean from the cleanTimeFile, if it doesn't exist, create it
        // and set it to DAY
        try {
            BufferedReader br = new BufferedReader(new FileReader("cleanTime.txt"));
            timeToClean = br.readLine();
            br.close();
        } catch (FileNotFoundException e) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("cleanTime.txt"));
                bw.write("DAY");
                bw.close();
                this.timeToClean = "DAY";
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new audit log entry
     * 
     * @param action The action that was performed
     * @param user   The user that performed the action
     * @return Whether the entry was created successfully
     */
    public boolean log(String action, String user) {
        try {
            PreparedStatement ps = cn.prepareStatement("INSERT INTO AuditLog VALUES (?, CURRENT_TIMESTAMP(), ?)");
            ps.setString(1, user);
            ps.setString(2, action);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets all the audit log entries
     * 
     * @return A list of all the audit log entries
     */
    public ArrayList<Log> getLogs() {
        ArrayList<Log> logs = new ArrayList<Log>();

        try {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM AuditLog");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                logs.add(new Log(rs.getString("username"), rs.getString("action"), rs.getString("time")));
            }
            return logs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Gets all the audit log entries for a specific user
     * 
     * @param user The user to get the audit log entries for
     * @return A list of all the audit log entries for the user
     */
    public ArrayList<Log> getLogs(String user) {
        ArrayList<Log> logs = new ArrayList<Log>();

        try {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM AuditLog WHERE user = ?");
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                logs.add(new Log(rs.getString("username"), rs.getString("action"), rs.getString("time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Gets all the audit log entries for a specific action
     * 
     * @param action The action to get the audit log entries for
     * @return A list of all the audit log entries for the action
     */
    public ArrayList<Log> getLogsByAction(String action) {
        ArrayList<Log> logs = new ArrayList<Log>();

        try {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM AuditLog WHERE action = ?");
            ps.setString(1, action);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                logs.add(new Log(rs.getString("username"), rs.getString("action"), rs.getString("time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Gets all the audit log entries for a specific user and action
     * 
     * @param user   The user to get the audit log entries for
     * @param action The action to get the audit log entries for
     * @return A list of all the audit log entries for the user and action
     */
    public ArrayList<Log> getLogs(String user, String action) {
        ArrayList<Log> logs = new ArrayList<Log>();

        try {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM AuditLog WHERE user = ? AND action = ?");
            ps.setString(1, user);
            ps.setString(2, action);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                logs.add(new Log(rs.getString("username"), rs.getString("action"), rs.getString("time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Remove all audit log entries that are more than x time old
     */
    public void cleanup() {
        try {
            if (!(timeToClean.equals("NEVER"))) {
                String query = "DELETE FROM AuditLog WHERE time < TIMESTAMPADD";
                query += "(" + timeToClean + ", -1, CURRENT_TIMESTAMP())";
                PreparedStatement ps = cn.prepareStatement(query);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove all audit log entries
     */
    public void clearAuditLog() {
        try {
            PreparedStatement ps = cn.prepareStatement("DELETE FROM AuditLog");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the time to clean up the audit log
     * 
     * @param cleanTime The time to clean up the audit log
     */
    public void setTimeToClean(String cleanTime) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("cleanTime.txt"));
            switch (cleanTime) {
                case "Daily":
                    this.timeToClean = "DAY";
                    bw.write(timeToClean);
                    break;
                case "Weekly":
                    this.timeToClean = "WEEK";
                    bw.write(timeToClean);
                    break;
                case "Monthly":
                    this.timeToClean = "MONTH";
                    bw.write(timeToClean);
                    break;
                case "Yearly":
                    this.timeToClean = "YEAR";
                    bw.write(timeToClean);
                    break;
                case "Never":
                    this.timeToClean = "NEVER";
                    bw.write(timeToClean);
                    break;
                default:
                    this.timeToClean = "DAY";
                    bw.write(timeToClean);
                    break;
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the time to clean up the audit log
     * 
     * @return The time to clean up the audit log
     */
    public String getTimeToClean() {
        switch (timeToClean) {
            case "DAY":
                return "Daily";
            case "WEEK":
                return "Weekly";
            case "MONTH":
                return "Monthly";
            case "YEAR":
                return "Yearly";
            case "NEVER":
                return "Never";
            default:
                return "Daily";
        }
    }
}

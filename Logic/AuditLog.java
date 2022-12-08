package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class AuditLog {

    private Connection cn;

    public AuditLog(ConnectLogic logic) {
        this.cn = logic.getConnection();
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
     * Remove all audit log entries that are more than 2 weeks old
     */
    public void cleanup() {
        try {
            PreparedStatement ps = cn.prepareStatement(
                    "DELETE FROM AuditLog WHERE time < TIMESTAMPADD(HOUR, -2, SYSDATE())");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

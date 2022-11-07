package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Group {

    private Connection cn;

    public Group(ConnectLogic logic) {
        this.cn = logic.getConnection();
    }

    /**
     * Adds a group to the database
     * 
     * @param name        The name of the group
     * @param description The description of the group
     * @return Returns true if the group was added successfully
     */
    public boolean addGroup(String name, String description) {
        try {
            String query = "INSERT INTO AssetGroup VALUES (?, ?)";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, description);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Gets all the groups from the database
     * 
     * @return An ArrayList of all Groups
     */
    public ArrayList<String[]> getAllGroups() {
        ArrayList<String[]> groups = new ArrayList<String[]>();

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM AssetGroup");

            while (rs.next()) {
                String[] group = new String[2];
                group[0] = rs.getString("Name");
                group[1] = rs.getString("Description");
                groups.add(group);
            }

            return groups;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Gets a group from the database
     * 
     * @param name The name of the group
     * @return An array of the name and description
     */
    public String[] getGroup(String name) {
        try {
            String query = "SELECT * FROM AssetGroup WHERE Name = ?";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            String[] group = new String[2];
            group[0] = rs.getString("Name");
            group[1] = rs.getString("Description");

            return group;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}

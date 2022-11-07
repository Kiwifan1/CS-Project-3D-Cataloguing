package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Set {

    private Connection cn;

    public Set(ConnectLogic logic) {
        this.cn = logic.getConnection();
    }

    /**
     * Adds a set to the database. This method relies upon a group already existing,
     * one is added by calling {@link Group#addGroup(String, String)}
     * 
     * @param name        The name of the set
     * @param groupName   The name of the group the set belongs to
     * @param description The description of the set
     * @return Returns true if the set was added successfully
     */
    public boolean addSet(String name, String groupName, String description) {
        try {
            String query = "INSERT INTO AssetSet VALUES (?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, groupName);
            ps.setString(3, description);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Gets all the sets from the database
     * 
     * @return An ArrayList of all Sets
     */
    public ArrayList<String[]> getAllSets() {
        ArrayList<String[]> sets = new ArrayList<String[]>();

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM AssetSet");

            while (rs.next()) {
                String[] set = new String[2];
                set[0] = rs.getString("Name");
                set[1] = rs.getString("Description");
                sets.add(set);
            }

            return sets;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Gets a set from the database
     * 
     * @param name The name of the set
     * @return An array of the name and description
     */
    public String[] getSet(String name) {
        try {
            String query = "SELECT * FROM AssetSet WHERE Name = ?";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            String[] set = new String[2];
            set[0] = rs.getString("Name");
            set[1] = rs.getString("Description");

            return set;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}

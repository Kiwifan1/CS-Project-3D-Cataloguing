package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class SelectLogic {

    private Connection cn;

    public SelectLogic(ConnectLogic logic) {
        this.cn = logic.getConnection();
    }

    /* ------------------ Attributes ----------------- */

    /**
     * Gets all the attributes from the database
     * 
     * @return An ArrayList of all Attributes
     */
    public ArrayList<String[]> getAllAttributes() {
        ArrayList<String[]> attributes = new ArrayList<String[]>();

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Attribute");

            while (rs.next()) {
                String[] attribute = new String[2];
                attribute[0] = rs.getString("Name");
                attribute[1] = rs.getString("Description");
                attributes.add(attribute);
            }

            return attributes;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Gets an attribute from the database
     * 
     * @param name The name of the attribute
     * @return An array of the name and description
     */
    public String[] getAttribute(String name) {
        try {
            String query = "SELECT * FROM Attribute WHERE Name = ?";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            String[] attribute = new String[2];
            attribute[0] = rs.getString("Name");
            attribute[1] = rs.getString("Description");

            return attribute;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /* -------------------- Groups ------------------- */

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

    /* --------------------- Sets -------------------- */

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

    /* -------------------- Assets ------------------- */

    /**
     * Gets all the assets from the database
     * 
     * @return An ArrayList of all Assets
     */
    public ArrayList<String[]> getAllAssets() {
        ArrayList<String[]> assets = new ArrayList<String[]>();

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT FilePath, AttributeName FROM Asset");

            while (rs.next()) {
                String[] asset = new String[2];
                asset[0] = rs.getString("FilePath");
                asset[1] = rs.getString("AttributeName");
                assets.add(asset);
            }

            return assets;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Gets an asset from the database
     * 
     * @param filePath      The path to the file
     * @param attributeName The name of the attribute
     * @return An array of the file path and attribute name
     */
    public String[] getAsset(String filePath, String attributeName) {
        try {
            String query = "SELECT * FROM Asset WHERE FilePath = ? AND AttributeName = ?";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, filePath);
            ps.setString(2, attributeName);

            ResultSet rs = ps.executeQuery();

            String[] asset = new String[2];
            asset[0] = rs.getString("FilePath");
            asset[1] = rs.getString("AttributeName");

            return asset;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}

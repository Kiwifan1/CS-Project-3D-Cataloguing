package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Attribute {

    private Connection cn;

    public Attribute(ConnectLogic logic) {
        this.cn = logic.getConnection();
    }

    /**
     * Adds an attribute to the database
     * 
     * @param name        The name of the attribute
     * @param description The description of the attribute
     * @return Returns true if the attribute was added successfully
     */
    public boolean addAttribute(String name, String description) {
        try {
            String query = "INSERT INTO Attribute VALUES (?, ?)";
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
     * Gets all the attributes from the database
     * 
     * @return An ArrayList of all Attributes
     */
    public ArrayList<String> getAllAttributes() {
        ArrayList<String> attributes = new ArrayList<String>();

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM Attribute");

            while (rs.next()) {
                attributes.add(rs.getString("name"));
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
     * @return An ArrayList of the name and description
     */
    public ArrayList<String> getAttributes(String name) {
        try {
            String query = "SELECT * FROM Attribute WHERE Name LIKE ?";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, name + "%");

            ResultSet rs = ps.executeQuery();

            ArrayList<String> attributes = new ArrayList<String>();

            while (rs.next()) {
                attributes.add(rs.getString("Name"));
            }

            return attributes;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Removes an attribute from the database
     * 
     * @param name The name of the attribute
     * @return Returns true if the attribute was removed successfully
     */
    public boolean removeAttribute(String name) {
        try {
            String query = "DELETE FROM Attribute WHERE Name = ?";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, name);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Updates an attribute in the database
     * 
     * @param name        The name of the attribute
     * @param description The description of the attribute
     * @return Returns true if the attribute was updated successfully
     */
    public boolean updateAttribute(String name, String description) {
        try {
            String query = "UPDATE Attribute SET Description = ? WHERE Name = ?";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, description);
            ps.setString(2, name);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Removes all attributes from the database
     * 
     * @return Returns true if the attributes were removed successfully
     */
    public boolean removeAllAttributes() {
        try {
            Statement st = cn.createStatement();
            st.executeUpdate("DELETE FROM Attribute");
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}

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
}

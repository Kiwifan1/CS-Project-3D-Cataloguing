package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Publisher {

    private Connection cn;

    public Publisher(ConnectLogic logic) {
        this.cn = logic.getConnection();
    }

    /**
     * Adds a publisher given a name and a source
     * 
     * @param name   the name of the publisher
     * @param source the source the publisher is from
     * @return true if publisher added successfully, false otherwise.
     */
    public boolean addPublisher(String name, String source) {
        try {
            String query = "INSERT INTO Publisher VALUES (?, ?)";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, source);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Removes a publisher given a name
     * 
     * @param name the name of the publisher
     * @return true if publisher removed successfully, false otherwise.
     */
    public boolean removePublisher(String name) {
        try {
            String query = "DELETE FROM Publisher WHERE name = ?";
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
     * Gets all the publishers from the database
     * 
     * @return An ArrayList of all Publishers
     */
    public ArrayList<String> getAllPublishers() {
        ArrayList<String> publishers = new ArrayList<String>();

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Publisher ORDER BY name");

            while (rs.next()) {
                publishers.add(rs.getString("Name"));
            }

            return publishers;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Gets all the publishers that start with the given string
     * 
     * @param name The name of the publisher
     * @return An ArrayList of all Publishers that start with the given string
     */
    public ArrayList<String> getPublishers(String name) {
        ArrayList<String> publishers = new ArrayList<String>();

        try {
            String query = "SELECT * FROM Publisher WHERE Name LIKE ? ORDER BY name";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, name + "%");


            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                publishers.add(rs.getString("Name"));
            }

            return publishers;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}

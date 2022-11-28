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
     * Gets all the publishers from the database
     * 
     * @return An ArrayList of all Publishers
     */
    public ArrayList<String> getAllPublishers() {
        ArrayList<String> publishers = new ArrayList<String>();

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Publisher");

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

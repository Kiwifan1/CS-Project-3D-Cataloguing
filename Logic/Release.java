package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Release {

    private Connection cn;

    public Release(ConnectLogic logic) {
        this.cn = logic.getConnection();
    }

    /**
     * Adds a Release to the database. Must have a publisher already in database:
     * See {@link Publisher#addPublisher(String, String)}
     * 
     * @param id
     * @param name
     * @param publisher
     * @param description
     * @return true if release is successfully added, false otherwise.
     */
    public boolean addRelease(int id, String name, String publisher, String description) {
        try {
            String query = "INSERT INTO AssetRelease VALUES (?, ?, ?, ?)";

            PreparedStatement ps = cn.prepareStatement(query);

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, publisher);
            ps.setString(4, description);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Gets all the publishers from the database.
     * 
     * @return Returns an ArrayList of all the publishers
     */
    public ArrayList<String> getAllPublishers() {
        try {
            String query = "SELECT DISTINCT Publisher FROM AssetRelease";
            PreparedStatement ps = cn.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            ArrayList<String> publishers = new ArrayList<String>();

            while (rs.next()) {
                publishers.add(rs.getString("Publisher"));
            }

            return publishers;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Gets all the releases from the database that match the publisher.
     * 
     * @param pubs The publisher(s) to search for
     * @return Returns an ArrayList of all the releases that match the publisher
     */
    public ArrayList<String> getReleaseFromPub(String[] pubs) {
        try {
            String query = "SELECT name FROM AssetRelease WHERE Publisher = ?";

            for (int i = 1; i < pubs.length; i++) {
                query += " OR Publisher = ?";
            }

            if(pubs.length == 0) {
                query = "SELECT name FROM AssetRelease";
            }

            PreparedStatement ps = cn.prepareStatement(query);

            for (int i = 0; i < pubs.length; i++) {
                ps.setString(i + 1, pubs[i]);
            }

            ResultSet rs = ps.executeQuery();
            ArrayList<String> releases = new ArrayList<String>();

            while (rs.next()) {
                releases.add(rs.getString("Name"));
            }

            return releases;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}

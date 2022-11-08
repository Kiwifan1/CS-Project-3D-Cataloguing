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
     * Adds a release to the database.
     * 
     * @param name    The name of the release
     * @param pubDate The date of the release
     * @param pub     The publisher of the release
     * @param src     The source of the release
     * @return Returns true if the release was added successfully
     */
    public boolean addRelease(String name, String pubDate, String pub, String src) {
        try {
            String query = "INSERT INTO AssetRelease VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, pubDate);
            ps.setString(3, pub);
            ps.setString(4, src);

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
            String query = "SELECT Name FROM AssetRelease WHERE Publisher = ?";

            for (int i = 1; i < pubs.length; i++) {
                query += " OR Publisher = ?";
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

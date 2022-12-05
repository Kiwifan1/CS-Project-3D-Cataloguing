package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class AssetRelease {

    private Connection cn;

    public AssetRelease(ConnectLogic logic) {
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
     * Gets the release from the database given an rid
     * 
     * @param id the rid of the release
     * @return the release
     */
    public Release getRelease(int id) {
        try {
            String query = "SELECT * FROM AssetRelease WHERE id = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Release(rs.getInt("id"), rs.getString("name"), rs.getString("publisher"),
                        rs.getString("description"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Gets all the releases from the database that match the publisher.
     * 
     * @param pubs The publisher(s) to search for
     * @return Returns an ArrayList of all the releases that match the publisher
     */
    public ArrayList<Release> getReleaseFromPub(String[] pubs) {
        try {
            ArrayList<Release> releases = new ArrayList<Release>();
            String query = "SELECT * FROM AssetRelease WHERE publisher = ?";

            if (pubs.length > 1) {
                for (int i = 1; i < pubs.length; i++) {
                    query += " OR publisher = ?";
                }
            } else if (pubs.length == 0) {
                query = "SELECT * FROM AssetRelease";
            }

            PreparedStatement ps = cn.prepareStatement(query);

            for (int i = 0; i < pubs.length; i++) {
                ps.setString(i + 1, pubs[i]);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                releases.add(new Release(rs.getInt("id"), rs.getString("name"), rs.getString("publisher"),
                        rs.getString("description")));
            }
            return releases;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    /**
     * Gets all releases that match the name and publisher(s)
     * 
     * @param name The name of the release
     * @param pubs The publisher(s) to search for
     * @return Returns an ArrayList of all the releases that match the name and
     *         publisher
     */
    public ArrayList<Release> getReleaseFromNameAndPub(String name, String[] pubs) {
        try {
            String query = "SELECT * FROM AssetRelease WHERE name LIKE ? AND Publisher = ?";

            PreparedStatement ps = cn.prepareStatement(query);

            if (pubs.length == 0) {
                query = "SELECT * FROM AssetRelease WHERE name LIKE ?";
                ps = cn.prepareStatement(query);
            } else {
                for (int i = 1; i < pubs.length; i++) {
                    query += " OR Publisher = ?";
                }
                for (int i = 0; i < pubs.length; i++) {
                    ps.setString(i + 2, pubs[i]);
                }
            }

            ps.setString(1, name + "%");

            ResultSet rs = ps.executeQuery();

            ArrayList<Release> releases = new ArrayList<Release>();

            while (rs.next()) {
                releases.add(new Release(rs.getInt("id"), rs.getString("name"), rs.getString("publisher"),
                        rs.getString("description")));
            }

            return releases;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Gets the last release ID from the database
     * 
     * @return The last release ID
     */
    public int getLastRID() {
        try {
            String query = "SELECT MAX(id) FROM AssetRelease";
            PreparedStatement ps = cn.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            rs.next();

            return rs.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }
}

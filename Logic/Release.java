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
     * Gets all the releases from the database that match the publisher.
     * 
     * @param pubs The publisher(s) to search for
     * @return Returns an ArrayList of all the releases that match the publisher
     */
    public ArrayList<String[]> getReleaseFromPub(String[] pubs) {
        try {
            String query = "SELECT name, id FROM AssetRelease WHERE Publisher = ?";

            if (pubs.length == 0) {
                query = "SELECT name, id FROM AssetRelease";
            } else {
                for (int i = 1; i < pubs.length; i++) {
                    query += " OR Publisher = ?";
                }
            }

            PreparedStatement ps = cn.prepareStatement(query);

            for (int i = 0; i < pubs.length; i++) {
                ps.setString(i + 1, pubs[i]);
            }

            ResultSet rs = ps.executeQuery();
            ArrayList<String[]> releases = new ArrayList<String[]>();

            while (rs.next()) {
                String[] release = new String[2];
                release[0] = rs.getString("name");
                release[1] = String.valueOf(rs.getInt("id"));
                releases.add(release);
            }

            return releases;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Gets all the releases from the database that start with the given name.
     * 
     * @param name The name to search for
     * @param pubs The publisher(s) to search within
     * @return Returns an ArrayList of all the releases that start with the given
     *         name.
     */
    public ArrayList<String[]> getReleaseFromNameAndPub(String name, String[] pubs) {
        try {
            String query = "SELECT name, id FROM AssetRelease WHERE name LIKE ? AND Publisher = ?";

            PreparedStatement ps = cn.prepareStatement(query);

            if (pubs.length == 0) {
                query = "SELECT name, id FROM AssetRelease WHERE name LIKE ?";
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
            ArrayList<String[]> releases = new ArrayList<String[]>();

            while (rs.next()) {
                String[] release = new String[2];
                release[0] = rs.getString("name");
                release[1] = String.valueOf(rs.getInt("id"));
                releases.add(release);
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

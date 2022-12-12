package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Analytics {
    private Connection cn;

    public Analytics(ConnectLogic logic) {
        this.cn = logic.getConnection();
    }

    /**
     * Gets the average file size of all the files in the database (in mb)
     * 
     * @return The average file size
     */
    public double getAverageFileSize() {

        try {
            String query = "SELECT DISTINCT filepath FROM Asset";
            double fileSize = 0;
            int fileCount = 0;
            PreparedStatement ps = cn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                File file = new File(rs.getString("filepath"));
                fileSize += file.length() / (1024 * 1024);
                fileCount++;
            }
            if (fileCount == 0) {
                return 0;
            }
            double average = (fileSize / (double) fileCount);
            average = Math.round(average * 100.0) / 100.0;
            return average;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Gets the total file size of all the files in the database (in mb)
     * 
     * @return The total file size
     */
    public double getTotalFileSize() {
        try {
            String query = "SELECT DISTINCT filepath FROM Asset";
            double fileSize = 0;
            PreparedStatement ps = cn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                File file = new File(rs.getString("filepath"));
                fileSize += file.length() / (1024 * 1024);
            }
            double total = Math.round(fileSize * 100.0) / 100.0;
            return total;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Gets the most popular publishers
     * 
     * @param limit The number of publishers to return
     * @return The most popular publishers
     */
    public ArrayList<String> getMostPopPubs(int limit) {
        try {
            String query = "SELECT p.name FROM Publisher p JOIN AssetRelease ar ON (p.name = ar.publisher) JOIN Asset a ON (ar.id = a.rid) GROUP BY p.name ORDER BY COUNT(*) DESC LIMIT ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ArrayList<String> publishers = new ArrayList<String>();

            ps.setInt(1, limit);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                publishers.add(rs.getString("name"));
            }
            return publishers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the most popular attributes
     * 
     * @param limit
     * @return
     */
    public ArrayList<String> getMostPopAttrs(int limit) {
        try {
            String query = "SELECT attribute, COUNT(*) FROM Asset GROUP BY attribute ORDER BY COUNT(*) DESC LIMIT ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ArrayList<String> attributes = new ArrayList<String>();

            ps.setInt(1, limit);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                attributes.add(rs.getString("attribute"));
            }
            return attributes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Returns a map with whether the user has been active in the last 30 days
     * 
     * @return A map with the user and whether they have been active in the last 30
     *         days
     */
    public HashMap<String, Boolean> getUserRecentActivity() {
        try {
            String query = "SELECT username, CASE WHEN last_login > TIMESTAMPADD(MONTH, -1, SYSDATE()) THEN 'active' ELSE 'inactive' END AS 'status' FROM AppUser";
            PreparedStatement ps = cn.prepareStatement(query);
            HashMap<String, Boolean> users = new HashMap<String, Boolean>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.put(rs.getString("username"), rs.getString("status").equals("active"));
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets all reeleases that have more than 1 asset and the number of assets that
     * they have
     * 
     * @return A list of releases and the number of assets that they have
     */
    public ArrayList<String> getNumAssetsRelease() {
        try {
            String query = "SELECT id, name, COUNT(*) OVER (PARTITION BY id) AS 'numAssets' FROM AssetRelease JOIN Asset ON (AssetRelease.id = Asset.rid) GROUP BY id, name HAVING COUNT(*) > 1 ORDER BY COUNT(*) DESC";
            PreparedStatement ps = cn.prepareStatement(query);
            ArrayList<String> releases = new ArrayList<String>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                releases.add(rs.getString("release"));
            }

            return releases;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the assets that have the most attributes
     * 
     * @return The assets that have the most attributes
     */
    public ArrayList<String> getAssetsMostAttrs() {
        try {
            // make a query that finds the file paths and names of attributes of assets that
            // have the most attributes
            String query = "SELECT a1.filepath FROM Asset a1 GROUP BY a1.filepath HAVING COUNT(*) = (SELECT MAX(COUNT(*)) FROM Asset a2 GROUP BY a2.filepath) a3";

            PreparedStatement ps = cn.prepareStatement(query);
            ArrayList<String> assets = new ArrayList<String>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                assets.add(rs.getString("filepath"));
            }

            return assets;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

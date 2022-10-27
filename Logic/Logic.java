package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Logic {

    Connection cn;
    int fileID = 0;
    int releaseID = 0;
    int assetID = 0;

    public Logic() {
        makeConnection();
    }

    /**
     * Creates a connection to the database
     */
    public void makeConnection() {
        try {
            FileInputStream fis = new FileInputStream("./Logic/config.properties");
            Properties prop = new Properties();
            prop.load(fis);

            String url = prop.getProperty("url");
            String user = prop.getProperty("username");
            String password = prop.getProperty("password");

            this.cn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Adds an attribute to the database
     * 
     * @param id   The id of the attribute
     * @param name The name of the attribute
     * @return Returns true if the attribute was added successfully
     */
    public boolean addAttribute(int id, String name) {
        try {
            String query = "INSERT INTO Attribute VALUES (?, ?)";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setInt(1, id);
            ps.setString(2, name);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Adds a group to the database
     * 
     * @param id   The id of the group
     * @param name The name of the group
     * @return Returns true if the group was added successfully
     */
    public boolean addGroup(int id, String name) {
        try {
            String query = "INSERT INTO Groups VALUES (?, ?)";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setInt(1, id);
            ps.setString(2, name);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Adds a set to the database. This method relies upon a group already existing,
     * one is added by calling {@link #addGroup(int, String)}
     * 
     * @param id   The id of the set
     * @param name The name of the set
     * @param gid  The id of the group the set belongs to
     * @return Returns true if the set was added successfully
     */
    public boolean addSet(int id, String name, int gid) {
        try {
            String query = "INSERT INTO Set VALUES (?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, gid);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Adds a release to the database.
     * 
     * @param id   The id of the release
     * @param name The name of the release
     * @param date The date of the release
     * @param pub  The publisher of the release
     * @param src  The source of the release
     * @return Returns true if the release was added successfully
     */
    public boolean addRelease(int id, String name, String date, String pub, String src) {
        try {
            String query = "INSERT INTO Release VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, date);
            ps.setString(4, pub);
            ps.setString(5, src);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Adds a file to the database. This method relies that the release
     * already exists in the database, one is added by calling
     * {@link #addRelease(int, String, String, String, String)}
     * 
     * @param id           The id of the file
     * @param rid          The release id of the file
     * @param path         The path of the file
     * @param image        The image of the file
     * @param downloadDate The download date of the file
     * @param editDate     The edit date of the file
     * @return Returns true if the file was added successfully
     */
    public boolean addFile(int id, int rid, String path, byte[] image, String downloadDate, String editDate) {
        try {
            String query = "INSERT INTO AssetFile VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setInt(1, id);
            ps.setInt(2, rid);
            ps.setString(3, path);
            ps.setBytes(4, image);
            ps.setString(5, downloadDate);
            ps.setString(6, editDate);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Adds an asset to the database. This method relies that the file was already
     * added with
     * {@link #addFile(int, int, String, byte[], String, String)} and it has an
     * attribute from {@link #addAttribute(int, String)}
     * 
     * @param fid   The file ID of the asset
     * @param aid   The asset ID of the asset
     * @param name  The name of the asset
     * @param sid   The set ID of the asset
     * @param scale The scale of the asset
     * @return Returns true if the asset was added successfully
     */
    public boolean addAsset(int fid, int aid, String name, int sid, String scale) {
        try {
            String query = "INSERT INTO Asset VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setInt(1, fid);
            ps.setInt(2, aid);
            ps.setString(3, name);
            ps.setInt(4, sid);
            ps.setString(5, scale);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

}
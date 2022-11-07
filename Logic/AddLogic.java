package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class AddLogic {

    private Connection cn;

    public AddLogic(ConnectLogic logic) {
        this.cn = logic.getConnection();
    }

    /* ------------------ Adding to Database ------------------ */

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
     * Adds a group to the database
     * 
     * @param name        The name of the group
     * @param description The description of the group
     * @return Returns true if the group was added successfully
     */
    public boolean addGroup(String name, String description) {
        try {
            String query = "INSERT INTO AssetGroup VALUES (?, ?)";
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
     * Adds a set to the database. This method relies upon a group already existing,
     * one is added by calling {@link #addGroup(int, String)}
     * 
     * @param name        The name of the set
     * @param groupName   The name of the group the set belongs to
     * @param description The description of the set
     * @return Returns true if the set was added successfully
     */
    public boolean addSet(String name, String groupName, String description) {
        try {
            String query = "INSERT INTO AssetSet VALUES (?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, groupName);
            ps.setString(3, description);

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
     * Adds a file to the database. This method relies that the release
     * already exists in the database, one is added by calling
     * {@link #addRelease(int, String, String, String, String)}
     * 
     * @param path         The path of the file
     * @param releaseName  The name of the release the file belongs to
     * @param imagePath    The path of the image of the file
     * @param downloadDate The download date of the file
     * @param editDate     The edit date of the file
     * @return Returns true if the file was added successfully
     */
    public boolean addFile(String path, String releaseName, String imagePath, String downloadDate, String editDate) {
        try {
            String query = "INSERT INTO AssetFile VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, path);
            ps.setString(2, releaseName);
            ps.setString(3, imagePath);
            ps.setString(4, downloadDate);
            ps.setString(5, editDate);

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
     * @param filePath The path of the file
     * @param attrName The name of the attribute
     * @param name     The name of the asset
     * @param setName  The name of the set the asset belongs to
     * @param scale    The scale of the asset
     * @return Returns true if the asset was added successfully
     */
    public boolean addAsset(String filePath, String attrName, String name, String setName, String scale) {
        try {
            String query = "INSERT INTO Asset VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, filePath);
            ps.setString(2, attrName);
            ps.setString(3, name);
            ps.setString(4, setName);
            ps.setString(5, scale);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

}

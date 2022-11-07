package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Asset {

    private Connection cn;

    public Asset(ConnectLogic logic) {
        this.cn = logic.getConnection();
    }

    /**
     * Adds an asset to the database. This method relies that the file was already
     * added with
     * {@link File#addFile(int, int, String, byte[], String, String)} and it has an
     * attribute from {@link Attribute#addAttribute(int, String)}
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

    /**
     * Gets all the assets from the database
     * 
     * @return An ArrayList of all Assets
     */
    public ArrayList<String[]> getAllAssets() {
        ArrayList<String[]> assets = new ArrayList<String[]>();

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT FilePath, AttributeName FROM Asset");

            while (rs.next()) {
                String[] asset = new String[2];
                asset[0] = rs.getString("FilePath");
                asset[1] = rs.getString("AttributeName");
                assets.add(asset);
            }

            return assets;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Gets an asset from the database
     * 
     * @param filePath      The path to the file
     * @param attributeName The name of the attribute
     * @return An array of the file path and attribute name
     */
    public String[] getAsset(String filePath, String attributeName) {
        try {
            String query = "SELECT * FROM Asset WHERE FilePath = ? AND AttributeName = ?";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, filePath);
            ps.setString(2, attributeName);

            ResultSet rs = ps.executeQuery();

            String[] asset = new String[2];
            asset[0] = rs.getString("FilePath");
            asset[1] = rs.getString("AttributeName");

            return asset;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}

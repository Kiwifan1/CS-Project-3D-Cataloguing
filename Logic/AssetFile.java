package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class AssetFile {

    private Connection cn;

    public AssetFile(ConnectLogic logic) {
        this.cn = logic.getConnection();
    }

    /**
     * Adds a file to the database. This method relies that the release
     * already exists in the database, one is added by calling
     * {@link Release#addRelease(int, String, String, String, String)}
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
}

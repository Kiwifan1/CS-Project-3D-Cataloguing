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
}

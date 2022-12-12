package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class AppUser {
    Connection cn;

    public AppUser(ConnectLogic logic) {
        this.cn = logic.getConnection();
    }

    /**
     * Adds a user to the database
     * 
     * @param user - username
     * @param pass - password
     * @return - true if user was added, false if user was not added
     */
    public boolean addUser(String user, String pass) {
        try {
            PreparedStatement ps = cn.prepareStatement("INSERT INTO AppUser VALUES (?, MD5(?), CURRENT_TIMESTAMP)");
            ps.setString(1, user);
            ps.setString(2, pass);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Checks if a user exists in the database
     * 
     * @param user - username
     * @return - true if user exists, false if user does not exist
     */
    public boolean userExists(String user) {
        try {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM AppUser WHERE username = ?");
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }
}

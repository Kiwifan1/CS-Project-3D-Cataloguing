package Logic;

import java.sql.*;
import java.util.*;

import com.mysql.cj.xdevapi.Result;

import java.io.*;

public class Login {

    private Connection cn;
    private String currUser;

    public Login(ConnectLogic logic) {
        this.cn = logic.getConnection();
    }

    /**
     * Adds a user to the database
     * 
     * @param username The username of the user
     * @param password The password of the user
     * @return True if the user was added, false otherwise
     */
    public boolean addUser(String username, String password) {
        try {
            String query = "INSERT INTO AppUser VALUES (MD5(?), MD5(?))";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Checks if the user exists in the database, and if the password is correct
     * 
     * @param username The username of the user
     * @param password The password of the user
     * @return True if the user exists and the password is correct, false otherwise
     */
    public boolean login(String username, String password) {
        try {
            String query = "SELECT * FROM AppUser WHERE Username = MD5(?) AND Pass = MD5(?)";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                currUser = username;
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Removes all users from the database
     * 
     * @return True if the users were removed, false otherwise
     */
    public boolean removeAllUsers() {
        try {
            String query = "DELETE FROM AppUser";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Checks if a user is an admin
     * @param username username of potential admin
     * @return true if admin, false otherwise
     */
    public boolean isAdmin(String username) {
        try {
            String query = "SELECT username FROM Admin WHERE username = ?";

            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}

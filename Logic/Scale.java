package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Scale {

    private Connection cn;

    public Scale(ConnectLogic logic) {
        this.cn = logic.getConnection();
    }

    /**
     * Adds a scale to the database
     * 
     * @param name The name of the scale
     * @return Returns true if the scale was added successfully
     */
    public boolean addScale(String name) {
        try {
            String query = "INSERT INTO Scale VALUES (?)";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, name);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Gets all the scales from the database
     * 
     * @return An ArrayList of all Scales
     */
    public ArrayList<String> getAllScales() {
        ArrayList<String> scales = new ArrayList<String>();

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM Scale");

            while (rs.next()) {
                scales.add(rs.getString("name"));
            }

            return scales;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}

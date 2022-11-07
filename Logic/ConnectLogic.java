package Logic;


import java.sql.*;
import java.util.*;
import java.io.*;

public class ConnectLogic {

    private Connection cn;

    public ConnectLogic() {
        makeConnection();
    }

    public Connection getConnection() {
        return this.cn;
    }

    public void setConnection(Connection cn) {
        this.cn = cn;
    }

    /**
     * Creates a connection to the database
     */
    public void makeConnection() {
        try {
            // TODO: FIX ABSOLUTE PATH
            File file = new File("C:/Users/joshu/OneDrive/Apps/Documents/GitHub/CS-Project-3D-Cataloguing/Logic/config.properties");
            FileInputStream fis = new FileInputStream(file);
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
}
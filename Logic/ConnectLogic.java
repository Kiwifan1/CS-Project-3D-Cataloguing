package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class ConnectLogic {

    private Connection cn;
    private boolean connected;

    public ConnectLogic() {
        connected = makeConnection();
    }

    public Connection getConnection() {
        return this.cn;
    }

    public void setConnection(Connection cn) {
        this.cn = cn;
    }

    public boolean isConnected() {
        return connected;
    }

    /**
     * Creates a connection to the database
     */
    public boolean makeConnection() {
        try {
            // TODO: FIX ABSOLUTE PATH
            FileInputStream fis = new FileInputStream("config.properties");
            Properties prop = new Properties();
            prop.load(fis);

            String url = prop.getProperty("url");
            String user = prop.getProperty("username");
            String password = prop.getProperty("password");

            this.cn = DriverManager.getConnection(url, user, password);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
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
            FileInputStream fis = new FileInputStream("./config.properties");
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
package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Logic {

    Connection cn;
    int fileID = 0;
    int releaseID = 0;
    int assetID = 0;

    public Logic() {
        makeConnection();
    }

    /**
     * Creates a connection to the database
     */
    public void makeConnection() {
        try {
            FileInputStream fis = new FileInputStream("./Logic/config.properties");
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
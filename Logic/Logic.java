package Logic;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.io.*;

class Logic {
    public Connection makeConnection() {
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            Properties prop = new Properties();
            prop.load(fis);

            String url = prop.getProperty("url");
            String user = prop.getProperty("username");
            String password = prop.getProperty("password");
            Connection cn = DriverManager.getConnection(url, user, password);
            return cn;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Adds a new Class to the Database
     * 
     * @param cn   Connection to the Database
     * @param name Name of the Class
     * @return true if the Class was added successfully, false otherwise
     */
    public boolean addClass(Connection cn, String name) {
        try {
            String sql = "INSERT INTO Class Values(?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, name);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Adds a new Type to the Database
     * 
     * @param cn   Connection to the Database
     * @param name Name of the Type
     * @return true if the Type was added successfully, false otherwise
     */
    public boolean addType(Connection cn, String name) {
        try {
            String sql = "INSERT INTO Type Values(?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, name);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Adds a new Entity to the Database
     * 
     * @param cn   Connection to the Database
     * @param name Name of the Entity
     * @return true if the Entity was added successfully, false otherwise
     */
    public boolean addEntity(Connection cn, String name) {
        try {
            String sql = "INSERT INTO Entity Values(?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, name);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Adds a new Release to the Database
     * 
     * @param cn        Connection to the Database
     * @param publisher Publisher of the Release
     * @param date      Date of the Release
     * @return true if the Release was added successfully, false otherwise
     */
    public boolean addRelease(Connection cn, String publisher, String source) {
        try {
            String sql = "INSERT INTO Release(Publisher, Source) Values(?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, publisher);
            ps.setString(2, source);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Adds a new Release to the Database
     * 
     * @param cn           Connection to the Database
     * @param filePath     Path of the file to be added
     * @param releaseID    ID of the Release
     * @param image        Image of the file (held as a byte[])
     * @param downloadDate Date of the download (DateTime format)
     * @param editDate     Date of the last edit (DateTime format)
     * @return true if the Release was added successfully, false otherwise
     */
    public boolean addFile(Connection cn, String filePath, int releaseID, byte[] image, String downloadDate,
            String editDate) {
        try {
            String sql = "INSERT INTO File(FilePath, ReleaseID, Image, DownloadDate, EditDate) Values(?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, filePath);
            ps.setInt(2, releaseID);
            ps.setBytes(3, image);
            ps.setString(4, downloadDate);
            ps.setString(5, editDate);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Adds a new Asset to the Database
     * 
     * @param cn         Connection to the Database
     * @param fileID     ID of the File
     * @param releaseID  ID of the Release
     * @param className  Name of the Class
     * @param typeName   Name of the Type
     * @param entityName Name of the Entity
     * @return true if the Asset was added successfully, false otherwise
     */
    public boolean addAsset(Connection cn, int fileID, int releaseID, String className, String typeName,
            String entityName) {
        try {
            String sql = "INSERT INTO Asset(FileID, ReleaseID, ClassName, TypeName, EntityName) Values(?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, fileID);
            ps.setInt(2, releaseID);
            ps.setString(3, className);
            ps.setString(4, typeName);
            ps.setString(5, entityName);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static void main(String[] args) {

    }
}
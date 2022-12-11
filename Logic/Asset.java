package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Asset {

    private Connection cn;
    private AssetRelease releaseLogic;

    public Asset(ConnectLogic logic) {
        this.cn = logic.getConnection();
        this.releaseLogic = new AssetRelease(logic);
    }

    /**
     * Adds an Asset to the SQL Database, there must be a release with the rid key
     * in the database.
     * One can be added with
     * {@link AssetRelease#addRelease(String, String, String, String)}
     * 
     * @param filePath    file path of the asset
     * @param attributes  attributes of the asset
     * @param username    username of person who added asset
     * @param name        name of asset
     * @param rid         release id of the asset
     * @param scale       scale of the asset
     * @param description description of the asset (optional)
     * @return true if asset successfully added, false otherwise
     */
    public boolean addAsset(String filePath, String[] attributes, String username, String name, int rid, String scale,
            String description) {
        try {
            String query = "INSERT INTO Asset VALUES (?, ?, MD5(?), ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(query);

            for (int i = 0; i < attributes.length; i++) {
                ps.setString(1, filePath);
                ps.setString(2, attributes[i]);
                ps.setString(3, username);
                ps.setString(4, name);
                ps.setInt(5, rid);
                ps.setString(6, scale);
                ps.setString(7, description);

                ps.executeUpdate();
            }

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Removes an asset given a filepath
     * 
     * @param filePath filepath to be removed
     * @return true if asset successfully removed, false otherwise
     */
    public boolean removeAsset(String filePath) {
        try {
            String query = "DELETE FROM Asset WHERE FilePath = ?";

            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, filePath);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Edits an asset given a filepath
     * 
     * @param filePath      filepath of asset to be edited
     * @param oldAttributes old attributes of asset
     * @param newAttributes new attributes of asset
     * @param username      username of person who edited asset
     * @param name          name of asset to be edited
     * @param rid           release id of asset to be edited
     * @param scale         scale of asset to be edited
     * @param description   description of asset to be edited
     * @return true if asset successfully edited, false otherwise
     */
    public boolean editAsset(String filePath, String[] oldAttributes, String[] newAttributes, String username,
            String name, int rid, String scale,
            String description) {
        try {

            // update all old attributes with new attributes, if there are more new
            // attributes than old attributes, add the extra new attributes, if there are
            // more old attributes than new attributes, remove the extra old attributes

            ArrayList<String> oldAttrList = new ArrayList<String>(Arrays.asList(oldAttributes));
            ArrayList<String> newAttrList = new ArrayList<String>(Arrays.asList(newAttributes));

            String query = "INSERT INTO Asset VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = cn.prepareStatement(query);

            ps = cn.prepareStatement(query);
            ps.setString(1, filePath);
            ps.setString(3, username);
            ps.setString(4, name);
            ps.setInt(5, rid);
            ps.setString(6, scale);
            ps.setString(7, description);

            // add all new attributes that are not in the old attributes
            for (int i = 0; i < newAttributes.length; i++) {
                if (!oldAttrList.contains(newAttributes[i])) {
                    ps.setString(2, newAttributes[i]);
                    ps.executeUpdate();
                }
            }
            query = "DELETE FROM Asset WHERE FilePath = ? AND Attribute = ?";

            ps = cn.prepareStatement(query);

            ps.setString(1, filePath);

            // delete all old attributes that are not in the new attributes
            for (int i = 0; i < oldAttributes.length; i++) {
                if (!newAttrList.contains(oldAttributes[i])) {
                    ps.setString(2, oldAttributes[i]);
                    ps.executeUpdate();
                }
            }

            ps.close();

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Gets all attributes of an asset given a filepath
     * 
     * @param filePath filepath of asset
     * @return array of attributes
     */
    public ArrayList<String> getAttributes(String filePath) {
        try {
            String query = "SELECT attribute FROM Asset WHERE filepath = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, filePath);

            ResultSet rs = ps.executeQuery();
            ArrayList<String> attributes = new ArrayList<String>();

            while (rs.next()) {
                attributes.add(rs.getString("attribute"));
            }

            ps.close();

            return attributes;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Gets all scales from the database from a given publisher
     * 
     * @param publishers The publisher(s) to search for
     * @return Returns an ArrayList of all the scales that match the publisher
     */
    public ArrayList<String> getScalesFromPub(String[] publishers) {
        try {
            String query = "SELECT DISTINCT scale FROM Asset WHERE rid IN (SELECT id FROM AssetRelease WHERE publisher = ?)";
            if (publishers.length == 0) {
                query = "SELECT DISTINCT scale FROM Asset";
            } else {
                for (int i = 1; i < publishers.length; i++) {
                    query += " OR rid IN (SELECT id FROM AssetRelease WHERE publisher = ?)";
                }
            }
            PreparedStatement ps = cn.prepareStatement(query);
            for (int i = 0; i < publishers.length; i++) {
                ps.setString(i + 1, publishers[i]);
            }

            ResultSet rs = ps.executeQuery();
            ArrayList<String> scales = new ArrayList<String>();
            while (rs.next()) {
                scales.add(rs.getString("scale"));
            }

            rs.close();
            ps.close();

            return scales;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    /**
     * Gets scales of assets from given release ids
     * 
     * @param rids rids of the releases
     * @return
     */
    public ArrayList<String> getScalesFromRelease(int[] rids) {
        try {
            String query = "SELECT DISTINCT Scale FROM Asset WHERE rid = ?";
            if (rids.length == 0) {
                query = "SELECT DISTINCT Scale FROM Asset";
            } else {
                for (int i = 1; i < rids.length; i++) {
                    query += " OR rid = ?";
                }
            }

            PreparedStatement ps = cn.prepareStatement(query);

            for (int i = 0; i < rids.length; i++) {
                ps.setInt(i + 1, rids[i]);
            }

            ResultSet rs = ps.executeQuery();
            ArrayList<String> scales = new ArrayList<String>();

            while (rs.next()) {
                scales.add(rs.getString("Scale"));
            }

            rs.close();
            ps.close();

            return scales;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Gets all assets from the database with a name containing the given string
     * 
     * @param name name to search for
     * @return ArrayList of all assets with a name containing the given string
     */
    ArrayList<Entity> getAssetFromName(String name) {
        try {
            String query = "SELECT * FROM Asset WHERE name LIKE ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, name + "%");

            ResultSet rs = ps.executeQuery();
            ArrayList<Entity> assets = new ArrayList<Entity>();
            while (rs.next()) {
                Release release = releaseLogic.getRelease(rs.getInt("rid"));
                String publisher = release.getPublisher();
                Entity entity = new Entity(rs.getString("filepath"), rs.getString("attribute"),
                        rs.getString("username"),
                        rs.getString("name"), rs.getString("scale"), rs.getString("description"), publisher, release);
                assets.add(entity);
            }
            return assets;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<Entity> getAssets(String[] publishers, int[] releases, String[] scales, String[] attributes) {
        return getAssets(publishers, releases, scales, attributes, "");
    }

    /**
     * Gets all the assets that match the given attributes
     * 
     * @param publishers publishers to search for
     * @param releases   releases to search for
     * @param scales     scales to search for
     * @param attributes attribute to search for
     * @param search     search string to search for
     * @return ArrayList of assets that match the given attributes
     */
    public ArrayList<Entity> getAssets(String[] publishers, int[] releases, String[] scales, String[] attributes,
            String search) {

        try {
            ArrayList<Entity> assets = new ArrayList<Entity>();
            Entity entity;
            boolean noPublisher = publishers.length == 0;
            boolean noRelease = releases.length == 0;
            boolean noScale = scales.length == 0;
            boolean noAttribute = attributes.length == 0;
            String query = "SELECT * FROM Asset WHERE";

            if (noPublisher && noRelease && noScale && noAttribute && search.equals("")) {
                query = "SELECT * FROM Asset";
            }

            // add publisher to query
            if (!noPublisher) {
                for (int i = 0; i < publishers.length; i++) {
                    if (i == 0) {
                        query += " rid IN (SELECT id FROM AssetRelease WHERE publisher = ?)";
                    } else {
                        query += " OR rid IN (SELECT id FROM AssetRelease WHERE publisher = ?)";
                    }
                }
            }

            // add release to query
            if (!noRelease) {
                if (!noPublisher) {
                    query += " AND";
                }
                for (int i = 0; i < releases.length; i++) {
                    if (i == 0) {
                        query += " rid = ?";
                    } else {
                        query += " OR rid = ?";
                    }
                }
            }

            // add scale to query
            if (!noScale) {
                if (!noPublisher || !noRelease) {
                    query += " AND";
                }
                for (int i = 0; i < scales.length; i++) {
                    if (i == 0) {
                        query += " scale = ?";
                    } else {
                        query += " OR scale = ?";
                    }
                }
            }

            // add attribute to query
            if (!noAttribute) {
                if (!noPublisher || !noRelease || !noScale) {
                    query += " AND";
                }
                for (int i = 0; i < attributes.length; i++) {
                    // check if filepath has all the given attributes
                    if (i == 0) {
                        query += " filepath IN (SELECT filepath FROM Asset WHERE attribute = ?)";
                    } else {
                        query += " AND filepath IN (SELECT filepath FROM Asset WHERE attribute = ?)";
                    }
                }
            }

            // add search to query
            if (!search.equals("")) {
                boolean first = noPublisher && noRelease && noScale && noAttribute;
                if (!first) { // if there are already attributes in the query
                    query += " AND";
                }
                query += " name LIKE ?";
            }

            PreparedStatement ps = cn.prepareStatement(query);

            // set all query parameters
            int index = 1;
            if (!noPublisher) {
                for (int i = 0; i < publishers.length; i++) {
                    ps.setString(index, publishers[i]);
                    index++;
                }
            }

            if (!noRelease) {
                for (int i = 0; i < releases.length; i++) {
                    ps.setInt(index, releases[i]);
                    index++;
                }
            }

            if (!noScale) {
                for (int i = 0; i < scales.length; i++) {
                    ps.setString(index, scales[i]);
                    index++;
                }
            }

            if (!noAttribute) {
                for (int i = 0; i < attributes.length; i++) {
                    ps.setString(index, attributes[i]);
                    index++;
                }
            }

            if (!search.equals("")) {
                ps.setString(index, "%" + search + "%");
            }

            ResultSet rs = ps.executeQuery();

            // add all assets to arraylist
            while (rs.next()) {
                String filepath = rs.getString("filepath");
                String attr = rs.getString("attribute");
                String username = rs.getString("username");
                String name = rs.getString("name");
                int rid = rs.getInt("rid");
                String scale = rs.getString("scale");
                String description = rs.getString("description");

                // make a release object
                Release release = releaseLogic.getRelease(rid);
                String publisher = release.getPublisher();

                entity = new Entity(filepath, attr, username, name, scale, description, publisher, release);

                // add entity to assets, if it is already in the list, add the attribute to it
                if (!assets.contains(entity)) {
                    assets.add(entity);
                }
            }

            // set the attributes of the entities

            for (Entity e : assets) {
                e.setAttributes(getAttributes(e.getFilePath()));
            }

            return assets;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}

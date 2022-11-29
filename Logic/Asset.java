package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Asset {

    private Connection cn;

    public Asset(ConnectLogic logic) {
        this.cn = logic.getConnection();
    }

    /**
     * Adds an Asset to the SQL Database, there must be a release with the rid key
     * in the database.
     * One can be added with
     * {@link Release#addRelease(String, String, String, String)}
     * 
     * @param filePath    file path of the asset
     * @param attribute   attribute of the asset
     * @param username    username of person who added asset
     * @param name        name of asset
     * @param rid         release id of the asset
     * @param scale       scale of the asset
     * @param description description of the asset (optional)
     * @return true if asset successfully added, false otherwise
     */
    public boolean addAsset(String filePath, String attribute, String username, String name, int rid, String scale,
            String description) {
        try {
            String query = "INSERT INTO Asset VALUES (?, ?, MD5(?), ?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, filePath);
            ps.setString(2, attribute);
            ps.setString(3, username);
            ps.setString(4, name);
            ps.setInt(5, rid);
            ps.setString(6, scale);
            ps.setString(7, description);

            ps.executeUpdate();

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
            String query = "DELETE FROM Asset WHERE filepath = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, filePath);

            ps.executeQuery();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
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
            boolean noRid = false;
            if (rids.length == 0) {
                query = "SELECT DISTINCT Scale FROM Asset";
                noRid = true;
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

            while (!noRid && rs.next()) {
                scales.add(rs.getString("Scale"));
            }

            return scales;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Gets all scales of all attributes
     * 
     * @return an arraylist<String> of all attributes
     */
    public ArrayList<String> getAllScales() {
        try {
            String scale;
            ArrayList<String> scales = new ArrayList<>();
            String query = "SELECT DISTINCT scale FROM Asset";
            PreparedStatement ps = cn.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                scale = rs.getString("scale");
                scales.add(scale);
            }

            return scales;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<Entity> getAssets(String[] publishers, int[] releases, String[] scales, String[] attribute) {

        try {
            ArrayList<Entity> assets = new ArrayList<Entity>();
            Entity entity;
            boolean noPublisher = publishers.length == 0;
            boolean noRelease = releases.length == 0;
            boolean noScale = scales.length == 0;
            boolean noAttribute = attribute.length == 0;
            String query = "SELECT * FROM Asset WHERE";

            if (noPublisher && noRelease && noScale && noAttribute) {
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
                for (int i = 0; i < attribute.length; i++) {
                    if (i == 0) {
                        query += " attribute = ?";
                    } else {
                        query += " AND attribute = ?";
                    }
                }
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
                for (int i = 0; i < attribute.length; i++) {
                    ps.setString(index, attribute[i]);
                    index++;
                }
            }

            ResultSet rs = ps.executeQuery();

            // add all assets to arraylist
            while (rs.next()) {
                entity = new Entity();
                entity.setFilePath(rs.getString("filepath"));
                entity.setRid(rs.getInt("rid"));
                entity.setScale(rs.getString("scale"));
                entity.setAttribute(rs.getString("attribute"));
                entity.setDescription(rs.getString("description"));
                entity.setName(rs.getString("name"));
                entity.setUsername(rs.getString("username"));
                assets.add(entity);
            }

            // remove duplicate file paths
            for (int i = 0; i < assets.size(); i++) {
                for (int j = i + 1; j < assets.size(); j++) {
                    if (assets.get(i).getFilePath().equals(assets.get(j).getFilePath())) {
                        assets.remove(j);
                        j--;
                    }
                }
            }

            return assets;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}

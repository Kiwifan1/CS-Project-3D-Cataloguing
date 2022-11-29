package Logic;

// Simple Entity class to hold asset information for the UI
public class Entity {
    private String filePath;
    private String attribute;
    private String username;
    private String name;
    private int rid;
    private String scale;
    private String description;

    public Entity() {
        filePath = "";
        attribute = "";
        username = "";
        name = "";
        rid = 0;
        scale = "";
        description = "";
    }

    public Entity(String filePath, String attribute, String username, String name, int rid, String scale,
            String description) {
        this.filePath = filePath;
        this.attribute = attribute;
        this.username = username;
        this.name = name;
        this.rid = rid;
        this.scale = scale;
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getRid() {
        return rid;
    }

    public String getScale() {
        return scale;
    }

    public String getDescription() {
        return description;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

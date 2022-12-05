package Logic;

import java.util.ArrayList;

// Simple Entity class to hold asset information for the UI
public class Entity {
    private String filePath;
    private ArrayList<String> attributes;
    private String username;
    private String name;
    private String publisher;
    private String release;
    private int rid;
    private String scale;
    private String description;

    public Entity() {
        filePath = "";
        attributes = new ArrayList<String>();
        username = "";
        name = "";
        rid = 0;
        scale = "";
        description = "";
        publisher = "";
        release = "";
    }

    public Entity(String filePath, String attribute, String username, String name, int rid, String scale,
            String description) {
        attributes = new ArrayList<String>();
        attributes.add(attribute);

        this.filePath = filePath;
        this.username = username;
        this.name = name;
        this.rid = rid;
        this.scale = scale;
        this.description = description;
        addPublisher();
        addRelease();
    }

    private void addPublisher() {
        Release release = new Release(new ConnectLogic());
        this.publisher = release.getPublisher(rid);
    }

    private void addRelease() {
        Release release = new Release(new ConnectLogic());
        this.release = release.getRelease(rid);
    }

    public String getPublisher() {
        return publisher;
    }

    public String getRelease() {
        return release;
    }

    public String getFilePath() {
        return filePath;
    }

    public ArrayList<String> getAttributes() {
        return attributes;
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

    public void setAttributes(ArrayList<String> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(String attribute) {
        attributes.add(attribute);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRid(int rid) {
        this.rid = rid;
        addPublisher();
        addRelease();
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

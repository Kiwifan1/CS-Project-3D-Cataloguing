package Logic;

import java.util.ArrayList;

// Simple Entity class to hold asset information for the UI
public class Entity {

    // local variables
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
            String description, Release releaseLogic) {
        attributes = new ArrayList<String>();
        attributes.add(attribute);

        this.filePath = filePath;
        this.username = username;
        this.name = name;
        this.rid = rid;
        this.scale = scale;
        this.description = description;

        addPublisher(releaseLogic);
        addRelease(releaseLogic);
    }

    private void addRelease(Release releaseLogic) {
        this.release = releaseLogic.getRelease(rid);
    }

    private void addPublisher(Release releaseLogic) {
        this.publisher = releaseLogic.getPublisher(rid);
    }

    public String getPublisher() {
        return publisher;
    }

    public String getRelease() {
        return release;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setRelease(String release) {
        this.release = release;
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

    public void setRid(int rid, Release releaseLogic) {
        this.rid = rid;
        addPublisher(releaseLogic);
        addRelease(releaseLogic);
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Entity)) {
            return false;
        }
        Entity entity = (Entity) o;
        return filePath.equals(entity.filePath);
    }
}

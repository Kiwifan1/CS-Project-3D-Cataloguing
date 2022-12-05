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
    private Release release;
    private String scale;
    private String description;

    public Entity() {
        filePath = "";
        attributes = new ArrayList<String>();
        username = "";
        name = "";
        scale = "";
        description = "";
        publisher = "";
        release = new Release();
    }

    public Entity(String filePath, String attribute, String username, String name, String scale,
            String description, Release release) {
        attributes = new ArrayList<String>();
        attributes.add(attribute);

        this.filePath = filePath;
        this.username = username;
        this.name = name;
        this.scale = scale;
        this.description = description;
        this.release = release;

    }

    public String getPublisher() {
        return publisher;
    }

    public Release getRelease() {
        return release;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setRelease(Release release) {
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

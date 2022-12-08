package Logic;

public class Release {
    private int id;
    private String name;
    private String publisher;
    private String description;

    public Release() {
        id = 0;
        name = "";
        publisher = "";
        description = "";
    }

    public Release(int id, String name, String publisher, String description) {
        this.id = id;
        this.name = name;
        this.publisher = publisher;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package Logic;

public class Log {
    private String user;
    private String action;
    private String timestamp;

    public Log(String user, String action, String timestamp) {
        this.user = user;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public String getAction() {
        return action;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

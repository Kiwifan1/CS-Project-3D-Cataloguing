package UI;

import javax.swing.JCheckBox;

public class ReleaseCheckBox extends JCheckBox {
    private String releaseName;
    private int releaseID;

    public ReleaseCheckBox(String releaseName, int releaseID) {
        super(releaseName);
        this.releaseName = releaseName;
        this.releaseID = releaseID;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public int getReleaseID() {
        return releaseID;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public void setReleaseID(int releaseID) {
        this.releaseID = releaseID;
    }
}

package Logic;

import javax.swing.JList;

public class ReleaseList extends JList {

    private String[] releases;
    private int[] releaseIDs;

    public ReleaseList(String[] releases, int[] releaseIDs) {
        super(releases);
        this.releases = releases;
        this.releaseIDs = releaseIDs;
    }

    public String[] getReleases() {
        return releases;
    }

    public int[] getReleaseIDs() {
        return releaseIDs;
    }

    public void setReleases(String[] releases) {
        this.releases = releases;
    }

    public void setReleaseIDs(int[] releaseIDs) {
        this.releaseIDs = releaseIDs;
    }

    public String getSelectedRelease() {
        return releases[getSelectedIndex()];
    }

    public int getSelectedReleaseID() {
        return releaseIDs[getSelectedIndex()];
    }
}

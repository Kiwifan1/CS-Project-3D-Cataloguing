package UI;

import Logic.*;

import javax.swing.*;

public abstract class BoilerPlateView extends JFrame {

    public static int WIDTH = 800;
    public static int HEIGHT = 650;

    protected JMenuBar menuBar;
    protected JMenu mainMenu;
    protected JMenuItem logout;
    protected JMenuItem analytics;
    protected JMenuItem library;
    protected JMenuItem addItem;

    public BoilerPlateView(String title) {
        super(title);

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        makeMenuBar();
        this.setJMenuBar(menuBar);
    }

    private void makeMenuBar() {
        // make the menu bar
        menuBar = new JMenuBar();
        mainMenu = new JMenu("Settings");
        logout = new JMenuItem("Logout");
        analytics = new JMenuItem("Analytics");
        library = new JMenuItem("Library");
        addItem = new JMenuItem("Add Asset");

        mainMenu.add(addItem);
        mainMenu.add(library);
        mainMenu.add(analytics);
        mainMenu.add(logout);

        menuBar.add(mainMenu);
    }

    abstract protected void addMenuListeners();
}

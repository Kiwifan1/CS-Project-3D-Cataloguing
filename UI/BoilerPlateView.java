package UI;

import javax.swing.JFrame;

public class BoilerPlateView extends JFrame {

    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    public BoilerPlateView(String title) {
        super(title);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }   
}

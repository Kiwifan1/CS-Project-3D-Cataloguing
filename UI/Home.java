package UI;

import javax.swing.*;
import Logic.Logic;
import java.awt.*;

public class Home extends JFrame {

    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    Logic logic;

    JPanel cataloguePanel;
    JButton catalogueBtn;

    public Home() {
        super("Home");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        logic = new Logic();

        makeCataloguePanel();

        this.setVisible(true);
    }

    private void makeCataloguePanel() {
        cataloguePanel = new JPanel();
        catalogueBtn = new JButton("Catalogue Asset");
        catalogueBtn.addActionListener(e -> {
            //TODO: Add catalogue asset functionalitys
        });

        cataloguePanel.add(catalogueBtn);

        this.add(cataloguePanel, BorderLayout.CENTER);
    }
}

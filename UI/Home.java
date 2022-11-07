package UI;

import javax.swing.*;

import Logic.ConnectLogic;

import java.awt.*;

public class Home extends JFrame {

    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    ConnectLogic logic;

    JPanel cataloguePanel;
    JButton catalogueBtn;

    public Home() {
        super("Home");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        logic = new ConnectLogic();

        makeCataloguePanel();

        this.setVisible(true);
    }

    private void makeCataloguePanel() {
        cataloguePanel = new JPanel();
        catalogueBtn = new JButton("Catalogue Asset");
        catalogueBtn.addActionListener(e -> {
            System.out.println("Catalogue Asset Button Pressed");
        });

        cataloguePanel.add(catalogueBtn);

        this.add(cataloguePanel, BorderLayout.CENTER);
    }
}

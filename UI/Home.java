package UI;

import javax.swing.*;
import java.awt.*;

public class Home extends JFrame {

    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    Logic logic;

    JPanel cataloguePanel;
    JButton catalogueBtn;
    JList <String> classList;
    JList <String> typeList;
    JList <String> entityList;

    int fileID = 0;
    int releaseID = 0;
    String className;
    String typeName;
    String entityName;

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
            logic.addAsset(fileID, releaseID, className, typeName, className);
            fileID++;
            releaseID++;
        });

        classList = new JList(logic.getClasses());
        classList.addListSelectionListener(e -> {
            className = classList.getSelectedValue();
        });

        typeList = new JList(logic.getTypes());
        typeList.addListSelectionListener(e -> {
            typeName = typeList.getSelectedValue();
        });

        entityList = new JList(logic.getEntities());
        entityList.addListSelectionListener(e -> {
            entityName = entityList.getSelectedValue();
        });

        cataloguePanel.add(catalogueBtn);
        cataloguePanel.add(classList);
        cataloguePanel.add(typeList);
        cataloguePanel.add(entityList);

        this.add(cataloguePanel, BorderLayout.CENTER);
    }
}

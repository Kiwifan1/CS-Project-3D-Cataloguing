package Logic;

import javax.swing.*;

public class ComboCheckBox extends JComboBox {
    public ComboCheckBox() {
        addBoxes();
    }

    public ComboCheckBox(JCheckBox[] items) {
        super(items);
        addBoxes();
    }

    private void addBoxes() {
        setRenderer(new ComboBoxRenderer());
        addActionListener(e -> {
            selectBox();
        });
    }

    /**
     * Selects or deselects the checkbox at the specified index.
     */
    private void selectBox() {
        if (getSelectedItem() instanceof JCheckBox) {
            JCheckBox checkBox = (JCheckBox) getSelectedItem();
            checkBox.setSelected(!checkBox.isSelected());
        }
    }

    /**
     * Renders a checkbox for each item in the list.
     */
    class ComboBoxRenderer implements ListCellRenderer {
        private JCheckBox checkBox = new JCheckBox();

        public ComboBoxRenderer() {
            checkBox.setOpaque(true);
        }

        public java.awt.Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            if (value instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) value;
                checkBox.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
                checkBox.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
                checkBox.setEnabled(list.isEnabled());
                checkBox.setFont(list.getFont());
                return checkBox;
            }
            return new JLabel();
        }
    }
}

package com.waldo.utils.icomponents;

import javax.swing.*;
import java.awt.*;

public class ITableLabel extends ILabel {

    private final ILabel textLabel = new ILabel("");
    private boolean hasIcon = true;

    public ITableLabel(Color background) {
        this(background, 0, true, null, "");
    }

    public ITableLabel(Color background, int row, boolean isSelected, ImageIcon icon) {
        this(background, row, isSelected, icon, "");
    }

    public ITableLabel(Color background, int row, boolean isSelected, String txt) {
        super(txt);
        updateBackground(background, row, isSelected);
        hasIcon = false;
        super.setOpaque(true);
    }

    public ITableLabel(Color background, int row, boolean isSelected, ImageIcon icon, String txt) {
        super(icon);
        createTextLabel(txt);
        updateBackground(background, row, isSelected);

        super.setOpaque(true);
        super.setLayout(new GridBagLayout());
        super.add(textLabel);
    }

    private void createTextLabel(String txt) {
        textLabel.setText(txt);
        textLabel.setForeground(Color.WHITE);
        Font f = textLabel.getFont();
        textLabel.setFont(new Font(f.getName(), Font.BOLD, f.getSize() - 5));
        textLabel.setOpaque(false);
    }

    public void updateWithTableComponent(Component c, int row, boolean isSelected) {
        updateBackground(c.getBackground(), row, isSelected);
        updateForeground(c.getForeground());
    }

    public void updateForeground(Color foreground) {
        super.setForeground(foreground);
    }

    public void updateBackground(Color background, int row, boolean isSelected) {
        if (row % 2 == 1 || isSelected) {
            super.setBackground(background);
        } else {
            super.setBackground(Color.WHITE);
        }
    }

    @Override
    public void setText(String text) {
        if (hasIcon) {
            textLabel.setText(text);
        } else {
            super.setText(text);
        }
    }
}

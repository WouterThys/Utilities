package com.waldo.utils.icomponents;

import javax.swing.*;
import java.awt.*;

public class ITableBallPanel extends JPanel {

    private final ITableLabel iconLabel;
    private final ITableLabel textLabel;

    public ITableBallPanel(Color background, int row, boolean isSelected, ImageIcon ballIcon, String ballText, String text) {
        super(new BorderLayout());
        iconLabel = new ITableLabel(background, row, isSelected, ballIcon,ballText);
        textLabel = new ITableLabel(background, row, isSelected, text);

        add(iconLabel, BorderLayout.WEST);
        add(textLabel, BorderLayout.CENTER);
    }


    public void updateBackground(Color background, int row, boolean isSelected) {
        iconLabel.updateBackground(background, row, isSelected);
        textLabel.updateBackground(background, row, isSelected);
    }

    public void updateBallIcon(ImageIcon imageIcon) {
        iconLabel.setIcon(imageIcon);
    }

    public void updateBallText(String text) {
        iconLabel.setText(text);
    }

    public void setText(String text) {
        textLabel.setText(text);
    }

}

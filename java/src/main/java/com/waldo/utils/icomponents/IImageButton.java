package com.waldo.utils.icomponents;

import javax.swing.*;

public class IImageButton extends JButton { //} implements ChangeListener {

    public IImageButton(ImageIcon activeIcon, ImageIcon rollOverIcon, ImageIcon pressedIcon, ImageIcon disabledIcon) {
        super();

        setContentAreaFilled(false);
        setBorder(BorderFactory.createEmptyBorder());

        setIcon(activeIcon);
        setRolloverIcon(rollOverIcon);
        setPressedIcon(pressedIcon);
        setDisabledIcon(disabledIcon);
    }
}

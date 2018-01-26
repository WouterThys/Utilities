package com.waldo.utils.icomponents;

import com.waldo.utils.GuiUtils;

import javax.swing.*;
import java.awt.*;

public abstract class IPanel extends JPanel implements GuiUtils.GuiInterface {


    public IPanel() {
        super();
    }

    public IPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public IPanel(LayoutManager layoutManager) {
        super(layoutManager);
    }

    public IPanel(LayoutManager layoutManager, boolean isDoubleBuffered) {
        super(layoutManager, isDoubleBuffered);
    }
}

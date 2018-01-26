package com.waldo.utils.icomponents;

import com.waldo.utils.GuiUtils;

import javax.swing.*;
import java.awt.*;

public abstract class IFrame extends JFrame implements GuiUtils.GuiInterface {


    public IFrame() {
        super();
    }

    public IFrame(String title) {
        super(title);
    }

    public IFrame(GraphicsConfiguration configuration) {
        super(configuration);
    }

    public IFrame(String title, GraphicsConfiguration configuration) {
        super(title, configuration);
    }

}

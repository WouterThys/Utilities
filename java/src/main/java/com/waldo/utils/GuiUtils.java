package com.waldo.utils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.awt.GridBagConstraints.BOTH;

public class GuiUtils {

    public interface GuiInterface {
        void initializeComponents();
        void initializeLayouts();
        void updateComponents(Object... args);
    }

    public static GridBagConstraints createFieldConstraints(int gridLocX, int gridLocY) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridLocX;
        constraints.gridy = gridLocY;
        constraints.weightx = 3;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(2,2,2,2);
        constraints.fill = BOTH;
        return constraints;
    }

    @Deprecated
    public static JPanel createFileOpenPanel(JTextField fileTf, JButton openBtn) {
        JPanel iconPathPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = createFieldConstraints(0,0);
        constraints.gridwidth = 1;
        iconPathPanel.add(fileTf, constraints);
        constraints = createFieldConstraints(1,0);
        constraints.gridwidth = 1;
        constraints.weightx = 0.1;
        iconPathPanel.add(openBtn, constraints);
        return iconPathPanel;
    }

    @Deprecated
    public static JPanel createComboBoxWithAction(JComboBox comboBox, Action action) {
        JPanel boxPanel = new JPanel(new BorderLayout());
        JToolBar toolBar = createNewToolbar(action);
        boxPanel.add(comboBox, BorderLayout.CENTER);
        boxPanel.add(toolBar, BorderLayout.EAST);
        return boxPanel;
    }

    public static JPanel createComponentWithActions(JComponent component, Action... actions) {
        JPanel boxPanel = new JPanel(new BorderLayout());
        JToolBar toolBar = createNewToolbar(actions);
        boxPanel.add(component, BorderLayout.CENTER);
        boxPanel.add(toolBar, BorderLayout.EAST);
        return boxPanel;
    }

    public static JToolBar createNewToolbar() {
        JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);
        toolBar.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        return toolBar;
    }

    public static JToolBar createNewToolbar(Action... actions) {
        JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);
        toolBar.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

        if (actions.length > 0) {
            for (Action a : actions) {
                toolBar.add(a);
            }
        }

        return toolBar;
    }

    public static TitledBorder createTitleBorder(String name) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(name);
        titledBorder.setTitleJustification(TitledBorder.RIGHT);
        titledBorder.setTitleColor(Color.gray);
        return titledBorder;
    }

    public static TitledBorder createTitleBorder(final ImageIcon icon) {
        return new TitledBorder("") {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
            {
                super.paintBorder(c, g, x, y, width, height);

                // Now use the graphics context to draw whatever needed
                g.drawImage(icon.getImage(), 0, 0, icon.getIconWidth(), icon.getIconHeight(), (img, infoflags, x1, y1, width1, height1) -> true);
            }
        };
    }

    public static class GridBagHelper extends GridBagConstraints {

        private JPanel panel;
        private int preferredWidth = 100;

        public GridBagHelper(JPanel panel) {
            this(panel, 100);
        }

        public GridBagHelper(JPanel panel, int preferredLabelWidth) {
            this.panel = panel;
            this.panel.setLayout(new GridBagLayout());
            this.preferredWidth = preferredLabelWidth;

            panel.setOpaque(false);
            reset();
        }

        public void reset() {
            insets = new Insets(2,2,2,2);
            anchor = GridBagConstraints.EAST;

            gridx = 0;
            gridy = 0;

            weighty = 0;
            weightx = 1;
        }

        public void addLineVertical(String labelText, JComponent component) {
            addLineVertical(labelText, component, GridBagConstraints.HORIZONTAL);
        }

        public void addLineVertical(String labelText, JComponent component, int fill) {
            int oldGw = gridwidth;
            int oldGh = gridheight;
            int oldAnchor = anchor;

            weightx = 0; weighty = 0;
            gridwidth = 1; anchor = GridBagConstraints.WEST;
            this.fill = GridBagConstraints.NONE;
            panel.add(new JLabel(labelText, JLabel.LEFT), this);

            gridwidth = oldGw;
            gridheight = oldGh;
            gridy += 1; weightx = 1;
            this.fill = fill;
            if (component != null) {
                panel.add(component, this);
            }

            anchor = oldAnchor;
            gridy += 1;
        }

        private JLabel newLbl(String text, ImageIcon icon) {
            JLabel lbl = new JLabel(icon);
            lbl.setVerticalTextPosition(JLabel.CENTER);
            lbl.setHorizontalTextPosition(JLabel.RIGHT);
            lbl.setToolTipText(text);
            return lbl;
        }

        public void addLine(String labelText, JComponent component) {
            addLine(labelText, component, GridBagConstraints.HORIZONTAL);
        }

        public void addLine(String labelText, JComponent component, int fill) {
            int oldGw = gridwidth;
            int oldGh = gridheight;
            double oldWx = weightx;
            double oldWy = weighty;

            weightx = 0; weighty = 0;
            gridwidth = 1;
            this.fill = GridBagConstraints.NONE;
            JLabel lbl = new JLabel(labelText, JLabel.RIGHT);
            if (preferredWidth > 1) {
                lbl.setPreferredSize(new Dimension(preferredWidth, 20));
            }
            panel.add(lbl, this);

            gridwidth = oldGw;
            gridheight = oldGh;
            gridx = 1;
            weightx = oldWx;
            weighty = oldWy;
            this.fill = fill;
            if (component != null) {
                panel.add(component, this);
            }


            gridx = 0; gridy++;
        }

        public void addLine(ImageIcon labelIcon, JComponent component) {
            addLine(labelIcon, component, GridBagConstraints.HORIZONTAL);
        }

        public void addLine(ImageIcon labelIcon, JComponent component, int fill) {
            int oldGw = gridwidth;
            int oldGh = gridheight;

            weightx = 0; weighty = 0;
            gridwidth = 1;
            this.fill = GridBagConstraints.NONE;
            panel.add(new JLabel(labelIcon, JLabel.RIGHT), this);

            gridwidth = oldGw;
            gridheight = oldGh;
            gridx = 1; weightx = 1;
            this.fill = fill;
            if (component != null) {
                panel.add(component, this);
            }


            gridx = 0; gridy++;
        }

        public void addLine(String toolTip, ImageIcon labelIcon, JComponent component) {
            addLine(toolTip, labelIcon, component, GridBagConstraints.HORIZONTAL);
        }

        public void addLine(String toolTip, ImageIcon labelIcon, JComponent component, int fill) {
            int oldGw = gridwidth;
            int oldGh = gridheight;

            weightx = 0; weighty = 0;
            gridwidth = 1;
            this.fill = GridBagConstraints.HORIZONTAL;
            panel.add(newLbl(toolTip, labelIcon), this);

            gridwidth = oldGw;
            gridheight = oldGh;
            gridx = 1; weightx = 1;
            this.fill = fill;
            if (component != null) {
                panel.add(component, this);
            }


            gridx = 0; gridy++;
        }

        public void add(JComponent component, int x, int y) {
            add(component, x, y, 1, weighty);
        }

        public void add(JComponent component, int x, int y, double weightX, double weightY) {
            int oldX = gridx;
            int oldY = gridy;
            double oldWeightX = weightx;
            double oldWeightY = weighty;

            gridx = x; weightx = weightX;
            gridy = y; weighty = weightY;
            panel.add(component, this);

            gridx = oldX; weightx = oldWeightX;
            gridy = oldY; weighty = oldWeightY;

            gridx = 0;
            gridy++;
        }



        public JPanel getPanel() {
            return panel;
        }

        public void setPanel(JPanel panel) {
            this.panel = panel;
        }
    }


}

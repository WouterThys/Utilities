package com.waldo.utils.icomponents;

import com.waldo.utils.GuiUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public abstract class IDialog extends JDialog implements GuiUtils.GuiInterface, WindowListener {

    public static final int OK = 1;
    public static final int NEUTRAL = 0;
    public static final int CANCEL = -1;

    private JPanel dialogPanel;
    private JPanel titlePanel;
    private JPanel contentPanel;
    private JPanel buttonPanel;

    private ILabel titleIconLabel;
    private ILabel titleNameLabel;
    private ILabel titleInfoLabel;

    protected JButton buttonOK;
    protected JButton buttonCancel;
    protected JButton buttonNeutral;

    //protected Application application;

    protected boolean isShown = false;

    protected int dialogResult = -1;

    public IDialog(Window parent) {
        super(parent);
        initializeDialog();
    }

    public IDialog(Window parent, String title) {
        super(parent, title);
        initializeDialog();
    }

//
//    public IDialog() {
//        initializeDialog();
//    }
//
//    public IDialog(Frame owner) {
//        super(owner);
//        if (owner instanceof Application) {
//            this.application = (Application) owner;
//        }
//        initializeDialog();
//    }
//
//    public IDialog(Frame owner, String title) {
//        super(owner, title);
//        if (owner instanceof Application) {
//            this.application = (Application) owner;
//        }
//        initializeDialog();
//    }
//
//    public IDialog(Dialog owner) {
//        super(owner);
//        initializeDialog();
//    }
//
//    public IDialog(Dialog owner, String title) {
//        super(owner, title);
//        initializeDialog();
//    }
//
//    public IDialog(Window owner) {
//        super(owner);
//        initializeDialog();
//    }
//
//    public IDialog(Window owner, ModalityType modalityType) {
//        super(owner, modalityType);
//        initializeDialog();
//    }
//
//    public IDialog(Window owner, String title) {
//        super(owner, title);
//        initializeDialog();
//    }
//
//    public IDialog(Window owner, String title, ModalityType modalityType) {
//        super(owner, title, modalityType);
//        initializeDialog();
//    }
//
//    public IDialog(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
//        super(owner, title, modalityType, gc);
//        initializeDialog();
//    }

    public int showDialog() {
        setLocationRelativeTo(getParent());
        pack();
        setMinimumSize(getSize());
        setVisible(true);
        return dialogResult;
    }

    public int showDialog(String focusComponent) {
        setLocationRelativeTo(getParent());
        pack();
        setMinimumSize(getSize());
        setFocusComponent(focusComponent);
        setVisible(true);
        return dialogResult;
    }

    public int showDialog(String focusTab, String focusComponent) {
        setLocationRelativeTo(getParent());
        pack();
        setMinimumSize(getSize());
        setFocusTab(focusTab);
        setFocusComponent(focusComponent);
        setVisible(true);
        return dialogResult;
    }

    private void initializeDialog() {

        setContentPane(createPanels());
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);

        // call onCancel() on Escape
        contentPanel.registerKeyboardAction(
                e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // call onOK() on Enter
        contentPanel.registerKeyboardAction(
                e -> onOK(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // Default no resize
        setResizable(false);

        // Title
        setTitleName(getTitle());
    }

    private JPanel createPanels() {
        dialogPanel = new JPanel(new BorderLayout(5,5));

        titlePanel = createTitlePanel();
        contentPanel = createContentPanel();
        buttonPanel = createButtonPanel();

        dialogPanel.add(titlePanel, BorderLayout.NORTH);
        dialogPanel.add(contentPanel, BorderLayout.CENTER);
        dialogPanel.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);

        return dialogPanel;
    }

    private JPanel createTitlePanel() {
        // Components
        titleIconLabel = new ILabel();
        titleIconLabel.setPreferredSize(new Dimension(48,48));
        titleNameLabel = new ILabel("?");
        titleNameLabel.setFontSize(36);
        titleNameLabel.setHorizontalAlignment(JLabel.CENTER);
        titleNameLabel.setVerticalAlignment(JLabel.CENTER);
        titleInfoLabel = new ILabel();

        // Panel
        JPanel panel = new JPanel(new BorderLayout(2,2));
        panel.add(titleIconLabel, BorderLayout.WEST);
        panel.add(titleNameLabel, BorderLayout.CENTER);
        panel.add(titleInfoLabel, BorderLayout.EAST);
        panel.setMinimumSize(new Dimension(50*3, 50));
        panel.setBorder(new EmptyBorder(10,10,10,10));

        return panel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(50*3,50*3));
        return panel;
    }

    private JPanel createButtonPanel() {
        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Cancel");
        buttonNeutral = new JButton("Save");
        buttonNeutral.setVisible(false);

        buttonOK.addActionListener( e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        buttonNeutral.addActionListener(e -> onNeutral());

        JPanel buttonBox = new JPanel(new GridLayout(1,2,5,5));
        buttonBox.add(buttonNeutral);
        buttonBox.add(buttonOK);
        buttonBox.add(buttonCancel);
        buttonBox.setBorder(new EmptyBorder(0,5,5,5));

        JPanel panel = new JPanel(new BorderLayout(5,5));
        panel.add(buttonBox, BorderLayout.EAST);

        return panel;
    }

    protected void setFocusComponent(String focusComponent) {
        Component c = getComponent(focusComponent);
        if (c != null) {
            c.requestFocus();
        }
    }

    protected void setFocusTab(String focusTab) {

    }

    protected Component getComponent(String name) {
        List<Component> components = getAllComponents(getContentPanel());
        if (components != null) {
            for (Component c : components) {
                if (c.getName() != null) {
                    if (c.getName().equals(name)) {
                        return c;
                    }
                }
            }
        }
        return null;
    }

    private List<Component> getAllComponents(Container c) {
        Component[] comps = c.getComponents();
        List<Component> compList = new ArrayList<Component>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container) {
                compList.addAll(getAllComponents((Container) comp));
            }
        }
        return compList;

    }



    protected void onOK() {
        dialogResult = OK;
        dispose();
    }

    protected void onCancel() {
        dialogResult = CANCEL;
        dispose();
    }

    protected void onNeutral() {
        dialogResult = NEUTRAL;
        dispose();
    }

    public void beginWait() {
        beginWait(IDialog.this);
    }

    public void beginWait(Component component) {
        component.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    public void endWait() {
        endWait(IDialog.this);
    }

    public void endWait(Component component) {
        component.setCursor(Cursor.getDefaultCursor());
    }

    public boolean isUpdating() {
        return isUpdating(IDialog.this);
    }

    public boolean isUpdating(Component component) {
        return component.getCursor().getType() == Cursor.WAIT_CURSOR;
    }







    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        onCancel();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        endWait();
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {
        isShown = true;
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    protected void showTitlePanel(boolean show) {
        titlePanel.setVisible(show);
    }

    protected JPanel getContentPanel() {
        return contentPanel;
    }

    protected JButton getButtonOK() {
        return buttonOK;
    }

    protected JButton getButtonNeutral() {
        return buttonNeutral;
    }

    protected void setTitleIcon(ImageIcon titleIcon) {
        titleIconLabel.setIcon(titleIcon);
    }

    protected void setTitleName(String name) {
        titleNameLabel.setText(name);
    }

    protected ILabel getTitleNameLabel() {
        return titleNameLabel;
    }

    protected ILabel getTitleIconLabel() {
        return titleIconLabel;
    }

    protected void setInfoIcon(ImageIcon infoIcon) {
        titleInfoLabel.setIcon(infoIcon);
    }
}

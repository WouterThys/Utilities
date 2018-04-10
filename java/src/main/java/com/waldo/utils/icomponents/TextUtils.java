package com.waldo.utils.icomponents;

import com.waldo.utils.ResourceManager;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;

class TextUtils {

    private static final ResourceManager imageResource = new ResourceManager("settings/", "Icons.properties");
    private static final ImageIcon copyIcon = imageResource.readImage("copy");
    private static final ImageIcon pasteIcon = imageResource.readImage("paste");
    private static final ImageIcon cutIcon = imageResource.readImage("cut");

    static void addCopyPastCutPopupToField(JComponent component) {
        if (component != null && component.getComponentPopupMenu() == null) {
            JPopupMenu menu = new JPopupMenu();

            AbstractAction cut = new DefaultEditorKit.CutAction();
            cut.putValue(Action.NAME, "Cut");
            cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
            cut.putValue(Action.SMALL_ICON, cutIcon);
            menu.add(cut);

            AbstractAction copy = new DefaultEditorKit.CopyAction();
            copy.putValue(Action.NAME, "Copy");
            copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
            copy.putValue(Action.SMALL_ICON, copyIcon);
            menu.add(copy);

            AbstractAction paste = new DefaultEditorKit.PasteAction();
            paste.putValue(Action.NAME, "Paste");
            paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
            paste.putValue(Action.SMALL_ICON, pasteIcon);
            menu.add(paste);

            component.setComponentPopupMenu(menu);
        }
    }
}

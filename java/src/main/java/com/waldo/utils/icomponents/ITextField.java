package com.waldo.utils.icomponents;

import com.waldo.utils.Error;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ITextField extends JTextField implements FocusListener {

    private boolean showingError = false;
    private boolean showingHint = false;
    private String hint = "";

    private Color originalColor;
    private String originalText = hint;
    private String originalToolTip = "";
    private Border originalBorder;

    private IBindingListener bindingListener;
    private Error error;

    public ITextField(String hint, IEditedListener editedListener, String fieldName, boolean enabled, int columns) {
        super(columns);
        this.setEnabled(enabled);
        if (hint == null) {
            hint = "";
        }
        this.hint = hint;
        this.originalColor = this.getForeground();
        this.originalBorder = this.getBorder();

        if (editedListener != null && fieldName != null) {
            addEditedListener(editedListener, fieldName);
        }

        TextUtils.addCopyPastCutPopupToField(this);
        addFocusListener(this);

        setFont(15, Font.BOLD);
        showHint(hint);
    }

    public ITextField(String hint, IEditedListener editedListener, String fieldName, boolean enabled) {
        this(hint, editedListener, fieldName, enabled, 15);
    }

    public ITextField(IEditedListener listener, String fieldName, boolean enabled) {
        this("", listener, fieldName, enabled, 15);
    }

    public ITextField(IEditedListener listener, String fieldName) {
        this("", listener, fieldName, true, 15);
    }

    public ITextField(String hint, int columns) {
        this(hint, null, null, true, columns);
    }

    public ITextField(boolean enabled) {
        this("", null, null, enabled, 15);
    }

    public ITextField(boolean enabled, int columns) {
        this("", null, null, enabled, columns);
    }

    public ITextField(String hint) {
        this(hint, null, null, true, 15);
    }

    public ITextField() {
        this("", null, null, true, 15);
    }

    public void addEditedListener(IEditedListener listener, String fieldName) {
        if (bindingListener != null) {
            this.getDocument().removeDocumentListener(bindingListener);
        }
        bindingListener = new IBindingListener(this, listener, fieldName);
        this.getDocument().addDocumentListener(bindingListener);
        bindingListener.setEnabled(true);
    }

    public void addEditedListener(IEditedListener listener, String fieldName, Class fieldClass) {
        if (bindingListener != null) {
            this.getDocument().removeDocumentListener(bindingListener);
        }
        bindingListener = new IBindingListener(this, listener, fieldName, fieldClass);
        this.getDocument().addDocumentListener(bindingListener);
    }

    public void fireValueChanged() {
        if (bindingListener != null) {
            bindingListener.fireValueEdited(this.getDocument());
        }
    }

    public void setError(String errorText) {
        if (errorText != null) {
            originalText = this.getText();
            originalToolTip = this.getToolTipText();
            error = new Error(Error.ERROR, errorText);
            this.setBorder(new IconBorder(error.getImage(), originalBorder));
            this.setToolTipText(error.getMessage());
            showingError = true;
        } else {
            error = null;
            this.setText(originalText);
            this.setBorder(originalBorder);
            this.setToolTipText(originalToolTip);
            showingError= false;
        }
    }

    public void setWarning(String warningText) {
        if (warningText != null) {
            originalText = this.getText();
            originalToolTip = this.getToolTipText();
            error = new Error(Error.WARNING, warningText);
            this.setBorder(new IconBorder(error.getImage(), originalBorder));
            this.setToolTipText(error.getMessage());
            showingError = true;
        } else {
            error = null;
            this.setText(originalText);
            this.setBorder(originalBorder);
            this.setToolTipText(originalToolTip);
            showingError = false;
        }
    }

    public void showHint(String hint) {
        this.hint = hint;
        setText(hint);

        super.setForeground(Color.gray);

        showingHint = true;
    }

    public void clearHint() {
        super.setText("");

        setForeground(originalColor);

        showingHint = false;
    }

    public void setFont(int size, int style) {
        Font f = this.getFont();
        this.setFont(new Font(f.getName(), style, size));
    }

    public void setBold(boolean bold) {
        Font f = this.getFont();
        int style = bold ? Font.BOLD : Font.PLAIN;
        this.setFont(new Font(f.getName(), style, f.getSize()));
    }

    public void setItalic(boolean italic) {
        Font f = this.getFont();
        int style = italic ? Font.ITALIC : Font.PLAIN;
        this.setFont(new Font(f.getName(), style, f.getSize()));
    }

    public void clearText() {
        showHint(hint);
    }

    @Override
    public void setText(String text) {
        boolean enabled = false;
        if (bindingListener != null) {
            enabled = bindingListener.isEnabled();
            bindingListener.setEnabled(false);
        }

        clearHint();
        super.setText(text);

        if (bindingListener != null) {
            bindingListener.setEnabled(enabled);
        }
    }

    @Override
    public String getText() {
        if (showingHint) {
            return "";
        } else {
            return super.getText();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (showingHint) {
            clearHint();
        }
        if (showingError) {
            setError(null);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (super.getText() == null || super.getText().isEmpty()) {
            showHint(hint);
        }
    }
}

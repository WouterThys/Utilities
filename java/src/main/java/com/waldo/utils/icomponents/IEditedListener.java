package com.waldo.utils.icomponents;

import java.awt.*;

public interface IEditedListener<O> {
    void onValueChanged(Component component, String fieldName, Object previousValue, Object newValue);
    O getGuiObject();
}

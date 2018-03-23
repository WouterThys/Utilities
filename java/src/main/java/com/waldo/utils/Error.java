package com.waldo.utils;

import javax.swing.*;
import java.awt.*;

public class Error {

    public static final int NO_ERROR = 0;
    public static final int INFO = 1;
    public static final int WARNING = 2;
    public static final int ERROR = 3;

    private static final Color errorColor = Color.RED;
    private static final Color warnColor = Color.ORANGE;
    private static final Color infoColor = Color.BLUE;

    private final int errorType;
    private final String message;

    private static final ResourceManager imageResource = new ResourceManager("settings/", "Icons.properties");

    public Error(int errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }

    int getErrorType() {
        return errorType;
    }

    public String getMessage() {
        return message;
    }

    public Color getColor() {
        switch (errorType) {
            case ERROR:
                return errorColor;
            case INFO:
                return infoColor;
            case NO_ERROR:
                return Color.WHITE;
            case WARNING:
                return warnColor;
            default:
                throw new IllegalArgumentException("Not a valid error type");
        }
    }

    public ImageIcon getImage()
    {
        switch (errorType) {
            case ERROR:
                return imageResource.readImage("error");
            case INFO:
                return null; //imageResource.readImage("ErrorProvider.InfoIcon");
            case NO_ERROR:
                return null;
            case WARNING:
                return imageResource.readImage("warning");
            default:
                return null;
        }
    }
}

package com.waldo.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class ResourceManager {

    private final Properties properties;

    public ResourceManager(String propertiesUrl, String fileName) {
        properties = new Properties();
        try {
            String resourceFileName = propertiesUrl + fileName;
            InputStream resourceInput = getClass().getClassLoader().getResourceAsStream(resourceFileName);
            properties.load(resourceInput);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Reads int from the properties file
     * @param key
     * @return
     */
    public int readInteger(String key) {
        int val = 0;
        try {
            val = Integer.parseInt(properties.getProperty(key));

        } catch (Exception ex) {
            //ignore
        }
        return val;
    }

    public int readInteger(String key,int radix) {
        int val = 0;
        try {
            val = Integer.parseInt(properties.getProperty(key),radix);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return val;
    }

    /**
     * Reads long from the properties file
     * @param key
     * @return
     */
    public long readLong(String key) {
        long val = 0;
        try {
            val = Long.parseLong(properties.getProperty(key));
        } catch (Exception ex) {
            //ignore
        }
        return val;
    }

    /**
     * Reads the string from the properties file
     * @param key
     * @return
     */
    public String readString(String key) {
        return properties.getProperty(key);
    }

    /**
     *  Reads the icon path from the properties file and gets the icon
     * from the path retrieved
     * @param resourceURL
     * @return
     */
    public ImageIcon readImage(URL resourceURL) {
        Image img = Toolkit.getDefaultToolkit().createImage(resourceURL);
        if (img == null) {
            return readImage("Common.UnknownIcon32");
        } else {
            return new ImageIcon(img);
        }
    }

    public ImageIcon readImage(String key) {
        InputStream is = null;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream("icons/" + readString(key));
            return new ImageIcon(ImageIO.read(is));
        } catch (Exception e) {
            //Status().setWarning("Error loading image icon " + key, e);
            //e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public ImageIcon readImage(String key, int size) {
        InputStream is = null;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream("icons/" + readString(key));
            BufferedImage image = ImageIO.read(is);
            return getScaledImageIcon(image, size, size);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public ImageIcon readImage(URL resourceURL, int width, int height) throws Exception {
        return getScaledImageIcon(ImageIO.read(resourceURL), width, height);
    }

    private ImageIcon getScaledImageIcon(BufferedImage image, int width, int height) {
        int newWidth;
        int newHeight;
        double targetRatio = (double) width / height;
        double originalRatio = (double) image.getWidth() / image.getHeight();
        if (originalRatio >= targetRatio) {
            newWidth = width;
            newHeight = newWidth;
        } else {
            newHeight = height;
            newWidth = newHeight;
        }
        Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public ImageIcon readImage (String path, int width, int height) {
        File imageFile = new File(path);
        if (imageFile.exists() && imageFile.isFile()) {
            try {
                return readImage(imageFile.toURI().toURL(), width, height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return readImage("Common.UnknownIcon48");
    }

    /**
     *
     * @param resourceURL
     * @return
     */
    public File readFile(URL resourceURL)  {
        File file = null;
        try {
            file =  new File(resourceURL.toURI());
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        return file;
    }

    public Color readColor(String key) {
        Color color;
        try {
            String cTxt = readString(key);
            color = Color.decode(cTxt);
        } catch (Exception e) {
            color = Color.DARK_GRAY;
        }
        return color;
    }

}

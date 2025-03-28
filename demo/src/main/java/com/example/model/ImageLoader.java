package com.example.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.example.App;

public class ImageLoader {
    public static Image load(String path) {
        Image image = cache.get(path);
        if (image != null)
            return image;

        try {
            image = ImageIO.read(App.class.getResourceAsStream(path));
        }
        catch (Exception e) {
            System.err.printf("Couldn't load image: %s\n", path);
            return null;
        }

        cache.put(path, image);
        return image;
    }

    private static Map<String, Image> cache = new HashMap<String, Image>();
}

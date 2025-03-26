package com.example;

import com.example.graphics.Window;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;

@SuppressWarnings("deprecation")
public class App 
{
    public static void main( String[] args )
    {
        
        Image image; 
        try {
            image = ImageIO.read(App.class.getResourceAsStream("graphics/test_img.png"));
        }
        catch (Exception e) {
            return;
        }

        Window.getInstance().open(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                Window.getInstance().clear();
                Window.getInstance().drawImage(10, 10, 150, 150, image);
                Window.getInstance().drawRect(100, 100, 200, 100, new Color(0, 255, 120, 255));
                Window.getInstance().write(100, 150, Window.getInstance().getMousePosition().toString(), Color.black);
                Window.getInstance().write(100, 200, Window.getInstance().isLeftMouseDown() ? "true" : "false", Color.black);
            }
        });
    }
}

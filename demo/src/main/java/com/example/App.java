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

        Window window = new Window();
        window.open(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                window.clear();
                window.drawImage(10, 10, 150, 150, image);
                window.drawRect(100, 100, 200, 100, new Color(0, 255, 120, 255));
                window.write(100, 150, "Welcome to the secret shop!", Color.black);
            }
        });
    }
}

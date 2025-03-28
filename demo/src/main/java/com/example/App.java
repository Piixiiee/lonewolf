package com.example;

import com.example.graphics.Window;
import com.example.model.*;

import java.awt.Color;
import java.awt.Image;
import java.util.*;

import javax.imageio.ImageIO;

@SuppressWarnings("deprecation")
public class App 
{
    public static void main( String[] args )
    {
        for (int i = 0; i < 200; i++) {
            System.out.println(Dice.rollPercentile());
        }

        Image image = ImageLoader.load("graphics/dice.png");

        D6View d6view = new D6View();
        d6view.rollNumber(Dice.roll(DiceType.D6));

        Window.getInstance().open(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                TimeHandler.update();
                d6view.update();

                Window.getInstance().clear();

                if (Window.getInstance().isLeftMouseDown() && !d6view.isRolling())
                    d6view.rollNumber(Dice.roll(DiceType.D6));
                // Window.getInstance().drawRect(100, 100, 200, 100, new Color(0, 255, 120, 255));
                // Window.getInstance().write(100, 150, Integer.toString((int)(1 / TimeHandler.getDelta())), Color.black);
                // Window.getInstance().write(100, 200, Window.getInstance().isLeftMouseDown() ? "true" : "false", Color.black);
                // Window.getInstance().drawImage(10, 10, 150, 150, image);

                d6view.draw();
            }
        });
    }
}

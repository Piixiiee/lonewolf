package com.example;

import com.example.graphics.Window;
import com.example.model.*;

import java.util.*;

@SuppressWarnings("deprecation")
public class App 
{
    public static void main( String[] args )
    {
        for (int i = 0; i < 200; i++) {
            System.out.println(Dice.rollPercentile());
        }

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
                    
                d6view.draw();
            }
        });
    }
}

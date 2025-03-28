package com.example.model;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import com.example.graphics.*;

public class D6View implements Drawable, Updateable {    
    public D6View() {
        currentSpriteRect = sprites[rolledNumber - 1];
    }

    @Override
    public void draw() {
        Window.getInstance().drawImage(
            (int)posX, (int)posY, 50, 50, 
            currentSpriteRect.x, currentSpriteRect.y, currentSpriteRect.width, currentSpriteRect.height, 
            spriteSheet);
    }

    @Override
    public void update() {
        if (rolling) {
            if (timer > nextSwitch) {
                if (smudge)
                    currentSpriteRect = smudgeSprite;
                else
                    currentSpriteRect = sprites[Dice.roll(DiceType.D6) - 1];
                nextSwitch = timer + switchInterval;
                smudge = !smudge;
            }

            if (timer > endTime) {
                currentSpriteRect = sprites[rolledNumber - 1];
                rolling = false;
            }

            posX = timer / endTime * 200 + (float)Math.sin(timer * 12) * 10;
            posY = 200 - (float)Math.pow(timer / endTime, 2) * 200 + (float)Math.cos(timer * 17) * 10;

            timer += TimeHandler.getDelta();
        }
    }

    public void rollNumber(int number) {
        rolledNumber = number;
        rolling = true;
        nextSwitch = switchInterval;
        timer = 0.0f;
        posX = 0.0f;
        posY = 0.0f;
    }

    public boolean isRolling() {
        return rolling;
    }

    private Image spriteSheet = ImageLoader.load("graphics/dice.png");
    private Rectangle smudgeSprite = new Rectangle(360, 30, 480, 170);
    private Rectangle[] sprites  = new Rectangle[] {
        new Rectangle(0, 8, 100, 103),
        new Rectangle(128, 14, 216, 114),
        new Rectangle(246, 13, 336, 119),
        new Rectangle(0, 135, 93, 228),
        new Rectangle(124, 135, 217, 229),
        new Rectangle(242, 135, 329, 227),
    };
    private Rectangle currentSpriteRect = sprites[0];

    private static final float switchInterval = 0.05f;
    private float timer = 0.0f;
    private float nextSwitch = 0.0f;
    private float endTime = 2f;
    private boolean rolling = false;
    private boolean smudge = false;
    private int rolledNumber = 1;
    private float posX;
    private float posY; 
}

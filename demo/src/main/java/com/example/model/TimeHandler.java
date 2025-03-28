package com.example.model;

public class TimeHandler {
    public static float getDelta() {
        return (time - lastTime) * ToSeconds;
    }
    
    public static float getTime() {
        return time * ToSeconds;
    }
    
    public static void update() {
        lastTime = time;
        time = System.currentTimeMillis();
    }

    private static long time = System.currentTimeMillis();
    private static long lastTime = 0;
    private static final float ToSeconds = 0.001f;
}

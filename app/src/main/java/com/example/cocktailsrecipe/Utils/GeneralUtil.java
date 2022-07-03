package com.example.cocktailsrecipe.Utils;

public class GeneralUtil {
    private static GeneralUtil instance;
    private static long prevTime;
    public static GeneralUtil getInstance() {
        if(instance == null) {
            instance = new GeneralUtil();
            return instance;
        } else {
            return instance;
        }
    }

    public static boolean isDoubleTapped(long now) {
        if(now - prevTime < 2000) {
            prevTime = now;
            return true;
        } else {
            prevTime = now;
            return false;
        }
    }
}

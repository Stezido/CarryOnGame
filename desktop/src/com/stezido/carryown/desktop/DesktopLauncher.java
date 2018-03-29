package com.stezido.carryown.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.stezido.carryown.CarryOwnGame;

import java.awt.Dimension;
import java.awt.Toolkit;

public class DesktopLauncher {

    public static Vector2 IPHONE_RESOLUTION = new Vector2(480, 320);
    public static Vector2 IPHONE_RESOLUTION_DOUBLED = new Vector2(960, 640);
    public static Vector2 HD_RESOLUTION = new Vector2(1024, 600);
    public static Vector2 CURRENT_RESOLUTION = IPHONE_RESOLUTION;
    public static Vector2 SCREENSIZE = new Vector2(1280, 720);


    public static void main(String[] arg) {

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "CarryOwn";
        config.width = (int) CURRENT_RESOLUTION.x;
        config.height = (int) CURRENT_RESOLUTION.y;

        //config the startposition of the gamescreen depending on Screensize and current Resolution
        config.x = (int) SCREENSIZE.x / 2 - (int) CURRENT_RESOLUTION.x / 2;
        config.y = (int) SCREENSIZE.y / 2 - (int) CURRENT_RESOLUTION.y / 2;
        new LwjglApplication(new CarryOwnGame(), config);
    }
}

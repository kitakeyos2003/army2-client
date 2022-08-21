package com.teamobi.mobiarmy2;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// config.setForegroundFPS(60);
		// config.setTitle("Mobi Army 2");
		// config.setWindowIcon("res/icon.png");
		config.foregroundFPS = 60;
		config.title = "Mobi Army 2";
		config.addIcon("res/icon.png", FileType.Internal);
		new LwjglApplication(new MainGame(), config);
	}
}

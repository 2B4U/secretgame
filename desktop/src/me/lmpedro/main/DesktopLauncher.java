package me.lmpedro.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setResizable(true);
		config.setWindowedMode(1280,720);
/*		config.setDecorated(false);*/
		config.setMaximized(false);
		config.useVsync(false);
		config.setTitle("Secret Game");
/*		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());*/
		new Lwjgl3Application(new Main(), config);
	}
}

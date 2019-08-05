package ru.grayfiles.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.grayfiles.game.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "VirusBattle";
		config.width = 1920 / 4 * 3;
		config.height = 1080 / 4 * 3;
		new LwjglApplication(new MyGame(), config);
	}
}

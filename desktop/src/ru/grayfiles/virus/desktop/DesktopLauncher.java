package ru.grayfiles.virus.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.grayfiles.virus.VirusGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = VirusGame.TITLE;
		config.width = VirusGame.WIDTH;
		config.height = VirusGame.HEIGHT;
		new LwjglApplication(new VirusGame(), config);
	}
}

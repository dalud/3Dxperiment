package discordia.threedee.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import discordia.threedee.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name", "pelaaja");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new Main(), config);
	}
}

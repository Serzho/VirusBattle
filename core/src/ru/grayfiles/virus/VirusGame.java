package ru.grayfiles.virus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.menuStates.MainMenu;

public class VirusGame extends ApplicationAdapter {
	public static int WIDTH = 1920;
	public static int HEIGHT = 1080;

	public static final String TITLE = "VirusGame";

	private GameStateManager gsm;
	private SpriteBatch batch;

	@Override
	public void create () {
		if(Gdx.graphics.getWidth() != 0) WIDTH = Gdx.graphics.getWidth();
		if(Gdx.graphics.getHeight() != 0) HEIGHT = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		gsm = new GameStateManager();

		Gdx.gl.glClearColor(1, 1, 1, 1);
		gsm.push(new MainMenu(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}

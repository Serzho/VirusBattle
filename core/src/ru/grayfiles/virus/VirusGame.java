package ru.grayfiles.virus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.MenuState;

public class VirusGame extends ApplicationAdapter {
	public static final int WIDTH = 1920 / 4 * 3;
	public static final int HEIGHT = 1080 / 4 * 3;

	public static final String TITLE = "VirusGame";

	private GameStateManager gsm;
	private SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();

		Gdx.gl.glClearColor(1, 1, 1, 1);
		gsm.push(new MenuState(gsm));
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

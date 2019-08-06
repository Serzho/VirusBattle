package ru.grayfiles.virus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.awt.Rectangle;

import ru.grayfiles.virus.game.Field;

public class VirusGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Field field;
	
	@Override
	public void create () {
		int width = 1920 / 4 * 3;
		int height = 1080 / 4 * 3;

		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);

		field = new Field(width / 2 - 700 / 2, (height - 700) / 2, (byte) 0);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		field.draw(batch);

		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			field.step((byte) 0, touchPos.x, touchPos.y);
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}

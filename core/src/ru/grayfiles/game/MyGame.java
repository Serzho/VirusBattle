package ru.grayfiles.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.awt.Rectangle;

public class MyGame extends ApplicationAdapter {
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture fieldImage;
	Texture blueMarkImage;
	Texture redMarkImage;
	Texture redInfectedImage;
	Texture blueInfectedImage;

	Rectangle fieldBox;
	
	@Override
	public void create () {
		int width = 1920 / 4 * 3;
		int height = 1080 / 4 * 3;

		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);

		fieldImage = new Texture("field.jpg");

		blueMarkImage = new Texture("blueMark.png");
		redMarkImage = new Texture("redMark.png");

		redInfectedImage = new Texture("redInfected.png");
		blueInfectedImage = new Texture("blueInfected.png");

		fieldBox = new Rectangle();
		fieldBox.setLocation(width / 2 - 700 / 2, (height - 700) / 2);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(fieldImage, fieldBox.x, fieldBox.y);
		batch.end();

		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			System.out.println(touchPos.x);
			System.out.println(touchPos.y);
			if(touchPos.x > 1090 && touchPos.x < 1790 && touchPos.y > 55 && touchPos.y < 155){
				batch.begin();
				System.out.println((int)((touchPos.x - 1080) / 70) * 70 - 35);
				System.out.println((int)((touchPos.y - 55) / 70) * 70 - 35);
				batch.draw(redMarkImage, (int)((touchPos.x - 1080) / 70) * 70 - 35, (int)((touchPos.y - 55) / 70) * 70 - 35);
				batch.end();
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}

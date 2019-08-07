package ru.grayfiles.virus.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import ru.grayfiles.virus.VirusGame;
import ru.grayfiles.virus.game.Field;

public class PlayState extends State {

    private Field field;
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VirusGame.WIDTH, VirusGame.HEIGHT);

        field = new Field(VirusGame.WIDTH / 2 - 700 / 2, (VirusGame.HEIGHT - 700) / 2, (byte) 0);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            field.step((byte) 0, touchPos.x, touchPos.y);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        field.draw(spriteBatch);
    }

    @Override
    public void dispose() {

    }
}

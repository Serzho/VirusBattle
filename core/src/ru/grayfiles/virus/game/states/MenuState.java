package ru.grayfiles.virus.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ru.grayfiles.virus.VirusGame;

public class MenuState extends State {

    private Stage stage;

    private ImageTextButton playButton;

    public MenuState(final GameStateManager gsm) {
        super(gsm);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        playButton = new ImageTextButton("PLAY", new Skin(Gdx.files.internal("skin/glassy-ui.json")));
        playButton.setPosition(VirusGame.WIDTH / 2 - playButton.getWidth() / 2, VirusGame.HEIGHT / 2);
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                System.out.println("Playing");
                gsm.set(new PlayState(gsm));
                stage.clear();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(playButton);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}

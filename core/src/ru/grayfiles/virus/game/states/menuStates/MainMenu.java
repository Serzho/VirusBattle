package ru.grayfiles.virus.game.states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import ru.grayfiles.virus.VirusGame;
import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.State;

public class MainMenu extends State {

    private Stage stage;

    private ImageTextButton singlePlayer;
    private ImageTextButton multiPlayer;

    private ImageButton statistics;
    private ImageButton settings;
    private ImageButton exit;

    public MainMenu(final GameStateManager gsm) {
        super(gsm);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        singlePlayer = new ImageTextButton("SinglePlayer", new Skin(Gdx.files.internal("skin/glassy-ui.json")));
        singlePlayer.setPosition(VirusGame.WIDTH / 2 - singlePlayer.getWidth() / 2, VirusGame.HEIGHT / 2 - VirusGame.HEIGHT / 10);
        spListener();
        stage.addActor(singlePlayer);

        multiPlayer = new ImageTextButton("MultiPlayer", new Skin(Gdx.files.internal("skin/glassy-ui.json")));
        multiPlayer.setPosition(VirusGame.WIDTH / 2 - multiPlayer.getWidth() / 2, VirusGame.HEIGHT / 2 + VirusGame.HEIGHT / 10);
        mpListener();
        stage.addActor(multiPlayer);

    }

    private void spListener(){
        singlePlayer.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                System.out.println("SinglePlayer");
                stage.clear();
                gsm.set(new SinglePlayerMenu(gsm));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    private void mpListener(){
        multiPlayer.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                System.out.println("MultiPlayer");
                stage.clear();
                gsm.set(new MultiPlayerMenu(gsm));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
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

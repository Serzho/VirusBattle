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
import ru.grayfiles.virus.game.states.playStates.MultiPlayerOffline;
import ru.grayfiles.virus.game.states.playStates.MultiPlayerOnline;

public class MultiPlayerMenu extends State {

    private Stage stage;

    private ImageButton exit;

    private ImageTextButton online;
    private ImageTextButton offline;

    public MultiPlayerMenu(final GameStateManager gsm) {

        super(gsm);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        online = new ImageTextButton("online", new Skin(Gdx.files.internal("skin/glassy-ui.json")));
        online.setPosition(VirusGame.WIDTH / 2 - online.getWidth() / 2, VirusGame.HEIGHT / 2 - VirusGame.HEIGHT / 10);
        onListener();
        stage.addActor(online);

        offline = new ImageTextButton("offline", new Skin(Gdx.files.internal("skin/glassy-ui.json")));
        offline.setPosition(VirusGame.WIDTH / 2 - offline.getWidth() / 2, VirusGame.HEIGHT / 2 + VirusGame.HEIGHT / 10);
        offListener();
        stage.addActor(offline);
    }

    private void onListener(){
        online.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                System.out.println("online");
                stage.clear();
                gsm.set(new MultiPlayerOnline(gsm));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    private void offListener(){
        offline.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                System.out.println("offline");
                stage.clear();
                gsm.set(new MultiPlayerOffline(gsm));
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

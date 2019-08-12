package ru.grayfiles.virus.game.states.menuStates;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import ru.grayfiles.virus.VirusGame;
import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.State;
import ru.grayfiles.virus.game.states.playStates.MultiPlayerOffline;
import ru.grayfiles.virus.game.states.playStates.MultiPlayerOnline;
import ru.grayfiles.virus.game.states.playStates.SinglePlayer;

public class SinglePlayerMenu extends State {

    private Stage stage;

    private String[] levels;

    private ImageButton exit;
    private ImageTextButton play;
    private SelectBox setDifficult;

    public SinglePlayerMenu(final GameStateManager gsm) {

        super(gsm);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        play = new ImageTextButton("Play", new Skin(Gdx.files.internal("skin/glassy-ui.json")));
        play.setPosition(VirusGame.WIDTH / 2 - play.getWidth() / 2, VirusGame.HEIGHT / 2 - VirusGame.HEIGHT / 10);
        pListener();
        stage.addActor(play);

        levels = new String[]{"Peaceful", "Easy", "Medium", "Hard", "Jesus"};
        setDifficult = new SelectBox<>(new Skin(Gdx.files.internal("skin/glassy-ui.json")));
        setDifficult.setItems(levels);
        setDifficult.setWidth(VirusGame.WIDTH / 4);
        setDifficult.setHeight(VirusGame.HEIGHT / 10);
        setDifficult.setPosition(VirusGame.WIDTH / 2 - setDifficult.getWidth() / 2, VirusGame.HEIGHT / 2 + VirusGame.HEIGHT / 10);
        stage.addActor(setDifficult);
    }

    private void pListener(){
        play.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                System.out.println("play");
                stage.clear();
                gsm.set(new SinglePlayer(gsm, 0));
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

package ru.grayfiles.virus.game.states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;

import ru.grayfiles.virus.VirusGame;
import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.State;
import ru.grayfiles.virus.game.states.menuStates.popups.ConfirmQuit;

public class MainMenu extends State {

    private ImageTextButton singlePlayer;
    private ImageTextButton multiPlayer;

    private ImageButton statistics;
    private ImageButton settings;
    private ImageTextButton exit;

    private Group actors = new Group();

    public MainMenu(final GameStateManager gsm) {
        super(gsm);

        exit = new ImageTextButton("exit", skin);
        exit.setPosition(Math.round(VirusGame.WIDTH - exit.getWidth()), Math.round(VirusGame.HEIGHT - exit.getHeight()));
        exListener();
        actors.addActor(exit);

        singlePlayer = new ImageTextButton("SinglePlayer", skin);
        singlePlayer.setPosition(Math.round(VirusGame.WIDTH / 2.0 - singlePlayer.getWidth() / 2.0), Math.round(VirusGame.HEIGHT / 2.0 - VirusGame.HEIGHT / 10.0));
        spListener();
        actors.addActor(singlePlayer);

        multiPlayer = new ImageTextButton("MultiPlayer", skin);
        multiPlayer.setPosition(Math.round(VirusGame.WIDTH / 2.0 - multiPlayer.getWidth() / 2.0), Math.round(VirusGame.HEIGHT / 2.0 + VirusGame.HEIGHT / 10.0));
        mpListener();
        actors.addActor(multiPlayer);

        stage.addActor(actors);

    }

    private void exListener(){
        exit.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                ConfirmQuit confirmQuit = new ConfirmQuit(skin, stage);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
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

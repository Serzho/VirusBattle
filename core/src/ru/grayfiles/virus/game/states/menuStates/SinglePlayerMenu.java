package ru.grayfiles.virus.game.states.menuStates;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ru.grayfiles.virus.VirusGame;
import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.State;
import ru.grayfiles.virus.game.states.playStates.SinglePlayer;

public class SinglePlayerMenu extends State {

    private Stage stage;

    private String[] levels;

    private ImageButton exit;
    private ImageTextButton play;
    private SelectBox<String> setDifficult;

    SinglePlayerMenu(final GameStateManager gsm) {

        super(gsm);

        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        play = new ImageTextButton("Play", skin);
        play.setPosition(Math.round(VirusGame.WIDTH / 2.0 - play.getWidth() / 2.0), Math.round(VirusGame.HEIGHT / 2.0 - VirusGame.HEIGHT / 10.0));
        pListener();
        stage.addActor(play);

        levels = new String[]{"Peaceful", "Easy", "Medium", "Hard", "Jesus"};
        setDifficult = new SelectBox<>(skin);
        setDifficult.setItems(levels);
        setDifficult.setWidth((float) VirusGame.WIDTH / 4);
        setDifficult.setHeight((float) VirusGame.HEIGHT / 10);
        setDifficult.setPosition(Math.round(VirusGame.WIDTH / 2.0 - setDifficult.getWidth() / 2.0), Math.round(VirusGame.HEIGHT / 2.0 + VirusGame.HEIGHT / 10.0));
        stage.addActor(setDifficult);
    }

    private void pListener(){
        play.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                System.out.println("play");
                stage.clear();
                gsm.set(new SinglePlayer(gsm, setDifficult.getSelectedIndex()));
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

package ru.grayfiles.virus.game.states.menuStates;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import ru.grayfiles.virus.VirusGame;
import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.State;
import ru.grayfiles.virus.game.states.menuStates.popups.ConfirmStop;
import ru.grayfiles.virus.game.states.playStates.OnePlayer;

public class SinglePlayerMenu extends State {

    private String[] levels;

    private ImageButton exit;
    private ImageTextButton play;
    private ImageButton back;
    private SelectBox<String> setDifficult;

    private Group actors = new Group();

    SinglePlayerMenu(final GameStateManager gsm) {
        super(gsm);

        play = new ImageTextButton("Play", skin);
        play.setPosition(Math.round(VirusGame.WIDTH / 2.0 - play.getWidth() / 2.0), Math.round(VirusGame.HEIGHT / 2.0 - VirusGame.HEIGHT / 10.0));
        pListener();
        actors.addActor(play);

        levels = new String[]{"Peaceful", "Easy", "Medium", "Hard", "Jesus"};
        setDifficult = new SelectBox<>(skin);
        setDifficult.setItems(levels);
        setDifficult.setWidth((float) VirusGame.WIDTH / 4);
        setDifficult.setHeight((float) VirusGame.HEIGHT / 10);
        setDifficult.setPosition(Math.round(VirusGame.WIDTH / 2.0 - setDifficult.getWidth() / 2.0), Math.round(VirusGame.HEIGHT / 2.0 + VirusGame.HEIGHT / 10.0));
        actors.addActor(setDifficult);

        back = new ImageButton(new TextureRegionDrawable(new TextureRegion(skin.get("back", Texture.class))));
        back.setHeight(VirusGame.HEIGHT/10f);
        back.setWidth(VirusGame.HEIGHT/10f);
        back.setPosition(0, VirusGame.HEIGHT - back.getHeight());
        bkListener();
        actors.addActor(back);

        stage.addActor(actors);
    }

    private void bkListener(){
        back.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                gsm.set(new MainMenu(gsm));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    private void pListener(){
        play.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                System.out.println("play");
                stage.clear();
                gsm.set(new OnePlayer(gsm, setDifficult.getSelectedIndex()));
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

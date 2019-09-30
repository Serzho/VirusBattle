package ru.grayfiles.virus.game.states.menuStates;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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

import java.util.ArrayList;
import java.util.Locale;

import ru.grayfiles.virus.VirusGame;
import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.State;
import ru.grayfiles.virus.game.states.menuStates.popups.ConfirmLoadSave;

public class SinglePlayerMenu extends State {

    private String[] levels;

    private ImageButton exit;
    private ImageTextButton play;
    private ImageButton back;
    private SelectBox<String> setDifficult;
    private SelectBox<Object> setMap;

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

        ArrayList<String> maps = new ArrayList<>();
        maps.add("Standard");

        for(int i = 1; i < 50; i++){
            String path = String.format(Locale.US, "fields/%d.txt", i);
            //for(FileHandle f : Gdx.files.local("fields").list()) System.out.println(f.name());
            FileHandle savedField = Gdx.files.internal(path);
            if(savedField.exists())maps.add("user" + savedField.name());
        }

        setMap = new SelectBox<>(skin);
        setMap.setItems(maps.toArray());
        setMap.setHeight(VirusGame.HEIGHT/10f);
        setMap.setWidth(VirusGame.HEIGHT/10f);
        setMap.setPosition(Math.round(VirusGame.WIDTH / 2.0 - setMap.getWidth() / 2.0),setDifficult.getY() + 20 + setMap.getHeight());

        actors.addActor(setMap);

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
                new ConfirmLoadSave(skin, stage, 0, gsm, setDifficult.getSelectedIndex(), setMap.getSelectedIndex());
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

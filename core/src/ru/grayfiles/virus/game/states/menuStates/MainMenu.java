package ru.grayfiles.virus.game.states.menuStates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import ru.grayfiles.virus.VirusGame;
import ru.grayfiles.virus.game.Assets;
import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.State;
import ru.grayfiles.virus.game.states.menuStates.popups.ConfirmQuit;
import ru.grayfiles.virus.game.states.menuStates.popups.SelectMap;

public class MainMenu extends State {

    private ImageTextButton singlePlayer;
    private ImageTextButton multiPlayer;
    private ImageTextButton help; //ImageButton


    private ImageButton statistics; //ImageButton
    private ImageButton settings; //ImageButton
    private ImageButton exit;

    private Skin skin;

    private Group actors = new Group();

    public MainMenu(final GameStateManager gsm) {
        super(gsm);

        skin = Assets.skin;

        exit = new ImageButton(new TextureRegionDrawable(new TextureRegion(skin.get("exit", Texture.class))));
        exit.setHeight(VirusGame.HEIGHT/8f);
        exit.setWidth(VirusGame.HEIGHT/8f);
        exit.setPosition(Math.round(VirusGame.WIDTH - exit.getWidth()), Math.round(VirusGame.HEIGHT - exit.getHeight()));
        exListener();
        actors.addActor(exit);

        settings = new ImageButton(new TextureRegionDrawable(new TextureRegion(skin.get("settings", Texture.class))));
        settings.setHeight(VirusGame.HEIGHT/8f);
        settings.setWidth(VirusGame.HEIGHT/8f);
        settings.setPosition(0, Math.round(VirusGame.HEIGHT - settings.getHeight()));
        setListener();
        actors.addActor(settings);

        statistics = new ImageButton(new TextureRegionDrawable(new TextureRegion(skin.get("statistics", Texture.class))));
        statistics.setHeight(VirusGame.HEIGHT/8f);
        statistics.setWidth(VirusGame.HEIGHT/8f);
        statistics.setPosition(settings.getWidth() + 50, Math.round(VirusGame.HEIGHT - statistics.getHeight()));
        statListener();
        actors.addActor(statistics);

        singlePlayer = new ImageTextButton("SinglePlayer", skin);
        singlePlayer.setPosition(Math.round(VirusGame.WIDTH / 2.0 - singlePlayer.getWidth() / 2.0), Math.round(VirusGame.HEIGHT / 2.0 - VirusGame.HEIGHT / 10.0));
        spListener();
        actors.addActor(singlePlayer);

        multiPlayer = new ImageTextButton("MultiPlayer", skin);
        multiPlayer.setPosition(Math.round(VirusGame.WIDTH / 2.0 - multiPlayer.getWidth() / 2.0), Math.round(VirusGame.HEIGHT / 2.0 + VirusGame.HEIGHT / 10.0));
        mpListener();
        actors.addActor(multiPlayer);

        help = new ImageTextButton("help", skin);
        help.setPosition(settings.getWidth() + statistics.getWidth() + 50, Math.round(VirusGame.HEIGHT - statistics.getHeight()));
        hpListener();
        actors.addActor(help);

        stage.addActor(actors);

    }

    private void hpListener(){
        help.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                System.out.println("Help");
                stage.clear();
                gsm.set(new HelpMenu(gsm));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    private void exListener(){
        exit.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                new ConfirmQuit(skin, stage);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    private void setListener(){
        settings.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                System.out.println("Settings");
                stage.clear();
                gsm.set(new SettingsMenu(gsm));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    private void statListener(){
        statistics.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                System.out.println("Statistics");
                stage.clear();
                gsm.set(new StatisticsMenu(gsm));
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
                new SelectMap(skin, stage, gsm, (byte) 1);
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

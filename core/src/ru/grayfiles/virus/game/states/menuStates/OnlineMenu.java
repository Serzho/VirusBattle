package ru.grayfiles.virus.game.states.menuStates;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


import ru.grayfiles.virus.VirusGame;
import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.State;
import ru.grayfiles.virus.game.states.playStates.MultiPlayerOffline;
import ru.grayfiles.virus.game.states.playStates.MultiPlayerOnline;

public class OnlineMenu extends State {

    private ImageButton exit;

    private ImageTextButton online;
    private ImageTextButton offline;

    private Group actors = new Group();

    public OnlineMenu(final GameStateManager gsm) {
        super(gsm);


        stage.addActor(actors);
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

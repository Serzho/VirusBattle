package ru.grayfiles.virus.game.states.menuStates.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.menuStates.OnlineMenu;
import ru.grayfiles.virus.game.states.playStates.MultiPlayerOffline;
import ru.grayfiles.virus.game.states.playStates.MultiPlayerOnline;


public class SelectMode {

    public SelectMode(final Skin skin, final Stage stage, final GameStateManager gsm) {
        Dialog dialog = new Dialog("Chose mode", skin) {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
        };
        dialog.text("Please, select mode", new Label.LabelStyle(skin.getFont("font"), Color.BLACK));

        ImageTextButton offline = new ImageTextButton("offline", skin);
        offline.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                System.out.println("Multiplayer offline");
                stage.clear();
                FileHandle savedField = Gdx.files.internal("saves/multiplayer/save.txt");
                if(!savedField.readString().isEmpty())new ConfirmLoadSave(skin, stage, 1, gsm);
                    else gsm.set(new MultiPlayerOffline(gsm));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        dialog.button(offline);

        ImageTextButton online = new ImageTextButton("online", skin);
        online.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                System.out.println("Multiplayer online");
                stage.clear();
                gsm.set(new OnlineMenu(gsm));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        dialog.button(online);

        dialog.button("Back", false);
        dialog.show(stage);
        /*                System.out.println("Exit Mainmenu");
                stage.clear();
                Gdx.app.exit();*/
    }
}

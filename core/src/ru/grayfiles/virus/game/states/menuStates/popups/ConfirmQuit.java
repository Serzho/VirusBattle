package ru.grayfiles.virus.game.states.menuStates.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ru.grayfiles.virus.game.Assets;


public class ConfirmQuit {

    public ConfirmQuit(Skin skin, final Stage stage) {
        Dialog dialog = new Dialog("Exit", skin) {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
        };
        dialog.text("Are you sure?", Assets.labelStyle);
        ImageTextButton yes = new ImageTextButton("Yes", skin);
        yes.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                Gdx.app.exit();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        dialog.button(yes);
        dialog.button("No", false);
        dialog.show(stage);
        /*                System.out.println("Exit Mainmenu");
                stage.clear();
                Gdx.app.exit();*/
    }
}

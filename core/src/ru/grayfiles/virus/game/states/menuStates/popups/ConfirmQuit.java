package ru.grayfiles.virus.game.states.menuStates.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import ru.grayfiles.virus.VirusGame;
import ru.grayfiles.virus.game.Assets;


public class ConfirmQuit {

    public ConfirmQuit(Skin skin, final Stage stage) {
        Dialog dialog = new Dialog("Exit", skin) {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
        };
        dialog.text("Are you sure?", Assets.labelStyle);
        ImageButton yes = new ImageButton(new TextureRegionDrawable(new TextureRegion(skin.get("accept", Texture.class))));
        yes.setHeight(VirusGame.HEIGHT/40f);
        yes.setWidth(VirusGame.HEIGHT/40f);
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
        ImageButton no = new ImageButton(new TextureRegionDrawable(new TextureRegion(skin.get("decline", Texture.class))));
        no.setHeight(VirusGame.HEIGHT/40f);
        no.setWidth(VirusGame.HEIGHT/40f);
        dialog.button(yes);
        dialog.button(no);
        dialog.show(stage);
        /*                System.out.println("Exit Mainmenu");
                stage.clear();
                Gdx.app.exit();*/
    }
}

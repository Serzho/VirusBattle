package ru.grayfiles.virus.game.states.menuStates.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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

import java.util.Locale;

import ru.grayfiles.virus.VirusGame;
import ru.grayfiles.virus.game.Assets;
import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.menuStates.MainMenu;


public class ConfirmStop {

    private String path;

    public ConfirmStop(Skin skin, final Stage stage, final byte[][] field, final int typeGame, final GameStateManager gsm, final int player, final int remainMoves) {
        Dialog dialog = new Dialog("Exit", skin) {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
        };

        ImageButton yes = new ImageButton(new TextureRegionDrawable(new TextureRegion(skin.get("accept", Texture.class))));
        yes.setHeight(VirusGame.HEIGHT/40f);
        yes.setWidth(VirusGame.HEIGHT/40f);
        yes.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                if(typeGame == 0)path = "saves/singleplayer/save.txt";
                else path = "saves/multiplayer/save.txt";

                FileHandle save = Gdx.files.local(path);

                StringBuilder tempStr = new StringBuilder();

                save.writeString("", false);

                for(byte[] tempArrayBytes : field){

                    for(byte tempByte : tempArrayBytes) tempStr.append((char) (tempByte + 48));
                    save.writeString(tempStr + "\n", true);
                    tempStr = new StringBuilder();
                }
                save.writeString(String.valueOf(player), true);
                save.writeString(String.valueOf(remainMoves), true);

                setMenu(gsm, stage);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        dialog.text("Are you want to save the game?", Assets.labelStyle);

        ImageButton no = new ImageButton(new TextureRegionDrawable(new TextureRegion(skin.get("decline", Texture.class))));
        no.setHeight(VirusGame.HEIGHT/8f);
        no.setWidth(VirusGame.HEIGHT/8f);
        no.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                if(typeGame == 0)path = "saves/singleplayer/save.txt";
                else path = "saves/multiplayer/save.txt";
                FileHandle save = Gdx.files.local(path);
                save.writeString("", false);
                setMenu(gsm, stage);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        ImageTextButton cancel = new ImageTextButton("cancel", skin);
        cancel.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        dialog.button(yes);
        dialog.button(no);
        dialog.button(cancel);
        dialog.show(stage);
    }

    private void setMenu(GameStateManager gsm, Stage stage){
        System.out.println("Stopped");
        stage.clear();
        gsm.set(new MainMenu(gsm));
    }
}

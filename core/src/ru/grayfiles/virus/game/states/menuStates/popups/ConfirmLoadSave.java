package ru.grayfiles.virus.game.states.menuStates.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

import ru.grayfiles.virus.VirusGame;
import ru.grayfiles.virus.game.Assets;
import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.playStates.OnePlayer;
import ru.grayfiles.virus.game.states.playStates.TwoPlayers;

public class ConfirmLoadSave {

    private String path;//

    public ConfirmLoadSave(Skin skin, final Stage stage, final int type, final GameStateManager gsm, final int difficult, final int map) {
        final Dialog dialog = new Dialog("Load Save", skin) {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
        };
        dialog.text("Are you want to load save?", Assets.labelStyle);
        ImageButton yes = new ImageButton(new TextureRegionDrawable(new TextureRegion(skin.get("accept", Texture.class))));
        yes.setHeight(VirusGame.HEIGHT/8f);
        yes.setWidth(VirusGame.HEIGHT/8f);
        yes.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                if(type == 0)path = "saves/singleplayer/save.txt";
                else path = "saves/multiplayer/save.txt";
                FileHandle savedField = Gdx.files.local(path);
                ArrayList<byte[]> tempArray = new ArrayList<>();

                for(String line : savedField.readString().split("\r?\n"))tempArray.add(line.getBytes());

                int player, remainMoves;

                player = tempArray.get(tempArray.size() - 1)[0] - 48;
                remainMoves = tempArray.get(tempArray.size() - 1)[1] - 48;

                tempArray.remove(tempArray.size() - 1);

                byte[][] tempArray1 = new byte[tempArray.size()][tempArray.get(0).length];
                for(int i = 0; i < tempArray.size(); i++){
                    tempArray1[i] = tempArray.get(i);
                    for(int k = 0; k < tempArray1[i].length; k++) tempArray1[i][k] -= 48;
                }

                gsm.set(new TwoPlayers(gsm, tempArray1, player, remainMoves));

                System.out.printf("Player %d RemainMoves %d \n", player, remainMoves);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        dialog.button(yes);

        final ImageButton no = new ImageButton(new TextureRegionDrawable(new TextureRegion(skin.get("decline", Texture.class))));
        no.setHeight(VirusGame.HEIGHT/40f);
        no.setWidth(VirusGame.HEIGHT/40f);

        no.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                if(type == 0){
                    path = "saves/singleplayer/save.txt";
                    FileHandle save = Gdx.files.local(path);
                    save.writeString("", false);

                    gsm.set(new OnePlayer(gsm, difficult, map));
                }
                else {
                    path = "saves/multiplayer/save.txt";
                    FileHandle save = Gdx.files.local(path);
                    save.writeString("", false);

                    gsm.set(new TwoPlayers(gsm, map));
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        dialog.button(no);
        dialog.show(stage);

    }
}

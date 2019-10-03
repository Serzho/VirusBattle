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
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.Locale;

import ru.grayfiles.virus.VirusGame;
import ru.grayfiles.virus.game.Assets;
import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.playStates.TwoPlayers;


public class SelectMap {

    private SelectBox<Object> setMap;

    public SelectMap(final Skin skin, final Stage stage, final GameStateManager gsm) {
        final Dialog dialog = new Dialog("Chose mode", skin) {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
        };

        Table table = new Table();

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
        setMap.setWidth((float) VirusGame.WIDTH);
        setMap.setHeight((float) VirusGame.HEIGHT / 10);
        setMap.setPosition(Math.round(VirusGame.WIDTH / 2.0 - setMap.getWidth() / 2.0), Math.round(VirusGame.HEIGHT / 2.0 + VirusGame.HEIGHT / 10.0));

        //dialog.add(setMap);


        Label text = new Label("Please, select map", Assets.labelStyle);

        table.add(text);
        table.add(setMap);



        /*ImageTextButton offline = new ImageTextButton("offline", skin);
        offline.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                System.out.println("Multiplayer offline");
                stage.clear();
                FileHandle savedField = Gdx.files.local("saves/multiplayer/save.txt");
                System.out.printf("Exists %b \n", savedField.exists());
                if(!savedField.readString().isEmpty())new ConfirmLoadSave(skin, stage, 1, gsm);
                    else gsm.set(new TwoPlayers(gsm));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        dialog.button(offline);*/

        ImageTextButton play = new ImageTextButton("play", skin);
        play.setHeight(VirusGame.HEIGHT/10f);
        play.setWidth(VirusGame.HEIGHT/10f);

        play.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                    System.out.println("Multiplayer offline");
                    stage.clear();
                    FileHandle savedField = Gdx.files.local("saves/multiplayer/save.txt");
                    System.out.printf("Exists %b \n", savedField.exists());
                    if(savedField.exists()) {
                        if (!savedField.readString().isEmpty())
                            new ConfirmLoadSave(skin, stage, 1, gsm, 0, setMap.getSelectedIndex());
                        else gsm.set(new TwoPlayers(gsm, setMap.getSelectedIndex()));
                    }
                    else{
                    savedField.writeString("", false);
                    gsm.set(new TwoPlayers(gsm, setMap.getSelectedIndex()));
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //dialog.button(play);

        table.row();
        table.add(play);

        ImageButton back = new ImageButton(new TextureRegionDrawable(new TextureRegion(skin.get("back", Texture.class))));
        back.setHeight(VirusGame.HEIGHT/10f);
        back.setWidth(VirusGame.HEIGHT/10f);
        back.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                dialog.hide();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        //dialog.button(back);
        table.add(back);

        dialog.add(table);

        dialog.show(stage);
        /*                System.out.println("Exit Mainmenu");
                stage.clear();
                Gdx.app.exit();*/
    }
}

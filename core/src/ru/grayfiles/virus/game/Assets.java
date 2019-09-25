package ru.grayfiles.virus.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    public static Label.LabelStyle labelStyle;
    public static Skin skin;

    public  Assets(){
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        skin.add("exit", new Texture("skin/exitbutton.png"));
        skin.add("accept", new Texture("skin/acceptbutton.png"));
        skin.add("decline", new Texture("skin/declinebutton.png"));
        skin.add("back", new Texture("skin/backbutton.png"));
        skin.add("settings", new Texture("skin/settingsbutton.png"));
        skin.add("statistics", new Texture("skin/statisticsbutton.png"));

        labelStyle = new Label.LabelStyle(skin.getFont("font"), Color.BLACK);
    }
}

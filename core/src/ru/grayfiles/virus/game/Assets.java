package ru.grayfiles.virus.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    public static Label.LabelStyle labelStyle;
    public static Skin skin;

    public  Assets(){
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        labelStyle = new Label.LabelStyle(skin.getFont("font"), Color.BLACK);
    }
}

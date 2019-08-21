package ru.grayfiles.virus.game.states.menuStates;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.State;

public class SettingsMenu extends State {
    public SettingsMenu(final GameStateManager gsm) {
        super(gsm);

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

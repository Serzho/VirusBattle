package ru.grayfiles.virus.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.grayfiles.virus.VirusGame;

public class MenuState extends State {

    private Texture playButton;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        playButton = new Texture("playButton.png");
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(playButton, (VirusGame.WIDTH / 2) - (playButton.getWidth() / 2), VirusGame.HEIGHT / 2);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        playButton.dispose();
    }
}

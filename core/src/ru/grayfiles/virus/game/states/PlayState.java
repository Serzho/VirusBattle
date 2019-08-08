package ru.grayfiles.virus.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import ru.grayfiles.virus.VirusGame;
import ru.grayfiles.virus.game.Field;

public class PlayState extends State {

    private Field field;
    private OrthographicCamera camera;

    private Byte quantityPlayers;
    private Byte currentPlayer;

    private Byte quantityMoves;
    private Byte remainMoves;


    PlayState(GameStateManager gsm) {
        super(gsm);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VirusGame.WIDTH, VirusGame.HEIGHT);

        field = new Field(VirusGame.WIDTH / 2 - 700 / 2, (VirusGame.HEIGHT - 700) / 2, (byte) 0);

        quantityPlayers = 2;
        currentPlayer = 0;

        quantityMoves = (byte) Math.round(Math.sqrt(field.getFieldSize()) / 10 + 2);
        remainMoves = quantityMoves;
        System.out.printf("Quantity moves = %d \n", quantityMoves);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if(field.step(currentPlayer, touchPos.x, touchPos.y))
                if (remainMoves > 0) remainMoves--;
                else {
                    remainMoves = quantityMoves;
                    if (currentPlayer == quantityPlayers - 1) currentPlayer = 0;
                    else currentPlayer++;
                }
            System.out.printf("Current Player %d \n", currentPlayer);
            System.out.printf("Remain moves %d \n", remainMoves);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        field.draw(spriteBatch);
    }

    @Override
    public void dispose() {

    }
}

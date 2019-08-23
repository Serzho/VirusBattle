package ru.grayfiles.virus.game.states.playStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;

import ru.grayfiles.virus.VirusGame;
import ru.grayfiles.virus.game.classes.Field;
import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.State;
import ru.grayfiles.virus.game.states.menuStates.MainMenu;
import ru.grayfiles.virus.game.states.menuStates.popups.ConfirmStop;

public class MultiPlayerOffline extends State {

    private Field field;
    private OrthographicCamera camera;

    private Byte quantityPlayers;
    private Byte currentPlayer;

    private Byte quantityMoves;
    private Byte remainMoves;

    private int step;

    private GameStateManager gsm;

    private ImageTextButton back;

    private Group actors = new Group();

    public MultiPlayerOffline(GameStateManager gsm) {
        super(gsm);

        this.gsm = gsm;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VirusGame.WIDTH, VirusGame.HEIGHT);

        field = new Field(VirusGame.WIDTH / 2 - 700 / 2, (VirusGame.HEIGHT - 700) / 2, (byte) 0);

        quantityPlayers = 2;
        currentPlayer = 0;
        step = 0;

        quantityMoves = (byte) Math.round(Math.sqrt(field.getFieldSize()) / 10 + 2);
        remainMoves = quantityMoves;
        System.out.printf("Quantity moves = %d \n", quantityMoves);

        back = new ImageTextButton("back", skin);
        back.setPosition(0, VirusGame.HEIGHT - back.getHeight());
        bkListener();
        actors.addActor(back);


        stage.addActor(actors);
    }

    public MultiPlayerOffline(GameStateManager gsm, byte[][] savedField, int player, int remainMoves){
        super(gsm);

        this.gsm = gsm;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VirusGame.WIDTH, VirusGame.HEIGHT);

        field = new Field(VirusGame.WIDTH / 2 - 700 / 2, (VirusGame.HEIGHT - 700) / 2, savedField);

        currentPlayer = (byte) player;
        this.remainMoves = (byte) remainMoves;

        quantityPlayers = 2;
        step = 0;

        quantityMoves = (byte) Math.round(Math.sqrt(field.getFieldSize()) / 10 + 2);

        back = new ImageTextButton("back", skin);
        back.setPosition(0, VirusGame.HEIGHT - back.getHeight());
        bkListener();
        actors.addActor(back);


        stage.addActor(actors);
    }

    private void bkListener(){
        back.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                new ConfirmStop(skin, stage, field.getField(), 1, gsm, currentPlayer, remainMoves);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            step(touchPos);
            if(field.checkWin(step < quantityMoves * 3) != -1) gsm.set(new MultiPlayerOffline(gsm));
        }
    }

    private void step(Vector3 touchPos){
        System.out.printf("STEP NUBMER %d \n", step);
        if(field.step(currentPlayer, touchPos.x, touchPos.y, remainMoves.equals(quantityMoves))) {
            if (remainMoves > 0) remainMoves--;
            else {
                remainMoves = quantityMoves;
                if (currentPlayer == quantityPlayers - 1) currentPlayer = 0;
                else currentPlayer++;
            }
            step++;
            //System.out.printf("Step %d \n", step);
        }
        //System.out.printf("Current Player %d \n", currentPlayer);
        //System.out.printf("Remain moves %d \n", remainMoves);

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
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

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {

    }


}

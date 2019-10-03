package ru.grayfiles.virus.game.states.playStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.Random;

import ru.grayfiles.virus.VirusGame;
import ru.grayfiles.virus.game.classes.Field;
import ru.grayfiles.virus.game.states.GameStateManager;
import ru.grayfiles.virus.game.states.State;
import ru.grayfiles.virus.game.states.menuStates.MainMenu;
import ru.grayfiles.virus.game.states.menuStates.popups.ConfirmStop;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.cell;

public class OnePlayer extends State {

    private Field field;
    private OrthographicCamera camera;

    private Byte quantityPlayers;
    private Byte currentPlayer;

    private Byte quantityMoves;
    private Byte remainMoves;

    private int step;
    private int difficult;

    private GameStateManager gsm;

    private ImageButton back;
    private ImageTextButton revert;

    private Group actors = new Group();

    private Random random = new Random();

    public OnePlayer(GameStateManager gsm, int difficult, int map) {
        super(gsm);

        this.difficult = difficult;
        this.gsm = gsm;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VirusGame.WIDTH, VirusGame.HEIGHT);

        field = new Field(VirusGame.WIDTH / 2 - 1000 / 2, (VirusGame.HEIGHT - 1000) / 2, (byte) 0);

        quantityPlayers = 2;
        currentPlayer = 0;
        step = 0;

        quantityMoves = (byte) Math.round(Math.sqrt(field.getFieldSize()) / 10 + 2);
        remainMoves = quantityMoves;

        System.out.printf("Difficult: %d \n", difficult);
        System.out.printf("Quantity moves = %d \n", quantityMoves);

        back = new ImageButton(new TextureRegionDrawable(new TextureRegion(skin.get("back", Texture.class))));
        back.setHeight(VirusGame.HEIGHT/10f);
        back.setWidth(VirusGame.HEIGHT/10f);
        back.setPosition(0, VirusGame.HEIGHT - back.getHeight());
        bkListener();
        actors.addActor(back);

        revert = new ImageTextButton("revert", skin);
        revert.setPosition((VirusGame.WIDTH - revert.getWidth() - 10), VirusGame.HEIGHT / 2f  - revert.getHeight());
        rtListener();
        actors.addActor(revert);

        stage.addActor(actors);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if(currentPlayer == 0)step(touchPos);
            if(field.checkWin(step < quantityMoves * 3) != -1) gsm.set(new MainMenu(gsm));
        }
    }

    private void switchDifficult(){
        switch (difficult){
            case 0: peacefulComputerStep(); break;
            case 1: easyComputerStep(); break;
            case 2: mediumComputerStep(); break;
        }
    }

    private void mediumComputerStep(){
        System.out.printf("COMPUTER STEP NUBMER %d \n", step);
        boolean goodStep = false;

        int[][] stepCount = new int[10][10];
        byte[][] cells = field.getField();

        if(step > 3)
        for(int i = 0; i < 10; i++){
            for(int k = 0; k < 10; k++){
                stepCount[i][k] = convertCell(cells[i][k]);
                System.out.printf("%d ", stepCount[i][k]);
            }
            System.out.println();
        }
        else easyComputerStep();

        for(int i = 0; i < 10; i++){
            for(int k = 0; k < 10; k++){
                //if(stepCount)
                System.out.printf("%d ", stepCount[i][k]);
            }
            System.out.println();
        }


        /*
        while (!goodStep){
            int cellX = random.nextInt(10);
            int cellY = random.nextInt(10);

            System.out.printf("CELL X %d CELL Y %d", cellX, cellY);
            goodStep = field.step((byte) 1, cellX, cellY, remainMoves.equals(quantityMoves), (byte) 0);
        }

        if (remainMoves > 0) remainMoves--;
        else {
            remainMoves = quantityMoves;
            if (currentPlayer == quantityPlayers - 1) currentPlayer = 0;
            else currentPlayer++;
        }
        step++;
        //System.out.printf("Step %d \n", step);

        System.out.printf("Current Player %d \n", currentPlayer);
        System.out.printf("Remain moves %d \n", remainMoves);

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    private int convertCell(byte cell){
        switch (cell){
            case 2: return 0;
            case 3: return 0;
            case 1: return -1;
            case 4: return 20;
        }


        return 1;
    }

    private void peacefulComputerStep(){
        System.out.printf("COMPUTER STEP NUBMER %d \n", step);
        boolean goodStep = false;
        while (!goodStep){
            int cellX = random.nextInt(10);
            int cellY = random.nextInt(10);
            System.out.printf("CELL X %d CELL Y %d", cellX, cellY);
            goodStep = field.step((byte) 1, cellX, cellY, remainMoves.equals(quantityMoves), (byte) 0);
        }

        if (remainMoves > 0) remainMoves--;
        else {
            remainMoves = quantityMoves;
            if (currentPlayer == quantityPlayers - 1) currentPlayer = 0;
            else currentPlayer++;
        }
        step++;
        //System.out.printf("Step %d \n", step);

        System.out.printf("Current Player %d \n", currentPlayer);
        System.out.printf("Remain moves %d \n", remainMoves);

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void easyComputerStep(){
        if(random.nextInt() % 2 == 0)peacefulComputerStep();
        else mediumComputerStep();
    }

    private void step(Vector3 touchPos){
        System.out.printf("STEP NUBMER %d \n", step);
        if(field.step(currentPlayer, touchPos.x, touchPos.y, remainMoves.equals(quantityMoves), (byte) 1)) {
            if (remainMoves > 0) remainMoves--;
            else {
                remainMoves = quantityMoves;
                if (currentPlayer == quantityPlayers - 1) currentPlayer = 0;
                else currentPlayer++;
            }
            step++;
            //System.out.printf("Step %d \n", step);
        }
        System.out.printf("Current Player %d \n", currentPlayer);
        System.out.printf("Remain moves %d \n", remainMoves);

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    private void rtListener(){
        revert.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(step > 4)
                    if (field.revert()) {
                        remainMoves++;
                        if (remainMoves > 2){
                            remainMoves = 0;
                            currentPlayer--;
                            if (currentPlayer < 0) currentPlayer = (byte) (quantityPlayers - 1);

                        }
                    }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    @Override
    public void update(float dt) {
        if(field.checkWin(step < quantityMoves * 3) != -1) gsm.set(new MainMenu(gsm));

        handleInput();

        if(currentPlayer == 1)switchDifficult();
        if(field.checkWin(step < quantityMoves * 3) != -1) gsm.set(new MainMenu(gsm));
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

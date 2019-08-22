package ru.grayfiles.virus.game.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

public class Field {

    private Texture image;
    private Texture baseImage;
    private Texture blueMarkImage;
    private Texture redMarkImage;
    private Texture redInfectedImage;
    private Texture blueInfectedImage;
    private Texture blockedCellImage;

    private Rectangle box;
    private Vector2 position = new Vector2();

    private byte[][] cells;
    private HashSet<Vector2> checkedAtackedCells;

    private int cellX;
    private int cellY;

    public Field(int x, int y, byte type){
        this.image = new Texture("field.jpg");
        this.baseImage = new Texture("baseImage.png");
        this.blueMarkImage = new Texture("blueMark.png");
        this.redMarkImage = new Texture("redMark.png");
        this.redInfectedImage = new Texture("redInfected.png");
        this.blueInfectedImage = new Texture("blueInfected.png");
        this.blockedCellImage = new Texture("blockedCell.png");

        this.box = new Rectangle(x, y, 700, 700);
        this.position.set(x, y);

        this.cells = getSavedField(type);
        this.checkedAtackedCells = new HashSet<>();
    }

    private byte[][] getSavedField(byte type){
        ArrayList<byte[]> tempArray = new ArrayList<>();

        String path = String.format(Locale.US, "fields/%d.txt", type);
        //for(FileHandle f : Gdx.files.local("fields").list()) System.out.println(f.name());
        FileHandle savedField = Gdx.files.internal(path);
        boolean exists = savedField.exists();
        System.out.println(exists);
        if(exists)
            for(String line : savedField.readString().split("\r?\n"))
                tempArray.add(line.getBytes());
        else{
            System.out.println(System.getProperty("user.dir"));
        }

        byte[][] tempArray1 = new byte[tempArray.size()][tempArray.get(0).length];
        for(int i = 0; i < tempArray.size(); i++){
            tempArray1[i] = tempArray.get(i);
            for(int k = 0; k < tempArray1[i].length; k++) tempArray1[i][k] -= 48;
        }

        return tempArray1;
    }

    public boolean step(byte player, float x, float y, boolean isFirstMove){
        boolean isDone = false;

        checkedAtackedCells.clear();

        //System.out.printf("player %d x %f y %f", player, x, y);
        //printField();
        if(box.contains(x, y)){
            //System.out.println("Contains");
            cellX = (int) ((x - box.x) / 70);
            cellY = (int) ((y - box.y) / 70);
            //System.out.printf("cellX %d, cellY %d \n", cellX, cellY);
            switch (cells[correctIndex(cellX)][correctIndex(cellY)]){
                case -1:
                    if(isFirstMove) {
                        cells[cellX][cellY] = (byte) (1 + player);
                        isDone = true;
                    }
                    break;
                case 0:
                    if (isCorrectMove(player, cellX, cellY)){
                        cells[cellX][cellY] += (1 + player);
                        isDone = true;
                    }
                    break;
                case 1:
                    if (isCorrectMove(player, cellX, cellY)) {
                        if (player == 1) if (cells[cellX][cellY] != 3) {
                            cells[cellX][cellY] = 3;
                            isDone = true;
                        }
                    }
                    break;
                case 2:
                    if (isCorrectMove(player, cellX, cellY)) {
                        if (player == 0) if (cells[cellX][cellY] != 4) {
                            cells[cellX][cellY] = 4;
                            isDone = true;
                        }
                    }
            }
        }
        return isDone;
    }

    public byte checkWin(boolean isFirstSteps){
        byte playerWin = -1;

        //System.out.printf("Is First steps %b \n", isFirstSteps);
        if(!isFirstSteps) {
            playerWin = checkKillingVictory();
            if(playerWin == -1) playerWin = checkToiletVictory();
        }
        //System.out.printf("Winned player %d \n", playerWin);
        return playerWin;
    }

    private byte checkToiletVictory() {
        byte playerWin = -1;

        boolean firstPlayerWin = true;
        boolean secondPlayerWin = true;

        for (int i = 0; i < 10; i++)
            for (int k = 0; k < 10; k++){
                //System.out.printf("CELL X %d CELL Y %d CHECK PLAYER0 %b CHECK PLAYER1 %b \n", i, k, isCorrectMove((byte) 0, i, k) && cells[i][k] != 4 && cells[i][k] != 1, isCorrectMove((byte) 1, i, k) && cells[i][k] != 3 && cells[i][k] != 2);
                if(isCorrectMove((byte) 0, i, k) && cells[i][k] != 4 && cells[i][k] != 1)secondPlayerWin = false;
                if(isCorrectMove((byte) 1, i, k) && cells[i][k] != 3 && cells[i][k] != 2)firstPlayerWin = false;
            }

        if (firstPlayerWin) playerWin = 0;
        else if (secondPlayerWin) playerWin = 1;

        //System.out.printf("Win by toilet %d \n", playerWin);

        return playerWin;
    }

    private byte checkKillingVictory(){
        byte playerWin = -1;

        boolean firstPlayerWin = true;
        boolean secondPlayerWin = true;

        for (byte[] cell : cells)
            for (int k = 0; k < cells[0].length; k++) {
                if (cell[k] == 1) secondPlayerWin = false;
                else if (cell[k] == 2) firstPlayerWin = false;
            }
        if (firstPlayerWin) playerWin = 0;
        else if (secondPlayerWin) playerWin = 1;

        //System.out.printf("Win by killing %d \n", playerWin);

        return playerWin;
    }

    private boolean isCorrectMove(byte player, int cellX, int cellY){
        boolean isCorrect = false;
        byte tempCell;

        System.out.printf("CURRENT CELL %d %d \n", cellX, cellY);

        for(int i = cellX - 1; i < cellX + 2; i++)
            for(int k = cellY - 1; k < cellY + 2; k++) {
                tempCell = cells[correctIndex(i)][correctIndex(k)];
                //System.out.printf("tempCell %d first %d second %d isCorrect %b \n", tempCell, 1 + player, 4 - player, isCorrect);
                if(tempCell == 4 - player && checkAtackedCell(player, correctIndex(i), correctIndex(k)))
                    isCorrect = true;
                else if(tempCell == 1 + player) isCorrect = true;
                System.out.printf("CELL X %d CELL Y %d IS CORRECT NOW %b \n", correctIndex(i), correctIndex(k), isCorrect);
            }

        if(cells[cellX][cellY] == 1 + player || cells[cellX][cellY] > 2){
            System.out.printf("Incorrect cell %d %d count %d \n", cellX, cellY, cells[cellX][cellY]);
            isCorrect = false;
        }

        return isCorrect;
    }

    private boolean checkAtackedCell(byte player, int cellX, int cellY) {
        boolean isConnected = false;

        //System.out.println("checked Atacked Cells \n");

        //for (Vector2 cell : checkedAtackedCells) System.out.printf("CELL %f %f ", cell.x, cell.y);
        //System.out.println("END");

        if(!checkedAtackedCells.contains(new Vector2(cellX, cellY))) {

            checkedAtackedCells.add(new Vector2(cellX, cellY));
            System.out.printf("Checking %d %d \n", cellX, cellY);

            for (int i = correctIndex(cellX - 1); i < correctIndex(cellX + 2); i++)
                for (int k = correctIndex(cellY - 1); k < correctIndex(cellY + 2); k++) {
                    if (cells[i][k] == 1 + player) {
                        isConnected = true;
                    }
                    if (cells[i][k] == 4 - player && checkAtackedCell(player, i, k)) {
                        isConnected = true;
                    }
                }

            System.out.printf("Is connected %b", isConnected);
        }
        //else
        //    System.out.println("DON'T CHECK");

        return isConnected;
    }

    private int correctIndex(int index){
        //System.out.printf("index %d \n", index);
        if (index < 0) index = 0;
        else if (index > 9) index = 9;
        //System.out.printf("second Index %d \n", index);
        return index;
    }

    private void printField(){
        for (byte[] cell : cells) {
            for (int k = 0; k < cells[0].length; k++) {
                System.out.printf(" %d", cell[k]);
            }
            System.out.println();
        }
    }

    public void draw(SpriteBatch batch){
        batch.begin();
        batch.draw(image, box.x, box.y);
        int drawX, drawY;
        for(int i = 0; i < cells.length; i++){
            for(int k = 0; k < cells[0].length; k++) {
                drawX = (int) (box.x + i * 70);
                drawY = (int) (box.y + k * 70);
                switch (cells[i][k]) {
                    case -2:
                        batch.draw(blockedCellImage, drawX, drawY);
                        break;
                    case -1:
                        batch.draw(baseImage, drawX, drawY);
                        break;
                    case 1:
                        batch.draw(redMarkImage, drawX, drawY);
                        break;
                    case 2:
                        batch.draw(blueMarkImage, drawX, drawY);
                        break;
                    case 3:
                        batch.draw(redMarkImage, drawX, drawY);
                        batch.draw(blueInfectedImage, drawX, drawY);
                        break;
                    case 4:
                        batch.draw(blueMarkImage, drawX, drawY);
                        batch.draw(redInfectedImage, drawX, drawY);
                        break;
                }
            }
        }
        batch.end();
    }

    public byte[][] getField(){return cells;}

    public int getFieldSize(){
        System.out.printf("Field size = %d \n", cells.length * cells.length);
        return cells.length;
    }
}

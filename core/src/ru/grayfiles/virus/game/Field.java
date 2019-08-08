package ru.grayfiles.virus.game;

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
    }

    private byte[][] getSavedField(byte type){
        ArrayList<byte[]> tempArray = new ArrayList<>();

        try {
            URL path = ClassLoader.getSystemResource(String.format("fields\\%d.txt", type));
            if(path != null){
                File f = new File(path.toURI());
                BufferedReader savedField = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                String line;

                while ((line = savedField.readLine()) != null) {
                    tempArray.add(line.getBytes());
                }
            }
            else{
                System.out.println(System.getProperty("user.dir"));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
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
        //System.out.printf("player %d x %f y %f", player, x, y);
        //printField();
        if(box.contains(x, y + 1)){
            //System.out.println("Contains");
            cellX = (int) ((x - box.x) / 70);
            cellY = (int) ((y - box.y) / 70);
            //System.out.printf("cellX %d, cellY %d \n", cellX, cellY);
            switch (cells[cellX][cellY]){
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

        System.out.printf("Is First steps %b \n", isFirstSteps);
        if(!isFirstSteps) {
            boolean firstPlayerWin = true;
            boolean secondPlayerWin = true;

            for (byte[] cell : cells)
                for (int k = 0; k < cells[0].length; k++) {
                    if (cell[k] == 1) secondPlayerWin = false;
                    else if (cell[k] == 2) firstPlayerWin = false;
                }
            if (firstPlayerWin) playerWin = 0;
            else if (secondPlayerWin) playerWin = 1;

        }
        System.out.printf("Winned player %d \n", playerWin);
        return playerWin;
    }

    private boolean isCorrectMove(byte player, int cellX, int cellY){
        boolean isCorrect = false;
        byte tempCell;
        for(int i = cellX - 1; i < cellX + 2; i++)
            for(int k = cellY - 1; k < cellY + 2; k++) {
                tempCell = cells[correctIndex(i)][correctIndex(k)];
                //System.out.printf("tempCell %d first %d second %d isCorrect %b \n", tempCell, 1 + player, 4 - player, isCorrect);
                if(tempCell == 1 + player || tempCell == 4 - player) isCorrect = true;
            }
        return isCorrect;
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

    public int getFieldSize(){
        System.out.printf("Field size = %d \n", cells.length * cells.length);
        return cells.length;
    }
}

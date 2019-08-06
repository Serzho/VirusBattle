package ru.grayfiles.virus.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

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
        }

        return tempArray1;
    }

    public void step(byte player, float x, float y){
        if(box.contains(x, y)){
            cellX = (int) ((x - box.x)/70);
            cellY = (int) ((y - box.y)/70);

            switch (cells[cellX][cellY]){
                case 1:
                    cells[cellX][cellY] = (byte) (3 + player);
                    break;
                case 2:
                    cells[cellX][cellY] += (1 + player);
                    break;
                case 3:
                    if(player == 1)cells[cellX][cellY] = 5;
                    break;
                case 4:
                    if(player == 0)cells[cellX][cellY] = 6;
                    break;
            }
        }
    }

    public void draw(SpriteBatch batch){
        batch.begin();
        batch.draw(image, box.x, box.y);

        for(int i = 0; i < cells.length; i++){
            for(int k = 0; k < cells[0].length; k++)
                switch (cells[i][k]) {
                    case 0:
                        batch.draw(blockedCellImage, i * 70 - 35, k * 70 - 35);
                        break;
                    case 1:
                        batch.draw(baseImage, i * 70 - 35, k * 70 - 35);
                        break;
                    case 3:
                        batch.draw(redMarkImage, i * 70 - 35, k * 70 - 35);
                        break;
                    case 4:
                        batch.draw(blueMarkImage, i * 70 - 35, k * 70 - 35);
                        break;
                    case 5:
                        batch.draw(blueMarkImage, i * 70 - 35, k * 70 - 35);
                        batch.draw(redInfectedImage, i * 70 - 35, k * 70 - 35);
                        break;
                    case 6:
                        batch.draw(redMarkImage, i * 70 - 35, k * 70 - 35);
                        batch.draw(blueInfectedImage, i * 70 - 35, k * 70 - 35);
                        break;
                }
        }
        batch.end();
    }

}

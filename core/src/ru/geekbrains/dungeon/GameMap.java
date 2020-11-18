package ru.geekbrains.dungeon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameMap {
    public static final int CELLS_X = 20;
    public static final int CELLS_Y = 20;
    public static final int CELL_SIZE = 60;

    public int getCellsX() {
        return CELLS_X;
    }

    public int getCellsY() {
        return CELLS_Y;
    }

    private byte[][] data;
    private TextureRegion grassTexture;

    private final int TERRAIN_GRASS = 0;
    private final int TERRAIN_WALL = 1;

    public GameMap(TextureAtlas atlas) {
        this.data = new byte[CELLS_X][CELLS_Y];
        this.data[3][3] = TERRAIN_WALL;
        this.grassTexture = atlas.findRegion("grass");
    }

    public boolean isCellPassable(int cx, int cy) {
        if (cx < 0 || cx > getCellsX() - 1 || cy < 0 || cy > getCellsY() - 1) {
            return false;
        }
        if (data[cx][cy] == TERRAIN_WALL) {
            return false;
        }
        return true;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < CELLS_X; i++) {
            for (int j = 0; j < CELLS_Y; j++) {
                if (data[i][j] == TERRAIN_GRASS) {
                    batch.draw(grassTexture, i * CELL_SIZE, j * CELL_SIZE);
                }
            }
        }
    }
}

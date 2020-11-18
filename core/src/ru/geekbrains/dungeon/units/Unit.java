package ru.geekbrains.dungeon.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.dungeon.GameController;
import ru.geekbrains.dungeon.GameMap;

public abstract class Unit {
    GameController gc;
    TextureRegion texture;
    TextureRegion textureHp;
    int hp;
    int hpMax;
    int cellX;
    int cellY;
    Vector2 tmp;

    public int getCellX() {
        return cellX;
    }

    public int getCellY() {
        return cellY;
    }

    public Unit(GameController gc, int cellX, int cellY, int hpMax) {
        this.gc = gc;
        this.hpMax = hpMax;
        this.hp = hpMax;
        this.cellX = cellX;
        this.cellY = cellY;
        this.tmp = new Vector2(0, 0);
    }

    public boolean takeDamage(int amount) {
        hp -= amount;
        return hp <= 0;
    }

    public abstract void update(float dt);

    public void render(SpriteBatch batch) {
        batch.draw(texture, cellX * GameMap.CELL_SIZE, cellY * GameMap.CELL_SIZE);
        batch.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        batch.draw(textureHp, cellX * GameMap.CELL_SIZE + 1, cellY * GameMap.CELL_SIZE + 51, 58, 10);
        batch.setColor(0.7f, 0.0f, 0.0f, 1.0f);
        batch.draw(textureHp, cellX * GameMap.CELL_SIZE + 2, cellY * GameMap.CELL_SIZE + 52, 56, 8);
        batch.setColor(0.0f, 1.0f, 0.0f, 1.0f);
        batch.draw(textureHp, cellX * GameMap.CELL_SIZE + 2, cellY * GameMap.CELL_SIZE + 52, (float) hp / hpMax * 56, 8);
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}

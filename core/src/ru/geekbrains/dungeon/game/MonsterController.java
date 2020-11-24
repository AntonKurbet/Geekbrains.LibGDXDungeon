package ru.geekbrains.dungeon.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.geekbrains.dungeon.helpers.ObjectPool;

public class MonsterController extends ObjectPool<Monster> {
    private GameController gc;

    public MonsterController(GameController gc) {
        this.gc = gc;
    }

    @Override
    protected Monster newObject() {
        return new Monster(gc);
    }

    public void activate(int cellX, int cellY) {
        getActiveElement().activate(cellX, cellY);
    }

    public Monster getMonsterInCell(int cellX, int cellY) {
        for (Monster m : getActiveList()) {
            if (m.getCellX() == cellX && m.getCellY() == cellY) {
                return m;
            }
        }
        return null;
    }

    public void update(float dt) {
        for (Monster m : getActiveList()) {
            m.update(dt);
        }
        checkPool();
    }

    public void render(SpriteBatch batch, BitmapFont font18) {
        for (Monster m : getActiveList()) {
            m.render(batch, font18);
        }
    }
}

package ru.geekbrains.dungeon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.geekbrains.dungeon.units.Monster;

public class MonsterController {
    private static final int MAX_MONSTERS = 100;

    private GameController gc;
    private Monster[] monsters;

    public Monster[] getMonsters() {
        return monsters;
    }

    public MonsterController(GameController gc, TextureAtlas atlas) {
        this.gc = gc;
        this.monsters = new Monster[MAX_MONSTERS];
        for (int i = 0; i < monsters.length; i++) {
            monsters[i] = new Monster(atlas, gc);
        }
    }

    public void activate(int cellX, int cellY) {
        for (Monster m : monsters) {
            if (!m.isActive()) {
                m.activate(cellX, cellY);
                return;
            }
        }
    }

    public Monster getMonsterInCell(int cellX, int cellY) {
        for (Monster m : monsters) {
            if (m.isActive()) {
                if (m.getCellX() == cellX && m.getCellY() == cellY) {
                    return m;
                }
            }
        }
        return null;
    }

    public void update(float dt) {
        for (Monster m : monsters) {
            if (m.isActive()) {
                m.update(dt);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Monster m : monsters) {
            if (m.isActive()) {
                m.render(batch);
            }
        }
    }
}

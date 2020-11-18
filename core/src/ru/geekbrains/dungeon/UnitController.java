package ru.geekbrains.dungeon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.dungeon.units.Hero;
import ru.geekbrains.dungeon.units.Monster;
import ru.geekbrains.dungeon.units.Unit;

import java.util.ArrayList;
import java.util.List;

public class UnitController {
    private GameController gc;
    private MonsterController monsterController;
    private Hero hero;
    private Unit currentUnit;
    private int index;
    private List<Unit> allUnits;

    public MonsterController getMonsterController() {
        return monsterController;
    }

    public Hero getHero() {
        return hero;
    }

    public boolean isItMyTurn(Unit unit) {
        return currentUnit == unit;
    }

    public boolean isCellFree(int cellX, int cellY) {
        for (Unit u : allUnits) {
            if (u.getCellX() == cellX && u.getCellY() == cellY) {
                return false;
            }
        }
        return true;
    }

    public UnitController(GameController gc, TextureAtlas atlas) {
        this.gc = gc;
        this.hero = new Hero(atlas, gc);
        this.monsterController = new MonsterController(gc, atlas);
    }

    public void init() {
        this.monsterController.activate(5, 5);
        this.monsterController.activate(9, 5);
        this.index = -1;
        this.allUnits = new ArrayList<>();
        this.allUnits.add(hero);
        for (Monster m : monsterController.getMonsters()) {
            if (m.isActive()) {
                this.allUnits.add(m);
            }
        }
        this.nextTurn();
    }

    public void nextTurn() {
        index++;
        if (index >= allUnits.size()) {
            index = 0;
        }
        currentUnit = allUnits.get(index);
        currentUnit.startTurn();
    }

    public void render(SpriteBatch batch) {
        hero.render(batch);
        monsterController.render(batch);
    }

    public void update(float dt) {
        hero.update(dt);
        monsterController.update(dt);

        if (!currentUnit.isActive() || currentUnit.getTurns() == 0) {
            nextTurn();
        }
    }

    public void removeUnitAfterDeath(Unit unit) {
        int unitIndex = allUnits.indexOf(unit);
        allUnits.remove(unit);
        if (unitIndex <= index) {
            index--;
        }
    }
}

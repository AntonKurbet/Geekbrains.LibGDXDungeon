package ru.geekbrains.dungeon.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.dungeon.BattleCalc;
import ru.geekbrains.dungeon.GameController;
import ru.geekbrains.dungeon.GameMap;

public abstract class Unit {
    GameController gc;
    TextureRegion texture;
    TextureRegion textureHp;
    int damage;
    int defence;
    int hp;
    int hpMax;
    int cellX;
    int cellY;
    int attackRange;
    float movementTime;
    float movementMaxTime;
    int targetX, targetY;
    int turns, maxTurns;

    public int getDefence() {
        return defence;
    }

    public int getDamage() {
        return damage;
    }

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
        this.targetX = cellX;
        this.targetY = cellY;
        this.damage = 2;
        this.defence = 1;
        this.maxTurns = 5;
        this.movementMaxTime = 0.2f;
        this.attackRange = 2;
    }

    public void startTurn() {
        turns = maxTurns;
    }

    public boolean isActive() {
        return hp > 0;
    }

    public boolean takeDamage(int amount) {
        hp -= amount;
        if (hp <= 0) {
            gc.getUnitController().removeUnitAfterDeath(this);
        }
        return hp <= 0;
    }

    public boolean canIMakeAction() {
        return gc.getUnitController().isItMyTurn(this) && turns > 0 && isStayStill();
    }

    public boolean isStayStill() {
        return cellY == targetY && cellX == targetX;
    }

    public void goTo(int argCellX, int argCellY) {
        if (!gc.getGameMap().isCellPassable(argCellX, argCellY) || !gc.getUnitController().isCellFree(argCellX, argCellY)) {
            return;
        }
        if (Math.abs(argCellX - cellX) + Math.abs(argCellY - cellY) == 1) {
            targetX = argCellX;
            targetY = argCellY;
        }
    }

    public boolean canIAttackThisTarget(Unit target) {
        return cellX - target.getCellX() == 0 && Math.abs(cellY - target.getCellY()) <= attackRange ||
                cellY - target.getCellY() == 0 && Math.abs(cellX - target.getCellX()) <= attackRange;
    }

    public void attack(Unit target) {
        target.takeDamage(BattleCalc.attack(this, target));
        this.takeDamage(BattleCalc.checkCounterAttack(this, target));
        turns--;
    }

    public void update(float dt) {
        if (!isStayStill()) {
            movementTime += dt;
            if (movementTime > movementMaxTime) {
                movementTime = 0;
                cellX = targetX;
                cellY = targetY;
                turns--;
            }
        }
    }

    public void render(SpriteBatch batch) {
        float px = cellX * GameMap.CELL_SIZE;
        float py = cellY * GameMap.CELL_SIZE;
        if (!isStayStill()) {
            px = cellX * GameMap.CELL_SIZE + (targetX - cellX) * (movementTime / movementMaxTime) * GameMap.CELL_SIZE;
            py = cellY * GameMap.CELL_SIZE + (targetY - cellY) * (movementTime / movementMaxTime) * GameMap.CELL_SIZE;
        }
        batch.draw(texture, px, py);
        batch.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        batch.draw(textureHp, px + 1, py + 51, 58, 10);
        batch.setColor(0.7f, 0.0f, 0.0f, 1.0f);
        batch.draw(textureHp, px + 2, py + 52, 56, 8);
        batch.setColor(0.0f, 1.0f, 0.0f, 1.0f);
        batch.draw(textureHp, px + 2, py + 52, (float) hp / hpMax * 56, 8);
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public int getTurns() {
        return turns;
    }
}

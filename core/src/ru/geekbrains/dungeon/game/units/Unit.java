package ru.geekbrains.dungeon.game.units;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import lombok.Data;
import ru.geekbrains.dungeon.game.BattleCalc;
import ru.geekbrains.dungeon.game.GameController;
import ru.geekbrains.dungeon.game.GameMap;
import ru.geekbrains.dungeon.game.Weapon;
import ru.geekbrains.dungeon.helpers.Assets;
import ru.geekbrains.dungeon.helpers.Poolable;

@Data
public abstract class Unit implements Poolable {
    public enum Direction {
        LEFT(0), RIGHT(1), UP(2), DOWN(3);

        int animationRowIndex;

        Direction(int animationRowIndex) {
            this.animationRowIndex = animationRowIndex;
        }

        public static Direction getMoveDirection(int fromX, int fromY, int toX, int toY) {
            int dx = toX - fromX;
            int dy = toY - fromY;
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx < 0) {
                    return LEFT;
                } else if (dx > 0) {
                    return RIGHT;
                }
            }
            if (Math.abs(dx) < Math.abs(dy)) {
                if (dy < 0) {
                    return DOWN;
                } else if (dy > 0) {
                    return UP;
                }
            }
            return DOWN;
        }
    }

    GameController gc;
    TextureRegion[][] textures;
    TextureRegion textureHp;
    Stats stats;

    int cellX;
    int cellY;
    int gold;
    float movementTime;
    float movementMaxTime;
    int targetX, targetY;
    Weapon weapon;

    float innerTimer;
    StringBuilder stringHelper;
    Direction currentDirection;

    float timePerFrame;
    float walkingTime;

    public Unit(GameController gc, int cellX, int cellY, int hpMax, String textureName) {
        this.gc = gc;
        this.stats = new Stats(hpMax, 2, 1, 1, 5, 1, 5);
        this.cellX = cellX;
        this.cellY = cellY;
        this.targetX = cellX;
        this.targetY = cellY;
        this.movementMaxTime = 0.4f;
        this.timePerFrame = 0.1f;
        this.innerTimer = MathUtils.random(1000.0f);
        this.stringHelper = new StringBuilder();
        this.gold = MathUtils.random(1, 5);
        this.textures = Assets.getInstance().getAtlas().findRegion(textureName).split(60, 60);
        this.currentDirection = Direction.DOWN;
    }

    public void addGold(int amount) {
        gold += amount;
    }

    public void cure(int amount) {
        stats.restoreHp(amount);
    }

    public void startTurn() {
        stats.restorePoints();
    }

    public void startRound() {
        cure(1);
    }

    @Override
    public boolean isActive() {
        return stats.hp > 0;
    }

    public boolean takeDamage(Unit source, int amount) {
        stats.hp -= amount;
        if (stats.hp <= 0) {
            gc.getUnitController().removeUnitAfterDeath(this);
            gc.getGameMap().generateDrop(cellX, cellY, 1);
        }
        return stats.hp <= 0;
    }

    public boolean canIMakeAction() {
        return gc.getUnitController().isItMyTurn(this) && getStats().doIHaveAnyPoints() && isStayStill();
    }

    public boolean isStayStill() {
        return cellY == targetY && cellX == targetX;
    }

    public void goTo(int argCellX, int argCellY) {
        if (!gc.getGameMap().isCellPassable(argCellX, argCellY)
            || !gc.getUnitController().isCellFree(argCellX, argCellY)
            || gc.getGameMap().getMoveCost(argCellX, argCellY) > stats.movePoints)  {
            return;
        }
        if (stats.movePoints > 0 && Math.abs(argCellX - cellX) + Math.abs(argCellY - cellY) == 1) {
            targetX = argCellX;
            targetY = argCellY;
            currentDirection = Direction.getMoveDirection(cellX, cellY, targetX, targetY);
        }
    }

    public boolean canIAttackThisTarget(Unit target, int cost) {
        return stats.attackPoints >= cost && (cellX - target.getCellX() == 0 && Math.abs(cellY - target.getCellY()) <= weapon.getRadius() ||
                cellY - target.getCellY() == 0 && Math.abs(cellX - target.getCellX()) <= weapon.getRadius());
    }

    public void attack(Unit target) {
        currentDirection = Direction.getMoveDirection(cellX, cellY, target.cellX, target.cellY);
        target.takeDamage(this, BattleCalc.attack(this, target));
        if (target.canIAttackThisTarget(this, 0)) {
            this.takeDamage(target, BattleCalc.checkCounterAttack(this, target));
        }
        stats.attackPoints--;
    }

    public void update(float dt) {
        innerTimer += dt;
        if (!isStayStill()) {
            movementTime += dt;
            walkingTime += dt;
            if (movementTime > movementMaxTime) {
                movementTime = 0;
                cellX = targetX;
                cellY = targetY;
                stats.movePoints -= gc.getGameMap().getMoveCost(targetX,targetY);
                gc.getGameMap().checkAndTakeDrop(this);
            }
        }
    }

    public void render(SpriteBatch batch, BitmapFont font18) {
        float hpAlpha = stats.hp == stats.maxHp ? 0.4f : 1.0f;

        float px = cellX * GameMap.CELL_SIZE;
        float py = cellY * GameMap.CELL_SIZE;

        if (!isStayStill()) {
            px = cellX * GameMap.CELL_SIZE + (targetX - cellX) * (movementTime / movementMaxTime) * GameMap.CELL_SIZE;
            py = cellY * GameMap.CELL_SIZE + (targetY - cellY) * (movementTime / movementMaxTime) * GameMap.CELL_SIZE;
        }

        int frameIndex = (int) (walkingTime / timePerFrame) % textures[0].length;
        batch.draw(textures[currentDirection.animationRowIndex][frameIndex], px, py);
        batch.setColor(0.0f, 0.0f, 0.0f, hpAlpha);

        float barX = px, barY = py + MathUtils.sin(innerTimer * 5.0f) * 2;
        batch.draw(textureHp, barX + 1, barY + 51, 58, 10);
        batch.setColor(0.7f, 0.0f, 0.0f, hpAlpha);
        batch.draw(textureHp, barX + 2, barY + 52, 56, 8);
        batch.setColor(0.0f, 1.0f, 0.0f, hpAlpha);
        batch.draw(textureHp, barX + 2, barY + 52, (float) stats.hp / stats.maxHp * 56, 8);
        batch.setColor(1.0f, 1.0f, 1.0f, hpAlpha);
        stringHelper.setLength(0);
        stringHelper.append(stats.hp);
        font18.setColor(1.0f, 1.0f, 1.0f, hpAlpha);
        font18.draw(batch, stringHelper, barX, barY + 64, 60, 1, false);

        font18.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        if (gc.getUnitController().isItMyTurn(this)) {
            stringHelper.setLength(0);
            stringHelper.append("MP: ").append(stats.movePoints).append(" AP: ").append(stats.attackPoints);
            font18.draw(batch, stringHelper, barX, barY + 80, 60, 1, false);
        }
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        if (gc.getCursorX() == cellX && gc.getCursorY() == cellY) {
            stringHelper.setLength(0);
            stringHelper.append("Vision: ").append(stats.getVisionRadius()).append("\n")
                        .append("Defence:").append(stats.getDefence()).append("\n")
                        .append("Max HP:").append(stats.getMaxHp());
            font18.draw(batch, stringHelper, barX + GameMap.CELL_SIZE, barY + GameMap.CELL_SIZE, 60, 1, false);
        };

    }


    public boolean amIBlocked() {
        return !(gc.isCellEmpty(cellX - 1, cellY) || gc.isCellEmpty(cellX + 1, cellY) || gc.isCellEmpty(cellX, cellY - 1) || gc.isCellEmpty(cellX, cellY + 1));
    }
}

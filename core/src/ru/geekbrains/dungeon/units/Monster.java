package ru.geekbrains.dungeon.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.dungeon.GameController;

public class Monster extends Unit {
    private float aiBrainsImplseTime;
    private Unit target;

    public Monster(TextureAtlas atlas, GameController gc) {
        super(gc, 5, 2, 10);
        this.texture = atlas.findRegion("monster");
        this.textureHp = atlas.findRegion("hp");
        this.hp = -1;
    }

    public void activate(int cellX, int cellY) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.targetX = cellX;
        this.targetY = cellY;
        this.hpMax = 10;
        this.hp = hpMax;
        this.target = gc.getUnitController().getHero();
    }

    public void update(float dt) {
        super.update(dt);
        if (canIMakeAction()) {
            if (isStayStill()) {
                aiBrainsImplseTime += dt;
            }
            if (aiBrainsImplseTime > 0.4f) {
                aiBrainsImplseTime = 0.0f;
                if (canIAttackThisTarget(target)) {
                    attack(target);
                } else if (canISeeTarget(target)) {
                    iAmAnger();
                    tryToMove(target);
                } else {
                    iAmNotAnger();
                    tryToRandomMove();
                }
            }
        }
    }

    private void iAmNotAnger() {
        moveCounterColor = Color.BLUE;
    }

    private void iAmAnger() {
        moveCounterColor = Color.RED;
    }

    private boolean canISeeTarget(Unit target) {
        for (int i = cellX - viewRange; i <= cellX + viewRange; i++) {
            for (int j = cellY - viewRange; j <= cellY + viewRange; j++) {
                if (target.cellX == i && target.cellY == j)
                    return true;
            }
        }
        return false;
    }

    private void tryToRandomMove() {
        int moveX, moveY;
        do {
            moveX = (int) (cellX + Math.round(Math.random()) * Math.round(1 - Math.random() * 2));
            moveY = (int) (cellY + Math.round(Math.random()) * Math.round(1 - Math.random() * 2));
        }  while (moveX == 0 && moveY == 0
                && gc.getGameMap().isCellPassable(moveX, moveY)
                && gc.getUnitController().isCellFree(moveX, moveY)) ;
        goTo(moveX, moveY);
    }

    public void tryToMove(Unit target) {
        int bestX = -1, bestY = -1;
        float bestDst = 10000;
        for (int i = cellX - 1; i <= cellX + 1; i++) {
            for (int j = cellY - 1; j <= cellY + 1; j++) {
                if (Math.abs(cellX - i) + Math.abs(cellY - j) == 1 && gc.getGameMap().isCellPassable(i, j) && gc.getUnitController().isCellFree(i, j)) {
                    float dst = (float) Math.sqrt((i - target.getCellX()) * (i - target.getCellX()) + (j - target.getCellY()) * (j - target.getCellY()));
                    if (dst < bestDst) {
                        bestDst = dst;
                        bestX = i;
                        bestY = j;
                    }
                }
            }
        }
        goTo(bestX, bestY);
    }
}

package ru.geekbrains.dungeon.game;

import com.badlogic.gdx.math.MathUtils;

public class BattleCalc {
    public static int attack(Unit attacker, Unit target) {
        int out = attacker.getDamage();
        out -= target.getDefence();
        if (out < 0) {
            out = 0;
        }
        return out;
    }

    public static int checkCounterAttack(Unit attacker, Unit target) {
        if (MathUtils.random() < 0.2f) {
            return attack(target, attacker);
        }
        return 0;
    }

    public static int getCoins(Unit target) {
        // может быть нужно gc.getUnitController().getHero() анализировать, но пока тут нет gc
        if (target.getClass().getSimpleName().equals("Monster"))
            return 1 + MathUtils.random(2);
        else
            return 0;
    }
}

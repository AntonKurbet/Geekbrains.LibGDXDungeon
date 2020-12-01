package ru.geekbrains.dungeon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.geekbrains.dungeon.game.GameController;
import ru.geekbrains.dungeon.game.GameMap;
import ru.geekbrains.dungeon.helpers.Assets;
import ru.geekbrains.dungeon.screens.ScreenManager;

public class DungeonGame extends Game {
    private SpriteBatch batch;

    // Домашнее задание:
    // 1. Разобраться с кодом
    // 2. Добавьте клеткам стоимость перехода и подшейте это к логике юнитов
    // 3. На карте случайно разместите сколько-то клеток стоимость 2, при
    // отрисовке их можно сделать более темными
    // 4. При наведении на персонажей, показывать их характеристики
    // 5. * Добавьте доспехи/броню с сопротивляемостью к разным видам оружия


    // Идея механики:
    // 1. На ваш ход выбатает случайный набор действий (2 шага, 4 атаки, 1 спец действие)
    // 2. На движение тратится по 1 шаг
    // 3. На обычную атаку требуется 1 атака, на усиленную 3 атаки
    // 4. Спец действия тратятся на лечение/приготовление/крафт/...
    // 5. Можно выбирать оружие, на каждого монстра оружие действует по разному, даже в рамках одного класса
    // 6. Добавить туман войны
    // *. Для монстров то же самое

    // Идеи по улучшению игры:
    // 1. Можно добавить конвертацию очков действий (например, 2 очка передвижения
    // можно потратить на 1 атаку и наоборот).
    // 2. Добавить кнопку «Ожидание», при нажатии на которую, герой перед своим ходом
    // будет либо ждать конца хода всех монстров, либо пропускать ход и получать бонусы
    // (ходы, атаки) к следующему ходу.
    // 3. Установить «линии» с увеличением силы монстров. Например, с какой-то
    // координаты по Х и Y (или с определённого количества клеток от героя)
    // монстры становятся сильнее.

    @Override
    public void create() {
        batch = new SpriteBatch();
        ScreenManager.getInstance().init(this, batch);
        ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float dt = Gdx.graphics.getDeltaTime();
        getScreen().render(dt);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

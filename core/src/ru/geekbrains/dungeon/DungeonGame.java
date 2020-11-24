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
    // 1. Разобраться с кодом ( в пакете ru.geekbrains.dungeon.game )
    // 2. Необходимо вывести на экран: имя персонажа, количество монет
    // 3. Если жизнь персонажа 100% то полоска жизни должна отрисовываться с альфа 0.2
    // 4. При убийстве монстра персонаж может получить 1-3 монеты
    // 5. Попробуйте посчитать раунды ( каждый раз, когда ход переходит к игроку
    // номер раунда должен увеличиваться )
    // 6. В начале 3 раунда должен появиться новый монстр ( * каждого третьего )
    // 7. В начале хода персонажи восстанавливают 1 хп

    @Override
    public void create() {
        batch = new SpriteBatch();
        ScreenManager.getInstance().init(this, batch);
        ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.GAME);
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

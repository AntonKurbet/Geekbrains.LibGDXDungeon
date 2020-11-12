package ru.geekbrains.dungeon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class DungeonGame extends ApplicationAdapter {
    private TextureAtlas atlas;
    private SpriteBatch batch;
    private ProjectileController projectileController;
    private GameMap gameMap;
    private Hero hero;

    // Домашнее задание:
    // 0. Разобраться со структурой кода
    // 1. Кнопкой Q необходимо переключать режим стрельбы: либо стреляем по одному снаряду,
    // либо по 2
    // 2. На векторах сделайте движение в разные стороны (влево, вверх, вниз, вправо),
    // с запретом на выезд за пределы карты
    // 3. * Снаряд должен выпускаться в сторону последнего движения

    @Override
    public void create() {
        batch = new SpriteBatch();
        atlas = new TextureAtlas("images/game.pack");
        projectileController = new ProjectileController(atlas);
        hero = new Hero(atlas, projectileController);
        gameMap = new GameMap(atlas);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        gameMap.render(batch);
        hero.render(batch);
        projectileController.render(batch);
        batch.end();
    }

    public void update(float dt) {
        projectileController.update(dt);
        hero.update(dt);
    }

    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
    }
}

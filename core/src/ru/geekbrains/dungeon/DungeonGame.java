package ru.geekbrains.dungeon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DungeonGame extends ApplicationAdapter {
    private TextureAtlas atlas;
    private TextureRegion cursorTexture;
    private SpriteBatch batch;
    private GameController gameController;

    // Домашнее задания:
    // 1. Разорабаться с кодом;
    // 2. Монстры охотятся за героем, только если он находится в радиусе N клеток (пусть 5),
    // в противном случае, бегают на случайную клетку
    // > Если п. 2 окажется сложным - можете скинуть в дз пачку вопросов, что не ясно

    @Override
    public void create() {
        batch = new SpriteBatch();
        atlas = new TextureAtlas("images/game.pack");
        cursorTexture = atlas.findRegion("cursor");
        gameController = new GameController(atlas);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        gameController.update(dt);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        gameController.getGameMap().render(batch);
        gameController.getUnitController().render(batch);
        gameController.getProjectileController().render(batch);
        batch.setColor(1,1,1,0.5f);
        batch.draw(cursorTexture, gameController.getCursorX() * GameMap.CELL_SIZE, gameController.getCursorY() * GameMap.CELL_SIZE);
        batch.setColor(1,1,1,1);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
    }
}

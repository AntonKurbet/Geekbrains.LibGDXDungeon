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
    // 2. Добавить герою параметр опыт, при уничтожении монстра опыт должен повыситься на 1;
    // 3. Добавьте счетчик ходов (5 штук), отобразите его возле персонада, каждый шаг
    // уменьшает счетчик, как только он падает до нуля, то сразу же восстанаваливается;
    // 4. * С вероятностью 25% монстр может стукнуть в ответ;

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
        gameController.getHero().render(batch);
        gameController.getMonsterController().render(batch);
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

package ru.geekbrains.dungeon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.PropertiesUtils;
import ru.geekbrains.dungeon.game.GameController;
import ru.geekbrains.dungeon.game.WorldRenderer;
import ru.geekbrains.dungeon.helpers.Assets;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class MenuScreen extends AbstractScreen {
    private Stage stage;
    private BitmapFont font72;
    private BitmapFont font36;

    public MenuScreen(SpriteBatch batch) {
        super(batch);
    }

    @Override
    public void show() {
        font72 = Assets.getInstance().getAssetManager().get("fonts/font72.ttf");
        font36 = Assets.getInstance().getAssetManager().get("fonts/font36.ttf");
        createGui();
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font72.draw(batch, "Dungeon", 0, 500, 1280, Align.center, false);
        batch.end();
        stage.draw();
    }

    public void createGui() {
        stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());

        TextButton.TextButtonStyle menuBtnStyle = new TextButton.TextButtonStyle(
                skin.getDrawable("simpleButton"), null, null, font36);

        ObjectMap<String, String> props = new ObjectMap<>();
        try {
            PropertiesUtils.load(props, Gdx.files.internal("gui/menu.txt").reader());
        } catch (IOException e) {
            throw new RuntimeException("Unable to load gui/menu.txt");
        }

        final TextButton btnNewGame = new TextButton("New Game", menuBtnStyle);
        btnNewGame.setPosition(Integer.parseInt(props.get("btn_new_game_x")), Integer.parseInt(props.get("btn_new_game_y")));

        TextButton btnExitGame = new TextButton("Exit Game", menuBtnStyle);
        btnExitGame.setPosition(480, 200);

        btnNewGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.GAME);
            }
        });

        btnExitGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(btnNewGame);
        stage.addActor(btnExitGame);
        skin.dispose();
    }

    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

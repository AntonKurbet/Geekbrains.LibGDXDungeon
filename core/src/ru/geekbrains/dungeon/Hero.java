package ru.geekbrains.dungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Hero {
    private ProjectileController projectileController;
    private Vector2 position;
    private TextureRegion texture;

    public Hero(TextureAtlas atlas, ProjectileController projectileController) {
        this.position = new Vector2(100, 100);
        this.texture = atlas.findRegion("tank");
        this.projectileController = projectileController;
    }

    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            projectileController.activate(position.x, position.y, 200, 0);
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 20, position.y - 20);
    }
}

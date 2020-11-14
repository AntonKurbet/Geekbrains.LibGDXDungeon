package ru.geekbrains.dungeon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ProjectileController {
    private static final int MAX_PROJECTILES = 100;
    private Projectile[] items;
    private int numShots;

    public void setNumShots(int numShots) {
        this.numShots = numShots;
    }
    public int getNumShots() {
        return numShots;
    }

    public Projectile[] getItems() {
        return items;
    }

    public ProjectileController(TextureAtlas atlas) {
        this.items = new Projectile[MAX_PROJECTILES];
        this.numShots = 1;
        TextureRegion region = atlas.findRegion("projectile");
        for (int i = 0; i < items.length; i++) {
            items[i] = new Projectile(region);
        }
    }

    public void activate(float x, float y, float vx, float vy) {
        int shot = numShots;
        for (Projectile p : items) {
            if (!p.isActive()) {
                p.activate(x + shot * vx / 10, y + shot * vy / 10, vx, vy);
                shot--;
                if (shot == 0) return;
            }
        }
    }

    public void update(float dt) {
        for (Projectile p : items) {
            if (p.isActive()) {
                p.update(dt);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Projectile p : items) {
            if (p.isActive()) {
                p.render(batch);
            }
        }
    }
}

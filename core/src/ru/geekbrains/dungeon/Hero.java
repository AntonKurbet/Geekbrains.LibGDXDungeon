package ru.geekbrains.dungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Hero {
    private static final float SPEED_Y = 100;
    private static final float SPEED_X = 100;
    public static final int CELLS_X = 20;
    public static final int CELLS_Y = 20;
    public static final int CELL_SIZE = 40;

    private ProjectileController projectileController;
    private Vector2 position;
    private Vector2 positionCell;
    private Vector2 targetCell;
    private Vector2 targetPosition;
    private TextureRegion texture;
    private Vector2 velocity;
    private Vector2 velocityCell;
    private float angle;

    public Hero(TextureAtlas atlas, ProjectileController projectileController) {
        this.positionCell = new Vector2(0, 0);
        this.position = new Vector2();
        this.position = position.set(positionCell).scl(CELL_SIZE).add(20,20);
        this.targetCell = new Vector2();
        this.targetPosition = new Vector2();
        this.velocity = new Vector2(0,0);
        this.velocityCell = new Vector2(0,0);
        this.texture = atlas.findRegion("tank");
        this.projectileController = projectileController;
    }

    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            projectileController.activate(position.x + 20, position.y + 20, velocityCell.x * 200, velocityCell.y * 200);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            projectileController.setNumShots(projectileController.getNumShots() == 1 ? 2 : 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            velocity.set(0, SPEED_Y);
            angle = 0;
            cellCalc();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            velocity.set(0, -SPEED_Y);
            cellCalc();
            angle = 180;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            velocity.set(-SPEED_X,0);
            cellCalc();
            angle = 270;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            velocity.set(SPEED_X,0);
            cellCalc();
            angle = 90;
        }

        if ((targetCell.x >= 0) && (targetCell.x <= CELLS_X) && (targetCell.y >= 0) && (targetCell.y <= CELLS_Y)) {
            if (position.epsilonEquals(targetPosition,1)) {
                velocity.set(0, 0);
                positionCell.set(targetCell);
            } else position.mulAdd(velocity, dt);
        } else velocity.set(0, 0);
    }

    private void cellCalc() {
        velocityCell.set(velocity).nor();
        targetCell.set(positionCell).add(velocityCell);
        targetPosition.set(targetCell).scl(CELL_SIZE).add(20,20);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }
}

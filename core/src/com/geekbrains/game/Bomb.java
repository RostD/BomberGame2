package com.geekbrains.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Bomb implements Poolable {
    private TextureRegion texture;
    private AnimationEmitter animationEmitter;
    private int cellX, cellY;
    private int radius;
    private float time;
    private float maxTime;
    private boolean active;

    @Override
    public boolean isActive() {
        return active;
    }

    public Bomb(AnimationEmitter animationEmitter, TextureRegion texture) {
        this.texture = texture;
        this.animationEmitter = animationEmitter;
    }

    public void update(float dt) {
        time += dt;
        if (time >= maxTime) {
            boom();
        }
    }

    public void boom() {
        active = false;
        animationEmitter.createAnimation(cellX * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, cellY * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, 4.0f, AnimationEmitter.AnimationType.EXPLOSION);
        for (int i = 1; i <= 5; i++) {
            animationEmitter.createAnimation((cellX + i) * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, cellY * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, 4.0f, AnimationEmitter.AnimationType.EXPLOSION);
            animationEmitter.createAnimation((cellX - i) * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, cellY * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, 4.0f, AnimationEmitter.AnimationType.EXPLOSION);
            animationEmitter.createAnimation(cellX * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, (cellY + i) * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, 4.0f, AnimationEmitter.AnimationType.EXPLOSION);
            animationEmitter.createAnimation(cellX * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, (cellY - i) * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, 4.0f, AnimationEmitter.AnimationType.EXPLOSION);
        }
    }

    public void activate(int cellX, int cellY, float maxTime, int radius) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.maxTime = maxTime;
        this.radius = radius;
        this.time = 0.0f;
        this.active = true;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, cellX * Rules.CELL_SIZE, cellY * Rules.CELL_SIZE);
    }
}

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
    private Map map;

    @Override
    public boolean isActive() {
        return active;
    }

    public Bomb(AnimationEmitter animationEmitter, TextureRegion texture) {
        this.texture = texture;
        this.animationEmitter = animationEmitter;
        this.map = Map.getInstance();
    }

    public void update(float dt) {
        time += dt;
        if (time >= maxTime) {
            this.map.clearCell(cellX,cellY);
        }
    }

    public void boom() {
        active = false;
        animationEmitter.createAnimation(cellX * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, cellY * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, 4.0f, AnimationEmitter.AnimationType.EXPLOSION);

        int[] ways = new int[]{1,1,1,1};

        for (int i = 1; i <= 5; i++) {
            for (int j = 0; j < ways.length; j++) {
                if(ways[j] != 1) {
                    continue;
                }

                int x = cellX;
                int y = cellY;
                switch (j){
                    case(0): x += i;break;
                    case(1): x -= i;break;
                    case(2): y += i;break;
                    case(3): y -= i;break;
                }

                if(! map.isCellEmpty(x,y) && map.isCellDestructable(x,y)) {
                    map.clearCell(x,y);
                    animationEmitter.createAnimation(x * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, y * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, 4.0f, AnimationEmitter.AnimationType.EXPLOSION);
                    ways[j] = 0;
                } else if(map.isCellEmpty(x,y)) {
                    animationEmitter.createAnimation(x * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, y * Rules.CELL_SIZE + Rules.CELL_HALF_SIZE, 4.0f, AnimationEmitter.AnimationType.EXPLOSION);
                } else {
                    ways[j] = 0;
                }
            }
        }
    }

    public void activate(int cellX, int cellY, float maxTime, int radius) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.maxTime = maxTime;
        this.radius = radius;
        this.time = 0.0f;
        this.map.setBomb(cellX,cellY,this);
        this.active = true;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, cellX * Rules.CELL_SIZE, cellY * Rules.CELL_SIZE);
    }
}

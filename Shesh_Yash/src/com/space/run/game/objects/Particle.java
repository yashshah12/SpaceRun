package com.space.run.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.space.run.game.Assets;

/**
 * Created by Shesh on 1/3/2015.
 */
public class Particle extends GameObject {

    private float lifeTime, alpha;

    public Particle(Vector2 position) {
        this.position = position;

        int angle = MathUtils.random(0, 360);
        float vx = (float) Math.cos(angle);
        float vy = (float) Math.sin(angle);

        this.velocity = new Vector2(vx, vy).scl(MathUtils.random(200, 300));

        this.lifeTime = MathUtils.random(0.25f, 0.5f);
        this.width = 14.1f;
        this.height = 6;
        this.alpha = MathUtils.random(0.3f, 1f);
    }

    @Override
    public void update(float speed) {
       position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime()));
       recBounds.set(position.x, position.y, width, height);

        lifeTime -= speed;
        alpha -= speed;

        if(lifeTime < 0 || alpha < 0)
        {
            flagForRemoval();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(0, 1, 1, alpha);
        batch.draw(Assets.texRegBullet1, position.x, position.y, width, height);
        batch.setColor(1, 1, 1, 1);
    }
}

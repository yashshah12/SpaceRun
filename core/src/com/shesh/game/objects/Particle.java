package com.shesh.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.shesh.game.Assets;
import com.shesh.game.SpaceRun;

/**
 * Created by Shesh on 1/3/2015.
 */
public class Particle extends GameObject {

    public static final int PARTICLE_MIN_ANGLE = 0;
    public static final int PARTICLE_MAX_ANGLE = 360;
    public static final float PARTICLE_MIN_VELOCITY = 200;
    public static final float PARTICLE_MAX_VELOCITY = 300;
    public static final float PARTICLE_MIN_LIFETIME = 0.25f;
    public static final float PARTICLE_MAX_LIFETIME = 0.5f;
    public static final float PARTICLE_MIN_ALPHA = 0.3f;
    public static final float PARTICLE_MAX_ALPHA = 1f;
    public static final float PARTICLE_WIDTH = 5;
    public static final float PARTICLE_HEIGHT = 5;

    private TextureRegion texture;
    private float lifeTime, alpha;
    private Color color;

    public Particle(Vector2 position) {
        this(position, new Color(1, 1, 1, 1));
    }

    public Particle(Vector2 position, Color color) {
        this(position, color, Assets.texRegBullet1);
    }

    public Particle(Vector2 position, Color color, TextureRegion texture) {
        this(position, color, texture, PARTICLE_WIDTH, PARTICLE_HEIGHT);
    }

    public Particle(Vector2 position, Color color, TextureRegion texture, float width, float height) {
        this.position = position;
        this.texture = texture;

        float angle = (float) Math.toRadians(MathUtils.random(PARTICLE_MIN_ANGLE, PARTICLE_MAX_ANGLE));
        float vx = (float) Math.cos(angle);
        float vy = (float) Math.sin(angle);

        this.velocity = new Vector2(vx, vy).scl(MathUtils.random(PARTICLE_MIN_VELOCITY, PARTICLE_MAX_VELOCITY));
        this.lifeTime = MathUtils.random(PARTICLE_MIN_LIFETIME, PARTICLE_MAX_LIFETIME);
        this.width = width;
        this.height = height;
        this.color = color;
        this.alpha = MathUtils.random(PARTICLE_MIN_ALPHA, PARTICLE_MAX_ALPHA);
    }

    @Override
    public void update(float speed) {
       position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime()));
       recBounds.set(position.x, position.y, width, height);

       lifeTime -= speed;
       alpha -= speed;

       if(lifeTime < 0 || alpha < 0 || position.x > SpaceRun.WIDTH || position.x < 0 || position.x > SpaceRun.HEIGHT || position.y < 0)
       {
           flagForRemoval();
       }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(color.r, color.g, color.b, alpha);
        batch.draw(texture, position.x, position.y, width, height);
        batch.setColor(1, 1, 1, 1);
    }
}

package com.shesh.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.shesh.game.Assets;

import java.util.ArrayList;

/**
 * Created by Shesh on 1/3/2015.
 */
public class Explosion extends GameObject {

    public static final int DEFAULT_MIN_PARTICLES = 85;
    public static final int DEFAULT_MAX_PARTICLES = 100;

    private ArrayList<Particle> particles;

    public Explosion(Vector2 position) {
        this(position, new Color(1, 1, 1, 1), DEFAULT_MIN_PARTICLES, DEFAULT_MAX_PARTICLES);
    }

    public Explosion(Vector2 position, Color color) {
        this(position, color, DEFAULT_MIN_PARTICLES, DEFAULT_MAX_PARTICLES, Assets.texRegBullet1, Particle.PARTICLE_WIDTH, Particle.PARTICLE_HEIGHT);
    }

    public Explosion(Vector2 position, Color color, TextureRegion texture) {
        this(position, color, DEFAULT_MIN_PARTICLES, DEFAULT_MAX_PARTICLES, texture, Particle.PARTICLE_WIDTH, Particle.PARTICLE_HEIGHT);
    }

    public Explosion(Vector2 position, Color color, TextureRegion texture, float width, float height) {
        this(position, color, DEFAULT_MIN_PARTICLES, DEFAULT_MAX_PARTICLES, texture, width, height);
    }

    public Explosion(Vector2 position, Color color, int minParticles, int maxParticles) {
        this(position, color, minParticles, maxParticles, Assets.texRegBullet1, Particle.PARTICLE_WIDTH, Particle.PARTICLE_HEIGHT);
    }

    public Explosion(Vector2 position, Color color, int minParticles, int maxParticles, TextureRegion texture, float width, float height) {
        this.position = position;
        this.particles = new ArrayList<Particle>();

        int numParticles = MathUtils.random(minParticles, maxParticles);

        for(int i = 0; i < numParticles; i++) {
            particles.add(new Particle(position.cpy(), color, texture, width, height));
        }
    }

    @Override
    public void update(float speed) {
        for (int i = 0; i < particles.size(); i++) {
            Particle p = particles.get(i);

            p.update(speed);

            if (p.needsRemoval()) {
                particles.remove(i);
                i--;
            }
        }

        if(particles.isEmpty())
        {
            flagForRemoval();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        for(Particle p: particles)
        {
            p.render(batch);
        }
    }
}

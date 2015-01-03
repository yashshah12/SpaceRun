package com.space.run.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Shesh on 1/3/2015.
 */
public class Explosion extends GameObject {

    private ArrayList<Particle> particles;

    public Explosion(Vector2 position) {
        this.position = position;
        this.particles = new ArrayList<Particle>();

        int numParticles = MathUtils.random(85, 100);

        for(int i = 0; i < numParticles; i++) {
            particles.add(new Particle(position.cpy()));
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

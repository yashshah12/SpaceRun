package com.shesh.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.shesh.game.Assets;
import com.shesh.game.Level;

public class Obstacle extends GameObject {

	/**
	 * The single box obstacle type.
	 */
	public static final int TYPE_SINGLE_BOX = 0;

	/**
	 * The width of the single box obstacle.
	 */
	public static final float SINGLE_BOX_WIDTH = 46;

	/**
	 * The height of the single box obstacle.
	 */
	public static final float SINGLE_BOX_HEIGHT = 42;

	/**
	 * The three box obstacle type.
	 */
	public static final int TYPE_THREE_BOX = 1;

	/**
	 * The width of the three box obstacle.
	 */
	public static final float THREE_BOX_WIDTH = 34;

	/**
	 * The height of the three box obstacle.
	 */
	public static final float THREE_BOX_HEIGHT = 68;

	/**
	 * The type of the obstacle.
	 */
	private int nType;

	/**
	 * The texture of the obstacle.
	 */
	private TextureRegion texRegObstacle;

    private Level level;

	public Obstacle(Vector2 position, Level level) {
		this(Obstacle.TYPE_SINGLE_BOX, position, level);
	}

	public Obstacle(int nType, Vector2 position, Level level) {
		this.position = position;
		this.nType = nType;
        this.level = level;

		width = nType == Obstacle.TYPE_SINGLE_BOX ? SINGLE_BOX_WIDTH : THREE_BOX_WIDTH;
		height = nType == Obstacle.TYPE_SINGLE_BOX ? SINGLE_BOX_HEIGHT : THREE_BOX_HEIGHT;

		recBounds.set(position.x, position.y, width, height);

		texRegObstacle = nType == Obstacle.TYPE_SINGLE_BOX ? Assets.texRegObstacle1 : Assets.texRegObstacle2;
	}

	@Override
	public void update(float speed) {
		position.x -= speed;
		recBounds.set(position.x, position.y, width, height);
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texRegObstacle, position.x, position.y, width, height);
	}

	/**
	 * Gets the type of this obstacle.
	 * 
	 * @return The value that represents the type of obstacle.
	 */
	public int getType() {
		return nType;
	}

    @Override
    public void flagForRemoval() {
        super.flagForRemoval();

        if(level.getExplosions() != null) {
            Color color = nType == Obstacle.TYPE_SINGLE_BOX ? new Color(0.2f, 0.1f, 0, 1) : new Color(0.8f, 0.9f, 0.7f, 1);
            level.addExplosion(new Explosion(position.cpy(), color, 30, 60));
        }
    }
}

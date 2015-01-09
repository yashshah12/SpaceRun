package com.shesh.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.shesh.game.objects.Heart;
import com.shesh.game.objects.Player;
import com.shesh.game.objects.PowerUp;
import com.shesh.game.screens.MainMenuScreen;
import com.shesh.game.screens.MainMenuScreen.ButtonListener;

public class StatusBar {

	/**
	 * The stage where the pause button is placed.
	 */
	private Stage stage;

	/**
	 * The pause button for the game. Pauses the game when pressed.
	 */
	private ImageTextButton btnPause;
    private TextButton btnMute;

	/**
	 * The level's player.
	 */
	private Player player;

	/**
	 * The value that keeps track of time for animation.
	 */
	private float fStateTime;
    private float alpha;


	public StatusBar(SpaceRun game) {
		this.player = game.getGameScreen().getLevel().getPlayer();

		/*
		 * Menus(Buttons, Labels, etc):
		 * https://github.com/libgdx/libgdx/wiki/Scene2d
		 * https://github.com/libgdx/libgdx/wiki/Scene2d.ui
		 */
        stage = new Stage(new StretchViewport(SpaceRun.WIDTH, SpaceRun.HEIGHT), game.getBatch());

		btnPause = new ImageTextButton("", Assets.skinUI);
		btnPause.setName("Pause");
		btnPause.setSize(40, 40);
		btnPause.setPosition(SpaceRun.WIDTH / 2 - btnPause.getWidth() / 2, (SpaceRun.HEIGHT - 40));
		btnPause.addListener(new ButtonListener(game));

        btnMute = new TextButton("Mute",Assets.skinUI);
        btnMute.setName("Mute");

        if(Assets.musicGameLoop.getVolume() <= 0)
        {
            btnMute.setText("UnMute");
            btnMute.setSize(130, 30);
        }
        else
        {
            btnMute.setSize(90, 30);
        }

        btnMute.setPosition(SpaceRun.WIDTH / 2 - btnMute.getWidth() / 2 - 130, (SpaceRun.HEIGHT - 35));
        btnMute.addListener(new ButtonListener(game));

		stage.addActor(btnPause);
        stage.addActor(btnMute);

        alpha = 1;
	}

	/**
	 * Updates the state time of the status bar.
	 * 
	 * @param delta
	 *            The time in seconds since the last frame.
	 */
	public void update(float delta) {
		fStateTime += delta;
		stage.act(delta);
	}

	/**
	 * Draws everything on the status bar to the screen.
	 * 
	 * @param batch
	 *            The SpriteBatch instance.
	 */
	public void render(SpriteBatch batch) {
        // Draws score and number of coins on top left part of screen.
        Assets.fontWhite.draw(batch, "Score " + player.getScore(), 10, SpaceRun.HEIGHT - 10);
        Assets.fontWhite.draw(batch, "Coins " + player.getCoins(), 10, SpaceRun.HEIGHT - 30);

		/*
		 * If the player has the bullet power up, draw the PowerUpOn texture,
		 * otherwise draw the PowerUpOff texture.
		 * 
		 * While the bullet power up is active, create a flashing effect on the
		 * PowerUpOn texture.
		 */
        if (player.getPowerUpManager().hasPowerUp(PowerUp.TYPE_BULLET_POWER_UP) || (player.getPowerUpManager().isActive(PowerUp.TYPE_BULLET_POWER_UP) && fStateTime % 0.25 < 0.125)) {
            batch.draw(Assets.texRegBulletPowerUpOn, SpaceRun.WIDTH / 2 + 200, SpaceRun.HEIGHT - 35, 34, 35);
        } else {
            batch.draw(Assets.texRegBulletPowerUpOff, SpaceRun.WIDTH / 2 + 200, SpaceRun.HEIGHT - 35, 34, 35);
        }

		/*
		 * Draws the number of lives the player has on the top right part of
		 * screen.
		 */
        for (int i = 0; i < player.getLives(); i++) {
            batch.draw(Assets.texRegHeart, SpaceRun.WIDTH - ((30 * i) + 30), SpaceRun.HEIGHT - 30, Heart.HEART_WIDTH, Heart.HEART_HEIGHT);
        }

        if (Gdx.app.getType().equals(Application.ApplicationType.Android)) {
            if (fStateTime >= 0 && fStateTime <= 2.5) {
                drawInstructions(batch, 1);
            }

            if (fStateTime > 2.5 && alpha > 0) {
                drawInstructions(batch, alpha);
                alpha -= 0.1f;
            }
        }
	}

    private void drawInstructions(SpriteBatch batch, float alpha)
    {
        Assets.fontBlack.setColor(1, 1, 1, alpha);
        Assets.fontBlack.draw(batch, "Tap Left ", 30, SpaceRun.HEIGHT / 2 - 80);
        Assets.fontBlack.draw(batch, "To Jump ", 30, SpaceRun.HEIGHT / 2 - 100);
        Assets.fontBlack.draw(batch, "Tap Right ", SpaceRun.WIDTH / 2 + 150, SpaceRun.HEIGHT / 2 - 80);
        Assets.fontBlack.draw(batch, "To Shoot ", SpaceRun.WIDTH / 2 + 150, SpaceRun.HEIGHT / 2 - 100);
        Assets.fontBlack.setColor(1, 1, 1, 1);
    }

	/**
	 * Gets the status bar's stage where the pause button is drawn.
	 * 
	 * @return The status bar's stage.
	 */
	public Stage getStage() {
		return stage;
	}

    public TextButton getBtnMute() { return btnMute; }
}

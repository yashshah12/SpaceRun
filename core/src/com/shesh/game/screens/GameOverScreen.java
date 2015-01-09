package com.shesh.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.shesh.game.Assets;
import com.shesh.game.SpaceRun;
import com.shesh.game.screens.MainMenuScreen.ButtonListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameOverScreen implements Screen {

	private SpaceRun game;

	private Stage stage;
	private TextButton btnRestart;
	private TextButton btnMainMenu;
	private Label lblTitle;
	private Label lblScore;
	private Label lblCoins;

	/**
	 * https://github.com/libgdx/libgdx/blob/master/tests/gdx-tests/src/com/
	 * badlogic/gdx/tests/UITest.java
	 * 
	 * The text field where the player enters their name.
	 */
	private TextField txtInput;
	private ButtonListener btnListener;

	public GameOverScreen(SpaceRun game) {
		this.game = game;

		btnListener = new ButtonListener(game);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		/*
		 * Menus(Buttons, Labels, etc):
		 * https://github.com/libgdx/libgdx/wiki/Scene2d
		 * https://github.com/libgdx/libgdx/wiki/Scene2d.ui
		 * https://github.com/libgdx
		 * /libgdx/blob/master/tests/gdx-tests/src/com/badlogic
		 * /gdx/tests/UITest.java
		 */
		stage = new Stage(new StretchViewport(SpaceRun.WIDTH, SpaceRun.HEIGHT), game.getBatch());
		stage.clear();

		Gdx.input.setInputProcessor(stage);

		btnRestart = new TextButton("Restart!", Assets.skinUI);
		btnRestart.setName("Restart");
		btnRestart.setSize(400, 100);
		btnRestart.setPosition(SpaceRun.WIDTH / 2 - btnRestart.getWidth() / 2, (SpaceRun.HEIGHT / 2 - btnRestart.getHeight() / 2) - 50);
		btnRestart.addListener(btnListener);

		btnMainMenu = new TextButton("MainMenu!", Assets.skinUI);
		btnMainMenu.setName("Menu");
		btnMainMenu.setSize(400, 100);
		btnMainMenu.setPosition(SpaceRun.WIDTH / 2 - btnMainMenu.getWidth() / 2, (SpaceRun.HEIGHT / 2 - btnMainMenu.getHeight() / 2) - 170);
		btnMainMenu.addListener(btnListener);

		lblTitle = new Label("Game Over!", Assets.skinUI);
		lblTitle.setPosition(0, SpaceRun.HEIGHT / 2 + 190);
		lblTitle.setWidth(SpaceRun.WIDTH);
		lblTitle.setAlignment(Align.center);

		lblScore = new Label("Score: " + (game.getGameScreen().getLevel().getPlayer().getScore() + game.getGameScreen().getLevel().getPlayer().getCoins()), Assets.skinUI);
		lblScore.setPosition(0, SpaceRun.HEIGHT / 2 + 90);
		lblScore.setWidth(SpaceRun.WIDTH);
		lblScore.setAlignment(Align.center);

		lblCoins = new Label("Coins: " + game.getGameScreen().getLevel().getPlayer().getCoins(), Assets.skinUI);
		lblCoins.setPosition(0, SpaceRun.HEIGHT / 2 + 40);
		lblCoins.setWidth(SpaceRun.WIDTH);
		lblCoins.setAlignment(Align.center);

		String name = Gdx.app.getPreferences("NameRemembering").getString("LatestName").isEmpty() ? "Player" : Gdx.app.getPreferences("NameRemembering").getString("LatestName");
		txtInput = new TextField(name, Assets.skinUI);
		txtInput.setWidth(135);
		txtInput.setPosition(SpaceRun.WIDTH / 2 - txtInput.getWidth() / 2, SpaceRun.HEIGHT / 2 + 140);
		txtInput.setTextFieldListener(new TextFieldListener() {
			public void keyTyped(TextField textField, char key) {
				// If the enter key is pressed, hide the on screen keyboard.
				if (key == 13) {
					textField.getOnscreenKeyboard().show(false);
				}
			}
		});

		stage.addActor(btnRestart);
		stage.addActor(btnMainMenu);
		stage.addActor(lblTitle);
		stage.addActor(lblScore);
		stage.addActor(lblCoins);
		stage.addActor(txtInput);
	}

	@Override
	public void show() {}

	@Override
	public void hide() {
		Preferences highScores = Gdx.app.getPreferences("Scores");
		Preferences latestName = Gdx.app.getPreferences("NameRemembering");

		String name = txtInput.getText().isEmpty() ? "Player" : txtInput.getText();
		String scores = highScores.getString(name);

		scores += game.getGameScreen().getLevel().getPlayer().getScore() + game.getGameScreen().getLevel().getPlayer().getCoins() + " ";
		highScores.putString(name, scores);
		highScores.flush();

		latestName.putString("LatestName", name);
		latestName.flush();

		dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}

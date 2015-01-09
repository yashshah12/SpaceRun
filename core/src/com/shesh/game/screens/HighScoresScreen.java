package com.shesh.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.shesh.game.Assets;
import com.shesh.game.SpaceRun;
import com.shesh.game.screens.MainMenuScreen.ButtonListener;

import java.lang.reflect.Array;
import java.util.*;

public class HighScoresScreen implements Screen {

	/**
	 * The number of high scores to show on the screen.
	 */
	public static final int NUM_SCORES_TO_SHOW = 10;

	private SpaceRun game;

	private Stage stage;
	private TextButton btnBack;
	private Label lblTitle;

	public HighScoresScreen(SpaceRun game) {
		this.game = game;

		show();
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

		btnBack = new TextButton("Back", Assets.skinUI);
		btnBack.setName("Back");
		btnBack.setSize(150, 50);
		btnBack.setPosition(SpaceRun.WIDTH / 2 - btnBack.getWidth() * 2, (SpaceRun.HEIGHT / 2 + btnBack.getHeight() * 3) + 20);
		btnBack.addListener(new ButtonListener(game));

		lblTitle = new Label("High Scores", Assets.skinUI);
		lblTitle.setPosition(0, SpaceRun.HEIGHT / 2 + 120);
		lblTitle.setWidth(SpaceRun.WIDTH);
		lblTitle.setAlignment(Align.center);

		displayHighScores();

		stage.addActor(btnBack);
		stage.addActor(lblTitle);
	}

	@Override
	public void show() {}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {
		stage.dispose();
	}

	private void displayHighScores() {
		Preferences highScores = Gdx.app.getPreferences("Scores");
		Map<String, String> map = (Map<String, String>)highScores.get();

		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String[]> scores = new ArrayList<String[]>();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			names.add(entry.getKey());
			scores.add(entry.getValue().split(" "));
		}

		ArrayList<String> arsScores = new ArrayList<String>();
		ArrayList<String> arsNames = new ArrayList<String>();
		int[] arnIndices = new int[NUM_SCORES_TO_SHOW];

		for(int i = 0; i < names.size(); i++)
		{
			for(int j = 0; j < scores.get(i).length; j++)
			{
				arsNames.add(names.get(i));
				arsScores.add(scores.get(i)[j]);
			}
		}

		String[] arsTemp = new String[arsScores.size()];
		for(int i = 0; i < arsScores.size(); i++)
		{
			arsTemp[i] = arsScores.get(i);
		}

		for (int i = 0; i < NUM_SCORES_TO_SHOW; i++) {
			long lLargest = 0;
			int nTempIndex = -1;
			for (int j = 0; j < arsTemp.length; j++) {

				if(arsTemp[j] == null) arsTemp[j] = "0";

				if (Long.valueOf(arsTemp[j]) > lLargest) {
					lLargest = Long.valueOf(arsTemp[j]);
					arsTemp[j] = "-1";
					arnIndices[i] = j;

					if (nTempIndex != -1) {
						arsTemp[nTempIndex] = arsScores.get(nTempIndex);
					}

					nTempIndex = j;
				}
			}
		}

		if(!arsNames.isEmpty()) {
			loop:
			for (int i = 0; i < NUM_SCORES_TO_SHOW; i++) {
				for (int j = 0; j < i; j++) {
					// Don't show duplicate indices.
					if (arnIndices[j] == arnIndices[i]) {
						continue loop;
					}
				}

				Label lblName = new Label(arsNames.get(arnIndices[i]), Assets.skinUI);
				lblName.setPosition(SpaceRun.WIDTH / 2 - lblName.getWidth() - 20, SpaceRun.HEIGHT / 2 + 75 - i * 30);

				Label lblScore = new Label(arsScores.get(arnIndices[i]), Assets.skinUI);
				lblScore.setPosition(SpaceRun.WIDTH / 2 + 75, SpaceRun.HEIGHT / 2 + 75 - i * 30);

				stage.addActor(lblName);
				stage.addActor(lblScore);
			}
		}
	}
}
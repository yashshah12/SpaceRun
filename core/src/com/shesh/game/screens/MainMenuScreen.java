package com.shesh.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.shesh.game.Assets;
import com.shesh.game.SpaceRun;

public class MainMenuScreen implements Screen {

	private SpaceRun game;

	private Stage stage;
	private TextButton btnPlay;
	private TextButton btnHelp;
	private TextButton btnStats;
	private Label lblTitle;

    private ButtonListener btnListener;

	public MainMenuScreen(SpaceRun game) {
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

		btnPlay = new TextButton("Play", Assets.skinUI);
		btnPlay.setName("Restart");
		btnPlay.setSize(400, 100);
		btnPlay.setPosition(SpaceRun.WIDTH / 2 - btnPlay.getWidth() / 2, SpaceRun.HEIGHT / 2 - btnPlay.getHeight() / 2 + 100);
		btnPlay.addListener(btnListener);

		btnHelp = new TextButton("Help", Assets.skinUI);
		btnHelp.setName("Help");
		btnHelp.setSize(400, 100);
		btnHelp.setPosition(SpaceRun.WIDTH / 2 - btnHelp.getWidth() / 2, (SpaceRun.HEIGHT / 2 - btnHelp.getHeight() / 2) - 20);
		btnHelp.addListener(btnListener);

		btnStats = new TextButton("High Scores", Assets.skinUI);
		btnStats.setName("Scores");
		btnStats.setSize(400, 100);
		btnStats.setPosition(SpaceRun.WIDTH / 2 - btnHelp.getWidth() / 2, (SpaceRun.HEIGHT / 2 - btnHelp.getHeight() / 2) - 140);
		btnStats.addListener(btnListener);

		lblTitle = new Label("Space Run", Assets.skinUI);
		lblTitle.setPosition(0, SpaceRun.HEIGHT / 2 + 180);
		lblTitle.setWidth(SpaceRun.WIDTH);
		lblTitle.setAlignment(Align.center);

		stage.addActor(btnPlay);
		stage.addActor(btnHelp);
		stage.addActor(btnStats);
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

	static public class ButtonListener extends ClickListener {
        public static boolean isMute = false;
        private SpaceRun game;

		public ButtonListener(SpaceRun game) {
			this.game = game;
		}

		@Override
		public void clicked(InputEvent event, float x, float y) {
			String sBtnName = event.getListenerActor().getName();

			if (sBtnName == "Restart") {
				game.getScreen().hide();
				game.setGameScreen(new GameScreen(game));
				game.setScreen(game.getGameScreen());
			} else if (sBtnName == "Help") {
				game.setScreen(game.getHelpScreen());
			} else if (sBtnName == "Resume") {
				game.setScreen(game.getGameScreen());
			} else if (sBtnName == "Scores") {
				game.setScreen(game.getHighScoresScreen());
			} else if (sBtnName == "Pause") {
				game.setScreen(game.getPauseScreen());
			}else if (sBtnName=="Mute") {
				if(isMute==false){
					Assets.musicGameLoop.setVolume(0f);
					isMute=true;
					game.getGameScreen().getStatusBar().getBtnMute().setText("UnMute");
					game.getGameScreen().getStatusBar().getBtnMute().setSize(130, 30);
				}else{
					Assets.musicGameLoop.setVolume(0.2f);
					isMute =false;
					game.getGameScreen().getStatusBar().getBtnMute().setText("Mute");
					game.getGameScreen().getStatusBar().getBtnMute().setSize(90, 30);
				}
			}else{
				game.setScreen(game.getMainMenuScreen());
			}

//            switch (sBtnName)
//            {
//                case "Restart":
//                    game.setGameScreen(new GameScreen(game));
//                    game.setScreen(game.getGameScreen());
//                    break;
//                case "Help":
//                    game.setScreen(game.getHelpScreen());
//                    break;
//                case "Resume":
//                    game.setScreen(game.getGameScreen());
//                    break;
//                case "Scores":
//                    game.setScreen(game.getHighScoresScreen());
//                    break;
//                case "Pause":
//                    game.setScreen(game.getPauseScreen());
//                    break;
//                case "Mute":
//                    TextButton btnMute = game.getGameScreen().getStatusBar().getBtnMute();
//
//                    if(isMute==false){
//                        Assets.musicGameLoop.setVolume(0f);
//                        isMute=true;
//                        btnMute.setText("UnMute");
//                        btnMute.setSize(130, 30);
//                        btnMute.setPosition(SpaceRun.WIDTH / 2 - btnMute.getWidth() / 2 - 130, (SpaceRun.HEIGHT - 35));
//                    }else{
//                        Assets.musicGameLoop.setVolume(0.2f);
//                        isMute =false;
//                        btnMute.setText("Mute");
//                        btnMute.setSize(90, 30);
//                        btnMute.setPosition(SpaceRun.WIDTH / 2 - btnMute.getWidth() / 2 - 130, (SpaceRun.HEIGHT - 35));
//                    }
//                    break;
//                default:
//                    game.setScreen(game.getMainMenuScreen());
//                    break;
//            }
		}
	}
}
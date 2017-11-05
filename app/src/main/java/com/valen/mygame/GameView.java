package com.valen.mygame;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by valen on 11/05/2017.
 * Project: MyGame
 */

public class GameView extends GLSurfaceView {

	private final GameRenderer gameRenderer;

	public GameView(Context context) {
		super(context);
		setEGLContextClientVersion(3);
		gameRenderer = new GameRenderer(context);
		setRenderer(gameRenderer);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (x < getWidth() / 2) {
					gameRenderer.setHeroMove(gameRenderer.getHeroMove() + 0.1f);
				}

				if (x > getWidth() / 2) {
					gameRenderer.setHeroMove(gameRenderer.getHeroMove() - 0.1f);
				}
		}
		return true;
	}
}

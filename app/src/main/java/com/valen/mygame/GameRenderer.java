package com.valen.mygame;

import android.content.Context;
import android.opengl.GLES32;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by valen on 10/21/2015.
 */
public class GameRenderer implements GLSurfaceView.Renderer {
	// fields
	private static final String TAG = "GameRenderer";
	public static float[] mMVPMatrix = new float[16];
	public static float[] mProjectionMatrix = new float[16];
	public static float[] mViewMatrix = new float[16];
	public static float[] mTranslationMatrix = new float[16];
	float starfieldScroll = 0;
	float debrisScroll;
	float heroSprite = 0;
	float heroMove;
	private Context context;
	private Starfield starfield;
	private Hero hero;

	// methods
	public GameRenderer(Context gameContext) {
		context = gameContext;
	}

	public static int loadShader(int type, String shaderCode) {
		int shader = GLES32.glCreateShader(type);
		// add the source code to the shader and compile it
		GLES32.glShaderSource(shader, shaderCode);
		GLES32.glCompileShader(shader);
		return shader;
	}

	public static void checkGlError(String glOperation) {
		int error;
		while ((error = GLES32.glGetError()) != GLES32.GL_NO_ERROR) {
			Log.e(TAG, glOperation + ": glError " + error);
			throw new RuntimeException(glOperation + ": glError " + error);
		}
	}

	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		GLES32.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		starfield = new Starfield();
		hero = new Hero();
		starfield.loadTexture(R.drawable.starfield, context);
		hero.loadTexture(R.drawable.ships, context);
	}

	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) {
		GLES32.glViewport(0, 0, width, height);
		float ratio = (float) width / height;
		Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

	}

	@Override
	public void onDrawFrame(GL10 unused) {
		float[] matrix = new float[16];
		GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT | GLES32.GL_DEPTH_BUFFER_BIT);
		Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
		starfield.draw(mMVPMatrix, starfieldScroll);
		GLES32.glEnable(GLES32.GL_BLEND);
		GLES32.glBlendFunc(GLES32.GL_SRC_ALPHA, GLES32.GL_ONE_MINUS_SRC_ALPHA);
		Matrix.setIdentityM(mTranslationMatrix, 0);
		Matrix.translateM(mTranslationMatrix, 0, heroMove, -0.5f, 0);
		Matrix.multiplyMM(matrix, 0, mMVPMatrix, 0, mTranslationMatrix, 0);
		hero.draw(matrix, 0, 0);
		GLES32.glDisable(GLES32.GL_BLEND);
		if (starfieldScroll == Float.MAX_VALUE) {
			starfieldScroll = 0;
		}
		if (debrisScroll == Float.MAX_VALUE) {
			debrisScroll = 0;
		}
		starfieldScroll += 0.001;
		debrisScroll += 0.01;
	}

	public float getHeroMove() {
		return heroMove;
	}

	public void setHeroMove(float movement) {
		heroMove = movement;
	}
}

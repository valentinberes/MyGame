package com.valen.mygame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by valen on 11/05/2017.
 * Project: MyGame
 */

public class MainActivity extends AppCompatActivity {
    // fields
    private GameView myGameView;

    // methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myGameView = new GameView(this);
        setContentView(myGameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        myGameView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to reallocate them.
        myGameView.onResume();
    }
}

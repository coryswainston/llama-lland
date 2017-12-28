package com.coryswainston.game.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.coryswainston.game.R;
import com.coryswainston.game.helpers.ViewListener;
import com.coryswainston.game.views.GameView;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    public MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        int score = getIntent().getIntExtra("score", 0);
        int level = getIntent().getIntExtra("level", 1);

        gameView = new GameView(this, score, level);
        gameView.setListener(new ViewListener() {
            @Override
            public void onAction(Intent data) {
                Log.d("GameActivity", "Listener triggered. Caling onLevelFinishedOrGameOver");
                onLevelFinishedOrGameOver(data);
            }
        });

        setContentView(gameView);
        mediaPlayer = MediaPlayer.create(this, R.raw.pixelland);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setVolume(0.6f, 0.6f);
        mediaPlayer.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        gameView.pause();
        mediaPlayer.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        gameView.resume();
        mediaPlayer.start();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    private void onLevelFinishedOrGameOver(Intent data) {
        Log.d("GameActivity", "Finished level. data=" + data.toString());
        setResult(1, data);
        finish();
    }
}

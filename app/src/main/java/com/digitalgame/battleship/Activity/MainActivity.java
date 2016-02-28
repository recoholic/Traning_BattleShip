package com.digitalgame.battleship.Activity;

import com.digitalgame.battleship.Enemy.EnemyState;
import com.digitalgame.battleship.R;
import com.digitalgame.battleship.Service.DeliverServiceImpl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), MainBattleActivity.class);
            switch (v.getId()) {
                case R.id.TutorialModeButton:
                    intent.putExtra(MainBattleActivity.MODE_SELECT, EnemyState.TUTORIAL);
                    break;
                case R.id.SinglePlayButton:
                    intent.putExtra(MainBattleActivity.MODE_SELECT, EnemyState.SIMPLE);
                    break;
                case R.id.RandomPlayButton:
                    intent.putExtra(MainBattleActivity.MODE_SELECT, EnemyState.RANDOM);
                    break;
                default:
                    break;
            }
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button tutorialButton = (Button) findViewById(R.id.TutorialModeButton);
        tutorialButton.setOnClickListener(onClickListener);

        Button randomPlayButton = (Button) findViewById(R.id.RandomPlayButton);
        randomPlayButton.setOnClickListener(onClickListener);

        Button singlePlayButton = (Button) findViewById(R.id.SinglePlayButton);
        singlePlayButton.setOnClickListener(onClickListener);

        startService(new Intent(getApplication(), DeliverServiceImpl.class));
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(getApplication(), DeliverServiceImpl.class));
        super.onDestroy();
    }
}

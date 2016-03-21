package com.digitalgame.battleship.Activities;

import com.digitalgame.battleship.Utils.BattleShipConstants;
import com.digitalgame.battleship.R;
import com.digitalgame.battleship.Service.DeliverService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button tutorialButton = (Button) findViewById(R.id.TutorialModeButton);
        tutorialButton.setOnClickListener(this);

        Button randomPlayButton = (Button) findViewById(R.id.RandomPlayButton);
        randomPlayButton.setOnClickListener(this);

        Button singlePlayButton = (Button) findViewById(R.id.SinglePlayButton);
        singlePlayButton.setOnClickListener(this);

        startService(new Intent(getApplication(), DeliverService.class));
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(getApplication(), DeliverService.class));
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ChoosePlayerActivity.class);
        switch (v.getId()) {
            case R.id.TutorialModeButton:
                intent.putExtra(BattleShipConstants.MODE_SELECT, BattleShipConstants.TUTORIAL);
                break;
            case R.id.SinglePlayButton:
                intent.putExtra(BattleShipConstants.MODE_SELECT, BattleShipConstants.SIMPLE);
                break;
            case R.id.RandomPlayButton:
                intent.putExtra(BattleShipConstants.MODE_SELECT, BattleShipConstants.RANDOM);
                break;
            default:
                Toast.makeText(getApplicationContext(),
                        android.R.string.no, Toast.LENGTH_SHORT).show();
                break;
        }
        startActivity(intent);
    }
}

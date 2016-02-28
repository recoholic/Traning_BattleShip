package com.digitalgame.battleship.Activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.digitalgame.battleship.Dialogs.FinMessageDialog;
import com.digitalgame.battleship.Enemy.EnemyAction;
import com.digitalgame.battleship.Enemy.EnemyState;
import com.digitalgame.battleship.GameState;
import com.digitalgame.battleship.MapArea.MapState;
import com.digitalgame.battleship.R;
import com.digitalgame.battleship.Service.DeliverServiceImpl;

public class MainBattleActivity extends Activity {
    public static final String MODE_SELECT = "SelectModeString";
    private static final int COUNT_START = 0;
    private static final int COUNT_END = 4;

    private GameState mGameState;
    private MapState mEnemyMap;
    private MapState mPlayerMap;
    private int mEnemyHitCount;
    private int mPlayerHitCount;
    private String mCommand;

    private TextView mPlayerScore;
    private TextView mEnemyScore;

    //Set Buttons
    private Button
            mEnemyA1, mEnemyA2, mEnemyA3, mEnemyA4, mEnemyA5,
            mEnemyB1, mEnemyB2, mEnemyB3, mEnemyB4, mEnemyB5,
            mEnemyC1, mEnemyC2, mEnemyC3, mEnemyC4, mEnemyC5,
            mEnemyD1, mEnemyD2, mEnemyD3, mEnemyD4, mEnemyD5,
            mEnemyE1, mEnemyE2, mEnemyE3, mEnemyE4, mEnemyE5;

    private Button
            mPlayerA1, mPlayerA2, mPlayerA3, mPlayerA4, mPlayerA5,
            mPlayerB1, mPlayerB2, mPlayerB3, mPlayerB4, mPlayerB5,
            mPlayerC1, mPlayerC2, mPlayerC3, mPlayerC4, mPlayerC5,
            mPlayerD1, mPlayerD2, mPlayerD3, mPlayerD4, mPlayerD5,
            mPlayerE1, mPlayerE2, mPlayerE3, mPlayerE4, mPlayerE5;

    private Button[][] enemyButtonList = {
            {mEnemyA1, mEnemyA2, mEnemyA3, mEnemyA4, mEnemyA5},
            {mEnemyB1, mEnemyB2, mEnemyB3, mEnemyB4, mEnemyB5},
            {mEnemyC1, mEnemyC2, mEnemyC3, mEnemyC4, mEnemyC5},
            {mEnemyD1, mEnemyD2, mEnemyD3, mEnemyD4, mEnemyD5},
            {mEnemyE1, mEnemyE2, mEnemyE3, mEnemyE4, mEnemyE5}};

    private Button[][] playerButtonList = {
            {mPlayerA1, mPlayerA2, mPlayerA3, mPlayerA4, mPlayerA5},
            {mPlayerB1, mPlayerB2, mPlayerB3, mPlayerB4, mPlayerB5},
            {mPlayerC1, mPlayerC2, mPlayerC3, mPlayerC4, mPlayerC5},
            {mPlayerD1, mPlayerD2, mPlayerD3, mPlayerD4, mPlayerD5},
            {mPlayerE1, mPlayerE2, mPlayerE3, mPlayerE4, mPlayerE5}};

    private int[][] enemyButtonId = {
            {R.id.enemyA1, R.id.enemyA2, R.id.enemyA3, R.id.enemyA4, R.id.enemyA5},
            {R.id.enemyB1, R.id.enemyB2, R.id.enemyB3, R.id.enemyB4, R.id.enemyB5},
            {R.id.enemyC1, R.id.enemyC2, R.id.enemyC3, R.id.enemyC4, R.id.enemyC5},
            {R.id.enemyD1, R.id.enemyD2, R.id.enemyD3, R.id.enemyD4, R.id.enemyD5},
            {R.id.enemyE1, R.id.enemyE2, R.id.enemyE3, R.id.enemyE4, R.id.enemyE5}};

    private int[][] playerButtonId = {
            {R.id.playerA1, R.id.playerA2, R.id.playerA3, R.id.playerA4, R.id.playerA5},
            {R.id.playerB1, R.id.playerB2, R.id.playerB3, R.id.playerB4, R.id.playerB5},
            {R.id.playerC1, R.id.playerC2, R.id.playerC3, R.id.playerC4, R.id.playerC5},
            {R.id.playerD1, R.id.playerD2, R.id.playerD3, R.id.playerD4, R.id.playerD5},
            {R.id.playerE1, R.id.playerE2, R.id.playerE3, R.id.playerE4, R.id.playerE5}};
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setClickable(false);

            int[] choosePoint = mEnemyMap.getLineColumn(v.getId());
            if (mEnemyMap.isExist(v.getId())) {
                enemyButtonList[choosePoint[0]][choosePoint[1]]
                        .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_24dp, 0, 0, 0);
                mPlayerHitCount++;
                if (mPlayerHitCount > COUNT_END) {
                    mGameState.setGameEnd();
                    mGameState.setPlayerWin();
                }
            } else {
                enemyButtonList[choosePoint[0]][choosePoint[1]]
                        .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close_24dp, 0, 0, 0);
            }
            setScore();

            if (mGameState.getGameNow()) {
                showDialogFragment();
            }

            enemyAction();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_battle);

        initButton();
        initScoreArea();
        mEnemyHitCount = COUNT_START;
        mPlayerHitCount = COUNT_START;
        setScore();

        mGameState = GameState.getState();
        mGameState.initGameState();

        Intent intent = getIntent();
        mCommand = intent.getStringExtra(MODE_SELECT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mGameState.isChooseAlready()) {
            if (mCommand.equals(EnemyState.TUTORIAL)) {
                startActivity(new Intent(getApplicationContext(), TutorialPlayer.class));
            } else {
                startActivity(new Intent(getApplicationContext(), ChoosePlayerActivity.class));
            }
        }

        if (mGameState.isDeliverAlready()) {
            mPlayerMap = DeliverServiceImpl.getPlayerMap(DeliverServiceImpl.IS_PLAYER);
            mPlayerMap.setWidgetId(playerButtonId);
            showPlayerPosition();

            DeliverServiceImpl.setEnemyMode(mCommand);
            mEnemyMap = DeliverServiceImpl.getPlayerMap(DeliverServiceImpl.IS_ENEMY);
            mEnemyMap.setWidgetId(enemyButtonId);
        }
    }

    private void initScoreArea() {
        mPlayerScore = (TextView) findViewById(R.id.playerScoreCount);
        mEnemyScore = (TextView) findViewById(R.id.enemyScoreCount);

        TextView playerMax;
        playerMax = (TextView) findViewById(R.id.playerCount);
        playerMax.setText(String.valueOf(COUNT_END + 1));

        TextView enemyMax;
        enemyMax = (TextView) findViewById(R.id.enemyCount);
        enemyMax.setText(String.valueOf(COUNT_END + 1));

        mEnemyHitCount = COUNT_START;
        mPlayerHitCount = COUNT_START;
        setScore();
    }

    private void enemyAction() {
        int[] damagePoint = EnemyAction.getRandom();
        if (mPlayerMap.isExist(damagePoint[0], damagePoint[1])) {
            playerButtonList[damagePoint[0]][damagePoint[1]]
                    .setBackgroundColor(Color.RED);
            mEnemyHitCount++;
            if (mEnemyHitCount > COUNT_END) {
                mGameState.setGameEnd();
            }
        } else {
            playerButtonList[damagePoint[0]][damagePoint[1]]
                    .setBackgroundColor(Color.BLACK);
        }

        setScore();
        if (mGameState.getGameNow()) {
            showDialogFragment();
        }
    }

    private void showDialogFragment() {
        FragmentManager manager = getFragmentManager();
        DialogFragment dialog;
        dialog = new FinMessageDialog();
        dialog.show(manager, "dialog");
    }

    private void showPlayerPosition() {
        for (int i = 0; i < MapState.MAX_LENGTH; i++) {
            for (int j = 0; j < MapState.MAX_LENGTH; j++) {
                if (mPlayerMap.isExist(playerButtonId[i][j])) {
                    playerButtonList[i][j].
                            setCompoundDrawablesWithIntrinsicBounds(R.drawable.exist_12dp, 0, 0, 0);
                }
            }
        }
    }

    private void setScore() {
        mPlayerScore.setText(String.valueOf(mPlayerHitCount));
        mEnemyScore.setText(String.valueOf(mEnemyHitCount));
    }

    private void initButton() {
        setEnemyButton();
        setPlayerButton();
    }

    private void setEnemyButton() {
        for (int i = 0; i < MapState.MAX_LENGTH; i++) {
            for (int j = 0; j < MapState.MAX_LENGTH; j++) {
                enemyButtonList[i][j] = (Button) findViewById(enemyButtonId[i][j]);
                enemyButtonList[i][j].setOnClickListener(onClickListener);
            }
        }
    }

    private void setPlayerButton() {
        for (int i = 0; i < MapState.MAX_LENGTH; i++) {
            for (int j = 0; j < MapState.MAX_LENGTH; j++) {
                playerButtonList[i][j] = (Button) findViewById(playerButtonId[i][j]);
            }
        }
    }
}
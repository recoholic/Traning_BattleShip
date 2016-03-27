package com.digitalgame.battleship.Activities;

import com.digitalgame.battleship.Dialogs.BackKeyDialog;
import com.digitalgame.battleship.Enemy.EnemyAction;
import com.digitalgame.battleship.MapArea.MapState;
import com.digitalgame.battleship.R;
import com.digitalgame.battleship.Utils.BattleShipConstants;
import com.digitalgame.battleship.Utils.GameState;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainBattleActivity extends Activity {

    private MapState mEnemyMap;
    private MapState mPlayerMap;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_battle);

        mPlayerMap = GameState.getInstance().getPlayerMap();
        mPlayerMap.setWidgetId(playerButtonId);
        mEnemyMap = GameState.getInstance().getEnemyMap();
        mEnemyMap.setWidgetId(enemyButtonId);

        initButton();
        initScoreArea();
        showPlayerPosition();
    }

    @Override
    public void onBackPressed() {
        DialogFragment dialogFragment = new BackKeyDialog();
        dialogFragment.setCancelable(false);
        dialogFragment.show(getFragmentManager(), BackKeyDialog.BACK_KEY_DIALOG);
    }

    private void initScoreArea() {
        TextView playerMax = (TextView) findViewById(R.id.playerCount);
        playerMax.setText(String.valueOf(mPlayerMap.getCountEnd()));

        TextView enemyMax = (TextView) findViewById(R.id.enemyCount);
        enemyMax.setText(String.valueOf(mEnemyMap.getCountEnd()));

        setScore();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setClickable(false);

            int[] choosePoint = mEnemyMap.getLineColumn(v.getId());
            if (mEnemyMap.isExist(v.getId())) {
                // Select area, show hit effect and count hit
                enemyButtonList[choosePoint[0]][choosePoint[1]]
                        .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_24dp, 0, 0, 0);
                mEnemyMap.setHitCount();
            } else {
                // miss
                enemyButtonList[choosePoint[0]][choosePoint[1]]
                        .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close_24dp, 0, 0, 0);
            }

            setScore();
            if (mEnemyMap.isLose()) {
                showDialogFragment(mEnemyMap.getOwner());
            }

            enemyAction();
        }
    };

    private void enemyAction() {
        int[] damagePoint = EnemyAction.getRandom();
        if (mPlayerMap.isExist(damagePoint[0], damagePoint[1])) {
            // Select area, show hit effect and count hit
            playerButtonList[damagePoint[0]][damagePoint[1]]
                    .setBackgroundColor(Color.RED);
            mPlayerMap.setHitCount();
        } else {
            // miss
            playerButtonList[damagePoint[0]][damagePoint[1]]
                    .setBackgroundColor(Color.BLACK);
        }

        setScore();
        if (mPlayerMap.isLose()) {
            showDialogFragment(mPlayerMap.getOwner());
        }
    }

    private void showDialogFragment(int player) {
        Bundle arguments = new Bundle();
        arguments.putInt(BattleShipConstants.BUNDLE_PLAYER, player);

        DialogFragment dialog = new FinMessageDialog();
        dialog.setArguments(arguments);
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), FinMessageDialog.FIN_MESSAGE_DIALOG);
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
        TextView scoreOfPlayer = (TextView) findViewById(R.id.playerScoreCount);
        scoreOfPlayer.setText(String.valueOf(mPlayerMap.getHitCount()));
        TextView scoreOfEnemy = (TextView) findViewById(R.id.enemyScoreCount);
        scoreOfEnemy.setText(String.valueOf(mEnemyMap.getHitCount()));
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

    public static class FinMessageDialog extends DialogFragment {
        public static final String FIN_MESSAGE_DIALOG = "FinMessageDialogTag";

        public static FinMessageDialog newInstance(int title) {
            FinMessageDialog frag = new FinMessageDialog();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            if (MapState.ENEMY == getArguments().getInt(BattleShipConstants.BUNDLE_PLAYER)) {
                builder.setTitle(R.string.end_message_win);
            } else {
                builder.setTitle(R.string.end_message_lose);
            }
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            getActivity().finish();
        }
    }
}
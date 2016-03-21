package com.digitalgame.battleship.Activities;

import com.digitalgame.battleship.Utils.BattleShipConstants;
import com.digitalgame.battleship.Dialogs.BackKeyDialog;
import com.digitalgame.battleship.Enemy.EnemyState;
import com.digitalgame.battleship.Utils.GameState;
import com.digitalgame.battleship.MapArea.MapState;
import com.digitalgame.battleship.MapArea.MapStateImpl;
import com.digitalgame.battleship.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChoosePlayerActivity extends Activity {

    private int countEnd;
    private MapState mPlayerChoose;

    private Button
            mChooseA1, mChooseA2, mChooseA3, mChooseA4, mChooseA5,
            mChooseB1, mChooseB2, mChooseB3, mChooseB4, mChooseB5,
            mChooseC1, mChooseC2, mChooseC3, mChooseC4, mChooseC5,
            mChooseD1, mChooseD2, mChooseD3, mChooseD4, mChooseD5,
            mChooseE1, mChooseE2, mChooseE3, mChooseE4, mChooseE5;

    private Button[][] chooseButtonList = {
            {mChooseA1, mChooseA2, mChooseA3, mChooseA4, mChooseA5},
            {mChooseB1, mChooseB2, mChooseB3, mChooseB4, mChooseB5},
            {mChooseC1, mChooseC2, mChooseC3, mChooseC4, mChooseC5},
            {mChooseD1, mChooseD2, mChooseD3, mChooseD4, mChooseD5},
            {mChooseE1, mChooseE2, mChooseE3, mChooseE4, mChooseE5}};

    private int[][] chooseButtonId = {
            {R.id.chooseA1, R.id.chooseA2, R.id.chooseA3, R.id.chooseA4, R.id.chooseA5},
            {R.id.chooseB1, R.id.chooseB2, R.id.chooseB3, R.id.chooseB4, R.id.chooseB5},
            {R.id.chooseC1, R.id.chooseC2, R.id.chooseC3, R.id.chooseC4, R.id.chooseC5},
            {R.id.chooseD1, R.id.chooseD2, R.id.chooseD3, R.id.chooseD4, R.id.chooseD5},
            {R.id.chooseE1, R.id.chooseE2, R.id.chooseE3, R.id.chooseE4, R.id.chooseE5}};
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPlayerChoose.getCountEnd() < countEnd) {
                v.setBackgroundColor(Color.DKGRAY);
                v.setClickable(false);

                mPlayerChoose.setHitArea(v.getId());
            }

            if (mPlayerChoose.getCountEnd() >= countEnd) {
                GameState.getInstance().setPlayerMap(mPlayerChoose);

                DialogFragment fragment = new ConformDialog();
                fragment.show(getFragmentManager(), ConformDialog.CONFORM_DIALOG);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player_point);

        initUI();
        mPlayerChoose = new MapStateImpl();
        mPlayerChoose.setOwner(MapState.PLAYER);
        mPlayerChoose.setWidgetId(chooseButtonId);

        EnemyState enemyState = new EnemyState();
        MapState enemyMap = enemyState.makeState(getIntent().getStringExtra(BattleShipConstants
                .MODE_SELECT));
        enemyMap.setOwner(MapState.ENEMY);
        GameState.getInstance().setEnemyMap(enemyMap);
    }

    @Override
    public void onBackPressed() {
        DialogFragment dialogFragment = new BackKeyDialog();
        dialogFragment.setCancelable(false);
        dialogFragment.show(getFragmentManager(), BackKeyDialog.BACK_KEY_DIALOG);
    }

    @Override
    public void recreate() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void initUI() {
        makeTitle();
        setChooseButton();
    }

    private void makeTitle() {
        switch (getIntent().getStringExtra(BattleShipConstants.MODE_SELECT)) {
            case BattleShipConstants.TUTORIAL:
                countEnd = 5;
                break;
            case BattleShipConstants.SIMPLE:
            case BattleShipConstants.RANDOM:
                countEnd = 6;
                break;
        }

        TextView titleText = (TextView) findViewById(R.id.Enemy_Map_String);
        String string = getString(R.string.choose_map_title_choose) + " "
                + countEnd + " " + getString(R.string.choose_map_title_points);
        titleText.setText(string);
    }

    private void setChooseButton() {
        for (int i = 0; i < MapState.MAX_LENGTH; i++) {
            for (int j = 0; j < MapState.MAX_LENGTH; j++) {
                chooseButtonList[i][j] = (Button) findViewById(chooseButtonId[i][j]);
                chooseButtonList[i][j].setOnClickListener(onClickListener);
            }
        }
    }

    private void sendNextActivity() {
        Intent intent = new Intent(getApplicationContext(),
                MainBattleActivity.class);
        intent.putExtra(BattleShipConstants.MODE_SELECT,
                getIntent().getStringExtra(BattleShipConstants.MODE_SELECT));
        startActivity(intent);
    }

    public static class ConformDialog extends DialogFragment {
        public static final String CONFORM_DIALOG = "ConfirmDialogTag";

        public static ConformDialog newInstance(int title) {
            ConformDialog frag = new ConformDialog();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final ChoosePlayerActivity targetActivity = (ChoosePlayerActivity) getActivity();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.confirm_dialog_title)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            targetActivity.sendNextActivity();
                            dismiss();
                            getActivity().finish();
                        }
                    })
                    .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            targetActivity.recreate();
                            dismiss();
                        }
                    });
            Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }
    }
}
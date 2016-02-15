package com.digitalgame.battleship;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.digitalgame.battleship.Dialogs.ConformDialog;
import com.digitalgame.battleship.Service.DeliverServiceImpl;
import com.digitalgame.battleship.Map.MapState;
import com.digitalgame.battleship.Map.MapStateImpl;

public class ChoosePlayer extends Activity {
    private static final int CHOOSE_COUNT_START = 0;
    private static final int CHOOSE_COUNT_END = 6;

    private int mChooseCount;
    private MapState mPlayerChoose;
    private TextView mChoosePlayerTitle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player_point);

        mChoosePlayerTitle = (TextView)findViewById(R.id.Enemy_Map_String);
        makeTitle();

        setChooseButton();
        mChooseCount = CHOOSE_COUNT_START;
        mPlayerChoose = new MapStateImpl();
        mPlayerChoose.setWidgetId(chooseButtonId);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mChooseCount < CHOOSE_COUNT_END) {
                v.setBackgroundColor(Color.DKGRAY);
                v.setClickable(false);

                mPlayerChoose.setHitArea(v.getId());
                mChooseCount++;
            }

            if (mChooseCount >= CHOOSE_COUNT_END) {
                DeliverServiceImpl.setPlayerMap(mPlayerChoose, DeliverServiceImpl.IS_PLAYER);

                DialogFragment fragment = new ConformDialog();
                fragment.show(getFragmentManager(), null);
            }
        }
    };

    private void setChooseButton() {
        for (int i = 0; i < MapState.MAX_LENGTH; i++) {
            for (int j = 0; j < MapState.MAX_LENGTH; j++) {
                chooseButtonList[i][j] = (Button) findViewById(chooseButtonId[i][j]);
                chooseButtonList[i][j].setOnClickListener(onClickListener);
            }
        }
    }

    private void makeTitle() {
        String string = getString(R.string.choose_map_title_choose)
                + " " + CHOOSE_COUNT_END + " " + getString(R.string.choose_map_title_points);
        mChoosePlayerTitle.setText(string);
    }
}
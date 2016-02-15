package com.digitalgame.battleship.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.digitalgame.battleship.GameState;
import com.digitalgame.battleship.R;

public class FinMessageDialog extends DialogFragment {
    private GameState mGameState;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mGameState = GameState.getState();
        if(mGameState.isPlayerWin()) {
            builder.setTitle(R.string.end_message_win);
        } else {
            builder.setTitle(R.string.end_message_lose);
        }
        builder.setPositiveButton(R.string.Dialog_Yes, new DialogInterface.OnClickListener() {
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


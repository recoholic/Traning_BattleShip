package com.digitalgame.battleship.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.digitalgame.battleship.GameState;
import com.digitalgame.battleship.R;

public class ConformDialog extends DialogFragment {
    private GameState mGameState = GameState.getState();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.Dialog_Title)
                .setPositiveButton(R.string.Dialog_Yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mGameState.setPlayerState();
                        getActivity().finish();
                    }
                })
                .setNeutralButton(R.string.Dialog_Cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finish();
                    }
                });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

}
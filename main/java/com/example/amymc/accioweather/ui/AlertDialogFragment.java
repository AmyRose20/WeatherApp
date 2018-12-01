package com.example.amymc.accioweather.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.amymc.accioweather.R;

public class AlertDialogFragment extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(getString(R.string.error_title)).setMessage(getString(R.string.error_message))
        .setPositiveButton(getString(R.string.error_button_ok_text), null);

        return builder.create();
    }
}

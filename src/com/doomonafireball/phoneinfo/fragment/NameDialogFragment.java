package com.doomonafireball.phoneinfo.fragment;

import com.google.inject.Inject;

import com.doomonafireball.phoneinfo.Datastore;
import com.doomonafireball.phoneinfo.R;
import com.doomonafireball.phoneinfo.activity.StartupActivity;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import oak.CancelEditText;

/**
 * User: derek Date: 10/1/12 Time: 3:48 PM
 */
public class NameDialogFragment extends RoboSherlockDialogFragment {

    @Inject Datastore mDatastore;

    public static NameDialogFragment newInstance() {
        NameDialogFragment frag = new NameDialogFragment();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
        final View view = getSherlockActivity().getLayoutInflater().inflate(R.layout.name_dialog_fragment, null);
        final CancelEditText cet = (CancelEditText) view.findViewById(R.id.phone_name_edit_text);
        cet.append(mDatastore.getPhoneName());
        builder.setTitle("Phone Name");
        builder.setView(view);
        builder.setPositiveButton("Set",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mDatastore.persistPhoneName(cet.getText().toString());
                        ((StartupActivity) getSherlockActivity()).doPositiveNameClick();
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }
}

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

import roboguice.inject.InjectResource;

/**
 * User: derek Date: 10/1/12 Time: 3:48 PM
 */
public class TierDialogFragment extends RoboSherlockDialogFragment {

    @Inject Datastore mDatastore;

    @InjectResource(R.array.tiers) String[] tiers;

    public static TierDialogFragment newInstance() {
        TierDialogFragment frag = new TierDialogFragment();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int currSelected = -1;
        for (int i = 0; i < tiers.length; i++) {
            if (tiers[i].equals(mDatastore.getPhoneTier())) {
                currSelected = i;
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
        builder.setSingleChoiceItems(tiers, currSelected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDatastore.persistPhoneTier(tiers[which]);
                ((StartupActivity) getSherlockActivity()).doPositiveTierClick();
                dialog.dismiss();
            }
        });
        //final View view = getSherlockActivity().getLayoutInflater().inflate(R.layout.tier_dialog_fragment, null);
        //final ListView lv = (ListView) view.findViewById(R.id.tier_list_view);
        //builder.setView(view);
        builder.setTitle("Phone Tier");
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }
}

package com.doomonafireball.phoneinfo.activity;

import com.google.inject.Inject;

import com.doomonafireball.phoneinfo.Datastore;
import com.doomonafireball.phoneinfo.R;
import com.doomonafireball.phoneinfo.fragment.NameDialogFragment;
import com.doomonafireball.phoneinfo.fragment.TierDialogFragment;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import roboguice.inject.InjectView;

public class StartupActivity extends RoboSherlockFragmentActivity {

    @Inject Datastore mDatastore;

    @InjectView(R.id.manufacturer) private TextView manufacturer;
    @InjectView(R.id.name) private TextView name;
    @InjectView(R.id.os) private TextView os;
    @InjectView(R.id.screen) private TextView screen;
    @InjectView(R.id.tier) private TextView tier;
    @InjectView(R.id.year) private TextView year;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        manufacturer.setText(Build.MANUFACTURER.toUpperCase() + "\n" + Build.MODEL.toUpperCase());
        // name.setText()
        setNameText();
        setOsText();
        setScreenText();

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment nameFragment = NameDialogFragment.newInstance();
                nameFragment.show(getSupportFragmentManager(), "dialog");
            }
        });
        tier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment tierFragment = TierDialogFragment.newInstance();
                tierFragment.show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    private void setTierText() {
        tier.setText(mDatastore.getPhoneTier().toUpperCase());
    }

    private void setNameText() {
        name.setText(mDatastore.getPhoneName().toUpperCase());
    }

    public void doPositiveNameClick() {
        setNameText();
    }

    public void doPositiveTierClick() {
        setTierText();
    }

    private void setScreenText() {
        String screenText = "";
        Display d = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int heightPx = d.getHeight();
        int widthPx = d.getWidth();
        try {
            Method mGetRawW = Display.class.getMethod("getRawWidth");
            Method mGetRawH = Display.class.getMethod("getRawHeight");
            heightPx = (Integer) mGetRawW.invoke(d);
            widthPx = (Integer) mGetRawH.invoke(d);
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        screenText += String.format("%.2f", screenInches);
        screenText += "\" ";
        screenText += heightPx + "x" + widthPx;
        screenText += "\n";
        switch (dm.densityDpi) {
            case 120:
                screenText += "LDPI";
                break;
            case 160:
                screenText += "MDPI";
                break;
            case 213:
                screenText += "TVDPI";
                break;
            case 240:
                screenText += "HDPI";
                break;
            case 320:
                screenText += "XHDPI";
                break;
            case 480:
                screenText += "XXHDPI";
                break;
        }
        screen.setText(screenText);
    }

    private void setOsText() {
        String osText = "" + Build.VERSION.RELEASE + " ";
        if (Build.VERSION.RELEASE.startsWith("1.5")) {
            osText += "CUPCAKE";
            osText += "\n";
            osText += "API LEVEL 3";
        } else if (Build.VERSION.RELEASE.startsWith("1.6")) {
            osText += "DONUT";
            osText += "\n";
            osText += "API LEVEL 4";
        } else if (Build.VERSION.RELEASE.startsWith("2.0.1")) {
            osText += "ECLAIR";
            osText += "\n";
            osText += "API LEVEL 6 [0_1]";
        } else if (Build.VERSION.RELEASE.startsWith("2.0")) {
            osText += "ECLAIR";
            osText += "\n";
            osText += "API LEVEL 5";
        } else if (Build.VERSION.RELEASE.startsWith("2.1")) {
            osText += "ECLAIR";
            osText += "\n";
            osText += "API LEVEL 7";
        } else if (Build.VERSION.RELEASE.startsWith("2.2")) {
            osText += "FROYO";
            osText += "\n";
            osText += "API LEVEL 8";
        } else if (Build.VERSION.RELEASE.startsWith("2.3.4")) {
            osText += "GINGERBREAD";
            osText += "\n";
            osText += "API LEVEL 10 [MR1]";
        } else if (Build.VERSION.RELEASE.startsWith("2.3.3")) {
            osText += "GINGERBREAD";
            osText += "\n";
            osText += "API LEVEL 10 [MR1]";
        } else if (Build.VERSION.RELEASE.startsWith("2.3")) {
            osText += "GINGERBREAD";
            osText += "\n";
            osText += "API LEVEL 9";
        } else if (Build.VERSION.RELEASE.startsWith("3.2")) {
            osText += "HONEYCOMB";
            osText += "\n";
            osText += "API LEVEL 13 [MR2]";
        } else if (Build.VERSION.RELEASE.startsWith("3.1")) {
            osText += "HONEYCOMB";
            osText += "\n";
            osText += "API LEVEL 12 [MR1]";
        } else if (Build.VERSION.RELEASE.startsWith("3.")) {
            osText += "HONEYCOMB";
            osText += "\n";
            osText += "API LEVEL 11";
        } else if (Build.VERSION.RELEASE.startsWith("4.0.4")) {
            osText += "ICE CREAM SANDWICH";
            osText += "\n";
            osText += "API LEVEL 15 [MR1]";
        } else if (Build.VERSION.RELEASE.startsWith("4.0.3")) {
            osText += "ICE CREAM SANDWICH";
            osText += "\n";
            osText += "API LEVEL 15 [MR1]";
        } else if (Build.VERSION.RELEASE.startsWith("4.0")) {
            osText += "ICE CREAM SANDWICH";
            osText += "\n";
            osText += "API LEVEL 14";
        } else if (Build.VERSION.RELEASE.startsWith("4.1")) {
            osText += "JELLY BEAN";
            osText += "\n";
            osText += "API LEVEL 16";
        }
        os.setText(osText);
    }
}


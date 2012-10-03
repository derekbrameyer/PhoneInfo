package com.doomonafireball.phoneinfo;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import android.content.SharedPreferences;

@Singleton
public class Datastore {

    private static final String DEVICE_VERSION = "DeviceVersion";
    private static final String PHONE_NAME = "PhoneName";
    private static final String PHONE_TIER = "PhoneTier";

    @Inject EncryptedSharedPreferences encryptedSharedPreferences;

    private SharedPreferences.Editor getEditor() {
        return encryptedSharedPreferences.edit();
    }

    private SharedPreferences getPrefs() {
        return encryptedSharedPreferences;
    }

    public int getVersion() {
        return getPrefs().getInt(DEVICE_VERSION, 0);
    }

    public void persistVersion(int version) {
        getEditor().putInt(DEVICE_VERSION, version).commit();
    }

    public String getPhoneName() {
        return getPrefs().getString(PHONE_NAME, "UNNAMED");
    }

    public void persistPhoneName(String phoneName) {
        getEditor().putString(PHONE_NAME, phoneName).commit();
    }

    public String getPhoneTier() {
        return getPrefs().getString(PHONE_TIER, "Worst-of-the-worst");
    }

    public void persistPhoneTier(String phoneTier) {
        getEditor().putString(PHONE_TIER, phoneTier).commit();
    }
}


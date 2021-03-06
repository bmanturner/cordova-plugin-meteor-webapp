package com.meteor.webapp;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class WebAppConfiguration {
    private SharedPreferences preferences;

    public WebAppConfiguration(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public String getAppId() {
        return preferences.getString("appId", null);
    }

    public void setAppId(String appId) {
        preferences.edit().putString("appId", appId).commit();
    }

    public String getRootUrlString() {
        return preferences.getString("rootUrl", null);
    }

    public void setRootUrlString(String rootUrlString) {
        preferences.edit().putString("rootUrl", rootUrlString).commit();
    }

    public String getCordovaCompatibilityVersion() {
        return preferences.getString("cordovaCompatibilityVersion", null);
    }

    public void setCordovaCompatibilityVersion(String version) {
        preferences.edit().putString("cordovaCompatibilityVersion", version).commit();
    }

    public String getLastDownloadedVersion() {
        return preferences.getString("lastDownloadedVersion", null);
    }

    public void setLastDownloadedVersion(String version) {
        preferences.edit().putString("lastDownloadedVersion", version).commit();
    }

    public String getLastSeenInitialVersion() {
        return preferences.getString("lastSeenInitialVersion", null);
    }

    public void setLastSeenInitialVersion(String version) {
        preferences.edit().putString("lastSeenInitialVersion", version).commit();
    }

    public String getLastKnownGoodVersion() {
        return preferences.getString("lastKnownGoodVersion", null);
    }

    public void setLastKnownGoodVersion(String version) {
        preferences.edit().putString("lastKnownGoodVersion", version).commit();
    }

    public Set<String> getBlacklistedVersions() {
        Set<String> blacklistedVersions = preferences.getStringSet("blacklistedVersions", Collections.EMPTY_SET);
        Log.d("BLACKLIST", "getBlacklistedVersions: " + blacklistedVersions);
        return blacklistedVersions;
    }

    public void addBlacklistedVersion(String version) {
        Set<String> blacklistedVersionForRetry = preferences.getStringSet("blacklistedVersionsForRetry", Collections.EMPTY_SET);
        Set<String> blacklistedVersions = new HashSet<String>(getBlacklistedVersions());
        blacklistedVersions.add(version);
        
        if (blacklistedVersionForRetry.isEmpty()) {
            Log.d("BLACKLIST", "blacklisting version for retry: " + blacklistedVersions);
            preferences.edit().putStringSet("blacklistedVersionsForRetry", blacklistedVersions).commit();
        } else {
            Log.d("BLACKLIST", "blacklisting version for real: " + blacklistedVersions);
            preferences.edit().putStringSet("blacklistedVersionsForRetry", Collections.EMPTY_SET).commit();
            preferences.edit().putStringSet("blacklistedVersions", blacklistedVersions).commit();
        }
    }

    public void reset() {
        preferences.edit().clear().commit();
    }
}

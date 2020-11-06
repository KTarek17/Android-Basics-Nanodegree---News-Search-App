package com.example.android.newsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }

    @SuppressWarnings("deprecation")
    public static class NewsPreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference pageSize = findPreference(getString(R.string.settings_page_size_key));
            bindPreferenceSummaryToValue(pageSize);
        }

        private void bindPreferenceSummaryToValue(Preference pageSize) {
            pageSize.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(pageSize.getContext());
            String preferenceString = preferences.getString(pageSize.getKey(), "");
            onPreferenceChange(pageSize, preferenceString);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            preference.setSummary(newValue.toString());
            return true;
        }
    }
}
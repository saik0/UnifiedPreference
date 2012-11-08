package net.saik0.android.unifiedpreference;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.text.TextUtils;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class UnifiedPreferenceFragment extends PreferenceFragment {
	public static final String ARG_PREFERENCE_RES = "unifiedpreference_preferenceRes";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Pseudo-inherit sharedPreferencesName and sharedPreferencesMode from Activity
		UnifiedPreferenceContainer container = (UnifiedPreferenceContainer) getActivity();
		String sharedPreferencesName = container.getSharedPreferencesName();
		int sharedPreferencesMode = container.getSharedPreferencesMode();
		PreferenceManager preferenceManager = getPreferenceManager();
		if (!TextUtils.isEmpty(sharedPreferencesName)) {
			preferenceManager.setSharedPreferencesName(sharedPreferencesName);
		}
		if (sharedPreferencesMode != 0) {
			preferenceManager.setSharedPreferencesMode(sharedPreferencesMode);
		}

		// Inflate from preferences.xml file
		int layoutRes = getArguments().getInt(ARG_PREFERENCE_RES, 0);
		if (layoutRes > 0) {
			addPreferencesFromResource(layoutRes);
			onBindPreferenceSummariesToValues();
		}
	}

	/**
	 * Bind the summaries of EditText/List/Dialog/Ringtone preferences
	 * to their values. When their values change, their summaries are
	 * updated to reflect the new value, per the Android Design
	 * guidelines.
	 */
	protected void onBindPreferenceSummariesToValues() {
		UnifiedPreferenceUtils.bindAllPreferenceSummariesToValues(getPreferenceScreen());
	}
}

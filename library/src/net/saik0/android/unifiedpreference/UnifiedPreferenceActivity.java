/* 
** Copyright 2012, Joel Pedraza
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
**     http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the License for the specific language governing permissions and
** limitations under the License.
*/

package net.saik0.android.unifiedpreference;

import android.annotation.TargetApi;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;
import android.preference.TwoStatePreference;
import android.text.TextUtils;

import java.util.List;

import net.saik0.android.unifiedpreference.R;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public abstract class UnifiedPreferenceActivity extends PreferenceActivity {
	public abstract List<UnifiedPreference> getPreferenceList();
	public abstract int getHeaders();

	private boolean mSinglePane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mSinglePane = isSimplePreferences();
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		setupSimplePreferencesScreen();
	}

	/**
	 * Shows the simplified settings UI if the device configuration if the
	 * device configuration dictates that a simplified, single-pane UI should be
	 * shown.
	 */
	@SuppressWarnings("deprecation")
	private void setupSimplePreferencesScreen() {
		if (!mSinglePane) {
			return;
		}

		// In the simplified UI, fragments are not used at all and we instead
		// use the older PreferenceActivity APIs.

		// Create blank PreferenceScreen
		PreferenceScreen screen = this.getPreferenceManager().createPreferenceScreen(this);
		setPreferenceScreen(screen);
		
		// Add all preferences
		for (UnifiedPreference pref : getPreferenceList() ) {
			PreferenceCategory category = new PreferenceCategory(this);
			category.setTitle(pref.getHeader());
			screen.addPreference(category);
			addPreferencesFromResource(pref.getLayout());
		}

		// Bind summaries
		bindPreferenceSummariesToValues();
	}

	/**
	 * Determines whether the simplified settings UI should be shown. This is
	 * true if device doesn't have newer APIs like {@link PreferenceFragment},
	 * or if forced via {@link onIsHidingHeaders}, or the device doesn't have
	 * an extra-large screen. In these cases, a single-pane "simplified"
	 * settings UI should be shown.
	 */
	private boolean isSimplePreferences() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
				|| !onIsMultiPane()
				|| onIsHidingHeaders();
	}

	/** {@inheritDoc} */
	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onBuildHeaders(List<Header> target) {
		if (!mSinglePane) {
			loadHeadersFromResource(getHeaders(), target);
		}
	}

	/**
	 * Bind the summaries of EditText/List/Dialog/Ringtone preferences
	 * to their values. When their values change, their summaries are
	 * updated to reflect the new value, per the Android Design
	 * guidelines.
	 */
	@SuppressWarnings("deprecation")
	private void bindPreferenceSummariesToValues() {
		bindAllPreferenceSummariesToValues(this.getPreferenceScreen());
	}

	/**
	 * A preference value change listener that updates the preference's summary
	 * to reflect its new value.
	 */
	private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
		@Override
		public boolean onPreferenceChange(Preference preference, Object value) {
			// Do not bind toggles. 
			if (preference instanceof CheckBoxPreference 
					|| (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
						&& preference instanceof TwoStatePreference)) {
				return true;
			}

			String stringValue = value.toString();
			
			if (preference instanceof ListPreference) {
				// For list preferences, look up the correct display value in
				// the preference's 'entries' list.
				ListPreference listPreference = (ListPreference) preference;
				int index = listPreference.findIndexOfValue(stringValue);

				// Set the summary to reflect the new value.
				preference
						.setSummary(index >= 0 ? listPreference.getEntries()[index]
								: null);

			} else if (preference instanceof RingtonePreference) {
				// For ringtone preferences, look up the correct display value
				// using RingtoneManager.
				if (TextUtils.isEmpty(stringValue)) {
					// Empty values correspond to 'silent' (no ringtone).
					preference.setSummary(R.string.ringtone_silent);
				} else {
					Ringtone ringtone = RingtoneManager.getRingtone(
							preference.getContext(), Uri.parse(stringValue));

					if (ringtone == null) {
						// Clear the summary if there was a lookup error.
						preference.setSummary(null);
					} else {
						// Set the summary to reflect the new ringtone display
						// name.
						String name = ringtone
								.getTitle(preference.getContext());
						preference.setSummary(name);
					}
				}

			} else {
				// For all other preferences, set the summary to the value's
				// simple string representation.
				preference.setSummary(stringValue);
			}
			return true;
		}
	};

	/**
	 * Binds a preference's summary to its value. More specifically, when the
	 * preference's value is changed, its summary (line of text below the
	 * preference title) is updated to reflect the value. The summary is also
	 * immediately updated upon calling this method. The exact display format is
	 * dependent on the type of preference.
	 * 
	 * @see #sBindPreferenceSummaryToValueListener
	 */
	protected static void bindPreferenceSummaryToValue(Preference preference) {
		// Set the listener to watch for value changes		
		preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

		// Trigger the listener immediately with the preference's
		// current value.
		try {
			sBindPreferenceSummaryToValueListener.onPreferenceChange(
					preference,
					preference.getSharedPreferences().getString(
							preference.getKey(), ""));
		} catch (ClassCastException e) {
			// This preference does not have a String value, do nothing
		}
	}

	/**
	 * Iterate over every preference and bind the summary to it's value.
	 * This is the default behavior for Activities and Fragments.
	 *
	 * @param screen The PreferenceScreen
	 */
	protected static void bindAllPreferenceSummariesToValues(PreferenceScreen screen) {
		for (int i = 0; i < screen.getPreferenceCount(); i++) {
			bindPreferenceSummaryToValue(screen.getPreference(i));
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public abstract static class UnifiedPreferenceFragment extends PreferenceFragment {
		public abstract UnifiedPreference getUnifiedPreference();

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(getUnifiedPreference().getLayout());
			bindPreferenceSummariesToValues();
		}

		/**
		 * Bind the summaries of EditText/List/Dialog/Ringtone preferences
		 * to their values. When their values change, their summaries are
		 * updated to reflect the new value, per the Android Design
		 * guidelines.
		 */
		private void bindPreferenceSummariesToValues() {
			UnifiedPreferenceActivity.bindAllPreferenceSummariesToValues(this.getPreferenceScreen());
		}
	}
}

package net.saik0.android.unifiedpreference;

import java.util.List;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
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

public final class UnifiedPreferenceUtils {

	private UnifiedPreferenceUtils() {
		// This class cannot be instantiated
	}

	/**
	 * Determines whether the simplified settings UI should be shown. This is
	 * true if device doesn't have newer APIs like {@link PreferenceFragment},
	 * or if forced via {@link onIsHidingHeaders}, or the device doesn't have
	 * an extra-large screen. In these cases, a single-pane "simplified"
	 * settings UI should be shown.
	 */
	public static boolean isSinglePane(PreferenceActivity activity) {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
					|| !activity.onIsMultiPane()
					|| activity.onIsHidingHeaders();
	}

	/**
	 * Shows the simplified settings UI if the device configuration if the
	 * device configuration dictates that a simplified, single-pane UI should be
	 * shown.
	 */
	@SuppressWarnings("deprecation")
	public static void getLegacyPreferencesScreen(PreferenceActivity activity, List<UnifiedPreference> prefs) {
		// In the simplified UI, fragments are not used at all and we instead
		// use the older PreferenceActivity APIs.

		// Add all preferences
		activity.setPreferenceScreen(activity.getPreferenceManager().createPreferenceScreen(activity));
		for (UnifiedPreference pref : prefs ) {
			PreferenceCategory category = new PreferenceCategory(activity);
			category.setTitle(pref.getHeader());
			activity.getPreferenceScreen().addPreference(category);
			activity.addPreferencesFromResource(pref.getLayout());
		}
	}

	/**
	 * A preference value change listener that updates the preference's summary
	 * to reflect its new value.
	 */
	private static final Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
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
	private static void bindPreferenceSummaryToValue(Preference preference) {
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
	public static void bindAllPreferenceSummariesToValues(PreferenceScreen screen) {
		for (int i = 0; i < screen.getPreferenceCount(); i++) {
			bindPreferenceSummaryToValue(screen.getPreference(i));
		}
	}
}

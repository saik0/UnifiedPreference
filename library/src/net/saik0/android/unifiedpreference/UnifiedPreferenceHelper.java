package net.saik0.android.unifiedpreference;

import java.util.List;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceActivity.Header;

public class UnifiedPreferenceHelper {
	private final PreferenceActivity mActivity;
	private final int mHeaders;
	private final List<UnifiedPreference> mPreferences;
	private Boolean mSinglePane;
	private String mSharedPreferencesName;
	private int mSharedPreferencesMode;

	public UnifiedPreferenceHelper(PreferenceActivity activity, int headers, List<UnifiedPreference> preferences) {
		mActivity = activity;
		mHeaders = headers;
		mPreferences = preferences;
	}

	/**
	 * Determines whether the simplified settings UI should be shown. This is
	 * true if device doesn't have newer APIs like {@link PreferenceFragment},
	 * or if forced via {@link onIsHidingHeaders}, or the device doesn't have
	 * an extra-large screen. In these cases, a single-pane "simplified"
	 * settings UI should be shown.
	 */
	private boolean isSinglePane() {
		if (mSinglePane == null) {
			mSinglePane = Boolean.valueOf(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
					|| !mActivity.onIsMultiPane()
					|| mActivity.onIsHidingHeaders());
		}
		return mSinglePane.booleanValue();
	}

	/**
	 * Returns the current name of the SharedPreferences file that preferences
	 * managed by this will use. Wraps {@link PreferenceManager#getSharedPreferencesName()} if single pane,
	 * otherwise returns a locally cached String.
	 *
	 * @return The name that can be passed to {@link Context#getSharedPreferences(String, int)}
	 */
	@SuppressWarnings("deprecation")
	public String getSharedPreferencesName() {
		if (isSinglePane()) {
			return mActivity.getPreferenceManager().getSharedPreferencesName();
		}
		return mSharedPreferencesName;
	}

	/**
	 * Sets the name of the SharedPreferences file that preferences managed by
	 * this will use. Wraps {@link PreferenceManager#setSharedPreferencesName()} if single pane,
	 * otherwise cache it for use by {@link UnifiedPreferenceFragment}.
	 *
	 * @param The name of the SharedPreferences file.
	 */
	@SuppressWarnings("deprecation")
	public void setSharedPreferencesName(String sharedPreferencesName) {
		if (isSinglePane()) {
			mActivity.getPreferenceManager().setSharedPreferencesName(sharedPreferencesName);
		}
		mSharedPreferencesName = sharedPreferencesName;
	}

	/**
	 * Returns the current mode of the SharedPreferences file that preferences
	 * managed by this will use. Wraps {@link PreferenceManager#getSharedPreferencesMode()} if single pane,
	 * otherwise returns a locally cached int.
	 *
	 * @return The mode that can be passed to {@link Context#getSharedPreferences(String, int)}
	 */
	@SuppressWarnings("deprecation")
	public int getSharedPreferencesMode() {
		if (isSinglePane()) {
			return mActivity.getPreferenceManager().getSharedPreferencesMode();
		}
		return mSharedPreferencesMode;
	}

	/**
	 * Sets the mode of the SharedPreferences file that preferences managed by
	 * this will use. Wraps {@link PreferenceManager#setSharedPreferencesMode()} if single pane,
	 * otherwise cache it for use by {@link UnifiedPreferenceFragment}.
	 *
	 * @param The mode of the SharedPreferences file.
	 */
	@SuppressWarnings("deprecation")
	public void setSharedPreferencesMode(int sharedPreferencesMode) {
		if (isSinglePane()) {
			mActivity.getPreferenceManager().setSharedPreferencesMode(sharedPreferencesMode);
		}
		mSharedPreferencesMode = sharedPreferencesMode;
	}

	/**
	 * Shows the simplified settings UI if the device configuration if the
	 * device configuration dictates that a simplified, single-pane UI should be
	 * shown.
	 */
	@SuppressWarnings("deprecation")
	protected void onPostCreate(Bundle savedInstanceState) {
		if (isSinglePane()) {
			// In the simplified UI, fragments are not used at all and we instead
			// use the older PreferenceActivity APIs.

			// Add all preferences
			mActivity.setPreferenceScreen(mActivity.getPreferenceManager().createPreferenceScreen(mActivity));
			for (UnifiedPreference preference : mPreferences ) {
				PreferenceCategory category = new PreferenceCategory(mActivity);
				category.setTitle(preference.getHeader());
				mActivity.getPreferenceScreen().addPreference(category);
				mActivity.addPreferencesFromResource(preference.getLayout());
			}
			onBindPreferenceSummariesToValues();
		}
	}

	/** @see PreferenceActivity#onBuildHeaders(List) */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onBuildHeaders(List<Header> target) {
		if (!isSinglePane()) {
			mActivity.loadHeadersFromResource(mHeaders, target);
		}
	}

	/**
	 * Bind the summaries of EditText/List/Dialog/Ringtone preferences
	 * to their values. When their values change, their summaries are
	 * updated to reflect the new value, per the Android Design
	 * guidelines.
	 */
	@SuppressWarnings("deprecation")
	public void onBindPreferenceSummariesToValues() {
		UnifiedPreferenceUtils.bindAllPreferenceSummariesToValues(mActivity.getPreferenceScreen());
	}
}

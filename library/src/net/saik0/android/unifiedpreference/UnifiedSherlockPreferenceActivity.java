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
import android.os.Build;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockPreferenceActivity;


import java.util.List;

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
public abstract class UnifiedSherlockPreferenceActivity extends SherlockPreferenceActivity {
	protected abstract List<UnifiedPreference> getPreferenceList();
	protected abstract int getHeaders();

	private Boolean mSinglePane;

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (isSinglePane()) {
			UnifiedPreferenceUtils.getLegacyPreferencesScreen(this, getPreferenceList());
			bindPreferenceSummariesToValues();
		}
	}

	/**
	 * Determines whether the simplified settings UI should be shown. This is
	 * true if device doesn't have newer APIs like {@link PreferenceFragment},
	 * or if forced via {@link onIsHidingHeaders}, or the device doesn't have
	 * an extra-large screen. In these cases, a single-pane "simplified"
	 * settings UI should be shown.
	 */
	public final boolean isSinglePane() {
		if (mSinglePane == null) {
			mSinglePane = UnifiedPreferenceUtils.isSinglePane(this);
		}
		return mSinglePane.booleanValue();
	}

	/** {@inheritDoc} */
	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onBuildHeaders(List<Header> target) {
		if (!isSinglePane()) {
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
	protected void bindPreferenceSummariesToValues() {
		UnifiedPreferenceUtils.bindAllPreferenceSummariesToValues(this.getPreferenceScreen());
	}
}

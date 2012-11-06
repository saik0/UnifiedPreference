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

import android.os.Bundle;
import android.preference.PreferenceActivity;

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
public abstract class UnifiedPreferenceActivity extends PreferenceActivity
		implements UnifiedPreferenceContainer {

	protected abstract List<UnifiedPreference> getPreferenceList();
	protected abstract int getHeaders();

	private UnifiedPreferenceHelper mHelper = new UnifiedPreferenceHelper(this, getHeaders(), getPreferenceList()); 

	/**
	 * Returns the current name of the SharedPreferences file that preferences
	 * managed by this will use.
	 *
	 * @return The name that can be passed to {@link Context#getSharedPreferences(String, int)}
	 * @see UnifiedPreferenceHelper#getSharedPreferencesName()
	 */
	@Override
	public String getSharedPreferencesName() {
		return mHelper.getSharedPreferencesName();
	}

	/**
	 * Sets the name of the SharedPreferences file that preferences managed by
	 * this will use.
	 *
	 * @param The name of the SharedPreferences file.
	 * @see UnifiedPreferenceHelper#setSharedPreferencesName()
	 */
	@Override
	public void setSharedPreferencesName(String sharedPreferencesName) {
		mHelper.setSharedPreferencesName(sharedPreferencesName);
	}

	/**
	 * Returns the current mode of the SharedPreferences file that preferences
	 * managed by this will use.
	 *
	 * @return The mode that can be passed to {@link Context#getSharedPreferences(String, int)}
	 * @see UnifiedPreferenceHelper#getSharedPreferencesMode()
	 */
	@Override
	public int getSharedPreferencesMode() {
		return mHelper.getSharedPreferencesMode();
	}

	/**
	 * Sets the mode of the SharedPreferences file that preferences managed by
	 * this will use.
	 *
	 * @param The mode of the SharedPreferences file.
	 * @see UnifiedPreferenceHelper#setSharedPreferencesMode()
	 */
	@Override
	public void setSharedPreferencesMode(int sharedPreferencesMode) {
		mHelper.setSharedPreferencesMode(sharedPreferencesMode);
	}

	/** {@inheritDoc} */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mHelper.onPostCreate(savedInstanceState);
	}

	/** {@inheritDoc} */
	@Override
	public void onBuildHeaders(List<Header> target) {
		mHelper.onBuildHeaders(target);
	}

	/**
	 * Bind the summaries of EditText/List/Dialog/Ringtone preferences
	 * to their values. When their values change, their summaries are
	 * updated to reflect the new value, per the Android Design
	 * guidelines.
	 */
	public void onBindPreferenceSummariesToValues() {
		mHelper.onBindPreferenceSummariesToValues();
	}
}

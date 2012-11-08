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

package net.saik0.android.unifiedpreference.demo;

import android.content.Context;
import android.os.Bundle;

import net.saik0.android.unifiedpreference.demo.R;
import net.saik0.android.unifiedpreference.UnifiedPreferenceFragment;
import net.saik0.android.unifiedpreference.UnifiedSherlockPreferenceActivity;

public class DemoUnifiedPreferenceActivity extends UnifiedSherlockPreferenceActivity {

	@Override public void onCreate(Bundle savedInstanceState) {
		// Set header resource MUST BE CALLED BEFORE super.onCreate 
		setHeaderRes(R.xml.pref_headers);
		// Set desired preference file and mode (optional)
		setSharedPreferencesName("unified_preference_demo");
		setSharedPreferencesMode(Context.MODE_PRIVATE);
		super.onCreate(savedInstanceState);
	}

	public static class GeneralPreferenceFragment extends UnifiedPreferenceFragment {}

	public static class NotificationPreferenceFragment extends UnifiedPreferenceFragment {}

	public static class DataSyncPreferenceFragment extends UnifiedPreferenceFragment {}
}

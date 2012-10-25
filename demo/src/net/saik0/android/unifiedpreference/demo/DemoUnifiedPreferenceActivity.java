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

import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;

import net.saik0.android.unifiedpreference.demo.R;
import net.saik0.android.unifiedpreference.UnifiedPreference;
import net.saik0.android.unifiedpreference.UnifiedPreferenceActivity;

public class DemoUnifiedPreferenceActivity extends UnifiedPreferenceActivity {
	private static UnifiedPreference generalPreferences;
	private static UnifiedPreference notificationPreferences;
	private static UnifiedPreference dataSyncPreferences;
	private static LinkedList<UnifiedPreference> mPreferences = new LinkedList<UnifiedPreference>();

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		generalPreferences = new UnifiedPreference(R.string.pref_header_general, R.xml.pref_general);
		notificationPreferences = new UnifiedPreference(R.string.pref_header_notifications, R.xml.pref_notification);
		dataSyncPreferences = new UnifiedPreference(R.string.pref_header_data_sync, R.xml.pref_data_sync);
		mPreferences.add(generalPreferences);
		mPreferences.add(notificationPreferences);
		mPreferences.add(dataSyncPreferences);
	}
	
	@Override
	public List<UnifiedPreference> getPreferenceList() {
		return mPreferences;
	}

	@Override
	public int getHeaders() {
		return R.xml.pref_headers;
	}
	
	public static class GeneralPreferenceFragment extends UnifiedPreferenceFragment {
		@Override
		public UnifiedPreference getUnifiedPreference() {
			return generalPreferences;
		}
	}

	public static class NotificationPreferenceFragment extends UnifiedPreferenceFragment {
		@Override
		public UnifiedPreference getUnifiedPreference() {
			return notificationPreferences;
		}
	}

	public static class DataSyncPreferenceFragment extends UnifiedPreferenceFragment {
		@Override
		public UnifiedPreference getUnifiedPreference() {
			return dataSyncPreferences;
		}
	}
}

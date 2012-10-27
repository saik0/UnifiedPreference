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
import android.preference.PreferenceFragment;

public class UnifiedPreference {
	private final int mHeader;
	private final int mLayout;

	public UnifiedPreference(int header, int layout) {
		mHeader = header;
		mLayout = layout;
	}

	public int getHeader() {
		return mHeader;
	}

	public int getLayout() {
		return mLayout;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public abstract static class Fragment extends PreferenceFragment {
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
		protected void bindPreferenceSummariesToValues() {
			UnifiedPreferenceUtils.bindAllPreferenceSummariesToValues(this.getPreferenceScreen());
		}
	}
}

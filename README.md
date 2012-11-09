UnifiedPreference
=================

UnifiedPreference is a library for working with all versions of the 
Android Preference package from API v4 and up.

Features
========

 * Easy to use
 * Takes care of most of the boilerplate code
 * Tiny, only ~5k!
 * Use a single pane for phones, or headers and fragments for tablets
 * Binds preference values to summaries according to Android Design 
   Guidelines
 * Translated to 50 locales (imported from AOSP internal strings)
 * Uses ActionBarSherlock (optional)

How to Use
==========

 1. Ensure your project has one preference xml file for each desired header
 2. Create or modify a header xml file *using your project's own namespace*
    for header attributes. Be sure to define preferenceRes for each.

	```xml
	<preference-headers xmlns:android="http://schemas.android.com/apk/res/android"
		xmlns:unified="http://schemas.android.com/apk/res-auto" >

		<header
		unified:fragment="your.project.namespace.SampleActivity$SampleFragment"
		unified:title="@string/pref_header_sample"
		unified:preferenceRes="@xml/pref_sample" />

	</preference-headers>
	```

 3. Subclass UnifiedPreferenceActivity (or UnifiedSherlockPreferenceActivity
    for ABS). Call setHeaderRes before super.onCreate and subclass the fragments here.

	```java
	public class SampleActivity extends UnifiedPreferenceActivity {

		@Override public void onCreate(Bundle savedInstanceState) {
			setHeaderRes(R.xml.pref_headers);
			super.onCreate(savedInstanceState);
		}

		public static class SampleFragment extends UnifiedPreferenceFragment {}
	```

Thats it! See the demo project for a more in depth example of how to use the library.

Author
======

 * Joel Pedraza - [Google+](http://plus.google.com/111289811888358912498/)

Screenshots
===========

![Framed artwork](https://raw.github.com/saik0/UnifiedPreference/website/images/framed_all.png "Framed artwork")


FAQ
===

When is a two-pane layout used?

 > When com.android.internal.R.bool.preferences_prefer_dual_pane evaluates to true. In AOSP this is true for sw720dp
   but this may vary by OEM or third party ROM.

License
=======

Copyright 2012 Joel Pedraza

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


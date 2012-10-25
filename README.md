UnifiedPreference
================

UnifiedPreference is a library for working with all versions of the 
Android Preference package from API v4 and up.

Features
========

 * Easy to use
 * Takes care of most of the boilerplate code
 * Very lightweight, only ~5k!
 * Use a single pane for phones, or headers and fragments for tablets
 * Binds preference values to summaries according to Android Design 
   Guidelines
 * Translated to 50 locales (imported from AOSP internal strings)

How to Use
==========

 1. Ensure the header strings are in your project's string resources.
 2. Create a new preference xml file for each heading (category)
 3. Create a headers xml file that enumerates them
 4. Extend UnifiedPreferenceActivity
 5. Instantiate a UnifiedPreference object for each heading and add them 
    to a list
 6. Implement methods getHeaders() and getPreferenceList()
 7. Add a new fragment for each heading by extending 
    UnifiedPreferenceFragment

See the demo project for an example of how to use the library.

Author
======

 * Joel Pedraza - [Google+](http://plus.google.com/111289811888358912498/)

Screenshots
===========

![Framed artwork](https://raw.github.com/saik0/UnifiedPreference/master/images/framed_all.png "Framed artwork")

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


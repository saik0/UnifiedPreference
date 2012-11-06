package net.saik0.android.unifiedpreference;

public interface UnifiedPreferenceContainer {
	public String getSharedPreferencesName();
	public void setSharedPreferencesName(String sharedPreferencesName);
	public int getSharedPreferencesMode();
	public void setSharedPreferencesMode(int sharedPreferencesMode);
}

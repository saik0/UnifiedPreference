package net.saik0.android.unifiedpreference;

public interface UnifiedPreferenceContainer {
	public int getHeaderRes();
	public void setHeaderRes(int headerRes);
	public String getSharedPreferencesName();
	public void setSharedPreferencesName(String sharedPreferencesName);
	public int getSharedPreferencesMode();
	public void setSharedPreferencesMode(int sharedPreferencesMode);
}

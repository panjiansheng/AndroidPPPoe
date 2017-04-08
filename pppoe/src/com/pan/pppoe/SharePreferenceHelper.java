package com.pan.pppoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;

public class SharePreferenceHelper {

	public static final String file = "handsSwjtu";
//	private boolean isFistStart;
//	private boolean haveLogged;
//	private String userId;
//	private String channelId;
//	private String stuCode;
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	 

	public SharePreferenceHelper() {
		sp = ContextUtil.getInstance().getSharedPreferences(file, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public boolean isFistStart() {

		return sp.getBoolean("isFirstStart", false);
	}

	public void setFistStart(boolean isFistStart) {
		editor.putBoolean("isFirstStart", isFistStart);
		editor.commit();
	}

	public boolean isHaveLogged() {

		return sp.getBoolean("haveLogged", false);
	}

	public void setHaveLogged(boolean haveLogged) {
		editor.putBoolean("haveLogged", haveLogged);
		editor.commit();
	}

	public String getUserId() {
		return sp.getString("userId", "null");
	}

	public void setUserId(String userId) {
		editor.putString("userId", userId);
		editor.commit();
	}

	public String getChannelId() {
		return sp.getString("channelId", "null");
	}

	public void setChannelId(String channelId) {
		editor.putString("channelId", channelId);
		editor.commit();
	}

	public String getStuCode() {
		return sp.getString("stuCode", "null");
	}

	public void setStuCode(String stuCode) {
		editor.putString("stuCode", stuCode);
		editor.commit();
	}
	
	public String getAppId() {
		return sp.getString("appId", "null");
	}
	
	public void setAppId(String appId) {
		editor.putString("appId", appId);
		editor.commit();
	}
	public String getPushSetting() {
		return sp.getString("pushSetting", "null");
		
	}
	public void setPushSetting(String pushSetting){
		editor.putString("pushSetting", pushSetting);
		editor.commit();
	}
	
	public void setUpdateInfo(String updateInfo){
		editor.putString("updateInfo", updateInfo);
		editor.commit();
	}
	
	public String getUpdateInfo() {
		return sp.getString("updateInfo", "null");
	}
	
	
	public void setIsMuteWhenClass(boolean isMuteWhenClass){
		editor.putBoolean("isMuteWhenClass", isMuteWhenClass);
		editor.commit();
	}
	
	public boolean getIsMuteWhenClass(){
		return sp.getBoolean("isMuteWhenClass", false);
		
	}

	
	public void setRingerMode(int RingerMode){
		editor.putInt("RingerMode", RingerMode);
		editor.commit();
	}
	
	public int getRingerMode() {
		return sp.getInt("RingerMode", AudioManager.RINGER_MODE_VIBRATE);
	}
	
	public void setVibrateTypeRinger(int VibrateTypeRinger){
		editor.putInt("VibrateTypeRinger", VibrateTypeRinger);
		editor.commit();
	}
	public int getVibrateTypeRinger() {
		return sp.getInt("VibrateTypeRinger", AudioManager.VIBRATE_SETTING_ON);
	}
	
	
	public void setVibrateTypeNotification(int VibrateTypeNotification) {
		editor.putInt("VibrateTypeNotification", VibrateTypeNotification);
		editor.commit();
	}
	
	public int getVibrateTypeNotification() {
		return sp.getInt("VibrateTypeNotification", AudioManager.VIBRATE_SETTING_ON);
	}
	
	public void setLoginUsername(String username) {
		editor.putString("loginUsername", username);
		editor.commit();
	}
	
	public String getLoginUsername() {
		return sp.getString("loginUsername", "");
	}
	
	public void setLoginPassword(String password) {
		editor.putString("loginPassword", password);
		editor.commit();
	}
	
	public String getLoginPassword() {
		return sp.getString("loginPassword", "");
	}
	
	public void setIsAutoLogin(boolean isAutoLogin) {
		editor.putBoolean("isAutoLogin", isAutoLogin);
		editor.commit();
	}
	
	public boolean getIsAutoLogin() {
		return sp.getBoolean("isAutoLogin", false);
	}
	
	
	public void setPPPoeLoginUsername(String username) {
		editor.putString("PPPoeLoginUsername", username);
		editor.commit();
	}
	
	public String getPPPoeLoginUsername() {
		return sp.getString("PPPoeLoginUsername", "");
	}
	
	public void setPPPoeLoginPassword(String password) {
		editor.putString("PPPoeLoginPassword", password);
		editor.commit();
	}
	
	public String getPPPoeLoginPassword() {
		return sp.getString("PPPoeLoginPassword", "");
	}
	
	public void setIsPPPoeAutoLogin(boolean isAutoLogin) {
		editor.putBoolean("isPPPoeAutoLogin", isAutoLogin);
		editor.commit();
	}
	
	public boolean getIsPPPoeAutoLogin() {
		return sp.getBoolean("isPPPoeAutoLogin", false);
	}
	
	
	public void setSportLoginUsername(String username) {
		editor.putString("sportLoginUsername", username);
		editor.commit();
	}
	
	public String getSportLoginUsername() {
		return sp.getString("sportLoginUsername", "");
	}
	
	public void setSportLoginPassword(String password) {
		editor.putString("sportLoginPassword", password);
		editor.commit();
	}
	
	public String getSportLoginPassword() {
		return sp.getString("sportLoginPassword", "");
	}
	
	
	public void setNewsTypeLastView(int newsType) {
		editor.putInt("newsNewsTypeLastView", newsType);
		editor.commit();
	}
	
	public int getNewsTypeLastView() {
		return sp.getInt("newsNewsTypeLastView", 3);
	}
	
}

package com.increvenue.core.driver;

public interface TestConstants {

	// Test constants
	public String APPIUM_DEMO_APP_PACKAGE = "io.appium.android.apis";
	public String APPIUM_DEMO_APP_ACTIVITY = "io.appium.android.apis.ApiDemos";
	public String DOWNLOAD_APK_URL = "https://github.com/bcbrijwasi/demoApkFile/raw/master/ApiDemos-debug.apk";
	public String APK_FILE_NAME = "ApiDemos-debug.apk";
	public String[] APK_COMMANDS = { "io.appium.android.ime", "io.appium.settings", "io.appium.unlock",
			"io.appium.uiautomator2.server", "io.appium.uiautomator2.server.test" };
	public String APK_INSTALL_COMMAND = "adb install " + System.getProperty("user.dir") + "\\apkFile\\";
	public String APK_UNINSTALL_COMMAND = "adb uninstall ";
	public String EMULATOR_NAME = "DemoEmulator";
	public String APPIUM_SERVER_IP = "127.0.0.1";
	public String APK_NAME = "ApiDemos-debug.apk";
	public int DEFAULT_TIMEOUT = 10;
	public int MIN_DEFAULT_TIMEOUT = 0;
}

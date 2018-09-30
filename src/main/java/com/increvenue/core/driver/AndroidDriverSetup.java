package com.increvenue.core.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;


import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AndroidDriverSetup {
	
	public static AndroidDriver<MobileElement> driver; 
	public void setup(String udid){
		DesiredCapabilities cap = new DesiredCapabilities();
		cap = new DesiredCapabilities();
		cap.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, true);
		cap.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true);
		cap.setCapability("device-orientation", "portrait");
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		cap.setCapability("automationName", "uiautomator2");
		cap.setCapability(MobileCapabilityType.UDID, udid);
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "ANDROID");
		cap.setCapability("appPackage", "com.android.vending");
		cap.setCapability("appActivity", "com.android.vending.AssetBrowserActivity");
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		builder.withIPAddress("127.0.0.1");
		builder.usingAnyFreePort();
		builder.withCapabilities(cap);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
		AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
		service.start();
		String appiumServiceUrl = service.getUrl().toString();
		System.out.println(appiumServiceUrl);
		try {
			driver = new AndroidDriver<MobileElement>(new URL(appiumServiceUrl), cap);
			System.out.println(driver);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	

}

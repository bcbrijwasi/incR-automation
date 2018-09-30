package com.increvenue.base;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.increvenue.core.driver.AndroidDriverSetup;

import io.appium.java_client.android.Activity;

public class BaseTest extends AndroidDriverSetup{
	
	@BeforeSuite(alwaysRun=true)
	public void startServer(ITestContext context){
		System.out.println("BeforeSuite >>>>>>>>>>>>>>>>>>");
		String udid = context.getCurrentXmlTest().getParameter("udid");
		setup(udid);
	}
	
	@BeforeTest(alwaysRun=true)
	public void launchApp(){
		System.out.println("BeforeTest >>>>>>>>>>>>>>>>>>>");
		try {
			Activity activity = new Activity("com.android.vending", "com.android.vending.AssetBrowserActivity");
			activity.setAppWaitPackage("com.android.vending");
			activity.setAppWaitActivity("com.android.vending.AssetBrowserActivity");
			activity.setStopApp(false);
			driver.startActivity(activity);
			System.out.println("App is launched");
		} catch (Exception e) {
		}
	}

}

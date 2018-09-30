package com.increvenue.test;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.increvenue.base.BaseTest;

import io.appium.java_client.MobileElement;

public class MyTest extends BaseTest{

	@Test(enabled = true, description = "demo")
	public void demoTest() throws InterruptedException{
		installAPK("wordweb");
		
	}
	
	public void installAPK(String apkFile) throws InterruptedException{
		pauseExecution();
		pauseExecution();
		pauseExecution();
		By SEARCH_BAR = By.id("com.android.vending:id/search_box_idle_text");
		MobileElement ele = driver.findElement(SEARCH_BAR);
		ele.click();	
		pauseExecution();
		pauseExecution();
		By search = By.id("com.android.vending:id/search_box_text_input");
		driver.findElement(search).sendKeys(apkFile);
		driver.pressKeyCode(66);
		pauseExecution();
		pauseExecution();
		By INSTALL_BUTTON = By.xpath("//android.widget.Button[@text='INSTALL']");
		ele = driver.findElement(INSTALL_BUTTON);
		ele.click();
		
	}
	
	public void pauseExecution() throws InterruptedException{
		Thread.sleep(5000);
	}
}

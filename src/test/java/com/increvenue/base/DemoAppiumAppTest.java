package com.increvenue.base;

import org.openqa.selenium.By;

public class DemoAppiumAppTest extends BaseTest {
	public By ACTION_BAR = By.id("android:id/action_bar");
	public By SECOND_LEVEL_ACTION_BAR = By.xpath("//android.widget.TextView[@text='API Demos']");
	public String MENU_OPTION = "//android.widget.TextView[@text='%s']";
	public By CLONING_SECOND_LEVEN_MENU_OPTION = By.xpath("//android.widget.TextView[@text='Cloning']");
	public By RUN_BUTTON = By.id("io.appium.android.apis:id/startButton");

	public void tapFirstLevelMenuOption(String option) {
		waitForElementPresent(ACTION_BAR);
		By loc = By.xpath(String.format(MENU_OPTION, option));
		waitForElementPresent(loc);
		if (isElementPresent(loc)) {
			tap(loc);
			System.out.println("Fisrt level menu option '" + option + "' is clicked");
		}
	}

	public void tapSecondLevelMenuOption(String option) {
		waitForElementPresent(SECOND_LEVEL_ACTION_BAR);
		By loc = By.xpath(String.format(MENU_OPTION, option));
		waitForElementPresent(loc);
		if (isElementPresent(loc)) {
			tap(loc);
			System.out.println("Second level menu option '" + option + "' is clicked");
		}
	}

	public void tapRunButton() {
		waitForElementPresent(RUN_BUTTON);
		if (isElementPresent(RUN_BUTTON)) {
			tap(RUN_BUTTON);
			System.out.println("Run biutton is clicked");
		}
		pauseExecution(8000);
	}
}

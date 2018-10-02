package com.increvenue.base;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import com.increvenue.core.driver.AndroidDriverSetup;
import io.appium.java_client.MobileElement;

public class BaseTest extends AndroidDriverSetup {

	@BeforeSuite(alwaysRun = true)
	public void startServer(ITestContext context) {
		System.out.println("BeforeSuite method starts >>>>>>>>>>>>>>>>>>");
		String udid = context.getCurrentXmlTest().getParameter("udid");
		String emulator = context.getCurrentXmlTest().getParameter("emulator_name");
		System.out.println("Using '" + emulator + "'");
		setup(udid, emulator);
		System.out.println("BeforeSuite method ends >>>>>>>>>>>>>>>>>>");
	}

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		System.out.println("BeforeTest method starts >>>>>>>>>>>>>>>>>>>");
		launchApp(APPIUM_DEMO_APP_PACKAGE, APPIUM_DEMO_APP_ACTIVITY);
		System.out.println("BeforeTest method ends >>>>>>>>>>>>>>>>>>");
	}
	
	@AfterTest
	public void afterTest(){
		System.out.println("AfterTest method starts >>>>>>>>>>>>>>>>>>>");
		uninstallApplication(APPIUM_DEMO_APP_PACKAGE);	
		System.out.println("AfterTest method ends >>>>>>>>>>>>>>>>>>>");
	}
	
	@AfterSuite
	public void afterSuite(){
		System.out.println("AfterSuite method starts >>>>>>>>>>>>>>>>>>>");
		removeAPKFile(APK_FILE_NAME);
		driver.quit();
		System.out.println("AfterSuite method starts >>>>>>>>>>>>>>>>>>>");
		
	}

	public void type(By locator, String text) {
		System.out.println("Going to type -'" + text + "' in" + locator);
		driver.findElement(locator).sendKeys(text);
		keyboardEnterEvent();
	}

	public void keyboardEnterEvent() {
		driver.pressKeyCode(66);
	}

	public void tap(By locator) {
		System.out.println("Trying to click the location with locator -'" + locator);
		MobileElement ele = driver.findElement(locator);
		clickElement(ele);
	}

	public void waitForElementPresent(By locator) {
		try {
			turnOnImplicitWaits();
			System.out.println("waiting for locator " + locator);
			waitExplicitly(locator, 5000);
		} catch (NoSuchElementException e) {
			System.out.println("Element -'" + locator.toString() + "' is not found");
		}
		turnOffImplicitWaits();
	}

	public void waitExplicitly(By locator, int time) throws NoSuchElementException {
		try {
			time = time / 1000;
			for (int i = 0; i < time; i++) {
				pauseExecution(1000);
				MobileElement element = driver.findElement(locator);
				if (!(element.getSize() == null)) {
					break;
				}
			}
		} catch (NoSuchElementException e) {
			System.out.println("Error occurred while clicking element '" + locator.toString() + "'");
			e.printStackTrace();
		}

	}
	
	public boolean isElementPresent(By locator){
		waitExplicitly(locator, 30);
		List<MobileElement> ele = driver.findElements(locator);
		if (ele.size() > 0) {
			System.out.println("Element " + locator + " is present and visible");
			return true;
		}else{
			System.out.println("Element " + locator + " is not present and visible");
			return false;
		}	
	}
	
	public static void turnOffImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(MIN_DEFAULT_TIMEOUT, TimeUnit.SECONDS);
	}

	public static void turnOnImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
	}

	public void clickElement(WebElement element) {
		waitForElementToBeClickable(element, DEFAULT_TIMEOUT).click();
	}
	
	public WebElement waitForElementToBeClickable(WebElement element, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		return element;
	}

}

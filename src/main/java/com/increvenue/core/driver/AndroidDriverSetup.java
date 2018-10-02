package com.increvenue.core.driver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AndroidDriverSetup implements TestConstants {
	Utils util = new Utils();
	public static AndroidDriver<MobileElement> driver;

	public void setup(String udid, String emulator) {
		boolean isDownloaded = false;
		for (int i = 0; i < 5; i++) {
			if (downloadAPK(APK_FILE_NAME, DOWNLOAD_APK_URL)) {
				isDownloaded = true;
				break;
			}
		}
		if (isDownloaded) {
			if(!isEmulatorAlreadyRunning()){
			launchEmulator(EMULATOR_NAME);
			}
			installAPK(APK_NAME);
			DesiredCapabilities cap = new DesiredCapabilities();
			cap = new DesiredCapabilities();
			cap.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, true);
			cap.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true);
			cap.setCapability("device-orientation", "portrait");
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
			cap.setCapability("automationName", "uiautomator2");
			if (Objects.nonNull(udid) && !udid.isEmpty()) {
				cap.setCapability(MobileCapabilityType.UDID, udid);
			}
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, "ANDROID");
			cap.setCapability(AndroidMobileCapabilityType.AVD, emulator);
			cap.setCapability(AndroidMobileCapabilityType.AVD_READY_TIMEOUT, "120000");	
			cap.setCapability("appPackage", APPIUM_DEMO_APP_PACKAGE);
			cap.setCapability("appActivity", APPIUM_DEMO_APP_ACTIVITY);
			AppiumServiceBuilder builder = new AppiumServiceBuilder();
			builder.withIPAddress(APPIUM_SERVER_IP);
			builder.usingAnyFreePort();
			builder.withCapabilities(cap);
			builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
			builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
			AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
			service.start();
			String appiumServiceUrl = service.getUrl().toString();
			System.out.println(appiumServiceUrl);
			try {
				util.uninstallPreviousVersionFileOnDevice(udid, APK_COMMANDS);
				driver = new AndroidDriver<MobileElement>(new URL(appiumServiceUrl), cap);
				System.out.println(driver);
				pauseExecution(5000);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("APK File is not available try after sometime");
			System.exit(0);
		}
	}

	public void launchApp(String appPackage, String appActivity) {
		try {
			Activity activity = new Activity(appPackage, appActivity);
			activity.setAppWaitPackage(appPackage);
			activity.setAppWaitActivity(appActivity);
			activity.setStopApp(false);
			driver.startActivity(activity);
			System.out.println("App is launched");
			System.out.println("BeforeTest method ends >>>>>>>>>>>>>>>>>>");
			pauseExecution(5000);
		} catch (Exception e) {
		}
	}

	public FirefoxDriver getFirefoxDriver() {
		try {
			String driverPath = new File("tools/geckoDriver/geckodriver.exe").getCanonicalPath();
			System.setProperty("webdriver.gecko.driver", driverPath);
			// DesiredCapabilities capabilities=DesiredCapabilities.firefox();
			// capabilities.setCapability("marionette", true);
			// return new FirefoxDriver(capabilities);
			return new FirefoxDriver();
		} catch (IOException e) {
			return null;
		}
	}

	public boolean downloadAndSaveAPK(String fileName, URL download) {
		try {	
			String saveTo = System.getProperty("user.dir") + "\\apkFile\\";
			URLConnection connection = download.openConnection();
			ReadableByteChannel rbc = Channels.newChannel( connection.getInputStream());
			FileOutputStream fos = new FileOutputStream(saveTo + fileName);
			long expectedSize = connection.getContentLength();
			System.out.println( "Expected APK file size: " + expectedSize );
			long transferedSize = 0L;
			while( transferedSize < expectedSize ) {
			   transferedSize +=
			      fos.getChannel().transferFrom( rbc, transferedSize, 1 << 24 );
			   System.out.println( transferedSize + " bytes received" );
			}
			fos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void downloadAPKFileUsingBrowser(String url, String userName, String password) throws Exception {
		try {
			WebDriver driver = getFirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			driver.get(url);
			// TODO
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pauseExecution(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean downloadAPK(String fileName, String url) {
		try {
			URL APKUrl = new URL(url);
			boolean isDownloaded = downloadAndSaveAPK(fileName, APKUrl);
			if (isDownloaded) {
				System.out.println("APK File is downloaded successfully");
				return isDownloaded;
			} else {
				System.out.println("Failed to download APK File");
				return isDownloaded;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public void installAPK(String apkName) {
		List<String> result = util.runProcess(util.isWindows(), APK_INSTALL_COMMAND.concat(apkName));
		if(result.contains("Success")){
			System.out.println("Application is installed successfully");
		}else{
			System.out.println("Not able to install the Application");
		}
	}

	public void launchEmulator(String emulatorName) {
		util.startEmulator(emulatorName);
	}
	
	public void uninstallApplication(String appPackage){
		List<String> result = util.runProcess(util.isWindows(), APK_UNINSTALL_COMMAND.concat(appPackage));
		if(result.contains("Success")){
			System.out.println("Application is uninstalled successfully");
		}else{
			System.out.println("Not able to uninstall the Application");
		}
	}
	
	public void removeAPKFile(String fileName){
		 File file = new File(System.getProperty("user.dir") + "\\apkFile\\"+fileName); 
         
	        if(file.delete()) 
	        { 
	            System.out.println("APK File deleted successfully"); 
	        } 
	        else
	        { 
	            System.out.println("Failed to delete the file"); 
	        } 
	}
	
	public boolean isEmulatorAlreadyRunning(){
		String command = "adb devices";
		List<String> result = util.runProcess(util.isWindows(), command);
		System.out.println(result);
		for(String str:result){
			if(str.trim().contains("emulator")){
				System.out.println("Emulator is already running");
				return true;
			}
		}
			return false;
		}
		
}

package com.increvenue.test;

import org.testng.annotations.Test;
import com.increvenue.base.DemoAppiumAppTest;


public class IncRevenueDemoTest extends DemoAppiumAppTest{
    
	@Test(enabled = true, description = "download an APK file from any url and install it on device then run the test on the application")
	public void incRevenueAssigmentOne() throws InterruptedException{
		System.out.println("Starting test...");
		tapFirstLevelMenuOption("Animation");
		tapSecondLevelMenuOption("Cloning");	
		tapRunButton();
		System.out.println("Demo test finished...");
	}
	
			
	
}

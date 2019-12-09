package webDriver;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ReportGenerator.Reporter;

public class GetChromeDriver {

	public static WebDriver getDriverInstance()   {
		WebDriver driver =null ;
		try {
			System.setProperty("webdriver.chrome.driver",
					"H:\\ScriptRunner Application\\Chrome and IE Driver\\chromedriver.exe");
			 driver = new ChromeDriver();
			driver.manage().window().maximize();
			String DriverValue = driver.toString();
			String BrowserName = DriverValue.substring(13, 26);
			Reporter.getBrowserName(BrowserName);
			
		} catch (Exception e) {
			if (e.toString().substring(0, 38).equalsIgnoreCase("org.openqa.selenium.WebDriverException")) {
				
			}
		System.out.println("Exception in launching driver is        "+e.toString().substring(0, 38)+"end");
		}
	
		return driver;
	}

}

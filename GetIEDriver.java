package webDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import ReportGenerator.Reporter;

public class GetIEDriver {
	public static WebDriver getDriverInstance() {
		System.setProperty("webdriver.ie.driver",
				"H:\\ScriptRunner Application\\Chrome and IE Driver\\IEDriverServer.exe");
		WebDriver driver = new InternetExplorerDriver();
		driver.manage().window().maximize();
		String DriverValue = driver.toString();
		String BrowserName = DriverValue.substring(13, 26);
		Reporter.getBrowserName(BrowserName);
		return driver;
	}
}

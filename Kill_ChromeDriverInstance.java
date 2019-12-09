package webDriver;

public class Kill_ChromeDriverInstance {

	public static void killDriverInstance() {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}

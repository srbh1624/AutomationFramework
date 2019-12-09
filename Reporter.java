package ReportGenerator;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.poi.xwpf.usermodel.XWPFDocument;


public class Reporter {

	private static ArrayList<Update_TestReport> reportDetails;
	private static final String resultPlaceholder = "<!-- INSERT_RESULTS -->";
	private static final String date_and_time = "<!-- INSERT_DATE_AND_TIME -->";
	private static final String TotalTestCase = "<!-- INSERT_TOTAL_TEST_CASE -->";
	private static final String Browser = "<!-- INSERT_BROWSER -->";
	private static final String TestCasePassed = "<!-- INSERT_TEST_CASE_PASSED -->";
	private static final String TestCaseFailed = "<!-- INSERT_TEST_CASE_FAILED -->";
	private static final String ReportName = "<!-- INSERT_REPORTNAME -->";
	private static final String executionTime = "<!-- TOTAL_EXECUTION_TIME -->";
	private static final String templatePath = "H:\\ScriptRunner Application\\Report Template\\Sample_Report_Template.html";
	private static final String ScreenshotPath = "H:\\\\ScriptRunner Application\\\\ReportScreenshots\\\\";
	public static ArrayList<String> Screeenshotname = new ArrayList<String>();
	public static ArrayList<String> Screeenshotfilename = new ArrayList<String>();
	public static String BrowserName;
	public Reporter() {
	}

	public static void initialize() {
		reportDetails = new ArrayList<Update_TestReport>();
	}

	public static void UpdateTestReport(String TestCaseName, String description, String status) throws Exception {
		capturescreen();
		Update_TestReport UTR = new Update_TestReport(TestCaseName, description, status);
		reportDetails.add(UTR);
		
	}

	public static void getBrowserName(String BrowserName) {
		Reporter.BrowserName = BrowserName;
	}

	public static void capturescreen() throws Exception {
		try {

			XWPFDocument docx = new XWPFDocument();
			String screenshot_name = System.currentTimeMillis() + ".png";
			Screeenshotname.add(screenshot_name);
			BufferedImage image = new Robot()
					.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			File file = new File("H:\\ScriptRunner Application\\ReportScreenshots\\" + screenshot_name);
			Screeenshotfilename.add("H:\\ScriptRunner Application\\ReportScreenshots\\" + screenshot_name);
			ImageIO.write(image, "png", file);
			docx.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void writeResults(String fileName, String ExecutionTime) {

		try {
			String reportIn = new String(Files.readAllBytes(Paths.get(templatePath)));
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			reportIn = reportIn.replaceFirst(date_and_time, dateFormat.format(date));
			reportIn = reportIn.replaceFirst(Browser, BrowserName);
			reportIn = reportIn.replaceFirst(TotalTestCase, String.valueOf(reportDetails.size()));
			reportIn = reportIn.replaceFirst(ReportName, fileName);
			reportIn = reportIn.replaceFirst(executionTime, ExecutionTime);

			int PassCount = 0, FailCount = 0;
			for (int i = 0; i < reportDetails.size(); i++) {
				int len = Screeenshotfilename.get(i).length();
				String Screenshotfilename = Screeenshotfilename.get(i).substring(46, len);
				if (reportDetails.get(i).getStatus().equalsIgnoreCase("Pass")) {
					PassCount++;
					reportIn = reportIn.replaceFirst(resultPlaceholder,
							"<tr class='content'><td>" + Integer.toString(i + 1) + "</td><td>"
									+ reportDetails.get(i).getTestCaseName() + "</td><td>"
									+ reportDetails.get(i).getDescription() + "</td><td class='pass'>"
									+ reportDetails.get(i).getStatus() + "</td><td><img src=\"" + ScreenshotPath
									+ Screenshotfilename + "\"/></td></tr>" + resultPlaceholder);
				} else {
					FailCount++;
					String description = reportDetails.get(i).getDescription().substring(0, 160);
					reportIn = reportIn.replaceFirst(resultPlaceholder,
							"<tr class='content'><td>" + Integer.toString(i + 1) + "</td><td>"
									+ reportDetails.get(i).getTestCaseName() + "</td><td class='fail'>" + description+"...."
									+ "</td><td class='fail'>" + reportDetails.get(i).getStatus()
									+ "</td><td><img src=\"" + ScreenshotPath + Screenshotfilename + "\"/></td></tr>"
									+ resultPlaceholder);
				}
			}
			reportIn = reportIn.replaceFirst(TestCasePassed,
					"<span class='pass'>" + String.valueOf(PassCount) + "  Pass</span>");
			System.out.println(String.valueOf(PassCount));
			reportIn = reportIn.replaceFirst(TestCaseFailed,
					"<span class='fail'>" + String.valueOf(FailCount) + "  Fail</span>");
			String currentDate = new SimpleDateFormat("dd-MM-yyyy HH mm").format(new Date());
			String reportPath = "H:\\ScriptRunner Application\\Reports\\" + fileName + "report_" + currentDate
					+ ".html";
			try {
				Files.write(Paths.get(reportPath), reportIn.getBytes(), StandardOpenOption.CREATE);
			} catch (Exception e) {
				System.out.println("Exception in last"+e);
			}
			

		} catch (Exception e) {
			System.out.println("Error when writing report file:\n" + e.toString());
		}
	}

}
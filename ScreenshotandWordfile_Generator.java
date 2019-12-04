package wordDocGenerator;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class ScreenshotandWordfile_Generator {
	public static ArrayList<String> Screeenshotfilename = new ArrayList<String>();
	public static ArrayList<String> Screeenshotname = new ArrayList<String>();

	public static void createDoc(String x) {
		try {

			XWPFDocument docx = new XWPFDocument();
			XWPFRun run = docx.createParagraph().createRun();
			FileOutputStream out = new FileOutputStream(
					"H:\\ScriptRunner Application\\Checkout Documents\\" + x + ".docx");

			addscreenshotinword(docx, run, out);
			docx.write(out);
			out.flush();
			out.close();
			docx.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addscreenshotinword(XWPFDocument docx, XWPFRun run, FileOutputStream out)
			throws InvalidFormatException, IOException {
		for (int i = 0; i < Screeenshotfilename.size(); i++) {
			File file = new File(Screeenshotfilename.get(i));
			InputStream pic = new FileInputStream(Screeenshotfilename.get(i));
			run.addBreak();
			run.addPicture(pic, XWPFDocument.PICTURE_TYPE_PNG, (String) Screeenshotname.get(i), Units.toEMU(450),
					Units.toEMU(250));
			pic.close();
			 file.delete();
		}
		Screeenshotfilename.clear();
		Screeenshotfilename.removeAll(Screeenshotfilename);
	}

	public static void capturescreen() throws Exception {
		try {

			XWPFDocument docx = new XWPFDocument();
			String screenshot_name = System.currentTimeMillis() + ".png";
			Screeenshotname.add(screenshot_name);
			BufferedImage image = new Robot()
					.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			File file = new File("H:\\ScriptRunner Application\\Checkout Documents\\" + screenshot_name);
			Screeenshotfilename.add("H:\\ScriptRunner Application\\Checkout Documents\\" + screenshot_name);
			ImageIO.write(image, "png", file);
			docx.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package dataTableBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Datatable {
	static String filePath = System.getProperty("user.dir")+"\\src\\dataTables";
	static String fileName = null;

	public static void SetFileName(String FileName) {
		Datatable.fileName = FileName;
	}

	public static String getData(String sheetName, String columnHeader) throws IOException {
		String headerValue = null;

		File file = new File(filePath + "\\" + fileName);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook workBook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if (fileExtensionName.equals(".xlsx")) {
			workBook = new XSSFWorkbook(inputStream);
		} else if (fileExtensionName.equals(".xls")) {
			workBook = new HSSFWorkbook(inputStream);
		}
		Sheet sheet = workBook.getSheet(sheetName);
		int i = 0;
		Row row = sheet.getRow(i);
		for (int j = 0; j < row.getLastCellNum(); j++) {
			String headerText = row.getCell(j).getStringCellValue();
			if (headerText.equalsIgnoreCase(columnHeader)) {
				row = sheet.getRow(i + 1);
				headerValue = row.getCell(j).getStringCellValue();
			}

		}
		return headerValue;
	}
}

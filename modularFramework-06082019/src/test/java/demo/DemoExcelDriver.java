package demo;

import org.testng.annotations.Test;

import commonLibs.utils.ExcelDriver;

public class DemoExcelDriver {

	ExcelDriver excelDriver;
	String filename = "C:/Users/Saurabh Dhingra/workspace/modularFramework-06082019/inputFile/TestData.xlsx";
	String sheetname = "Test Data";

	@Test
	public void verifyExcelDriver() throws Exception {

		excelDriver = new ExcelDriver();

		excelDriver.createWorkbook(filename);

		excelDriver.openWorkbook(filename);

		excelDriver.createSheet(sheetname);
		
		excelDriver.setCellData(sheetname, 0, 0, "Gaurav");
		excelDriver.setCellData(sheetname, 0, 1, "Yadav");
		excelDriver.setCellData(sheetname, 1, 0, "Saurabh");
		excelDriver.setCellData(sheetname, 1, 1, "Dhingra");
		excelDriver.setCellData(sheetname, 2, 0, "Kawal");
		excelDriver.setCellData(sheetname, 2, 1, "Preet");

		excelDriver.saveFile();

		excelDriver.closeWorkbook();
	}

}

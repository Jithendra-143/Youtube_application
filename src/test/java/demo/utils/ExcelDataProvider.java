package demo.utils;

import java.io.File;

import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

    @DataProvider(name = "excelData")
    public static Object[][] excelData() {
        String fileLocation = System.getProperty("user.dir") + File.separator + "src" +
         File.separator + "test" + File.separator + "resources" + File.separator + "itemsToSearch.xlsx";

        // Name of the worksheet within the Excel file
        String worksheetName = "Sheet1";

        // Column index containing the search terms
        int columnIndex = 0;

        System.out.println("Fetching excel file from "+fileLocation);
        return ExcelReaderUtil.readExcelData(fileLocation, worksheetName, columnIndex);
    }
    // public static void main(String args[]){
    //     excelData();
    // }
}
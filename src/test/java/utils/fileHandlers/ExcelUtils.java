package utils.fileHandlers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {
    private static Logger logger = LogManager.getLogger(ExcelUtils.class);
    public static String readPropertyFromExcel(String sheetName, String property) throws IOException {
        logger.info("Reading Property '" + property +"' from sheet '" + sheetName + "'");
        String value = "";
        FileInputStream fis = new FileInputStream("src/test/resources/TestCaseMaster.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        XSSFRow row;
        for(int i=1;i<=sheet.getLastRowNum();i++){
            row = sheet.getRow(i);
            if(row.getCell(0).toString().equalsIgnoreCase(property)){
//                logger.info(row.getCell(0).toString() + ":" + row.getCell(1).toString());
                value = row.getCell(1).toString();
                break;
            }
        }
        logger.info("Value is : " + value );
        return value;
    }
}

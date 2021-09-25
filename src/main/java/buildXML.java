import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.xml.*;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class buildXML {

    private static final String testCaseMaster = "src/test/resources/TestCaseMaster.xlsx";
    private static final String listener = "utils.listeners.TestListeners";
    private static final String testNGFile = "src/test/resources/TestNG.xml";
    private static List<ITestCase> testCases;
    private static List<HashMap<String, String>> data;
    private static Logger logger;

    public static void main(String ... a) throws IOException {
        setLogger();
        logger.info("::::::::::     Creating TestNG.xml File     ::::::::::");
        getDataFromSheet();
        readActiveCases();
        creteTestNGXML();
    }

    public static void setLogger(){
        logger = LogManager.getLogger("buildXML");
        String log4JPropertyFile = "src/test/resources/log4j.properties";
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(log4JPropertyFile));
            PropertyConfigurator.configure(p);
            logger.info("\n\n\n\n\n");
            logger.info("::::::::::     Logger for buildXML configured     ::::::::::");
        } catch (IOException e) {
            logger.info("::::::::::     Logger for buildXML is not configured     ::::::::::");
        }
    }

    public static void getDataFromSheet() throws IOException {
        FileInputStream fis = new FileInputStream(testCaseMaster);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        data = new ArrayList<>();
        List<String> sheets = new ArrayList<>();
        sheets.add("Test");
//        sheets.add("TestSetup");
        for (String sheetName : sheets) {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow header = sheet.getRow(0);
            for (int rowNumber = 1; rowNumber <= sheet.getLastRowNum(); rowNumber++) {
                HashMap<String, String> rowData = new HashMap<>();
                for (int k = 0; k < header.getLastCellNum(); k++) {
                    rowData.put(header.getCell(k).toString(), sheet.getRow(rowNumber).getCell(k).toString());
                }
                data.add(rowData);
            }
        }
    }

    public static void readActiveCases() {
        testCases = new ArrayList<>();
        List<HashMap<String, String>> activeTests = data.stream().filter(x -> x.get("RunMode").equalsIgnoreCase("yes")).collect(Collectors.toList());

        for (HashMap<String, String> activeTest : activeTests) {
            ITestCase testCase = new ITestCase() {
                @Override
                public String getTestCaseId() {
                    return activeTest.get("TestCaseNo");
                }

                @Override
                public String getClassPath() {
                    return activeTest.get("TestClassPath");
                }

                @Override
                public String getTestSummary() {
                    return null;
                }

                @Override
                public String getTestDescription() {
                    return activeTest.get("TestDescription");
                }

                @Override
                public String getTestSuite() {
                    return null;
                }

                @Override
                public String getTestComponent() {
                    return null;
                }

                @Override
                public String getTestTag() {
                    return null;
                }

                @Override
                public String getTestError() {
                    return null;
                }

                @Override
                public List<String> getTestIssueLinkKeys() {
                    return null;
                }

                @Override
                public List<String> getUnverifiedTestIssueLinkKeys() {
                    return null;
                }

                @Override
                public String getTestGroups() {
                    return activeTest.get("Groups");
                }
            };
            testCases.add(testCase);
        }
    }

    public static void creteTestNGXML() throws IOException {
        String testNGXML = getTestNGSuiteXML();
        writeToFile(testNGXML);
    }

    public static String getTestNGSuiteXML() {
        XmlSuite suite = new XmlSuite();
        suite.setName("Automation Suite");
        suite.setParallel(XmlSuite.ParallelMode.TESTS);
        suite.setThreadCount(1);
        XmlTest test;
        XmlClass xmlClass;
        XmlGroups xmlGroups;
        XmlDefine define;
        XmlInclude xmlInclude;
        XmlRun xmlRun;

        suite.addListener(listener);
        for (ITestCase testCase : testCases) {
            xmlClass = new XmlClass();
            xmlClass.setName(testCase.getClassPath());
            xmlRun = new XmlRun();
            for (String includeGroups : testCase.getTestGroups().split(",")) {
                xmlRun.onInclude(includeGroups);
            }
            xmlGroups = new XmlGroups();
            xmlGroups.setRun(xmlRun);
            test = new XmlTest(suite);
            test.setGroups(xmlGroups);
            test.setName(testCase.getTestDescription());
            test.setClasses(Arrays.asList(xmlClass));
        }
        return suite.toXml();
    }

    public static void writeToFile(String xml) throws IOException {
        logger.info("::::::::::     TestNG.xml File Start     ::::::::::");
        logger.info("\n"+xml);
        logger.info("::::::::::     TestNG.xml File Ends     ::::::::::");
        FileWriter fw = new FileWriter(testNGFile);
        fw.write(xml);
        fw.close();
    }
}
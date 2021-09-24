import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class buildXML {
    private static Logger logger;
    public static void main(String ... a){
        logger = LogManager.getLogger("buildXML");
        String log4JPropertyFile = "src/test/resources/log4j.properties";
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(log4JPropertyFile));
            PropertyConfigurator.configure(p);
            System.out.println("\n\n\n\n\nLogger for buildXML configured");
        } catch (IOException e) {
            System.out.println("Logger for buildXML is not configured!");
        }
        logger.info("\n\n\n\n\n");
        logger.info("I am in buildXML");
    }
}

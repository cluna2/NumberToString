package numbertostring.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Central logger for all classes to use. */
public class GlobalLogger {
    /** Uses root logger. */
    public static final Logger LOGGER = LogManager.getRootLogger();

    /** Prevent instantiation. */
    private GlobalLogger() {} 
}

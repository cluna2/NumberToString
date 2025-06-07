package numbertostring.core.exception;

/** Wrapper for UnsupportedOperationException. 
 * Internally thrown when specific methods from some rules objects are called
 * when the language does not support them. 
 * Example: Calling the applyNonPositonalConversion on an EnglishNumeralRules instance.
 */
public class NumeralRulesException  extends UnsupportedOperationException {
    
    /** Error message. */
    private final String errorMessage;

    /** Creates a NumberConversionException.
     * @param errorMessage message details
     */
    public NumeralRulesException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    /** Retrieves error message
     *  @return Error message.
     */
    public String getErrorMessage() { return errorMessage; }
}

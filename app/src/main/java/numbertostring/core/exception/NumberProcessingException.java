package numbertostring.core.exception;

 /** Wrapper for RuntimeException. Internally thrown when processing a number . */
public class NumberProcessingException extends RuntimeException{
    
    /** Error message. */
    private final String errorMessage;

    /** Creates a NumberConversionException.
     * @param errorMessage message details
     */
    public NumberProcessingException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    /** Retrieves error message
     *  @return Error message.
     */
    public String getErrorMessage() { return errorMessage; }
}

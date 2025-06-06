package numbertostring.api.exception;

/** Wrapper for RuntimeException. Thrown when number conversion fails. */
public class NumberConversionException extends RuntimeException {
    /** Error message. */
    private final String errorMessage;

    /** Creates a NumberConversionException.
     * @param errorMessage message details
     */
    public NumberConversionException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    /** Retrieves error message
     *  @return Error message.
     */
    public String getErrorMessage() { return errorMessage; }
}

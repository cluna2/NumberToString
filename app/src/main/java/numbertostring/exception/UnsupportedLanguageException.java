package numbertostring.exception;

/** Wrapper for RuntimeException. Thrown when language support is not yet implemented. */
public class UnsupportedLanguageException extends RuntimeException {
    /** Error message. */
    private final String errorMessage;

    /** Creates a NumberConversionException.
     * @param errorMessage message details
     */
    public UnsupportedLanguageException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    /** Retrieves error message
     *  @return Error message.
    */
    public String getErrorMessage() { return errorMessage; }
}


package numbertostring.dto;



import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import numbertostring.exception.NumberConversionException;

/**
 * Response object returned by the {@code Service}.
 * Will hold the converted number to word string, a status code,
 * and a NumberConversionException. 
 */
@Builder
@Getter
@ToString
@RequiredArgsConstructor
public class NumberToWordsResponse {

    /** Number converted to words. 
     * In event of conversion failure, this field will be an empty string.
     * @param words String to return to user.
     * @return Converted word string
    */
    private final String words;

    /**
     * Status of the request. Has values {@code SUCCESS}
     * or {@code FAILURE}.
     * @param status Code to set.
     * @return Status to user.
     */
    private final Status status;

    /** Holds exceptions if the request was unsuccessful.
     * @param exception Exception to set.
     * @return Exception details for user.
     */
    private final NumberConversionException exception;

    /** Enum representing possible response statuses */
    public enum Status {
        /** Request was successful. */
        SUCCESS,
        /** Request was unsuccessful. */
        FAILURE
    }
}
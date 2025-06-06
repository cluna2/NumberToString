package numbertostring.api.dto;



import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import numbertostring.api.exception.NumberConversionException;

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

    /** DTO containing structured number conversion details
     * @param convertedData DTO with number conversion details.
     * @return Converted word string.
    */
    private final ConvertedNumberDTO convertedData;

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
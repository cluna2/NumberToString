package numbertostring.dto;



import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import numbertostring.exception.NumberConversionException;

@Builder
@Getter
@RequiredArgsConstructor
public class NumberToWordsResponse {
    private final String words;
    private final Status status;
    private final NumberConversionException exception;

    /** Enum representing possible response statuses */
    public enum Status {
        SUCCESS, FAILURE
    }
}
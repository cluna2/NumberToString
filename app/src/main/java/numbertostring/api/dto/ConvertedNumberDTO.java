package numbertostring.api.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@RequiredArgsConstructor
public class ConvertedNumberDTO {

    /** Original request number for conversion.
     * @return Requested number.
     */
    private final BigDecimal originalNumber;
    /** Converted word representation of the number.
     * @return Word form string.
     */
    private final String convertedText;
    /** Language used for conversion.
     * @return Language string.
     */
    private final String language;


}

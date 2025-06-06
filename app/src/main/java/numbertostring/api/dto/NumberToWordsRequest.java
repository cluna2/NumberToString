package numbertostring.api.dto;

import java.math.BigDecimal;
import java.util.Locale;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Request object used to interface with the {@code NumberToWordsService}.
 * Requires a {@code BigDecimal} with an optional {@code Locale} parameter.
 * 
 * <p>Requests can be instantiated using normal syntax:</p>
 * <pre>{@code
 * NumberToWordsRequest<IntegerNum> req = new NumberToWordsRequest(BigDecimal.valueOf(1000));
 * }</pre>
 * 
 * <p>Or with a builder:</p>
 * <pre>{@code
 * NumberToWordsRequest<IntegerNum> req = NumberToWordsRequest.builder()
 *     .number(IntegerNum.builder().number(BigInteger.valueOf(1000)).build())
 *     .build();
 * }</pre>
 * 
 */
@Builder
@Getter
@RequiredArgsConstructor
public class NumberToWordsRequest {

    /** Number of type T to convert. Required.
     * @return Number of type T.
     */
    private final BigDecimal numberValue;

    /** Optional parameter specifying desired conversion language.
     *  Defaults to English language.
     * @return Locale of request
     */
    @Builder.Default
    private final Locale locale = Locale.ENGLISH;


}

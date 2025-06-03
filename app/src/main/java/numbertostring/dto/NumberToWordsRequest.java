package numbertostring.dto;

import numbertostring.pojo.Number;
import java.util.Locale;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Request object used to interface with the {@code NumberToWordsService}.
 * Requires a {@code Number<T>} with an optional {@code Locale} parameter.
 * Requests are parametrized by the type of {@code Number} you wish to convert.
 * Currently, only {@code IntegerNum} is supported.
 * 
 * <p>Requests can be instantiated using normal syntax:</p>
 * <pre>{@code
 * NumberToWordsRequest<IntegerNum> req = new NumberToWordsRequest(new IntegerNum(BigInteger.valueOf(1000)));
 * }</pre>
 * 
 * <p>Or with a builder:</p>
 * <pre>{@code
 * NumberToWordsRequest<IntegerNum> req = NumberToWordsRequest.builder()
 *     .number(IntegerNum.builder().number(BigInteger.valueOf(1000)).build())
 *     .build();
 * }</pre>
 * 
 * @param <T> The number type T.
 */
@Builder
@Getter
@RequiredArgsConstructor
public class NumberToWordsRequest<T extends Number<T>> {

    /** Number of type T to convert. Required.
     * @return Number of type T.
     */
    private final Number<T> number;

    /** Optional parameter specifying desired conversion language.
     *  Defaults to English language.
     * @return Locale of request
     */
    @Builder.Default
    private final Locale locale = Locale.ENGLISH;


}

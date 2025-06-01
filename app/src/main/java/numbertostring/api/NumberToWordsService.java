package numbertostring.api;

import numbertostring.converter.LocalizedNumberConverter;
import numbertostring.dto.NumberToWordsRequest;
import numbertostring.dto.NumberToWordsResponse;
import numbertostring.exception.NumberConversionException;
import numbertostring.factory.NumberConverterFactory;
import numbertostring.pojo.Number;


import com.google.common.annotations.VisibleForTesting;

/**
 * Entrypoint for consumers who wish to convert numbers to strings. 
 * Call {@link create} to instantiate service.
 * Consumers can call the public APIs specified here to convert a number to a string.
 */
public class NumberToWordsService {


    /** Default factory that the service uses to create a converter. */
    private static final NumberConverterFactory DEFAULT_FACTORY = new NumberConverterFactory();

    /** Converter object that is responsible for performing the acutal conversion.
     * It is parametrized by the type of Number a user passes in.
     */
    private LocalizedNumberConverter<? extends Number<?>> converter;

    /**
     * A factory responsible for creating a converter. 
     */
    private final NumberConverterFactory converterFactory;


    /** Static method to initialize the service.
     * Users should use this method to create the Service.
     * @return Service class to handle user requests.
    */
    public static NumberToWordsService create() {
        return new NumberToWordsService(DEFAULT_FACTORY);
    }

    /** Private constructor enforcing use of a converter factory. 
     * @param converterFactory factory 
    */
    private NumberToWordsService(NumberConverterFactory converterFactory) { 
        this.converterFactory = converterFactory;
    }
    
    /** Constructor to create the Service using a custom factory for testing.
     * Used for test overrides. Users should not call this method.
     * @param factory Mock factory injected for tests.
     * @return Service instance used for tests.
     */
    @VisibleForTesting
    protected static NumberToWordsService create(NumberConverterFactory factory) {
        return new NumberToWordsService(factory);
    }

    /**
     * Converts any Number type to English word form string representation.
     * Use {@link convertNumberToWordsWithLocale} to specify locale.
     * @param number Instance of Number
     * @param <T> Type of number
     * @return Word representation in English by default.
     */
    public <T extends Number<T>> NumberToWordsResponse convertNumberToWords(NumberToWordsRequest<T> req) {
        return convertNumberToWordsWithLocale(req);
    }

    /**
     * Converts any Number type to word form of specified locale.
     * Failure to convert will result in an empty string.
     * 
     * @param number Instance of Number
     * @param locale Locale for language conversion
     * @param <T> Type of number
     * @return Word representation in the given language if successful
     */
    public <T extends Number<T>> NumberToWordsResponse convertNumberToWordsWithLocale(NumberToWordsRequest<T> req) {
        NumberToWordsResponse res;
        try {
            this.converter = converterFactory.getConverterForType(req.getNumber().getType(), req.getLocale());
            String words =  converter.convertToWords(req.getNumber());
            res = NumberToWordsResponse.builder()
                .words(words)
                .status(NumberToWordsResponse.Status.SUCCESS)
                .exception(null)
                .build();
        } catch (UnsupportedOperationException | IllegalArgumentException e ) {
            res = NumberToWordsResponse.builder()
                .words("")
                .status(NumberToWordsResponse.Status.FAILURE)
                .exception(new NumberConversionException(e.getMessage()))
                .build();
        }
        return res;
    }
}
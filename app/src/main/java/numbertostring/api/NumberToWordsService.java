package numbertostring.api;

import numbertostring.api.dto.ConvertedNumberDTO;
import numbertostring.api.dto.NumberToWordsRequest;
import numbertostring.api.dto.NumberToWordsResponse;
import numbertostring.api.exception.NumberConversionException;
import numbertostring.core.factory.NumberConverterFactory;
import numbertostring.core.factory.NumberConverterFactorySingleton;
import numbertostring.core.model.Number;
import numbertostring.core.utils.logger.GlobalLogger;

import com.google.common.annotations.VisibleForTesting;

/**
 * Entrypoint for consumers who wish to convert numbers to strings. 
 * Call {@link create} to instantiate service.
 * Consumers can call the public APIs specified here to convert a number to a string.
 */
public class NumberToWordsService {


    /**
     * A factory responsible for creating a converter. 
     */
    private final NumberConverterFactory converterFactory;

    public NumberToWordsService() {
        this.converterFactory = NumberConverterFactorySingleton.getInstance();
    }

    /** Constructor for unit testing (allows passing a mock factory) */
    public NumberToWordsService(NumberConverterFactory converterFactory) {
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
     * @param req Request object with number parameter
     * @param <T> Type of number
     * @return Response object with converted string, status code, and exceptions if any
     */
    public <T extends Number<T>> NumberToWordsResponse convertNumberToWords(NumberToWordsRequest req) {
        return convertNumberToWordsWithLocale(req);
    }

    /**
     * Converts any Number type to word form of specified locale.
     * Failure to convert will result in an empty string.
     * 
     * @param req Request object with number parameter
     * @param <T> Type of number
     * @return Response object with converted string, status code, and exceptions if any
     */
    public <T extends Number<T>> NumberToWordsResponse convertNumberToWordsWithLocale(NumberToWordsRequest req) {
        NumberToWordsResponse res;
        try {
            // Convert number to words using factory's method
            String words = converterFactory.convertNumberToWords(req.getNumberValue(), req.getLocale());

            // Create structured DTO for encapsulated conversion results
            ConvertedNumberDTO convertedData = new ConvertedNumberDTO(req.getNumberValue(), words, req.getLocale().getLanguage());

            GlobalLogger.LOGGER.debug("Conversion complete. Creating response.");
            res = NumberToWordsResponse.builder()
                .convertedData(convertedData)
                .status(NumberToWordsResponse.Status.SUCCESS)
                .exception(null)
                .build();
        } catch (Exception e) {
            // DTO with empty data for failures
            ConvertedNumberDTO failedData = new ConvertedNumberDTO(req.getNumberValue(), "", req.getLocale().getLanguage());

            res = NumberToWordsResponse.builder()
                .convertedData(failedData)
                .status(NumberToWordsResponse.Status.FAILURE)
                .exception(new NumberConversionException(e.getMessage()))
                .build();
            GlobalLogger.LOGGER.warn(String.format("Response object created. Response is: %s", res.toString()));
        }
        return res;
    }


}
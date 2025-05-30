package numbertostring.constants;

import java.util.Map;

/**
 * Mapping of language codes to associated langauge field constants.
 */
public class LanguageConstants {
    public static final Map<String, String[]> LANGUAGE_ONES = Map.of(
        "en", EnglishWordConstants.ENGLISH_ONES
    );

    public static final Map<String, String[]> LANGUAGE_TEENS = Map.of(
        "en", EnglishWordConstants.ENGLISH_TEENS
    );

    public static final Map<String, String[]> LANGUAGE_TENS = Map.of(
        "en", EnglishWordConstants.ENGLISH_TENS
    );

    public static final Map<String, String[]> LANGUAGE_LARGE_GROUP_STRINGS = Map.of(
        "en", EnglishWordConstants.ENGLISH_LARGE_GROUP_STRINGS
    );

    public static final Map<String, String> LANGUAGE_ZERO = Map.of(
        "en", EnglishWordConstants.ZERO_EN
    );

    public static final Map<String, String> LANGUAGE_NEGATIVE = Map.of(
        "en", EnglishWordConstants.NEGATIVE_EN
    );

    public static final Map<String, String> LANGUAGE_INTEGER_MIN_VALUE = Map.of(
        "en", EnglishWordConstants.MIN_INTEGER_EN
    );

    public static final Map<String, String> LANGUAGE_HUNDRED = Map.of(
        "en", EnglishWordConstants.HUNDRED_EN
    );
}

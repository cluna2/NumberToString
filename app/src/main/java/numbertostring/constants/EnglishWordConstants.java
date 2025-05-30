package numbertostring.constants;

/*
 * Constants mapping number names to English words. Leading entries are null strings
 * for ease of implementation.
 */
public class EnglishWordConstants {

    public static final String NEGATIVE_EN = "Negative";
    public static final String ZERO_EN = "Zero";
    public static final String MIN_INTEGER_EN = "Negative Two Billion One Hundred Forty Seven Million Four Hundred Eighty Three Thousand Six Hundred Forty Eight";
    public static final String HUNDRED_EN = "Hundred";

    public static final String[] ENGLISH_ONES = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
    public static final String[] ENGLISH_TEENS = {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    public static final String[] ENGLISH_TENS = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    public static final String[] ENGLISH_LARGE_GROUP_STRINGS = {"", "Thousand", "Million", "Billion"}; // English groups numbers with these names beyond 1000

}

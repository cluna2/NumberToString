package numbertostring.constants;

/*
 * Constants mapping number names to English words. Leading entries are null strings
 * for ease of implementation.
 */
public class NumberToEnglishWordConstants {

    public static final String ZERO = "Zero";
    public static final String[] ONES = {
        "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"
    };
    public static final String[] TEENS = {
        "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"
    };
    public static final String[] TENS = {
        "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
    };
    public static final String[] THOUSANDS = {
        "", "Thousand", "Million", "Billion"
    };
}

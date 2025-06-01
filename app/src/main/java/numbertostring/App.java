package numbertostring;

import java.math.BigInteger;

import numbertostring.api.NumberToWordsService;
import numbertostring.pojo.IntegerNum;

/**
 * Simple App to call conversion API. 
 */
public class App {

    /**
     * App object
     * 
     */
    public App() {}

    /**
     * Sample call using Integer wrapper
     * @param args none
     */
    public static void main(String[] args) {
        try {
            BigInteger value = BigInteger.valueOf(21);
            IntegerNum num = new IntegerNum(value);
            NumberToWordsService api = NumberToWordsService.create();
            String words = api.convertNumberToWords(num);
            System.out.println(words);
 
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}

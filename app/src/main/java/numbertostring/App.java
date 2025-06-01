package numbertostring;

import java.math.BigInteger;

import numbertostring.api.NumberToWordsService;
import numbertostring.dto.NumberToWordsRequest;
import numbertostring.dto.NumberToWordsResponse;
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
            NumberToWordsRequest<IntegerNum> req = NumberToWordsRequest.<IntegerNum>builder()
                .number(num)
                .build();
            
            NumberToWordsResponse response = api.convertNumberToWords(req);
            System.out.println(response.getWords());
 
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}

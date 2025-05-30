package numbertostring;

import numbertostring.api.NumberToWordsAPI;
import numbertostring.pojo.IntegerNum;

public class App {


    public static void main(String[] args) {
        try {
            double value = -1590231;
            IntegerNum num = IntegerNum.builder()
                .value(value)
                .build();
            String words = NumberToWordsAPI.convertNumberToWords(num);
            System.out.println(words);
 
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}

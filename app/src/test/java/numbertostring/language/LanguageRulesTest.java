package numbertostring.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigInteger;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LanguageRulesTest {
    private static String expectedWord = "vingt et un";     

    private TreeMap<Integer, String> numerals;
    private TreeMap<BigInteger, String> largeUnits;
    private Set<Integer> baseUnits;
    private Integer grouping;
    private boolean needsExplicitOne;
    private String negativeWord;
    private Function<Integer, String> customNumeralLogic;


    private LanguageRules rules; 

    @BeforeEach
    void setUp() {
        numerals = new TreeMap<>(Map.ofEntries(Map.entry(1, "Un")));
        largeUnits = new TreeMap<>(
            Map.ofEntries(Map.entry(BigInteger.valueOf(10), "Dix")));
        grouping = 1000;
        needsExplicitOne = true;
        negativeWord = "Moins";
        customNumeralLogic = new Function<Integer,String>() {

            @Override
            public String apply(Integer t) {
                if (t == 21) {return expectedWord;}
                return null;
            }
        };

        rules = new LanguageRules(numerals, largeUnits, baseUnits, grouping, needsExplicitOne, negativeWord, customNumeralLogic);
    }

    @Test
    void testGetWordAppliesCustomFunc() {
        String wordForm = rules.getWord(21);
        assertNotNull(wordForm);
        assertEquals(expectedWord, wordForm);
    }

    @Test
    void testIfCustomRulesIsNull_ThenSearchMap() {
        rules = new LanguageRules(numerals, largeUnits, baseUnits, grouping, needsExplicitOne, negativeWord, null);
        String wordForm = rules.getWord(2);
        assertEquals("", wordForm);
    }

    @Test
    void testCustomRulesReturnsNull() {
        String wordForm = rules.getWord(2);
        assertNotNull(rules.getCustomNumeralLogic());
        assertNull(wordForm);
    }
}

package numbertostring.unit.core.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import numbertostring.core.conversion.IntegerNumConverter;
import numbertostring.core.language.GroupingStrategy;
import numbertostring.core.language.formatting.LocalizedGrammarFormatter;
import numbertostring.core.language.rules.LocalizedNumeralRules;
import numbertostring.core.model.IntegerNum;
import numbertostring.core.model.NumberBase;
import numbertostring.core.utils.logger.GlobalLogger;

public class IntegerNumConverterTest {
    
    @Mock
    private LocalizedNumeralRules mockRules;

    @Mock
    private LocalizedGrammarFormatter mockFormatter;

    private IntegerNumConverter converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        converter = new IntegerNumConverter(mockRules, mockFormatter);
        when(mockRules.isPositionalSystem()).thenReturn(true);
        when(mockRules.getNumberBase()).thenReturn(NumberBase.BASE_10);
        when(mockRules.getGroupingStrategy()).thenReturn(GroupingStrategy.SHORT_SCALE);
        
    }

    @ParameterizedTest
    @CsvSource({
        "1, Mock One",
        "15, Mock Fifteen",
        "100, Mock One Hundred"
    })
    void testBasicNumberConversion(String input, String expectedOutput) {

        when(mockRules.applySmallNumeralRules(new BigInteger(input))).thenReturn(expectedOutput);
        when(mockRules.applyLargeUnitsRules(any())).thenReturn("");
        when(mockFormatter.applyLanguageSpecificFormatting(any(), any(), any(), any())).thenReturn(expectedOutput);
        
        assertEquals(expectedOutput, converter.convertToWords(new IntegerNum(new BigInteger(input))));

    }

    @Test
    void testNegativeNumberFormatting() {
        GlobalLogger.LOGGER.debug("Beginning test for negative number formatting.");
        when(mockRules.applySmallNumeralRules(BigInteger.valueOf(57))).thenReturn("Mock Fifty-Seven");
        when(mockRules.applyLargeUnitsRules(any())).thenReturn("");
        when(mockFormatter.applyLanguageSpecificFormatting(any(), any(), any(), any())).thenReturn("Mock Fifty-Seven");
        when(mockRules.applyNegativeHandling("Mock Fifty-Seven")).thenReturn("Mock Negative Fifty-Seven");

        assertEquals("Mock Negative Fifty-Seven", converter.convertToWords(new IntegerNum(BigInteger.valueOf(-57))));

        GlobalLogger.LOGGER.debug("testNegativeNumberFormatting successful.");

    }
    
    @Test 
    void testZeroHandling() {
        when(mockRules.applyNumeralRulesForZero()).thenReturn("Mock Zero");
        assertEquals("Mock Zero", converter.convertToWords(new IntegerNum(BigInteger.ZERO)));
    }
    
}

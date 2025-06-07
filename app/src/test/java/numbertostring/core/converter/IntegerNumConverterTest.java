package numbertostring.core.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import numbertostring.core.conversion.IntegerNumConverter;
import numbertostring.core.language.rules.LocalizedNumeralRules;
import numbertostring.core.model.IntegerNum;
import numbertostring.core.utils.logger.GlobalLogger;

public class IntegerNumConverterTest {
    
    @Mock
    private LocalizedNumeralRules mockRules;

    private IntegerNumConverter converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        converter = new IntegerNumConverter(mockRules);
    }

    @Test
    void testBasicNumberConversion() {
        GlobalLogger.LOGGER.debug("Beginning test for basic integer number conversion.");
        when(mockRules.applyNumeralRulesForZero()).thenReturn("Mock Zero");
        when(mockRules.processNumber(BigInteger.ONE)).thenReturn("Mock One");
        when(mockRules.processNumber(BigInteger.valueOf(15))).thenReturn("Mock Fifteen");
        when(mockRules.processNumber(BigInteger.valueOf(100))).thenReturn("Mock Hundred");

        assertEquals("Mock Zero", converter.convertToWords(new IntegerNum(BigInteger.ZERO)));
        assertEquals("Mock One", converter.convertToWords(new IntegerNum(BigInteger.ONE)));
        assertEquals("Mock Fifteen", converter.convertToWords(new IntegerNum(BigInteger.valueOf(15))));
        assertEquals("Mock Hundred", converter.convertToWords(new IntegerNum(BigInteger.valueOf(100))));

        GlobalLogger.LOGGER.debug("testBasicNumberConversion successful.");
    }

    @Test
    void testNegativeNumberFormatting() {
        GlobalLogger.LOGGER.debug("Beginning test for negative number formatting.");
        when(mockRules.processNumber(BigInteger.valueOf(57))).thenReturn("Mock Fifty-Seven");
        when(mockRules.applyNegativeHandling("Mock Fifty-Seven")).thenReturn("Mock Negative Fifty-Seven");

        assertEquals("Mock Negative Fifty-Seven", converter.convertToWords(new IntegerNum(BigInteger.valueOf(-57))));

        GlobalLogger.LOGGER.debug("testNegativeNumberFormatting successful.");

    }
    
    
}

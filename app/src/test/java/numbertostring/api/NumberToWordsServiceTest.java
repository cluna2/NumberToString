package numbertostring.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import numbertostring.api.dto.NumberToWordsRequest;
import numbertostring.api.dto.NumberToWordsResponse;
import numbertostring.api.exception.NumberConversionException;
import numbertostring.core.conversion.LocalizedNumberConverter;
import numbertostring.core.factory.NumberConverterFactory;

@ExtendWith(MockitoExtension.class)
public class NumberToWordsServiceTest {
    

    private static final String FAILURE_STRING = "";
    private static final String expectedNumString = "One Thousand";
    private static final BigDecimal value = BigDecimal.valueOf(1000);;

    private NumberToWordsRequest req;
    private NumberToWordsResponse res;
    
    @Mock
    private LocalizedNumberConverter mockConverter;
    
    @Mock
    private NumberConverterFactory factory;

    @InjectMocks
    private NumberToWordsService api;

    @BeforeEach
    void setUp() {
        req = NumberToWordsRequest.builder()
            .numberValue(value)
            .build();
    }


    @Test
    void testConvertNumberToWordsSuccess() {

        when(factory.convertNumberToWords(eq(value), eq(Locale.ENGLISH)))
            .thenReturn(expectedNumString);

        res = api.convertNumberToWords(req);
        assertEquals(expectedNumString, res.getConvertedData().getConvertedText());
        assertEquals(NumberToWordsResponse.Status.SUCCESS, res.getStatus());

        verify(factory).convertNumberToWords(any(), eq(Locale.ENGLISH));

    }

    @Test
    void whenConvertFails_thenResponseHasException()  {
        when(factory.convertNumberToWords(eq(value), eq(Locale.ENGLISH)))
            .thenThrow(new NumberConversionException(FAILURE_STRING));

        res = api.convertNumberToWords(req);
        assertEquals(FAILURE_STRING, res.getConvertedData().getConvertedText());
        assertEquals(NumberConversionException.class, api.convertNumberToWords(req).getException().getClass());
    }

    /** Runs entire service. */
    @Test
    void testConvertNumberToWordsWithoutMocks() {
        api = new NumberToWordsService();
        NumberToWordsResponse res = api.convertNumberToWords(req);
        assertNull(res.getException());
        assertEquals(NumberToWordsResponse.Status.SUCCESS, res.getStatus());
        assertTrue(expectedNumString.equals(res.getConvertedData().getConvertedText()));
    }
}

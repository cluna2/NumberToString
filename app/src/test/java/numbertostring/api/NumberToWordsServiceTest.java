package numbertostring.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import numbertostring.converter.LocalizedNumberConverter;
import numbertostring.dto.NumberToWordsRequest;
import numbertostring.dto.NumberToWordsResponse;
import numbertostring.exception.NumberConversionException;
import numbertostring.exception.UnsupportedLanguageException;
import numbertostring.factory.NumberConverterFactory;
import numbertostring.pojo.IntegerNum;

@ExtendWith(MockitoExtension.class)
public class NumberToWordsServiceTest {
    

    private static final String FAILURE_STRING = "";
    private static final String expectedNumString = "One Thousand";
    private static final BigInteger value = BigInteger.valueOf(1000);;

    private IntegerNum num;
    private NumberToWordsRequest<IntegerNum> req;
    private NumberToWordsResponse res;
    
    @Mock
    private LocalizedNumberConverter<IntegerNum> mockConverter;
    
    @Mock
    private NumberConverterFactory factory;

    @InjectMocks
    private NumberToWordsService api;

    @BeforeEach
    void setUp() {
        num = new IntegerNum(value);
        req = NumberToWordsRequest.<IntegerNum>builder()
            .number(num)
            .build();
        api = NumberToWordsService.create(factory);
    }

    @Test
    void testConvertNumberToWordsSuccess() throws ReflectiveOperationException {

        when(factory.getConverterForType(eq(IntegerNum.class), eq(Locale.ENGLISH)))
            .thenAnswer(invocation -> mockConverter);
        when(mockConverter.convertToWords(any(IntegerNum.class)))
            .thenReturn(expectedNumString);

        res = api.convertNumberToWords(req);
        assertEquals(expectedNumString, res.getWords());
        assertEquals(NumberToWordsResponse.Status.SUCCESS, res.getStatus());

        verify(factory).getConverterForType(any(), eq(Locale.ENGLISH));
        verify(mockConverter).convertToWords(num);

    }

    @Test
    void whenConvertFails_thenResponseHasException() throws ReflectiveOperationException {
        when(factory.getConverterForType(eq(IntegerNum.class), eq(Locale.ENGLISH)))
            .thenAnswer(invocation -> mockConverter);
        when(mockConverter.convertToWords(any(IntegerNum.class)))
            .thenThrow(new UnsupportedLanguageException("Language unsupported."));

        res = api.convertNumberToWords(req);
        assertEquals(FAILURE_STRING, res.getWords());
        assertEquals(NumberConversionException.class, api.convertNumberToWords(req).getException().getClass());
    }

    /** Runs entire service. */
    @Test
    void testConvertNumberToWordsWithoutMocks() {
        api = NumberToWordsService.create();
        NumberToWordsResponse res = api.convertNumberToWords(req);
        assertNull(res.getException());
        assertEquals(NumberToWordsResponse.Status.SUCCESS, res.getStatus());
        assertTrue(expectedNumString.equals(res.getWords()));
    }
}

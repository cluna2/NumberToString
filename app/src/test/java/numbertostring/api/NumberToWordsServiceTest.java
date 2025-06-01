package numbertostring.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import numbertostring.factory.NumberConverterFactory;
import numbertostring.pojo.IntegerNum;

@ExtendWith(MockitoExtension.class)
public class NumberToWordsServiceTest {
    

    private BigInteger value;
    private IntegerNum num;
    private String expectedNumString = "One Thousand";
    
    
    @Mock
    private LocalizedNumberConverter<IntegerNum> mockConverter;
    
    @Mock
    private NumberConverterFactory factory;

    @InjectMocks
    private NumberToWordsService api;

    @BeforeEach
    void setUp() {
        value = BigInteger.valueOf(1000);
        num = new IntegerNum(value);
        api = NumberToWordsService.create(factory);
    }

    @Test
    void testConvertNumberToWords() {

        when(mockConverter.convertToWords(any(IntegerNum.class)))
            .thenReturn(expectedNumString);
        when(factory.getConverterForType(any(IntegerNum.class), eq(Locale.ENGLISH)))
            .thenReturn(mockConverter);


        assertNotNull(mockConverter, "Mock converter instance should not be null");
        assertEquals(expectedNumString, api.convertNumberToWords(num));

        verify(factory).getConverterForType(any(IntegerNum.class), eq(Locale.ENGLISH));
        verify(mockConverter).convertToWords(num);

    }
}

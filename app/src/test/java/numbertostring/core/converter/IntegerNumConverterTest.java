package numbertostring.core.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import numbertostring.core.conversion.IntegerNumConverter;
import numbertostring.core.language.LocalizedNumberRulesRegistry;
import numbertostring.core.language.rules.LocalizedNumberRules;
import numbertostring.core.model.IntegerNum;
import numbertostring.core.utils.logger.GlobalLogger;

public class IntegerNumConverterTest {
    
    private LocalizedNumberRules rules;

    private IntegerNumConverter converter;

    @BeforeEach
    void setUp() {
        rules = LocalizedNumberRulesRegistry.getRules(Locale.ENGLISH);
        converter = new IntegerNumConverter(rules);
    }

    
    
}

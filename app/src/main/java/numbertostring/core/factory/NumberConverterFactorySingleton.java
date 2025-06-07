package numbertostring.core.factory;

import numbertostring.core.model.IntegerNum;
import numbertostring.core.provider.IntegerNumConverterProvider;

public class NumberConverterFactorySingleton {
    private static final NumberConverterFactory INSTANCE = new NumberConverterFactory();

    static {
        // Register default converters at library initialization
        INSTANCE.registerConverter(IntegerNum.class, new IntegerNumConverterProvider());
    }

    private NumberConverterFactorySingleton() {}

    public static NumberConverterFactory getInstance() {
        return INSTANCE;
    }
}

package numbertostring.language;

import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

public class ItalianNumeralRules {
    public static final TreeMap<BigInteger, String> LARGE_UNITS_ITALIAN_PLURAL = new TreeMap<BigInteger, String>(Map.ofEntries(
        Map.entry(BigInteger.valueOf(1_000), "Mille"),
        Map.entry(BigInteger.valueOf(1_000_000), "Milioni"),
        Map.entry(BigInteger.valueOf(1_000_000_000), "Miliardi"),
        Map.entry(BigInteger.valueOf(1_000_000_000_000L), "Bilioni"),
        Map.entry(BigInteger.valueOf(1_000_000_000_000_000L), "Mille Bilioni"),
        Map.entry(BigInteger.valueOf(1_000_000_000_000_000_000L), "Trilioni"),
        Map.entry(new BigInteger("1000000000000000000000"), "Mille Trilioni"),
        Map.entry(new BigInteger("1000000000000000000000000"), "Quadrilioni"),
        Map.entry(new BigInteger("1000000000000000000000000000"), "Mille Quadrilioni"),
        Map.entry(new BigInteger("1000000000000000000000000000000"), "Quintilioni"),
        Map.entry(new BigInteger("9223372036854775807"), "Long.MAX.VALUE")
    ));
}

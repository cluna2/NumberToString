package numbertostring.pojo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@RequiredArgsConstructor
public abstract class Number {
    public static enum Type {
        INTEGER, RATIONAL, REAL, COMPLEX
    }

    // Underlying value in base 10
    protected final Double value;
    protected final Type type;
}

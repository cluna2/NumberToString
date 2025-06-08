package numbertostring.core.model;

public enum NumberBase {
    BASE_2(2, true),
    BASE_10(10, true),
    BASE_20(20, true),
    BASE_60(60, true),
    NON_POSITIONAL(0, false);

    private final int radix;
    private final boolean positional;

    NumberBase(int radix, boolean positional) {
        this.radix = radix;
        this.positional = positional;
    }

    public int getRadix() {
        return radix;
    }

    public boolean isPositional() {
        return positional;
    }
}

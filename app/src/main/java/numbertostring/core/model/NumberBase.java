package numbertostring.core.model;

public enum NumberBase {
    BASE_2(2),
    BASE_10(10),
    BASE_20(20),
    BASE_60(60);

    private final int radix;

    NumberBase(int radix) {
        this.radix = radix;
    }

    public int getRadix() {
        return radix;
    }
}

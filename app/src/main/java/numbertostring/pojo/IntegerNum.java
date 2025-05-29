package numbertostring.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;


@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder(builderMethodName = "integerBuilder")
public class IntegerNum extends Number{
    
    public static final Integer ZERO_INT = Integer.valueOf(0);
    public static final Integer MIN_VAL = Integer.MIN_VALUE;
    public static final Integer MAX_VAL = Integer.MAX_VALUE;

    // Number base 
    @Builder.Default
    private Integer base = 10;
    // Option to use scientific notation representation
    @Builder.Default
    private Boolean scientific = false;


    protected IntegerNum(Double value) {
        super(value, Type.INTEGER);
    }
    // Explicitly set number type to INTEGER in builder
    public static IntegerNumBuilder<?, ?> builder() {
        return new IntegerNumBuilderImpl().type(Type.INTEGER);
    }

}

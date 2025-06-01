package numbertostring.dto;

import numbertostring.pojo.Number;
import java.util.Locale;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class NumberToWordsRequest<T extends Number<T>> {

    private final T number;
    @Builder.Default
    private final Locale locale = Locale.ENGLISH;
}

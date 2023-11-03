package lotto.domain;

import static lotto.constant.Constant.COMMA;
import static lotto.constant.Constant.MAX_RANGE;
import static lotto.constant.Constant.MIN_RANGE;
import static lotto.constant.Constant.NUMBERS_COUNT;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.Arrays;
import java.util.List;

public class LottoNumberMaker {
    public List<Integer> createLottoNumbers() {
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(MIN_RANGE, MAX_RANGE, NUMBERS_COUNT);

        return numbers.stream()
                .sorted()
                .toList();
    }

    public List<Integer> createByInput(String input) {
        return Arrays.stream(input.split(COMMA))
                .map(Integer::parseInt)
                .toList();
    }
}

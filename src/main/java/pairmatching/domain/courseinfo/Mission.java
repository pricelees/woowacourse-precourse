package pairmatching.domain.courseinfo;

import java.util.Arrays;
import java.util.Optional;

public enum Mission {
    CAR_RACING("자동차경주"),
    LOTTO("로또"),
    NUMBER_BASEBALL("숫자야구게임"),
    SHOPPING_CART("장바구니"),
    PAY("결제"),
    METRO_MAP("지하철노선도"),
    PERFOMANCE_IMPROVEMENT("성능개선"),
    LAUNCH("배포");

    private final String name;

    Mission(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Optional<Mission> fromName(String name) {
        return Arrays.stream(Mission.values())
                .filter(mission -> name.equals(mission.getName()))
                .findFirst();
    }
}

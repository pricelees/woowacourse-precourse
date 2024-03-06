package pairmatching.domain.courseinfo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum Level {
    LEVEL_1("레벨1", List.of(Mission.CAR_RACING, Mission.LOTTO, Mission.NUMBER_BASEBALL)),
    LEVEL_2("레벨2", List.of(Mission.SHOPPING_CART, Mission.PAY, Mission.METRO_MAP)),
    LEVEL_3("레벨3", List.of()),
    LEVEL_4("레벨4", List.of(Mission.PERFOMANCE_IMPROVEMENT, Mission.LAUNCH)),
    LEVEL_5("레벨5", List.of());

    private final String name;
    private final List<Mission> missions;

    Level(String name, List<Mission> missions) {
        this.name = name;
        this.missions = missions;
    }

    public String getName() {
        return name;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public static Optional<Level> fromName(String name) {
        return Arrays.stream(Level.values())
                .filter(level -> name.equals(level.getName()))
                .findFirst();
    }
}

package pairmatching.domain;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Pair {
    private final Set<Crew> crews;

    public Pair(Set<Crew> crews) {
        this.crews = crews;
    }

    public List<String> getCrewsName() {
        return crews.stream()
                .map(Crew::getName)
                .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pair pair = (Pair) o;
        return crews.containsAll(pair.crews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crews);
    }
}

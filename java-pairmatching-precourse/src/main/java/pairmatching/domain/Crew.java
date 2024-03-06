package pairmatching.domain;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Crew {
    private final String name;

    private Crew(String name) {
        this.name = name;
    }

    public static Crew fromName(String name) {
        return new Crew(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(name, crew.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

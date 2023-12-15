package menu.domain;

import java.util.Objects;

public class CoachName {
    private final String name;

    public CoachName(String name) {
        this.name = name;
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
        CoachName coachName = (CoachName) o;
        return Objects.equals(getName(), coachName.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}

package pairmatching.domain.courseinfo;

import java.util.Arrays;
import java.util.Optional;

public enum Course {
    BACKEND("백엔드"),
    FRONTEND("프론트엔드");

    private final String name;

    Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Optional<Course> fromName(String name) {
        return Arrays.stream(Course.values())
                .filter(course -> name.equals(course.getName()))
                .findFirst();
    }

    public boolean isBackend() {
        return this == BACKEND;
    }

    public boolean isFrontend() {
        return this == FRONTEND;
    }
}

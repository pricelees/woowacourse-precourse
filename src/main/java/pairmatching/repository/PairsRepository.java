package pairmatching.repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import pairmatching.domain.Pairs;
import pairmatching.domain.courseinfo.CourseInfo;
import pairmatching.exception.DuplicatePairException;

public class PairsRepository {
    private final Set<Pairs> allPairs;

    public PairsRepository() {
        this.allPairs = new HashSet<>();
    }

    public void addPairs(Pairs pairs) {
        validatePairs(pairs);
        allPairs.add(pairs);
    }

    private void validatePairs(Pairs otherPairs) {
        if (hasDuplicatePair(otherPairs)) {
            throw new DuplicatePairException();
        }
    }

    private boolean hasDuplicatePair(Pairs otherPairs) {
        return allPairs.stream()
                .flatMap(pairs -> pairs.getPairInfo().stream())
                .anyMatch(otherPairs::isContain);
    }

    public void clear() {
        allPairs.clear();
    }

    public Optional<Pairs> getPairsFromCourse(CourseInfo courseInfo) {
        return allPairs.stream()
                .filter(pairs -> pairs.isSameCourseInfo(courseInfo))
                .findFirst();
    }

    public boolean hasPairs(CourseInfo courseInfo) {
        return allPairs.stream()
                .map(Pairs::getCourseInfo)
                .toList().contains(courseInfo);
    }
}

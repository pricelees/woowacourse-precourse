package pairmatching.domain;

import java.util.List;
import pairmatching.domain.courseinfo.CourseInfo;

public class Pairs {
    private final CourseInfo courseInfo;
    private final List<Pair> pairInfo;

    public Pairs(CourseInfo courseInfo, List<Pair> pairInfo) {
        this.courseInfo = courseInfo;
        this.pairInfo = pairInfo;
    }

    public boolean isSameCourseInfo(CourseInfo otherCourseInfo) {
        return courseInfo.equals(otherCourseInfo);
    }

    public boolean isContain(Pair pair) {
        return pairInfo.contains(pair);
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public List<Pair> getPairInfo() {
        return pairInfo;
    }
}

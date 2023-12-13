package pairmatching.dto;

import pairmatching.domain.courseinfo.Course;
import pairmatching.domain.courseinfo.Level;
import pairmatching.domain.courseinfo.Mission;

public record CourseInfoRequest(
        Course course,
        Level level,
        Mission mission
) {
}

package pairmatching.util;

import pairmatching.domain.courseinfo.Course;
import pairmatching.domain.courseinfo.Level;
import pairmatching.domain.courseinfo.Mission;
import pairmatching.dto.CourseInfoRequest;
import pairmatching.exception.InvalidCourseInfoException;

public class CourseInfoRequestMapper {
    private static final String COURSE_LEVEL_MISSION_DELIMITER = ", ";
    private static final int COURSE_INDEX = 0;
    private static final int LEVEL_INDEX = 1;
    private static final int MISSION_INDEX = 2;

    public static CourseInfoRequest from(String courseInfo) {
        String[] allInfo = courseInfo.split(COURSE_LEVEL_MISSION_DELIMITER);
        Course course = Course.fromName(allInfo[COURSE_INDEX])
                .orElseThrow(InvalidCourseInfoException::new);
        Level level = Level.fromName(allInfo[LEVEL_INDEX])
                .orElseThrow(InvalidCourseInfoException::new);
        Mission mission = Mission.fromName(allInfo[MISSION_INDEX])
                .orElseThrow(InvalidCourseInfoException::new);

        return new CourseInfoRequest(course, level, mission);
    }
}

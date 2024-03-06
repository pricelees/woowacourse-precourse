package pairmatching.view.input;

import java.util.Arrays;
import java.util.List;
import pairmatching.domain.courseinfo.Course;
import pairmatching.domain.courseinfo.Level;
import pairmatching.domain.courseinfo.Mission;

public class InputMessages {
    public static final String PROGRAM_OPTION_MESSAGE = """
    기능을 선택하세요.
    1. 페어 매칭
    2. 페어 조회
    3. 페어 초기화
    Q. 종료""";
    public static final String SELECT_COURSE_INFO_MESSAGE = """
    과정, 레벨, 미션을 선택하세요.
    ex) 백엔드, 레벨1, 자동차경주""";
    public static final String REMATCH_MESSAGE = """
    매칭 정보가 있습니다. 다시 매칭하시겠습니까?
    네 | 아니오""";
    private static final String COURSE_FORMAT = "과정: %s\n";
    private static final String MISSION_PREFIX= "미션: \n";
    private static final String NONE = "";
    private static final String BAR_WITH_SPACE = " | ";
    private static final String LEVEL_FORMAT = "  - %s: %s\n";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String SHARPS = "#".repeat(45);

    public static String displayAllCourseInfo() {
        return SHARPS
                + LINE_SEPARATOR
                + displayCourse()
                + displayAllLevels()
                + SHARPS;
    }

    private static String displayCourse() {
        List<String> courseName = Arrays.stream(Course.values())
                .map(Course::getName)
                .toList();
        return String.format(COURSE_FORMAT, String.join(BAR_WITH_SPACE, courseName));
    }

    private static String displayAllLevels() {
        StringBuilder result = new StringBuilder(MISSION_PREFIX);
        Arrays.stream(Level.values())
                .forEach(level -> result.append(
                        String.format(LEVEL_FORMAT, level.getName(), displayAllMissionByLevel(level))
                ));
        return result.toString();
    }

    private static String displayAllMissionByLevel(Level level) {
        List<Mission> missions = level.getMissions();
        if (missions.isEmpty()) {
            return NONE;
        }
        List<String> missionNames = missions.stream()
                .map(Mission::getName)
                .toList();

        return String.join(BAR_WITH_SPACE, missionNames);
    }
}

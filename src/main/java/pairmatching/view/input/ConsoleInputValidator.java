package pairmatching.view.input;

import pairmatching.exception.InvalidCourseInfoException;
import pairmatching.exception.InvalidOptionException;
import pairmatching.exception.InvalidRematchOptionException;

public class ConsoleInputValidator {
    private static final String OPTION_REGEX = "[123Q]";
    private static final String COURSE_INFO_REGEX = "([가-힣]+)(,\\s)([가-힣]+[1-5])(,\\s)([가-힣]+)";
    private static final String REMATCH_REGEX = "(네)|(아니오)";

    public void validateOption(String option) {
        if (!option.matches(OPTION_REGEX)) {
            throw new InvalidOptionException();
        }
    }

    public void validateCourseInfo(String courseInfo) {
        if (!courseInfo.matches(COURSE_INFO_REGEX)) {
            throw new InvalidCourseInfoException();
        }
    }

    public void validateRematchOption(String rematchOption) {
        if (!rematchOption.matches(REMATCH_REGEX)) {
            throw new InvalidRematchOptionException();
        }
    }
}

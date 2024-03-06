package pairmatching.util;

import pairmatching.dto.ProgramOptionRequest;
import pairmatching.exception.InvalidOptionException;

public class ProgramOptionRequestMapper {
    private static final String INVALID_OPTION_REGEX = "[^123Q]";
    public static ProgramOptionRequest from(String option) {
        validateOption(option);
        return new ProgramOptionRequest(option);
    }

    private static void validateOption(String option) {
        if (option.matches(INVALID_OPTION_REGEX)) {
            throw new InvalidOptionException();
        }
    }
}

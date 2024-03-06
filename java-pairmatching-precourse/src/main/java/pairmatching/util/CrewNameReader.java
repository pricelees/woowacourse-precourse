package pairmatching.util;

import camp.nextstep.edu.missionutils.Randoms;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import pairmatching.domain.courseinfo.Course;

public class CrewNameReader {
    private static final String BACKEND_CREW_FILE = "backend-crew.md";
    private static final String FRONTEND_CREW_FILE = "frontend-crew.md";

    public static List<String> readShuffledCrewNames(Course course) {
        if (course.isBackend()) {
            return Randoms.shuffle(readBackendCrewNames());
        }
        return Randoms.shuffle(readFrontendCrewNames());
    }

    private static List<String> readBackendCrewNames() {
        return readOf(BACKEND_CREW_FILE);
    }

    private static List<String> readFrontendCrewNames() {
        return readOf(FRONTEND_CREW_FILE);
    }

    private static List<String> readOf(String fileName) {
        String path = "src/main/resources/";
        List<String> names = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(path + fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            names = bufferedReader.lines().toList();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return names;
    }

    public static void main(String[] args) {
        System.out.println(CrewNameReader.readBackendCrewNames());
        System.out.println(CrewNameReader.readShuffledCrewNames(Course.BACKEND));
    }
}

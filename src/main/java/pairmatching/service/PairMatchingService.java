package pairmatching.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import pairmatching.domain.Crew;
import pairmatching.domain.Pair;
import pairmatching.domain.Pairs;
import pairmatching.domain.courseinfo.CourseInfo;
import pairmatching.util.CrewNameReader;

public class PairMatchingService {
    public Pairs matchAllCrews(CourseInfo courseInfo) {
        List<String> crewNames = CrewNameReader.readShuffledCrewNames(courseInfo.getCourse());
        List<Pair> pairInfo = pair(crewNames);

        return new Pairs(courseInfo, pairInfo);
    }

    private List<Pair> pair(List<String> crewNames) {
        List<Pair> pairInfo = new ArrayList<>();
        int crewsCount = crewNames.size();
        int searchingIndex = getSearchingIndex(crewsCount);
        for (int i = 0; i < searchingIndex; i += 2) {
            Set<Crew> crews = new LinkedHashSet<>(List.of(
                    Crew.fromName(crewNames.get(i)),
                    Crew.fromName(crewNames.get(i + 1))
            ));
            pairInfo.add(new Pair(crews));
        }
        Set<Crew> lastCrews = crewNames.subList(searchingIndex, crewsCount).stream()
                .map(Crew::fromName).collect(Collectors.toCollection(LinkedHashSet::new));
        pairInfo.add(new Pair(lastCrews));
        return pairInfo;
    }

    private int getSearchingIndex(int crewCount) {
        if (crewCount % 2 == 0) {
            return crewCount - 2;
        }
        return crewCount - 3;
    }
}

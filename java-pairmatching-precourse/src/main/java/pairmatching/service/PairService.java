package pairmatching.service;

import java.util.List;
import pairmatching.domain.Pairs;
import pairmatching.domain.courseinfo.CourseInfo;
import pairmatching.dto.CourseInfoRequest;
import pairmatching.dto.MatchResponse;
import pairmatching.dto.PairResponse;
import pairmatching.exception.DuplicatePairException;
import pairmatching.repository.PairsRepository;
import pairmatching.util.MatchResponseMapper;

public class PairService {
    private final PairsRepository pairsRepository;
    private final PairMatchingService pairMatchingService;
    private final PairSearchingService pairSearchingService;

    public PairService(
            PairsRepository pairsRepository,
            PairMatchingService pairMatchingService,
            PairSearchingService pairSearchingService
    ) {
        this.pairsRepository = pairsRepository;
        this.pairMatchingService = pairMatchingService;
        this.pairSearchingService = pairSearchingService;
    }

    public void clear() {
        pairsRepository.clear();
    }

    public CourseInfo loadCourseInfoFrom(CourseInfoRequest courseInfoRequest) {
        return new CourseInfo(
                courseInfoRequest.course(),
                courseInfoRequest.level(),
                courseInfoRequest.mission()
        );
    }

    public MatchResponse match(CourseInfo courseInfo) throws DuplicatePairException {
        Pairs pairs = pairMatchingService.matchAllCrews(courseInfo);
        pairsRepository.addPairs(pairs);

        return MatchResponseMapper.from(pairs);
    }

    public boolean checkIfPairsExist(CourseInfo courseInfo) {
        return pairsRepository.hasPairs(courseInfo);
    }

    public MatchResponse search(CourseInfo courseInfo) {
        Pairs pairs = pairSearchingService.searchMatching(pairsRepository, courseInfo);
        return MatchResponseMapper.from(pairs);
    }
}

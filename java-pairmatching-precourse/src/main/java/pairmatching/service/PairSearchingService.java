package pairmatching.service;

import pairmatching.domain.Pairs;
import pairmatching.domain.courseinfo.CourseInfo;
import pairmatching.exception.NotExistMatchingException;
import pairmatching.repository.PairsRepository;

public class PairSearchingService {
    public Pairs searchMatching(PairsRepository pairsRepository, CourseInfo courseInfo) {
        return pairsRepository.getPairsFromCourse(courseInfo)
                .orElseThrow(NotExistMatchingException::new);
    }
}

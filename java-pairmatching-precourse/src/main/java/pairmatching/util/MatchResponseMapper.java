package pairmatching.util;

import java.util.List;
import pairmatching.domain.Pairs;
import pairmatching.dto.MatchResponse;
import pairmatching.dto.PairResponse;

public class MatchResponseMapper {
    public static MatchResponse from(Pairs pairs) {
        List<PairResponse> pairResponses = pairs.getPairInfo().stream()
                .map(pair -> new PairResponse(pair.getCrewsName()))
                .toList();
        return new MatchResponse(pairResponses);
    }
}

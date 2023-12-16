package oncall.dto;

import java.util.List;

public record TimeTableResponse(List<TimeResponse> timeResponses) {
}

package oncall.dto;

public record TimeResponse(int month, int dayOfMonth, String dayOfWeek, String workerName) {
}

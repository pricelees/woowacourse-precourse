package oncall.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import oncall.config.ErrorMessage;

public record WorkerNamesRequests(WorkerNamesRequest weekdayWorkers, WorkerNamesRequest holidayWorkers) {
    public record WorkerNamesRequest(List<String> workerNames) {
        private static final int MAX_WORKER_COUNT = 35;
        private static final int MIN_WORKER_COUNT = 5;
        private static final int MAX_NAME_LENGTH = 5;

        public WorkerNamesRequest {
            validateDuplicateName(workerNames);
            validateNameLength(workerNames);
            validateSize(workerNames);
        }

        private void validateSize(List<String> workerNames) {
            int workerCount = workerNames.size();
            if (workerCount > MAX_WORKER_COUNT || workerCount < MIN_WORKER_COUNT) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
            }
        }

        private void validateNameLength(List<String> workerNames) {
            if (hasInvalidNameLength(workerNames)) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
            }
        }

        private boolean hasInvalidNameLength(List<String> workerNames) {
            return workerNames.stream().anyMatch(name ->
                    name.isBlank() || name.length() > MAX_NAME_LENGTH
            );
        }

        private void validateDuplicateName(List<String> workerNames) {
            if (hasDuplicateName(workerNames)) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
            }
        }

        private boolean hasDuplicateName(List<String> workerNames) {
            Set<String> nonDuplicate = new HashSet<>(workerNames);
            return workerNames.size() != nonDuplicate.size();
        }
    }
}

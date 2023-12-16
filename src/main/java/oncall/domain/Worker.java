package oncall.domain;

import java.util.Objects;

public class Worker {
    private final WorkerName workerName;

    public Worker(WorkerName workerName) {
        this.workerName = workerName;
    }

    public static Worker fromName(String workerName) {
        return new Worker(new WorkerName(workerName));
    }

    public String getWorkerName() {
        return workerName.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Worker worker = (Worker) o;
        return Objects.equals(getWorkerName(), worker.getWorkerName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWorkerName());
    }
}

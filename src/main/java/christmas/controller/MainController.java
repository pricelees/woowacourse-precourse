package christmas.controller;

public record MainController(PlannerControllable plannerControllable) {
    public void run() {
        plannerControllable.run();
    }
}

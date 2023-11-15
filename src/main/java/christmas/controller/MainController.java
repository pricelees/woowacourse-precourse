package christmas.controller;

public final class MainController {
    private final PlannerControllable plannerControllable;

    public MainController(final PlannerControllable plannerControllable) {
        this.plannerControllable = plannerControllable;
    }

    public void run() {
        plannerControllable.run();
    }
}

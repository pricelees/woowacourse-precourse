package menu.controller;

import java.util.List;
import menu.domain.Coach;
import menu.domain.WeeklyMenu;
import menu.dto.CoachNamesRequest;
import menu.dto.WeeklyLunchDayResponse;
import menu.dto.WeeklyMenuResponse;
import menu.service.CoachService;
import menu.service.MenuRecommendService;
import menu.util.ResponseMapper;
import menu.view.input.InputView;
import menu.view.output.OutputView;

public class MenuRecommendController {
    private final CoachService coachService;
    private final MenuRecommendService menuRecommendService;
    private final InputView inputView;
    private final OutputView outputView;

    public MenuRecommendController(CoachService coachService, MenuRecommendService menuRecommendService,
                                   InputView inputView, OutputView outputView) {
        this.coachService = coachService;
        this.menuRecommendService = menuRecommendService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        outputView.printStartMessage();
        CoachNamesRequest coachNamesRequest = inputView.receiveCoachNames();
        List<Coach> coaches = coachService.loadAllCoachInfo(inputView.receiveAllCoachesInfo(coachNamesRequest));
        WeeklyMenu recommendedMenu = menuRecommendService.recommendWeeklyMenu(coaches);

        WeeklyLunchDayResponse weeklyLunchDayResponse = ResponseMapper.mapWeeklyLunchDayResponse(recommendedMenu);
        WeeklyMenuResponse weeklyMenuResponse = ResponseMapper.mapWeeklyMenuResponse(coaches, recommendedMenu);

        outputView.printResultMessage();
        outputView.printDayInfo(weeklyLunchDayResponse);
        outputView.printCoachesMenu(weeklyMenuResponse);
        outputView.printCompleteMessage();
    }
}

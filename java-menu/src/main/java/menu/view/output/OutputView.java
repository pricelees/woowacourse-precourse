package menu.view.output;

import java.util.List;
import menu.dto.WeeklyLunchDayResponse;
import menu.dto.WeeklyMenuResponse;

public final class OutputView {
    private static final String OUTPUT_FORMAT = "[ %s | %s ]";
    private static final String DIVISION = "구분";
    private static final String CATEGORY = "카테고리";
    private static final String BAR_WITH_SPACE = " | ";
    private static final String START_MESSAGE = "점심 메뉴 추천을 시작합니다.";
    private static final String RECOMMEND_RESULT = "메뉴 추천 결과입니다.";
    private static final String RECOMMEND_COMPLETE = "추천을 완료했습니다.";
    private final Printer printer;

    public OutputView(Printer printer) {
        this.printer = printer;
    }

    public void printStartMessage() {
        printer.print(START_MESSAGE);
        printer.printEmptyLine();
    }

    public void printCoachesMenu(WeeklyMenuResponse weeklyMenuResponse) {
        weeklyMenuResponse.weeklyCoachMenuResponses().forEach(
                weeklyCoachMenuResponse -> {
                    String coachName = weeklyCoachMenuResponse.coachName();
                    List<String> menus = weeklyCoachMenuResponse.menuList();
                    printer.printFormat(OUTPUT_FORMAT, coachName, String.join(BAR_WITH_SPACE, menus));
                }
        );
        printer.printEmptyLine();
    }

    public void printDayInfo(WeeklyLunchDayResponse weeklyLunchDayResponse) {
        printer.printFormat(OUTPUT_FORMAT, DIVISION, String.join(BAR_WITH_SPACE, weeklyLunchDayResponse.lunchDays()));
        printer.printFormat(OUTPUT_FORMAT, CATEGORY, String.join(BAR_WITH_SPACE, weeklyLunchDayResponse.menuCategories()));
    }

    public void printCompleteMessage() {
        printer.print(RECOMMEND_COMPLETE);
    }

    public void printResultMessage() {
        printer.print(RECOMMEND_RESULT);
    }


    public void printExceptionMessage(String message) {
        printer.print(message);
        printer.printEmptyLine();
    }
}

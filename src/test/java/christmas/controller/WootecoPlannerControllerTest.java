package christmas.controller;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.test.NsTest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WootecoPlannerControllerTest extends NsTest {
    @DisplayName("방문 날짜에 대한 모든 예외를 발생시키며, 입력을 반복하여 받는지 확인")
    @Test
    void start_WithInvalidVisitDate_CanRetryInput() {
        assertSimpleTest(() -> {
            runException("", "a", "-1", "0", "32");
            assertThat(output()).contains(
                    "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요."
            );
        });
    }

    @DisplayName("메뉴와 갯수에 대한 모든 예외를 발생시키며, 입력을 반복하여 받는지 확인")
    @Test
    void start_WithInvalidMenuAndCount_CanRetryInput() {
        assertSimpleTest(() -> {
            runException(
                    "3", // 날짜는 정상적인 값을 입력
                    "", // 입력이 비어있는 경우
                    "김치찌개-1,시저샐러드-1,타파스-1", // 없는 메뉴를 주문한 경우
                    "양송이수프-1:타파스-1:제로콜라-1", // 구분자가 컴마가 아닌 경우
                    "시저샐러드-a,티본스테이크-1,바비큐립-1", // 갯수가 잘못된 경우
                    "시저샐러드-0,티본스테이크-1,바비큐립-1", // 갯수가 0인 경우
                    "제로콜라-1,레드와인-2,샴페인-3", // 음료만 주문하는 경우
                    "해산물파스타:1,크리스마스파스타:1,초코케이크:1", // 메뉴 이름과 갯수를 -로 구분하지 않는경우
                    "아이스크림-20,타파스-1" // 메뉴를 20개 이상 주문하는 경우
            );
            assertThat(output()).contains(
                    "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요."
            );
        });
    }

    @DisplayName("12월 25일에 모든 메뉴를 1개씩 주문시켰을 때의 가격과 할인 내역을 정확히 반환하는지 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("argumentsProvider")
    void start_WhenChristmas_WithAllMenu_isPrintCorrectInfo(String day, String... expectedDiscount) {
        // 입력 스트림 설정
        command(
                day,
                "양송이수프-1,타파스-1,시저샐러드-1,티본스테이크-1,바비큐립-1,"
                        + "해산물파스타-1,크리스마스파스타-1,초코케이크-1,"
                        + "아이스크림-1,제로콜라-1,레드와인-1,샴페인-1");
        // 출력 스트림 설정
        OutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // 플래너 실행
        runMain();

        // 결과 확인
        assertThat(out.toString()).contains(expectedDiscount);
    }

    static Stream<Arguments> argumentsProvider() {
        return Stream.of(
                // 증정 이벤트는 중복되므로 제외. 다르게 표현되는 모든 경우를 테스트함.
                arguments("1", new String[]{"크리스마스 디데이 할인: -1,000원", "주말 할인: -8,092원"}),
                arguments("3", new String[]{"크리스마스 디데이 할인: -1,200원", "평일 할인: -4,046원", "특별 할인: -1,000원"}),
                arguments("5", new String[]{"크리스마스 디데이 할인: -1,400원", "평일 할인: -4,046원"}),
                arguments("25", new String[]{"크리스마스 디데이 할인: -3,400원", "평일 할인: -4,046원", "특별 할인: -1,000원"}),
                arguments("30", new String[]{"주말 할인: -8,092원"}),
                arguments("31", new String[]{"평일 할인: -4,046원", "특별 할인: -1,000원"})
        );
    }

    private void command(final String... args) {
        final byte[] buf = String.join("\n", args).getBytes();
        System.setIn(new ByteArrayInputStream(buf));
    }

    @Override
    protected void runMain() {
        PlannerControllable wootecoPlannerController = WootecoPlannerController.getInstance();
        wootecoPlannerController.run();
        Console.close();
    }
}
package oncall.controller;

import static oncall.config.AppConfig.controller;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import camp.nextstep.edu.missionutils.Console;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ControllerTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private PrintStream standardOut;
    private OutputStream captor;

    @BeforeEach
    void setUp() {
        standardOut = System.out;
        captor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
        System.out.println(captor.toString().trim());
        Console.close();
    }

    @DisplayName("이틀 연속 근무 상황 발생시, 정확히 교대하는지 확인")
    @Test
    void run_WithWorkOverTime() {
        command(
                "4,토",
                "상진,상돌,엄마,아빠,누나", // 평일 근무자 지정
                "허브,상진,말랑,라온,헤나" // 휴일 근무자 지정
        );
        run();
        assertThat(captor.toString())
                .contains(
                        "4월 1일 토 허브" + LINE_SEPARATOR,
                        "4월 2일 일 상진" + LINE_SEPARATOR,
                        "4월 3일 월 상돌" + LINE_SEPARATOR,
                        "4월 4일 화 상진" + LINE_SEPARATOR,
                        "4월 5일 수 엄마" + LINE_SEPARATOR,
                        "4월 6일 목 아빠" + LINE_SEPARATOR,
                        "4월 7일 금 누나" + LINE_SEPARATOR,
                        "4월 8일 토 말랑" + LINE_SEPARATOR,
                        "4월 9일 일 라온" + LINE_SEPARATOR
                );
    }

    @DisplayName("휴일 비상 근무자 입력 예외 발생시, 평일부터 다시 입력받은 뒤 정상 작동 확인")
    @Test
    void run_WithInvalidHolidayWorkers() {
        // 평일 5명, 주말 5명으로 지정한 뒤 순환이 정확히 이루어지는지도 확인.
        command(
                "4,토",
                "상진,상돌,엄마,아빠,누나", // 평일 근무자 초기 입력
                "허브,쥬니,말랑,라온", // 휴일 근무자를 4명만 지정하여 예외 발생
                "상진,상돌,엄마,아빠,누나", // 평일 근무자 재입력
                "허브,쥬니,말랑,라온,헤나" // 휴일 근무자 정상 입력
        );
        run();
        assertThat(captor.toString())
                .contains(
                        "[ERROR]",
                        "4월 1일 토 허브" + LINE_SEPARATOR,
                        "4월 2일 일 쥬니" + LINE_SEPARATOR,
                        "4월 3일 월 상진" + LINE_SEPARATOR,
                        "4월 4일 화 상돌" + LINE_SEPARATOR,
                        "4월 5일 수 엄마" + LINE_SEPARATOR,
                        "4월 6일 목 아빠" + LINE_SEPARATOR,
                        "4월 7일 금 누나" + LINE_SEPARATOR,
                        "4월 8일 토 말랑" + LINE_SEPARATOR,
                        "4월 9일 일 라온" + LINE_SEPARATOR,
                        "4월 10일 월 상진" + LINE_SEPARATOR,
                        "4월 11일 화 상돌" + LINE_SEPARATOR,
                        "4월 12일 수 엄마" + LINE_SEPARATOR,
                        "4월 13일 목 아빠" + LINE_SEPARATOR,
                        "4월 14일 금 누나" + LINE_SEPARATOR,
                        "4월 15일 토 헤나" + LINE_SEPARATOR,
                        "4월 16일 일 허브" + LINE_SEPARATOR,
                        "4월 17일 월 상진" + LINE_SEPARATOR,
                        "4월 18일 화 상돌" + LINE_SEPARATOR,
                        "4월 19일 수 엄마" + LINE_SEPARATOR,
                        "4월 20일 목 아빠" + LINE_SEPARATOR,
                        "4월 21일 금 누나" + LINE_SEPARATOR,
                        "4월 22일 토 쥬니" + LINE_SEPARATOR,
                        "4월 23일 일 말랑" + LINE_SEPARATOR,
                        "4월 24일 월 상진" + LINE_SEPARATOR,
                        "4월 25일 화 상돌" + LINE_SEPARATOR,
                        "4월 26일 수 엄마" + LINE_SEPARATOR,
                        "4월 27일 목 아빠" + LINE_SEPARATOR,
                        "4월 28일 금 누나" + LINE_SEPARATOR,
                        "4월 29일 토 라온" + LINE_SEPARATOR,
                        "4월 30일 일 헤나"
                );
    }

    @DisplayName("근무자 입력에 대한 예외 발생시 반복 입력 확인")
    @Test
    void run_WithInvalidWorkerNames() {
        command(
                "5,월", // 날짜는 정상 입력
                "허브,쥬니,말랑,라온,헤나,우코,에단,수달,파워,히이로,허브", // 중복되는 이름
                "허브,쥬니,말랑,라온", // 5명 미만
                "상진,허브,쥬니,말랑,라온,헤나,우코,에단,수달,파워,"
                        + "히이로,마코,슬링키,모디,연어,깃짱,리오,고니,박스터,"
                        + "달리,조이,노아이즈,도이,도치,홍고,스캇,폴로,해시,로지,첵스,"
                        + "아이크,우가,푸만능,애쉬,로이스,오션", // 35명 초과(36명)
                "상진,상돌,엄마,아빠,안녕하세요옹" // 이름 길이 초과(5글자 초과)
        );
        try {
            assertTimeoutPreemptively(Duration.ofSeconds(1L), this::run);
        } catch (NoSuchElementException ignored) {
        }
    }

    @DisplayName("날짜 입력 형식에 대한 예외 발생시 반복 입력 확인")
    @Test
    void run_WithInvalidDateInfo() {
        command(
                "", // 빈 입력
                "0,월", // 월 범위 초과
                "13,화", // 월 범위 초과
                "5,월요일", // FullName 입력
                "5:월" // 컴마 이외의 구분자
        );
        try {
            assertTimeoutPreemptively(Duration.ofSeconds(1L), this::run);
        } catch (NoSuchElementException ignored) {
        }
    }

    private void run() {
        controller().run();
    }

    private void command(final String... args) {
        final byte[] buf = String.join("\n", args).getBytes();
        System.setIn(new ByteArrayInputStream(buf));
    }
}
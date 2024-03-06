package pairmatching.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mockStatic;
import static pairmatching.config.AppConfig.pairProgramController;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class PairProgramControllerTest {
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

    @DisplayName("홀수 인원 매칭, 짝수 인원 재매칭, 매칭 조회 및 초기화 후 정상 종료 확인")
    @Test
    void run_WithAllProcess() {
        command("1", "백엔드, 레벨1, 자동차경주", "1", "백엔드, 레벨1, 자동차경주", "네", "2", "백엔드, 레벨1, 자동차경주", "3", "Q");
        runAfterSetCrewNames(List.of("상진", "상돌", "엄마", "아빠", "누나"),
                List.of("상진", "엄마", "상돌", "아빠"));
        assertThat(captor.toString()).contains(
                "페어 매칭 결과입니다.",
                "상진 : 상돌",
                "엄마 : 아빠 : 누나",
                "매칭 정보가 있습니다. 다시 매칭하시겠습니까?",
                "상진 : 엄마",
                "상돌 : 아빠",
                "초기화 되었습니다."
        );
    }

    @DisplayName("해당 미션에 대한 매칭 정보가 없을 때의 예외 발생 확인")
    @Test
    void run_WithNotExistMatch_ThrowsException() {
        command("1", "백엔드, 레벨1, 자동차경주", "2", "백엔드, 레벨1, 로또", "백엔드, 레벨1, 자동차경주", "Q");
        runAfterSetCrewNames(List.of("상진", "상돌"));
        assertThat(captor.toString()).contains(
                "페어 매칭 결과입니다.",
                "상진 : 상돌",
                "[ERROR] 매칭 이력이 없습니다."
        );
    }

    @DisplayName("기능 선택에서의 예외 발생시 반복 입력 확인")
    @Test
    void run_WithInvalidOption() {
        command(
                "A", // 없는 옵션
                "", // 빈 입력
                "0" // 없는 옵션
        );
        try {
            assertTimeoutPreemptively(Duration.ofSeconds(1L), this::run);
        } catch (NoSuchElementException ignored) {
        }
    }

    @DisplayName("교육과정 선택에서의 예외 발생시 반복 입력 확인")
    @Test
    void run_WithInvalidCourseInfo() {
        command(
                "1", // 기능은 정상적인 값을 입력
                "", // 빈 입력인 경우
                "백엔드: 레벨1: 자동차경주", // 구분자 오류
                "백스타트, 레벨1, 자동차경주", // 없는 과정인 경우
                "백엔드, 레벨6, 자동차경주", // 없는 레벨인 경우
                "백엔드, 레벨1, 코딩테스트" // 없는 미션인 경우
        );
        try {
            assertTimeoutPreemptively(Duration.ofSeconds(1L), this::run);
        } catch (NoSuchElementException ignored) {
        }
    }

    @DisplayName("매칭을 다시 할 때의 입력값에 대한 예외 발생시 반복 입력 확인")
    @Test
    void run_WithInvalidRematchOption() {
        command(
                "1",
                "백엔드, 레벨1, 자동차경주",
                "1",
                "백엔드, 레벨1, 자동차경주", // 재 매칭 시도
                "넵",
                "아니용",
                "GOGO"
        );
        try {
            assertTimeoutPreemptively(Duration.ofSeconds(1L), () -> runAfterSetCrewNames(List.of("상돌", "상진")));
        } catch (NoSuchElementException ignored) {
        }
    }

    @DisplayName("매칭에서 3회 이상 매칭 결과를 못 찾았을때의 예외 발생 확인")
    @Test
    void run_WithDuplicatePair() {
        command(
                "1",
                "백엔드, 레벨1, 자동차경주",// 프로그램 종료를 위해 하나는 정상적으로 입력
                "1",
                "백엔드, 레벨2, 결제",
                "백엔드, 레벨2, 결제", // 마지막엔 매칭이 되도록 설정
                "Q"
        );
        runAfterSetCrewNames(List.of("상진", "상돌", "엄마", "아빠", "누나"),
                        List.of("상진", "상돌", "엄마", "아빠", "누나"),
                        List.of("상진", "상돌", "엄마", "아빠", "누나"),
                        List.of("상진", "상돌", "엄마", "아빠", "누나"),
                        List.of("상진", "엄마", "상돌", "아빠", "누나", "사촌형")
        );
        assertThat(captor.toString()).contains(
                "페어 매칭 결과입니다.",
                "상진 : 상돌",
                "엄마 : 아빠 : 누나",
                "[ERROR] 중복되는 페어가 존재합니다.",
                "[ERROR] 매칭 가능 정보가 없습니다.",
                "상진 : 엄마",
                "상돌 : 아빠",
                "누나 : 사촌형"
        );
    }


    private void runAfterSetCrewNames(Object value, Object... values) {
        try (final MockedStatic<Randoms> mock = mockStatic(Randoms.class)) {
            mock.when(() -> Randoms.shuffle(anyList()))
                    .thenReturn(value, values);
            run();
        }
    }

    private void run() {
        pairProgramController().run();
    }

    private void command(final String... args) {
        final byte[] buf = String.join("\n", args).getBytes();
        System.setIn(new ByteArrayInputStream(buf));
    }
}
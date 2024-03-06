package bridge.controller;

import static bridge.config.AppConfig.bridgeGame;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.mockito.Mockito.mockStatic;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
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
import org.mockito.MockedStatic;

class BridgeGameTest {
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


    @DisplayName("1회 실패, 재시도 성공 확인")
    @Test
    void start() {
        command("5", "U", "D", "U", "D", "D", "R", "U", "D", "U", "D", "U");
        runAfterSetBridge(1, 0, 1, 0, 1);
        assertThat(captor.toString()).contains(
                "[ O ]",
                "[   ]",

                "[ O |   ]",
                "[   | O ]",

                "[ O |   | O ]",
                "[   | O |   ]",

                "[ O |   | O |   ]",
                "[   | O |   | O ]",

                "[ O |   | O |   |   ]",
                "[   | O |   | O | X ]",

                "[ O |   | O |   | O ]",
                "[   | O |   | O |   ]",

                "게임을 다시 시도할지 여부를 입력해주세요. (재시도: R, 종료: Q)",
                "최종 게임 결과",
                "게임 성공 여부: 성공",
                "총 시도한 횟수: 2"
        );
    }

    @DisplayName("실패후 게임 종료 확인")
    @Test
    void start_1() {
        command("3", "U", "D", "D", "Q");
        runAfterSetBridge(1, 0, 1);
        assertThat(captor.toString()).contains(
                "[ O ]",
                "[   ]",

                "[ O |   ]",
                "[   | O ]",

                "[ O |   |   ]",
                "[   | O | X ]",

                "게임을 다시 시도할지 여부를 입력해주세요. (재시도: R, 종료: Q)",
                "최종 게임 결과",
                "게임 성공 여부: 실패",
                "총 시도한 횟수: 1"
        );
    }

    @DisplayName("이동 위치 입력값 예외 발생시 재입력 후 정상 진행 확인")
    @Test
    void start_WithInvalidLocation_WhenPlaying() {
        command("3", "D", "A", "D", "U");
        runAfterSetBridge(0, 0, 1);

        assertThat(captor.toString()).contains(
                "[   ]",
                "[ O ]",

                "[ERROR] 이동할 칸은 U(위) 또는 D(아래)만 입력 가능합니다.",

                "[   |   ]",
                "[ O | O ]",

                "[   |   | O ]",
                "[ O | O |   ]",

                "최종 게임 결과",
                "게임 성공 여부: 성공",
                "총 시도한 횟수: 1"
        );
    }

    @DisplayName("다리 길이 대한 예외 발생시 반복 입력 확인")
    @Test
    void start_WithInvalidBridgeLength() {
        command(
                "", // 빈 입력
                "abc", // 문자 입력
                "2", // 3 미만 숫자 입력
                "21" // 20 초과 숫자 입력
        );
        try {
            assertTimeoutPreemptively(Duration.ofSeconds(1L), this::run);
        } catch (NoSuchElementException ignored) {
        }
    }

    @DisplayName("건널 위치에 대한 예외 발생시 반복 입력 확인")
    @Test
    void start_WithInvalidLocation() {
        command(
                "3", // 다리 길이는 정상적으로 입력
                "", // 빈 입력
                "1", // 숫자 입력
                "A", // U, D가 아닌 알파벳 입력
                "u" // 소문자 입력
        );
        try {
            assertTimeoutPreemptively(Duration.ofSeconds(1L), this::run);
        } catch (NoSuchElementException ignored) {
        }
    }

    @DisplayName("재시도 명령어에 대한 예외 발생시 반복 입력 확인")
    @Test
    void start_WithInvalidCommand() {
        command(
                "3", // 다리 길이는 정상적으로 입력
                "D", // 재시도 여부를 확인하도록 틀린 정답 입력
                "A", // R, Q가 아닌 알파벳
                "", // 빈 입력
                "1", // 숫자 입력
                "r"  // 소문자 입력
        );
        try {
            assertTimeoutPreemptively(Duration.ofSeconds(1L), () -> runAfterSetBridge(1, 1, 1));
        } catch (NoSuchElementException ignored) {
        }
    }

    private void runAfterSetBridge(Object value, Object... values) {
        try (final MockedStatic<Randoms> mock = mockStatic(Randoms.class)) {
            mock.when(() -> Randoms.pickNumberInRange(0, 1))
                    .thenReturn(value, values);
            run();
        }
    }

    private void run() {
        bridgeGame().start();
    }

    private void command(final String... args) {
        final byte[] buf = String.join("\n", args).getBytes();
        System.setIn(new ByteArrayInputStream(buf));
    }
}
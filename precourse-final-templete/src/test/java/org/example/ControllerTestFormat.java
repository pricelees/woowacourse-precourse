package org.example;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

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

public class ControllerTestFormat {
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

    // 예외 반복 입력 테스트 포맷
    @DisplayName("에 대한 예외 발생시 반복 입력 확인")
    @Test
    void start_WithInvalidLocation() {
        command();
        try {
            assertTimeoutPreemptively(Duration.ofSeconds(1L), this::run);
        } catch (NoSuchElementException ignored) {
        }
    }

    // 반복 랜덤 입력 테스트 포맷
    @DisplayName("게임 종료 확인")
    @Test
    void start_1() {
        // 지정하고 싶은 입력값 넣기
        command();

        // 랜덤값 지정하기
        runAfterSetBridge();
        assertThat(captor.toString()).contains(
                // 예상 출력 결과 넣기
        );
    }
    // 랜덤값을 사용하는 경우 랜덤을 지정하고 실행
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

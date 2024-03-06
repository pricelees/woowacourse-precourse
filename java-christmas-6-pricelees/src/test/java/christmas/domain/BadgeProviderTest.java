package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BadgeProviderTest {
    @DisplayName("할인 후 금액이 음수일 때 예외 발생 확인")
    @Test
    void constructor_WithNegativeAmount_ThrowsException() {
        assertThatThrownBy(() -> BadgeProvider.getBadgeName(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 할인 후의 금액이 할인 전 금액보다 클 수 없습니다.");
    }

    @DisplayName("입력된 금액에 대해 올바른 배지 이름을 반환하는지 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "0 / 없음", "4999 / 없음",
            "5000 / 별", "9999 / 별",
            "10000 / 트리", "19999 / 트리",
            "20000 / 산타"
    }, delimiter = '/')
    void getBadgeInfo(int amountAfterDiscount, String expectedBadgeName) {
        assertThat(BadgeProvider.getBadgeName(amountAfterDiscount))
                .isEqualTo(expectedBadgeName);
    }
}
package christmas.discount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.constants.menu.Menu;
import christmas.domain.Customer;
import christmas.domain.SelectedMenu;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChampagneDiscountTest {
    DiscountStrategy champagneDiscount = new ChampagneDiscount();

    @DisplayName("유효하지 않은 조건일 때, 예외 발생 확인")
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidCustomerInfoProvider")
    void getDiscountAmount_WithInvalidInput_ThrowsException(String testName, Customer customer,
                                                            String expectedMessage) {
        assertThatThrownBy(() -> champagneDiscount.getDiscountAmount(customer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    @DisplayName("조건을 만족시켰을 때, 25_000원인 할인 금액을 정확히 얻어내는지 확인")
    @Test
    void getDiscountAmount_WithValidInput() {
        Customer customer = new Customer(
                LocalDateTime.of(2023, 12, 1, 0, 0, 0),
                new SelectedMenu(Map.of(Menu.CHRISTMAS_PASTA, 10))
        );

        assertThat(champagneDiscount.getDiscountAmount(customer))
                .isEqualTo(-25_000);
    }

    static Stream<Arguments> invalidCustomerInfoProvider() {
        return Stream.of(
                arguments("12만원 미만의 금액인 경우",
                        new Customer(
                                LocalDateTime.of(2023, 12, 1, 0, 0, 0),
                                new SelectedMenu(Map.of(Menu.BBQ_RIBS, 1))
                        ),
                        "[ERROR] 샴페인 증정을 위한 최소 주문금액은 12만원 입니다."
                ));
    }
}
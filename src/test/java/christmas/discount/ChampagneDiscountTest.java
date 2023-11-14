package christmas.discount;

import static christmas.TestConstants.CHRISTMAS;
import static christmas.TestConstants.HOUR_17;
import static christmas.TestConstants.MINUTE_00;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.constants.menu.Menu;
import christmas.domain.Customer;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChampagneDiscountTest {
    @DisplayName("12만원 미만 주문 고객의 할인 금액을 0원으로 반환하는지 확인")
    @Test
    void getDiscountAmount_WithInvalidAmount_ThrowsException() {
        Customer customer = new Customer(
                LocalDateTime.of(2023, 12, CHRISTMAS, HOUR_17, MINUTE_00),
                Map.of(Menu.BBQ_RIBS, 1)
        );
        DiscountStrategy discountStrategy = ChampagneDiscount.getInstance();

        assertThat(discountStrategy.getDiscountAmount(customer))
                .isEqualTo(0);
    }

    @DisplayName("12만원 이상 주문 고객의 할인 금액을 25000원으로 반환하는지 확인")
    @Test
    void getDiscountAmount_WithValidInput() {
        Customer customer = new Customer(
                LocalDateTime.of(2023, 12, CHRISTMAS, HOUR_17, MINUTE_00),
                Map.of(Menu.CHRISTMAS_PASTA, 10)
        );
        DiscountStrategy discountStrategy = ChampagneDiscount.getInstance();

        assertThat(discountStrategy.getDiscountAmount(customer))
                .isEqualTo(-25_000);
    }
}
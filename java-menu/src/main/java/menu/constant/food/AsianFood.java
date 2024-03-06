package menu.constant.food;

import java.util.Arrays;
import java.util.List;

public enum AsianFood {
    PAD_THAI("팟타이"),
    KHAO_PAD("카오 팟"),
    NASI_GORENG("나시고렝"),
    PINEAPPLE_FRIED_RICE("파인애플 볶음밥"),
    RICE_NOODLES("쌀국수"),
    TOM_YUM_GOONG("똠얌꿍"),
    BANH_MI("반미"),
    VIETNAMESE_SSAM("월남쌈"),
    BUN_CHA("분짜");

    private final String name;

    AsianFood(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<String> getMenuList() {
        return Arrays.stream(AsianFood.values())
                .map(AsianFood::getName)
                .toList();
    }
}

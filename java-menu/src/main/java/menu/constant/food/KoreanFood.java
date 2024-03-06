package menu.constant.food;

import java.util.Arrays;
import java.util.List;

public enum KoreanFood {
    GIMBAP("김밥"),
    KIMCHI_STEW("김치찌개"),
    SSAMBAP("쌈밥"),
    SOYBEAN_PASTE_STEW("된장찌개"),
    BIBIMBAP("비빔밥"),
    KALGUKSU("칼국수"),
    BULGOGI("불고기"),
    TTEOKBOKKI("떡볶이"),
    STIR_FRIED_PORK("제육볶음");

    private final String name;

    KoreanFood(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<String> getMenuList() {
        return Arrays.stream(KoreanFood.values())
                .map(KoreanFood::getName)
                .toList();
    }
}

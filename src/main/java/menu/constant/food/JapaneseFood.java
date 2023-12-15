package menu.constant.food;

import java.util.Arrays;
import java.util.List;

public enum JapaneseFood {
    GYUDON("규동"),
    UDON("우동"),
    MISO_SOUP("미소시루"),
    SUSHI("스시"),
    KATSUDON("가츠동"),
    ONIGIRI("오니기리"),
    HASHED_RICE("하이라이스"),
    RAMEN("라멘"),
    OKONOMIYAKI("오코노미야끼");

    private final String name;

    JapaneseFood(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<String> getMenuList() {
        return Arrays.stream(JapaneseFood.values())
                .map(JapaneseFood::getName)
                .toList();
    }
}

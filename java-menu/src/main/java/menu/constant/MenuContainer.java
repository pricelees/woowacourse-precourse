package menu.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import menu.constant.food.AsianFood;
import menu.constant.food.ChineseFood;
import menu.constant.food.JapaneseFood;
import menu.constant.food.KoreanFood;
import menu.constant.food.WesternFood;

public enum MenuContainer {
    JAPANESE_FOOD(1, "일식", JapaneseFood.getMenuList()),
    KOREAN_FOOD(2, "한식", KoreanFood.getMenuList()),
    CHINESE(3, "중식", ChineseFood.getMenuList()),
    ASIAN(4, "아시안", AsianFood.getMenuList()),
    WESTERN(5, "양식", WesternFood.getMenuList());

    private final int categoryNumber;
    private final String categoryName;
    private final List<String> menus;

    MenuContainer(int categoryNumber, String categoryName, List<String> menus) {
        this.categoryNumber = categoryNumber;
        this.categoryName = categoryName;
        this.menus = menus;
    }

    public static MenuContainer getCategoryFromNumber(int number) {
        return Arrays.stream(MenuContainer.values())
                .filter(category -> category.categoryNumber == number)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<String> getMenus() {
        return menus;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public static boolean isNotExistMenu(String name) {
        return menuNameStream().noneMatch(name::equals);
    }

    private static Stream<String> menuNameStream() {
        return Arrays.stream(MenuContainer.values())
                .flatMap(category -> category.getMenus().stream());
    }
}

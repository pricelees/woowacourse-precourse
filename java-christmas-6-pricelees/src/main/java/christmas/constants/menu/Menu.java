package christmas.constants.menu;

import java.util.Arrays;

public enum Menu {
    MUSHROOM_SOUP(MenuCategory.APPETITE, "양송이수프", 6_000),
    TAPAS(MenuCategory.APPETITE, "타파스", 5_500),
    CAESAR_SALAD(MenuCategory.APPETITE, "시저샐러드", 8_000),
    T_BONE_STEAK(MenuCategory.MAIN, "티본스테이크", 55_000),
    BBQ_RIBS(MenuCategory.MAIN, "바비큐립", 54_000),
    SEAFOOD_PASTA(MenuCategory.MAIN, "해산물파스타", 35_000),
    CHRISTMAS_PASTA(MenuCategory.MAIN, "크리스마스파스타", 25_000),
    CHOCOLATE_CAKE(MenuCategory.DESSERT, "초코케이크", 15_000),
    ICE_CREAM(MenuCategory.DESSERT, "아이스크림", 5_000),
    ZERO_COKE(MenuCategory.DRINK, "제로콜라", 3_000),
    RED_WINE(MenuCategory.DRINK, "레드와인", 60_000),
    CHAMPAGNE(MenuCategory.DRINK, "샴페인", 25_000);

    private final MenuCategory category;
    private final String koreanName;
    private final int price;

    Menu(MenuCategory category, String koreanName, int price) {
        this.category = category;
        this.koreanName = koreanName;
        this.price = price;
    }

    public static Menu findMenuFromKoreanName(String menuName) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menuName.equals(menu.koreanName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean isSameCategory(MenuCategory other) {
        return category.equals(other);
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return koreanName;
    }
}
